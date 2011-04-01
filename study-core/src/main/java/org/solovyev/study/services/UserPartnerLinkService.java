/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.services;

import org.jetbrains.annotations.NotNull;
import org.solovyev.study.model.partner.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.solovyev.study.model.PartnerSearchParams;
import org.solovyev.study.model.User;
import org.solovyev.study.model.UserSearchParams;
import org.solovyev.study.model.db.SQLBuilder;
import org.solovyev.study.model.db.Tables;
import org.solovyev.study.model.db.mappers.IntegerMapper;
import org.solovyev.study.model.db.tables.users_partners_link;
import org.solovyev.study.resources.Config;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: May 4, 2010
 * Time: 12:37:02 AM
 */

@Service
public class UserPartnerLinkService {

	private PartnerService partnerService;

	@Autowired
	public void setPartnerService(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	/**
	 * Method loads partners linked to user with current user id
	 *
	 * @param userId     user id
	 * @param dataSource data source
	 * @return list of linked partners. Never null.
	 */
	@NotNull
	public List<Partner> loadLinkedPartners(@NotNull Integer userId, @NotNull DataSource dataSource) {
		final List<Partner> linkedPartners = new ArrayList<Partner>();

		//prepare sql
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		SQLBuilder sqlBuilder = new SQLBuilder().select().columns("upl", users_partners_link.values()).from().tables(Config.DATABASE_SCHEMA, Tables.users_partners_link.name(), "upl").where();
		sqlBuilder.equalsCondition("upl", users_partners_link.user_id.name(), userId, sqlParameterSource);

		//execute
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		List<Integer> ids = jdbcTemplate.query(sqlBuilder.toString(), new IntegerMapper(users_partners_link.partner_id.name()), sqlParameterSource);

		PartnerSearchParams partnerSearchParams = new PartnerSearchParams(true);
		partnerSearchParams.setFullLoad(false);
		for (Integer id : ids) {
			//for each id - load partner  and add to linked partners' list
			partnerSearchParams.setId(id);
			linkedPartners.add(partnerService.loadPartner(partnerSearchParams, dataSource));
		}

		return linkedPartners;
	}

	/**
	 * Method loads userslinked to partner with current partner id
	 *
	 * @param partnerId  partner id
	 * @param dataSource data source
	 * @return list of linked users. Never null.
	 */
	@NotNull
	public static List<User> loadLinkedUsers(@NotNull Integer partnerId, @NotNull DataSource dataSource) {
		final List<User> linkedUsers = new ArrayList<User>();

		//prepare sql
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		SQLBuilder sqlBuilder = new SQLBuilder().select().columns("upl", users_partners_link.values()).from().tables(Config.DATABASE_SCHEMA, Tables.users_partners_link.name(), "upl").where();
		sqlBuilder.equalsCondition("upl", users_partners_link.partner_id.name(), partnerId, sqlParameterSource);

		//execute
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		List<Integer> ids = jdbcTemplate.query(sqlBuilder.toString(), new IntegerMapper(users_partners_link.user_id.name()), sqlParameterSource);

		UserSearchParams userSearchParams = new UserSearchParams();
		userSearchParams.setFullLoad(false);
		for (Integer id : ids) {
			//for each id - load user  and add to linked users' list
			userSearchParams.setId(id);
			linkedUsers.add(UserService.loadUser(userSearchParams, dataSource));
		}

		return linkedUsers;
	}

	public static void insertLinkedPartners(@NotNull User user, @NotNull DataSource dataSource) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).withSchemaName(Config.DATABASE_SCHEMA).
				withTableName(Tables.users_partners_link.name()).
				usingColumns(users_partners_link.user_id.name(), users_partners_link.partner_id.name());

		//prepare sql parameters
		MapSqlParameterSource parameters;

		for (Partner partner : user.getLinkedPartners()) {
			parameters = new MapSqlParameterSource();
			parameters.addValue(users_partners_link.user_id.name(), user.getId());
			parameters.addValue(users_partners_link.partner_id.name(), partner.getId());
			jdbcInsert.execute(parameters);
		}
	}

	public static void deletelLinkedPartners(@NotNull Integer userId, @NotNull DataSource dataSource) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		SQLBuilder deleteSql = new SQLBuilder().delete().from().tables(Config.DATABASE_SCHEMA, Tables.users_partners_link.name()).where();
		deleteSql.equalsCondition(null, users_partners_link.user_id.name(), userId, parameterSource);

		new SimpleJdbcTemplate(dataSource).update(deleteSql.toString(), parameterSource);
	}

	public static void updateLinkedPartners(@NotNull User user, @NotNull DataSource dataSource) {
		deletelLinkedPartners(user.getId(), dataSource);
		insertLinkedPartners(user, dataSource);
	}
}