/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.services;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.solovyev.study.model.User;
import org.solovyev.study.model.UserRole;
import org.solovyev.study.model.db.SQLBuilder;
import org.solovyev.study.model.db.Tables;
import org.solovyev.study.model.db.mappers.EnumMapper;
import org.solovyev.study.model.db.tables.users_user_roles_link;
import org.solovyev.study.resources.Config;

import javax.sql.DataSource;
import java.util.List;

/**
 * User: serso
 * Date: Apr 5, 2010
 * Time: 2:27:27 AM
 */

@Service
public class UserRoleService {
	
	public static void delete(Integer userId, DataSource dataSource) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		//first prepare delete sql
		SQLBuilder sqlBuilder = new SQLBuilder().delete().from().tables(Config.DATABASE_SCHEMA, Tables.users_user_roles_link.name()).where();
		sqlBuilder.equalsCondition(null, users_user_roles_link.user_id.name(), userId, sqlParameterSource);

		//do delete
		new SimpleJdbcTemplate(dataSource).update(sqlBuilder.toString(), sqlParameterSource);
	}

	public static void insert(User user, DataSource dataSource) {
		//prepare insert user roles command
		SimpleJdbcInsert insertUserRole = new SimpleJdbcInsert(dataSource).withSchemaName(Config.DATABASE_SCHEMA).
				withTableName(Tables.users_user_roles_link.name()).usingColumns(users_user_roles_link.user_id.name(), users_user_roles_link.user_role.name());

		//for each user role make insert
		for (UserRole userRole : user.getUserRoles()) {
			//prepare sql parameters and insert
			insertUserRole.execute(new MapSqlParameterSource().addValue(users_user_roles_link.user_id.name(), user.getId()).addValue(users_user_roles_link.user_role.name(), userRole.name()));
		}
	}

	public static void update(User user, DataSource dataSource) {
		//delete user roles
		delete(user.getId(), dataSource);

		//now insert new user roles
		insert(user, dataSource);
	}

	public static List<UserRole> load(Integer userId, DataSource dataSource) {
		String sql = "select user_role from " + Config.DATABASE_SCHEMA + "." +Tables.users_user_roles_link + " uurl where uurl.user_id = :user_id ";
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		return jdbcTemplate.query(sql, new EnumMapper<UserRole>(UserRole.class, "user_role"), new MapSqlParameterSource("user_id", userId));
	}
}