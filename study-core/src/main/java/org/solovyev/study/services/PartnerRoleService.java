/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.solovyev.study.model.Partner;
import org.solovyev.study.model.PartnerRole;
import org.solovyev.study.model.db.SQLBuilder;
import org.solovyev.study.model.db.Tables;
import org.solovyev.study.model.db.mappers.EnumMapper;
import org.solovyev.study.model.db.tables.partners_partner_roles_link;
import org.solovyev.study.resources.Config;

import javax.sql.DataSource;
import java.util.List;

/**
 * User: serso
 * Date: Apr 19, 2010
 * Time: 1:06:56 AM
 */

@Service
public class PartnerRoleService {

	public static List<PartnerRole> load(Integer partnerId, DataSource dataSource) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		SQLBuilder sqlBuilder = new SQLBuilder().select().columns("pprl", partners_partner_roles_link.values()).from().tables(Config.DATABASE_SCHEMA, Tables.partners_partner_roles_link.name(), "pprl").where();
		sqlBuilder.equalsCondition("pprl", partners_partner_roles_link.partner_id.name(), partnerId, mapSqlParameterSource);

		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		return jdbcTemplate.query(sqlBuilder.toString(), new EnumMapper<PartnerRole>(PartnerRole.class, partners_partner_roles_link.partner_role.name()), mapSqlParameterSource);
	}


	public static void insert(Partner partner, DataSource dataSource) {
		//prepare insert partner roles command
		SimpleJdbcInsert insertUserRole = new SimpleJdbcInsert(dataSource).withSchemaName(Config.DATABASE_SCHEMA).
				withTableName(Tables.partners_partner_roles_link.name()).usingColumns(partners_partner_roles_link.partner_id.name(), partners_partner_roles_link.partner_role.name());

		//for each partner role make insert
		for (PartnerRole partnerRole : partner.getPartnerRoles()) {
			//prepare sql parameters and insert
			insertUserRole.execute(new MapSqlParameterSource().addValue(partners_partner_roles_link.partner_id.name(), partner.getId()).addValue(partners_partner_roles_link.partner_role.name(), partnerRole.name()));
		}
	}

	public static void delete(@NotNull Integer partnerId, @NotNull DataSource dataSource) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		//first prepare delete sql
		SQLBuilder sqlBuilder = new SQLBuilder().delete().from().tables(Config.DATABASE_SCHEMA, Tables.partners_partner_roles_link.name()).where();
		sqlBuilder.equalsCondition(Tables.partners_partner_roles_link.name(), partners_partner_roles_link.partner_id.name(), partnerId, sqlParameterSource);
		//do delete
		new SimpleJdbcTemplate(dataSource).update(sqlBuilder.toString(), sqlParameterSource);
	}

	public static void update(@NotNull Partner partner, @NotNull DataSource dataSource) {
		//first delete all existing
		delete(partner.getId(), dataSource);

		//then add chosen
		insert(partner, dataSource);
	}
}
