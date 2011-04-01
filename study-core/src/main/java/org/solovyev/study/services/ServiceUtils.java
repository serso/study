/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.solovyev.common.definitions.Property;
import org.solovyev.study.resources.Config;

import javax.sql.DataSource;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 8:25:28 PM
 */
public class ServiceUtils {

	public static boolean checkAvailabilityOfUniqueColumn(@NotNull Property<?, String> notMe, @NotNull Property<?, String> requestedValue, String tableName, DataSource dataSource) {
		boolean result = false;

		if (requestedValue.getValue() != null) {
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			MapSqlParameterSource parameters = new MapSqlParameterSource(requestedValue.getId(), requestedValue.getValue());

			String sql = "select count(*) from " + Config.DATABASE_SCHEMA + "." + tableName + " where ";
			sql += requestedValue.getId() + " = :" + requestedValue.getId() + " ";

			if (notMe.getValue() != null ) {
				sql += "and " + notMe.getId() + " <> :" + notMe.getId() + " ";
				parameters.addValue(notMe.getId(), notMe.getValue());
			}

			Integer count = jdbcTemplate.queryForObject(sql, parameters, Integer.class);
			if (count.equals(0)) {
				result = true;
			}
		}

		return result;
	}
}