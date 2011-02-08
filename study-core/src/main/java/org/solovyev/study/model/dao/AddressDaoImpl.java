/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.solovyev.common.utils.StringsUtils;
import org.solovyev.study.model.Address;
import org.solovyev.study.model.AddressType;
import org.solovyev.study.model.Partner;
import org.solovyev.study.model.db.SQLBuilder;
import org.solovyev.study.model.db.Tables;
import org.solovyev.study.model.db.tables.addresses;
import org.solovyev.study.resources.Config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: serso
 * Date: May 11, `2010
 * Time: 11:57:49 PM
 */

public class AddressDaoImpl implements AddressDao {
	                      //todo serso: just delete
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public AddressDaoImpl() {
	}

	@NotNull
	public List<Address> load(@NotNull Integer partnerId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		SQLBuilder sqlBuilder = new SQLBuilder().select().columns("a", addresses.values()).from().tables(Config.DATABASE_SCHEMA, Tables.addresses.name(), "a").where();
		sqlBuilder.equalsCondition("a", addresses.partner_id.name(), partnerId, mapSqlParameterSource);
		final org.hibernate.classic.Session session = hibernateTemplate.getSessionFactory().openSession();
		org.hibernate.Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			//session.load(Address.class, )
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			Logger.getLogger(this.getClass()).error(e);
		} finally {
			session.close();
		}

		// todo serso: remove
		return null;//getSimpleJdbcTemplate().query(sqlBuilder.toString(), new AddressRowMapper(), mapSqlParameterSource);
	}

	private SimpleJdbcTemplate getSimpleJdbcTemplate() {
		// todo serso: remove
		return null;  //To change body of created methods use File | Settings | File Templates.
	}

	public int delete(@NotNull Integer partnerId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		SQLBuilder sqlBuilder = new SQLBuilder().delete().from().tables(Config.DATABASE_SCHEMA, Tables.addresses.name()).where();
		sqlBuilder.equalsCondition(null, addresses.partner_id.name(), partnerId, mapSqlParameterSource);
		return getSimpleJdbcTemplate().update(sqlBuilder.toString(), mapSqlParameterSource);
	}

	public void insert(@NotNull Partner partner) {
		MapSqlParameterSource mapSqlParameterSource;
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(getJdbcTemplate()).withSchemaName(Config.DATABASE_SCHEMA).withTableName(Tables.addresses.name());
		simpleJdbcInsert.usingColumns(StringsUtils.toString(addresses.values()));

		for (Address address : partner.getAddresses()) {
			mapSqlParameterSource = new MapSqlParameterSource();
			mapSqlParameterSource.addValue(addresses.partner_id.name(), partner.getId());
			mapSqlParameterSource.addValue(addresses.country.name(), address.getCountry());
			mapSqlParameterSource.addValue(addresses.address_type.name(), address.getAddressType().name());
			mapSqlParameterSource.addValue(addresses.city.name(), address.getCity());
			mapSqlParameterSource.addValue(addresses.email.name(), address.getEmail());
			mapSqlParameterSource.addValue(addresses.house.name(), address.getHouse());
			mapSqlParameterSource.addValue(addresses.apartment.name(), address.getApartment());
			mapSqlParameterSource.addValue(addresses.phone_number.name(), address.getPhoneNumber());
			mapSqlParameterSource.addValue(addresses.postal_code.name(), address.getPostalCode());
			mapSqlParameterSource.addValue(addresses.street.name(), address.getStreet());
			mapSqlParameterSource.addValue(addresses.is_main.name(), String.valueOf(address.isMain()));
			simpleJdbcInsert.execute(mapSqlParameterSource);
		}

	}

		// todo serso: remove
	private JdbcTemplate getJdbcTemplate() {
		return null;  //To change body of created methods use File | Settings | File Templates.
	}

	private class AddressRowMapper implements ParameterizedRowMapper<Address> {
		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			Address address = new Address();

			address.setAddressType(AddressType.valueOf(rs.getString(addresses.address_type.name())));
			address.setCountry(rs.getString(addresses.country.name()));
			address.setCity(rs.getString(addresses.city.name()));
			address.setEmail(rs.getString(addresses.email.name()));
			address.setHouse(rs.getString(addresses.house.name()));
			address.setApartment(rs.getString(addresses.apartment.name()));
			address.setPhoneNumber(rs.getString(addresses.phone_number.name()));
			address.setPostalCode(rs.getString(addresses.postal_code.name()));
			address.setStreet(rs.getString(addresses.street.name()));
			address.setMain(Boolean.valueOf(rs.getString(addresses.is_main.name())));

			return address;
		}
	}

}
