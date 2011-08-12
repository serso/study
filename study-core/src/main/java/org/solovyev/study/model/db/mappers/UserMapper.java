/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.db.mappers;

import org.solovyev.study.model.user.User;
import org.solovyev.study.model.db.tables.users;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
* User: serso
* Date: Apr 5, 2010
* Time: 2:41:53 AM
*/
public final class UserMapper implements ParameterizedRowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs.getInt(users.user_id.name()), rs.getString(users.username.name()), rs.getString(users.password.name()), rs.getString(users.email.name()));

		DataObjectMapper.mapRow(user, rs);

		user.setEnabled(rs.getBoolean(users.enabled.name()));

		return user;
	}
}
