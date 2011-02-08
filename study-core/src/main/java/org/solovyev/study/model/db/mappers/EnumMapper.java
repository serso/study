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
* Date: Apr 5, 2010
* Time: 2:01:11 AM
*/
public class EnumMapper<T extends Enum<T>> implements ParameterizedRowMapper<T> {

	private Class<T> clazz;
	private String fieldName;

	public EnumMapper(Class<T> clazz, String fieldName) {
		this.clazz = clazz;
		this.fieldName = fieldName;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Enum.valueOf(clazz, rs.getString(fieldName));
	}
}
