/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.db.mappers;

import org.jetbrains.annotations.NotNull;
import org.solovyev.study.model.data_object.DataObject;
import org.solovyev.study.model.db.tables.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * User: serso
 * Date: Apr 19, 2010
 * Time: 12:57:45 AM
 */
public class DataObjectMapper {

	@NotNull
	public static DataObject mapRow(@NotNull DataObject dataObject, @NotNull ResultSet rs) throws SQLException {

		if (rs.getObject(users.creation_date.name()) != null) {
			dataObject.setCreationDate(new Date(rs.getTimestamp(users.creation_date.name()).getTime()));
		}

		dataObject.setCreatorId(rs.getInt(users.creator_id.name()));

		if (rs.getObject(users.modification_date.name()) != null) {
			dataObject.setModificationDate(new Date(rs.getTimestamp(users.modification_date.name()).getTime()));
		}

		return dataObject;
	}
}
