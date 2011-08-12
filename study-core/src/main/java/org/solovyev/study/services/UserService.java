/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.CollectionsUtils;
import org.solovyev.common.utils.StringsUtils;
import org.solovyev.study.model.user.User;
import org.solovyev.study.model.user.UserSearchParams;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.solovyev.common.definitions.MessageImpl;
import org.solovyev.common.definitions.MessageType;
import org.solovyev.common.definitions.Property;
import org.solovyev.study.exceptions.DatabaseException;
import org.solovyev.study.model.db.SQLBuilder;
import org.solovyev.study.model.db.Tables;
import org.solovyev.study.model.db.mappers.UserMapper;
import org.solovyev.study.model.db.tables.users;
import org.solovyev.study.model.db.tables.users_user_roles_link;
import org.solovyev.study.resources.Config;
import org.solovyev.study.resources.MessageCodes;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * User: serso
 * Date: Mar 20, 2010
 * Time: 12:05:50 AM
 */

@Service
public class UserService {

	@Nullable
	public static User delete(@NotNull Integer userId, @NotNull DataSource dataSource) throws DatabaseException {
		User user = loadUser(new UserSearchParams(userId), dataSource);
		if (user != null) {

			if (checkAvailabilityToDelete(user, dataSource)) {
				//first prepare delete sql
				MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("user_id", userId);
				SQLBuilder deleteSql = new SQLBuilder().delete().from().tables(Config.DATABASE_SCHEMA, Tables.users.name()).where();
				deleteSql.equalsCondition(null, users.user_id.name(), userId, sqlParameterSource);
				//do delete
				new SimpleJdbcTemplate(dataSource).update(deleteSql.toString(), sqlParameterSource);
			} else {
				throw new DatabaseException(new MessageImpl(MessageCodes.msg0009.name(), MessageType.error, user.getUsername()));
			}
		}
		return user;
	}

	private static boolean checkAvailabilityToDelete(User user, DataSource dataSource) {
		boolean result = false;
		String sql = "select count(*) from " + Config.DATABASE_SCHEMA + "." + Tables.users + " u where u.creator_id = :creator_id ";

		Integer count = new NamedParameterJdbcTemplate(dataSource).queryForObject(sql, new MapSqlParameterSource("creator_id", user.getId()), Integer.class);
		if (count.equals(0)) {
			result = true;
		}

		return result;
	}

	@Nullable
	public static User loadUser(@NotNull UserSearchParams userSearchParams, @NotNull DataSource dataSource) {
		User result = null;
		List<User> users = loadUsers(userSearchParams, dataSource);
		if (users.size() > 0) {
			result = users.get(0);
		}
		return result;
	}

	@NotNull
	public static List<User> loadUsers(@NotNull UserSearchParams userSearchParams, @NotNull DataSource dataSource) {
		SQLBuilder sqlBuilder = new SQLBuilder().select().columns("u", users.values()).from().tables(Config.DATABASE_SCHEMA, Tables.users.name(), "u").where();

		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		if (userSearchParams.getId() != null) {
			sqlBuilder.equalsCondition(true, "u", users.user_id.name(), userSearchParams.getId(), sqlParameterSource);
		}

		if (StringsUtils.notEmpty(userSearchParams.getUsername())) {
			sqlBuilder.equalsCondition(userSearchParams.isStrictSearch(), "u", users.username.name(), userSearchParams.getUsername(), sqlParameterSource);
		}

		if (StringsUtils.notEmpty(userSearchParams.getEmail())) {
			sqlBuilder.equalsCondition(userSearchParams.isStrictSearch(), "u", users.email.name(), userSearchParams.getEmail(), sqlParameterSource);
		}

		if (CollectionsUtils.notEmpty(userSearchParams.getUserRoles())) {
			sqlBuilder.exists();
			sqlBuilder.getSql().append("( ");

			SQLBuilder innerSql = new SQLBuilder();
			innerSql.select().allColumns().from().tables(Config.DATABASE_SCHEMA, Tables.users_user_roles_link.name(), "uurl").where();
			innerSql.equalsCondition("uurl", users_user_roles_link.user_id.name(), "u", users.user_id.name());
			innerSql.in("uurl", users_user_roles_link.user_role.name(), userSearchParams.getUserRoles(), sqlParameterSource, "empty");


			sqlBuilder.append(innerSql);
			sqlBuilder.getSql().append(") ");
		}

		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		List<User> users = jdbcTemplate.query(sqlBuilder.toString(), new UserMapper(), sqlParameterSource);

		for (User user : users) {
			user.getUserRoles().addAll(UserRoleService.load(user.getId(), dataSource));
			if (userSearchParams.isFullLoad()) {
				// todo serso: check
				//user.getLinkedPartners().addAll(UserPartnerLinkService.loadLinkedPartners(user.getId(), dataSource));
			}
		}

		return users;
	}

	public static User insert(User user, DataSource dataSource) {
		//create insert user command
		SimpleJdbcInsert insertUser = new SimpleJdbcInsert(dataSource).withSchemaName(Config.DATABASE_SCHEMA).
				withTableName(Tables.users.name()).usingGeneratedKeyColumns(users.user_id.name()).
				usingColumns(users.username.name(), users.password.name(), users.email.name(), users.creation_date.name(), users.creator_id.name(), users.modification_date.name(), users.enabled.name());

		//then encrypt user password (as it come from form not encrypted)
		encryptUserPassword(user);

		user.setCreationDate(new Date());

		//prepare sql parameters
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue(users.username.name(), user.getUsername());
		parameters.addValue(users.password.name(), user.getPassword());
		parameters.addValue(users.email.name(), user.getEmail());
		parameters.addValue(users.creation_date.name(), user.getCreationDate());
		parameters.addValue(users.creator_id.name(), user.getCreatorId());
		parameters.addValue(users.modification_date.name(), user.getModificationDate());
		parameters.addValue(users.enabled.name(), String.valueOf(user.isEnabled()));

		//execute and get id
		Number newId = insertUser.executeAndReturnKey(parameters);
		//set user id
		user.setId(newId.intValue());

		//now insert user roles
		UserRoleService.insert(user, dataSource);

		//insert linked partners
		UserPartnerLinkService.insertLinkedPartners(user, dataSource);

		return user;
	}

	private static void encryptUserPassword(User user) {
		user.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(), user.getUsername()));
	}

	public static void update(User user, DataSource dataSource) {
		//prepare update sql
		String sql = "update " + Config.DATABASE_SCHEMA + "." + Tables.users.name() + " " +
				"set " + users.username.name() + " = :username, " + users.email.name() + " = :email, " + users.modification_date.name() + " = :modification_date, " +
				users.enabled.name() + " = :enabled ";

		user.setModificationDate(new Date());

		//prepare sql parameters
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		sqlParameterSource.addValue("username", user.getUsername());
		sqlParameterSource.addValue("email", user.getEmail());
		sqlParameterSource.addValue("modification_date", user.getModificationDate());
		sqlParameterSource.addValue("enabled", String.valueOf(user.isEnabled()));

		if (!user.isDoNotChangePassword()) {
			//only if password can be changed (and password must be not null)
			encryptUserPassword(user);
			sql += ", ";
			sql += "password = :password ";
			sqlParameterSource.addValue("password", user.getPassword());
		}

		//set where part
		sql += "where user_id = :user_id ";
		sqlParameterSource.addValue("user_id", user.getId());

		//create template and execute
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		jdbcTemplate.update(sql, sqlParameterSource);

		UserRoleService.update(user, dataSource);
		UserPartnerLinkService.updateLinkedPartners(user, dataSource);
	}

	public static boolean checkAvailabilityOfUsername(User user, DataSource dataSource) {
		return ServiceUtils.checkAvailabilityOfUniqueColumn(
				Property.create(user.getId(), users.user_id.name()),
				Property.create(user.getUsername(), users.username.name()),
				Tables.users.name(), dataSource);
	}


	public static boolean checkAvailabilityOfEmail(User user, DataSource dataSource) {
		return ServiceUtils.checkAvailabilityOfUniqueColumn(
				Property.create(user.getId(), users.user_id.name()),
				Property.create(user.getEmail(), users.email.name()),
				Tables.users.name(), dataSource);
	}
}