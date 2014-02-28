/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.collections.Collections;
import org.solovyev.common.text.Strings;
import org.solovyev.study.model.partner.LegalPerson;
import org.solovyev.study.model.partner.NaturalPerson;
import org.solovyev.study.model.partner.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.solovyev.study.model.*;
import org.solovyev.study.model.db.SQLBuilder;
import org.solovyev.study.model.db.Tables;
import org.solovyev.study.model.db.mappers.DataObjectMapper;
import org.solovyev.study.model.db.tables.addresses;
import org.solovyev.study.model.db.tables.partners;
import org.solovyev.study.model.db.tables.partners_partner_roles_link;
import org.solovyev.study.resources.Config;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 8:18:14 PM
 */
@Service
public class PartnerService {

	private AddressService addressService;

	@Autowired
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	/**
	 * Method deletes partner from system.
	 *
	 * @param partnerId  unique partner identifier
	 * @param dataSource data source
	 * @return Deleted user. If no user was deleted - returnes null.
	 */
	@Nullable
	public Partner delete(@NotNull Integer partnerId, @NotNull DataSource dataSource) {
		//load partner by id to check the existence
		Partner partner = loadPartner(new PartnerSearchParams(partnerId), dataSource);

		//if partner exists
		if (partner != null) {
			MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
			//first prepare delete sql
			SQLBuilder sqlBuilder = new SQLBuilder().delete().from().tables(Config.DATABASE_SCHEMA, Tables.partners.name()).where();
			sqlBuilder.equalsCondition(true, null, partners.partner_id.name(), partnerId, sqlParameterSource);
			//do delete
			new SimpleJdbcTemplate(dataSource).update(sqlBuilder.toString(), sqlParameterSource);
		}

		return partner;
	}

	/**
	 * Method inserts new partner in database and updates current according
	 * to changes while inserting (e.g. identifier)
	 *
	 * @param partner    prepared and fullfilled partner object
	 * @param dataSource data source
	 */
	public void insert(@NotNull Partner partner, DataSource dataSource) {

		//create insert command
		SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withSchemaName(Config.DATABASE_SCHEMA).
				withTableName(Tables.partners.name()).usingGeneratedKeyColumns(partners.partner_id.name()).
				usingColumns(
						//natural person
						partners.first_name.name(), partners.last_name.name(),
						partners.birthdate.name(),
						partners.gender.name(),
						//legal person
						partners.company_name.name(), partners.incorporation_date.name(),
						//common
						partners.partner_type.name(),
						partners.creator_id.name(), partners.modification_date.name(), partners.creation_date.name());

		partner.setCreationDate(new Date());

		//prepare sql parameters (they are wired via bean property source)
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		setCommonPartnerParameters(partner, parameters);
		if (partner.isNaturalPerson()) {
			setParametersForPerson((NaturalPerson) partner, parameters);
		} else {
			setParametersForPerson((LegalPerson) partner, parameters);
		}

		//execute and get id
		Number newId = insert.executeAndReturnKey(parameters);
		//set user id
		partner.setId(newId.intValue());

		//insert partner roles
		PartnerRoleService.insert(partner, dataSource);

		PartnerDetailsService.insert(partner, dataSource);

		addressService.update(partner, dataSource);
	}

	/**
	 * Method sets sql parameters for legal person
	 *
	 * @param partner    legal person
	 * @param parameters parameters source
	 */
	private static void setParametersForPerson(LegalPerson partner, MapSqlParameterSource parameters) {
		parameters.addValue(partners.first_name.name(), null);
		parameters.addValue(partners.last_name.name(), null);
		parameters.addValue(partners.birthdate.name(), null);
		parameters.addValue(partners.gender.name(), null);
		parameters.addValue(partners.company_name.name(), partner.getCompanyName());
		parameters.addValue(partners.incorporation_date.name(), partner.getIncorporationDate());
	}

	/**
	 * Method sets sql parameters for natural person
	 *
	 * @param partner    natural person
	 * @param parameters parameters source
	 */
	private static void setParametersForPerson(NaturalPerson partner, MapSqlParameterSource parameters) {
		parameters.addValue(partners.first_name.name(), partner.getFirstName());
		parameters.addValue(partners.last_name.name(), partner.getLastName());
		parameters.addValue(partners.birthdate.name(), partner.getBirthdate());
		parameters.addValue(partners.gender.name(), partner.getGender().name());
		parameters.addValue(partners.company_name.name(), null);
		parameters.addValue(partners.incorporation_date.name(), null);
	}

	/**
	 * Method sets common sql parameters for both legal and natural person
	 *
	 * @param partner    partner
	 * @param parameters parameters source
	 */
	private static void setCommonPartnerParameters(Partner partner, MapSqlParameterSource parameters) {
		parameters.addValue(partners.creator_id.name(), partner.getCreatorId());
		parameters.addValue(partners.modification_date.name(), partner.getModificationDate());
		parameters.addValue(partners.creation_date.name(), partner.getCreationDate());
		parameters.addValue(partners.partner_type.name(), partner.getPartnerType().name());
	}

	/**
	 * Method loads only one partner (and first according to sql result) according to search parameters
	 *
	 * @param psp        partner search parameters
	 * @param dataSource data source
	 * @return loaded partner. If no partner was found - null;
	 */
	@Nullable
	public Partner loadPartner(PartnerSearchParams psp, DataSource dataSource) {
		Partner partner = null;

		//load list of partners
		List<Partner> partners = loadPartners(psp, dataSource);
		if (!partners.isEmpty()) {
			//and if more then one - get first
			partner = partners.get(0);
		}

		return partner;
	}

	/**
	 * Method loads list of partners accoring to search parameters
	 *
	 * @param psp        partner search parameters
	 * @param dataSource data sources
	 * @return list of loaded partners. Never null but may be empty if no partners were found.
	 */
	@NotNull
	public List<Partner> loadPartners(PartnerSearchParams psp, DataSource dataSource) {
		SQLBuilder sqlBuilder = new SQLBuilder().select().columns("p", partners.values()).from().tables(Config.DATABASE_SCHEMA, Tables.partners.name(), "p").where();

		//let's bind search parameters with sql parameters source
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		if (psp.getId() != null) {
			sqlBuilder.equalsCondition(psp.isStrictSearch(), "p", partners.partner_id.name(), psp.getId(), sqlParameterSource);
		}

		if (Strings.notEmpty(psp.getFirstName())) {
			//noinspection ConstantConditions
			sqlBuilder.equalsCondition(psp.isStrictSearch(), "p", partners.first_name.name(), psp.getFirstName(), sqlParameterSource);
		}

		if (Strings.notEmpty(psp.getLastName())) {
			//noinspection ConstantConditions
			sqlBuilder.equalsCondition(psp.isStrictSearch(), "p", partners.last_name.name(), psp.getLastName(), sqlParameterSource);
		}

		if (Strings.notEmpty(psp.getCompanyName())) {
			//noinspection ConstantConditions
			sqlBuilder.equalsCondition(psp.isStrictSearch(), "p", partners.company_name.name(), psp.getCompanyName(), sqlParameterSource);
		}

		if (Collections.notEmpty(psp.getGenders())) {
			//noinspection ConstantConditions
			sqlBuilder.in("p", partners.gender.name(), psp.getGenders(), sqlParameterSource, "empty");
		}

		if (Collections.notEmpty(psp.getPartnerRoles())) {
			sqlBuilder.exists();
			sqlBuilder.append("( ");

			SQLBuilder innerSql = new SQLBuilder();
			innerSql.select().allColumns().from().tables(Config.DATABASE_SCHEMA, Tables.partners_partner_roles_link.name(), "pprl").where();
			innerSql.equalsCondition("pprl", partners_partner_roles_link.partner_id.name(), "p", partners.partner_id.name());
			innerSql.in("pprl", partners_partner_roles_link.partner_role.name(), psp.getPartnerRoles(), sqlParameterSource, "empty");

			sqlBuilder.append(innerSql);
			sqlBuilder.append(") ");
		}

		if (Collections.notEmpty(psp.getPartnerTypes())) {
			sqlBuilder.in("p", partners.partner_type.name(), psp.getPartnerTypes(), sqlParameterSource, "empty");
		}

		if (Strings.notEmpty(psp.getCity()) || Strings.notEmpty(psp.getCountry())) {
			//noinspection ConstantConditions
			sqlBuilder.exists().append("( ");

			sqlBuilder.select().append("* ").from().tables(Config.DATABASE_SCHEMA, Tables.addresses.name(), "inner_a").where();
			sqlBuilder.equalsCondition("inner_a", addresses.partner_id.name(), "p", partners.partner_id.name());
			sqlBuilder.equalsCondition("inner_a", addresses.is_main.name(), Boolean.toString(true), sqlParameterSource);			
			if (Strings.notEmpty(psp.getCity())) {
				sqlBuilder.equalsCondition(false, "inner_a", addresses.city.name(), psp.getCity(), sqlParameterSource);
			}
			if (Strings.notEmpty(psp.getCountry())) {
				sqlBuilder.equalsCondition(false, "inner_a", addresses.country.name(), psp.getCountry(), sqlParameterSource);
			}

			sqlBuilder.append(") ");
		}

		//do execute
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		List<Partner> partners = jdbcTemplate.query(sqlBuilder.toString(), new PartnerMapper(), sqlParameterSource);

		//for each partner we have to load additional info -load partner roles  and linked users
		for (Partner partner : partners) {
			partner.getPartnerRoles().addAll(PartnerRoleService.load(partner.getId(), dataSource));
			if (psp.isFullLoad()) {
				partner.getLinkedUsers().addAll(UserPartnerLinkService.loadLinkedUsers(partner.getId(), dataSource));
			}
			PartnerDetailsService.load(partner, dataSource);
			partner.setAddresses(addressService.load(partner.getId(), dataSource));
		}

		return partners;
	}

	/**
	 * Method updates partner object stored in database
	 *
	 * @param partner    partner object which will update current
	 * @param dataSource data source
	 */
	public void update(@NotNull Partner partner, @NotNull DataSource dataSource) {
		//prepare sql parameters
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		partner.setModificationDate(new Date());

		//prepare update sql
		SQLBuilder sqlBuilder = new SQLBuilder().update().tables(Config.DATABASE_SCHEMA, Tables.partners.name(), "p").set();
		sqlBuilder.equalsConditionForUpdate("p", partners.modification_date.name(), partner.getModificationDate(), sqlParameterSource).comma();

		//setting parameters
		if (partner.isNaturalPerson()) {
			NaturalPerson np = (NaturalPerson) partner;
			sqlBuilder.equalsConditionForUpdate("p", partners.first_name.name(), np.getFirstName(), sqlParameterSource).comma();
			sqlBuilder.equalsConditionForUpdate("p", partners.last_name.name(), np.getLastName(), sqlParameterSource).comma();
			sqlBuilder.equalsConditionForUpdate("p", partners.birthdate.name(), np.getBirthdate(), sqlParameterSource).comma();
			sqlBuilder.equalsConditionForUpdate("p", partners.gender.name(), np.getGender().name(), sqlParameterSource);
		} else {
			LegalPerson lp = (LegalPerson) partner;
			sqlBuilder.equalsConditionForUpdate("p", partners.company_name.name(), lp.getCompanyName(), sqlParameterSource).comma();
			sqlBuilder.equalsConditionForUpdate("p", partners.incorporation_date.name(), lp.getIncorporationDate(), sqlParameterSource);
		}

		//set where part
		sqlBuilder.where();
		sqlBuilder.equalsCondition("p", partners.partner_id.name(), partner.getId(), sqlParameterSource);

		//create template and execute
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		jdbcTemplate.update(sqlBuilder.toString(), sqlParameterSource);

		//make update
		PartnerRoleService.update(partner, dataSource);

		PartnerDetailsService.update(partner, dataSource);

		addressService.update(partner, dataSource);
	}

	/**
	 * User: serso
	 * Date: Apr 19, 2010
	 * Time: 12:50:57 AM
	 */
	public static class PartnerMapper implements ParameterizedRowMapper<Partner> {

		@Override
		public Partner mapRow(ResultSet rs, int rowNum) throws SQLException {
			Partner partner;

			if (PartnerType.valueOf(rs.getString(partners.partner_type.name())) == PartnerType.natural_person) {
				partner = new NaturalPerson(rs.getInt(partners.partner_id.name()));
				((NaturalPerson) partner).setFirstName(rs.getString(partners.first_name.name()));
				((NaturalPerson) partner).setLastName(rs.getString(partners.last_name.name()));
				if (rs.getObject(partners.birthdate.name()) != null) {
					((NaturalPerson) partner).setBirthdate(new Date(rs.getDate(partners.birthdate.name()).getTime()));
				}
				((NaturalPerson) partner).setGender(Gender.valueOf(rs.getString(partners.gender.name())));
			} else {
				partner = new LegalPerson(rs.getInt(partners.partner_id.name()));
				((LegalPerson) partner).setCompanyName(rs.getString(partners.company_name.name()));
				if (rs.getObject(partners.incorporation_date.name()) != null) {
					((LegalPerson) partner).setIncorporationDate(new Date(rs.getDate(partners.incorporation_date.name()).getTime()));
				}
			}

			DataObjectMapper.mapRow(partner, rs);


			return partner;
		}
	}
}