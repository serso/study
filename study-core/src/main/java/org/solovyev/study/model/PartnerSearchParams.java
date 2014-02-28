/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.Identity;
import org.solovyev.common.collections.Collections;
import org.solovyev.study.model.partner.PartnerRole;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 11:00:00 PM
 */
public class PartnerSearchParams extends Identity<Integer> implements StrictSearch {

	@Nullable
	private String firstName;
	@Nullable
	private String lastName;
	@Nullable
	private String companyName;
	@Nullable
	private String country;
	@Nullable
	private String city;
	@NotNull
	private List<PartnerType> partnerTypes = new ArrayList<PartnerType>();
	@NotNull
	private List<Gender> genders = new ArrayList<Gender>();
	@NotNull
	private List<PartnerRole> partnerRoles = new ArrayList<PartnerRole>();

	@NotNull
	private Integer linkedUserId;

	private boolean fullLoad = true;

	private boolean strictSearch = true;

	public PartnerSearchParams(Integer id) {
		super(id);
	}

	public PartnerSearchParams(boolean strictSearch) {
		super(null);
		this.strictSearch = strictSearch;
	}

	@Nullable
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@Nullable String firstName) {
		this.firstName = firstName;
	}

	@Nullable
	public String getLastName() {
		return lastName;
	}

	public void setLastName(@Nullable String lastName) {
		this.lastName = lastName;
	}

	@Nullable
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(@Nullable String companyName) {
		this.companyName = companyName;
	}

	@NotNull
	public List<PartnerType> getPartnerTypes() {
		return partnerTypes;
	}

	public void setPartnerTypes(@Nullable List<PartnerType> partnerTypes) {
		this.partnerTypes = (List<PartnerType>)Collections.setNotNull(partnerTypes, this.partnerTypes);
	}

	@NotNull
	public List<Gender> getGenders() {
		return genders;
	}

	public void setGenders(@Nullable List<Gender> genders) {
		this.genders = (List<Gender>)Collections.setNotNull(genders, this.genders);
	}

	@NotNull
	public List<PartnerRole> getPartnerRoles() {
		return partnerRoles;
	}

	public void setPartnerRoles(@Nullable List<PartnerRole> partnerRoles) {
		this.partnerRoles = (List<PartnerRole>)Collections.setNotNull(partnerRoles, this.partnerRoles);
	}

	public boolean isStrictSearch() {
		return strictSearch;
	}

	public void setStrictSearch(boolean strictSearch) {
		this.strictSearch = strictSearch;
	}

	@NotNull
	public Integer getLinkedUserId() {
		return linkedUserId;
	}

	public void setLinkedUserId(@NotNull Integer linkedUserId) {
		this.linkedUserId = linkedUserId;
	}

	public boolean isFullLoad() {
		return fullLoad;
	}

	public void setFullLoad(boolean fullLoad) {
		this.fullLoad = fullLoad;
	}

	@Nullable
	public String getCountry() {
		return country;
	}

	public void setCountry(@Nullable String country) {
		this.country = country;
	}

	@Nullable
	public String getCity() {
		return city;
	}

	public void setCity(@Nullable String city) {
		this.city = city;
	}
}
