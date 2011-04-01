/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.services;

import org.jetbrains.annotations.NotNull;
import org.solovyev.study.model.partner.Partner;
import org.solovyev.study.model.partner.PartnerRole;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.solovyev.study.model.SchoolDetails;
import org.solovyev.study.model.db.SQLBuilder;
import org.solovyev.study.model.db.Tables;
import org.solovyev.study.model.db.tables.school_details;
import org.solovyev.study.resources.Config;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: serso
 * Date: May 7, 2010
 * Time: 11:56:03 PM
 */

@Service
public class PartnerDetailsService {

	public static void load(@NotNull Partner partner, @NotNull DataSource dataSource) {
		for (PartnerRole partnerRole : partner.getPartnerRoles()) {
			switch (partnerRole) {
				case student:
					break;
				case teacher:
					break;
				case school_employee:
					break;
				case school:
					loadSchoolDetails(partner, dataSource);
					break;
				case institute:
					break;
				case university:
					break;
			}
		}
	}

	private static void loadSchoolDetails(@NotNull Partner partner, @NotNull DataSource dataSource) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		SQLBuilder sqlBuilder = new SQLBuilder().select().columns("sd", school_details.values()).from().tables(Config.DATABASE_SCHEMA, Tables.school_details.name(), "sd").where();
		sqlBuilder.equalsCondition("sd", school_details.school_id.name(), partner.getId(), sqlParameterSource);

		List<SchoolDetails> schoolDetails = new SimpleJdbcTemplate(dataSource).query(sqlBuilder.toString(), new SchoolDetailsRowMapper(), sqlParameterSource);

		if (!schoolDetails.isEmpty()) {
			partner.getDetails().put(PartnerRole.school, schoolDetails.get(0));
		}
	}

	private static class SchoolDetailsRowMapper implements ParameterizedRowMapper<SchoolDetails> {

		@Override
		public SchoolDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			SchoolDetails schoolDetails = new SchoolDetails();

			if (rs.getObject(school_details.school_number.name()) != null) {
				schoolDetails.setSchoolNumber(rs.getInt(school_details.school_number.name()));
			}

			return schoolDetails;
		}
	}

	public static void update(@NotNull Partner partner, @NotNull DataSource dataSource) {
		for (PartnerRole partnerRole : partner.getPartnerRoles()) {
			if (partner.getDetails().get(partnerRole) != null) {
				switch (partnerRole) {
					case student:
						break;
					case teacher:
						break;
					case school_employee:
						break;
					case school:
						updateSchoolDetails(partner, dataSource);
						break;
					case institute:
						break;
					case university:
						break;
				}
			}
		}
	}

	public static void updateSchoolDetails(@NotNull Partner partner, @NotNull DataSource dataSource) {
		SchoolDetails schoolDetails = (SchoolDetails) partner.getDetails().get(PartnerRole.school);
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		SQLBuilder sqlBuilder = new SQLBuilder().update().tables(Config.DATABASE_SCHEMA, Tables.school_details.name(), "sd").set();
		sqlBuilder.equalsConditionForUpdate("sd", school_details.school_number.name(), schoolDetails.getSchoolNumber(), sqlParameterSource);
		sqlBuilder.where().equalsCondition("sd", school_details.school_id.name(), partner.getId(), sqlParameterSource);
		new SimpleJdbcTemplate(dataSource).update(sqlBuilder.toString(), sqlParameterSource);
	}

	public static void insert(@NotNull Partner partner, @NotNull DataSource dataSource) {
		for (PartnerRole partnerRole : partner.getPartnerRoles()) {
			switch (partnerRole) {
				case student:
					break;
				case teacher:
					break;
				case school_employee:
					break;
				case school:
					insertSchoolDetails(partner, dataSource);
					break;
				case institute:
					break;
				case university:
					break;
			}
		}
	}

	private static void insertSchoolDetails(@NotNull Partner partner, @NotNull DataSource dataSource) {
		SchoolDetails schoolDetails = (SchoolDetails) partner.getDetails().get(PartnerRole.school);
		if (schoolDetails == null) {
			schoolDetails = new SchoolDetails();
			partner.getDetails().put(PartnerRole.school, schoolDetails);
		}
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		SQLBuilder sqlBuilder = new SQLBuilder().insertInto().tables(Config.DATABASE_SCHEMA, Tables.school_details.name()).values();
		sqlBuilder.append("( ");
		sqlBuilder.addValue(partner.getId(), sqlParameterSource).comma();
		sqlBuilder.addValue(schoolDetails.getSchoolNumber(), sqlParameterSource);
		sqlBuilder.append(") ");				
		new SimpleJdbcTemplate(dataSource).update(sqlBuilder.toString(), sqlParameterSource);
	}
}