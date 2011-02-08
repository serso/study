/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.db.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: serso
 * Date: May 3, 2010
 * Time: 10:11:49 PM
 */
public class IntegerMapper implements ParameterizedRowMapper<Integer> {

	private final String columnName;

	public IntegerMapper(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer integer = null;

		if (rs.getObject(columnName) != null) {
			integer = rs.getInt(columnName);
		}

		return integer;
	}
}
