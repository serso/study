/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.solovyev.common.utils.LoopData;

import java.util.Collection;

/**
 * User: serso
 * Date: Apr 5, 2010
 * Time: 2:55:50 AM
 */
public class SQLBuilder {

	private StringBuilder sql = new StringBuilder();
	private int count = 0;

	public SQLBuilder() {
	}

	public SQLBuilder in(@NotNull String tableAlias, @NotNull String columnName, @NotNull Collection c, @NotNull MapSqlParameterSource sqlParameterSource, @NotNull String emptyValue) {
		sql.append("and").append(" ").append(tableAlias).append(".").append(columnName).append(" ");
		sql.append("in ('").append(emptyValue).append("'");
		String el;
		LoopData ld = new LoopData(c);
		for (Object o : c) {
			sql.append(", ");
			el = columnName + "_" + ld.getIndex();
			sql.append(":").append(el);
			sqlParameterSource.addValue(el, String.valueOf(o));
			ld.next();
		}
		sql.append(") ");
		return this;
	}

	public final static String escape = "\\";

	public SQLBuilder like(@NotNull String field, @NotNull String valueName, boolean ignoreCase) {
		sql.append(" ");
		if (ignoreCase) {
			sql.append("upper(").append(field).append(") ");
		} else {
			sql.append(field).append(" ");
		}
		sql.append("like ");

		if (ignoreCase) {
			sql.append("upper(:").append(valueName).append(") ");
		} else {
			sql.append(":").append(valueName).append(" ");
		}
		sql.append("escape \'" + escape + escape + "\' ");
		return this;
	}

	@NotNull
	public static String prepareLikeValue(@NotNull String source) {
		final StringBuilder sb = new StringBuilder();

		//if source string doesn't start with wildcard - let's add it to result string
		if (!source.startsWith("*") && !source.startsWith("?")) {
			sb.append("%");
		}

		//process screen and replacing human-oriented wildcard with sql wildcard
		sb.append(screen(source).replace('?', '_').replace('*', '%'));

		//if source string doesn't end with wildcard - let's add it to result string
		if (!source.endsWith("*") && !source.endsWith("?")) {
			sb.append("%");
		}

		return sb.toString();
	}

	@NotNull
	public static String screen(@NotNull String source) {
		return screen(screen(source, "_", escape), "%", escape);
	}

	@NotNull
	private static String screen(@NotNull String source, @NotNull String ch, @NotNull String shield) {
		String[] parts = source.split(ch, -1);
		StringBuilder sb = new StringBuilder();
		if (parts != null) {
			LoopData loopData = new LoopData(parts);
			for (String part : parts) {
				if (!loopData.isFirstAndNext()) {
					sb.append(shield).append(ch).append(part);
				} else {
					sb.append(part);
				}
			}
		}
		return sb.toString();
	}

	@NotNull
	public SQLBuilder columns(@Nullable String tableAlias, @NotNull Enum[] enumeration) {
		LoopData ld = new LoopData(enumeration);
		for (Enum anEnum : enumeration) {
			if (tableAlias != null) {
				sql.append(tableAlias).append(".");
			}
			sql.append(anEnum);
			if (!ld.isLastAndNext()) {
				sql.append(", ");
			} else {
				sql.append(" ");
			}
		}

		return this;
	}

	@NotNull
	public SQLBuilder tables(@NotNull String databaseName, @NotNull String tableName) {
		return tables(databaseName, tableName, null);
	}

	@NotNull
	public SQLBuilder tables(@NotNull String databaseName, @NotNull String tableName, @Nullable String tableAlias) {
		sql.append(databaseName).append(".").append(tableName).append(" ");
		if (tableAlias != null) {
			sql.append(tableAlias).append(" ");
		}
		return this;
	}

	@NotNull
	public SQLBuilder delete() {
		sql.append("delete ");
		return this;
	}

	@NotNull
	public SQLBuilder select() {
		sql.append("select ");
		return this;
	}

	@NotNull
	public SQLBuilder where() {
		sql.append("where 1=1 ");
		return this;
	}

	@NotNull
	public SQLBuilder from() {
		sql.append("from ");
		return this;
	}

	@NotNull
	public SQLBuilder exists() {
		sql.append("and exists ");
		return this;
	}

	@NotNull
	public SQLBuilder equalsCondition(@Nullable String tableAlias, @NotNull String column, @Nullable Object parameter, @NotNull MapSqlParameterSource sqlParameterSource) {
		return equalsCondition(true, tableAlias, column, parameter, sqlParameterSource);
	}

	@NotNull
	public SQLBuilder equalsCondition(boolean strict, @Nullable String tableAlias, @NotNull String column, @Nullable Object parameter, @NotNull MapSqlParameterSource sqlParameterSource) {
		sql.append("and ");
		return commonEqualsCondition(strict, tableAlias, column, parameter, sqlParameterSource);
	}

	@NotNull
	public SQLBuilder equalsConditionForUpdate(@Nullable String tableAlias, @NotNull String column, @Nullable Object parameter, @NotNull MapSqlParameterSource sqlParameterSource) {
		return commonEqualsCondition(true, tableAlias, column, parameter, sqlParameterSource);
	}

	private SQLBuilder commonEqualsCondition(boolean strict, @Nullable String tableAlias, @NotNull String column, @Nullable Object parameter, @NotNull MapSqlParameterSource sqlParameterSource) {
		if (strict) {
			if (tableAlias != null) {
				sql.append(tableAlias).append(".");
			}
			sql.append(column).append(" = :").append(column).append(" ");
			sqlParameterSource.addValue(column, parameter);
		} else {
			if (tableAlias != null) {
				sql.append(tableAlias).append(".");
			}
			sql.append(column).append(" like :").append(column).append(" ");
			if (parameter != null) {
				sqlParameterSource.addValue(column, SQLBuilder.prepareLikeValue(String.valueOf(parameter)));
			} else {
				sqlParameterSource.addValue(column, parameter);
			}
		}
		return this;
	}

	public SQLBuilder addUpdatedValue(@Nullable String tableAlias, @NotNull String column, @Nullable Object parameter, @NotNull MapSqlParameterSource sqlParameterSource) {
		if (tableAlias != null) {
			sql.append(tableAlias).append(".");
		}
		sql.append(column).append(" = :").append(column);
		sqlParameterSource.addValue(column, parameter);
		return this;
	}

	public SQLBuilder append(@Nullable String s) {
		if (s != null) {
			sql.append(s);
		}
		return this;
	}

	public SQLBuilder append(@Nullable SQLBuilder sqlBuilder) {
		if (sqlBuilder != null) {
			sql.append(sqlBuilder.toString());
		}
		return this;
	}

	@NotNull
	public SQLBuilder allColumns() {
		sql.append("* ");
		return this;
	}

	@NotNull
	public SQLBuilder equalsCondition(@NotNull String leftTableAlias, @NotNull String leftColumn, @NotNull String rightTableAlias, @NotNull String rightColumn) {
		sql.append("and ").append(leftTableAlias).append(".").append(leftColumn).append("=").append(rightTableAlias).append(".").append(rightColumn).append(" ");
		return this;
	}

	@NotNull
	public StringBuilder getSql() {
		return sql;
	}

	@NotNull
	@Override
	public String toString() {
		return sql.toString();
	}

	public SQLBuilder update() {
		sql.append("update ");
		return this;
	}

	public SQLBuilder set() {
		sql.append("set ");
		return this;
	}

	public SQLBuilder comma() {
		sql.append(", ");
		return this;
	}

	public SQLBuilder insertInto() {
		sql.append("insert into ");
		return this;
	}

	public SQLBuilder values() {
		sql.append("values ");
		return this;
	}

	public SQLBuilder addValue(@Nullable Object parameter, @NotNull MapSqlParameterSource sqlParameterSource) {
		String paramName = "parameter_" + getCount();
		sql.append(":").append(paramName).append(" ");
		sqlParameterSource.addValue(paramName, parameter);
		return this;
	}

	public int getCount() {
		return count++;
	}
}
