/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

import javax.persistence.*;

/**
 * User: serso
 * Date: Apr 11, 2010
 * Time: 5:32:30 PM
 */

@Entity
@Table(name = "addresses")
public class Address{

	@NotNull
	private Integer partnerId;

	@NotNull
	private AddressType addressType = AddressType.residence;

	@NotNull
	@NotBlank
	@Length (max = 255)
	private String country = "";

	@NotNull
	@NotBlank
	@Length (max = 255)
	private String city = "";

	@Nullable
	@Length (max = 255)
	private String street;

	@Nullable
	@Length (max = 255)
	private String house;

	@Nullable
	@Length (max = 255)
	private String apartment;

	@Nullable
	@Length (max = 255)
	private String phoneNumber;

	@Nullable
	@Email
	@Length (max = 255)
	private String email;

	@Nullable
	@Length (max = 255)	
	private String postalCode;

	private boolean main = false;

	public Address() {
	}

	@Column(name = "address_type")
	@NotNull
	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(@NotNull AddressType addressType) {
		this.addressType = addressType;
	}

	@Column(name = "country")
	@NotNull
	public String getCountry() {
		return country;
	}

	public void setCountry(@NotNull String country) {
		this.country = country;
	}

	@Column(name = "city")
	@NotNull
	public String getCity() {
		return city;
	}

	public void setCity(@NotNull String city) {
		this.city = city;
	}

	@Column(name = "street")
	@Nullable
	public String getStreet() {
		return street;
	}

	public void setStreet(@Nullable String street) {
		this.street = street;
	}

	@Column(name = "house")
	@Nullable
	public String getHouse() {
		return house;
	}

	public void setHouse(@Nullable String house) {
		this.house = house;
	}

	@Column(name = "phone_number")
	@Nullable
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(@Nullable String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "email")
	@Nullable
	public String getEmail() {
		return email;
	}

	public void setEmail(@Nullable String email) {
		this.email = email;
	}

	@Column(name = "postal_code")
	@Nullable
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(@Nullable String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "is_main")
	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	@Column(name = "apartment")
	@Nullable
	public String getApartment() {
		return apartment;
	}

	public void setApartment(@Nullable String apartment) {
		this.apartment = apartment;
	}

	@Column(name = "partner_id")
	@ManyToOne(targetEntity = Partner.class, cascade = CascadeType.ALL)
	@NotNull
	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(@NotNull Integer partnerId) {
		this.partnerId = partnerId;
	}
}
