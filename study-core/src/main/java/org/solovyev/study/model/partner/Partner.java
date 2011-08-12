/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.partner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.CollectionsUtils;
import org.solovyev.study.model.data_object.DataObject;
import org.solovyev.study.model.PartnerType;
import org.solovyev.study.model.address.Address;
import org.solovyev.study.model.user.User;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: serso
 * Date: Apr 10, 2010
 * Time: 10:52:11 PM
 */

public abstract class Partner extends DataObject<Integer> {

	@NotNull
	private List<Address> addresses = new ArrayList<Address>();

	@NotNull
	@NotEmpty
	private List<PartnerRole> partnerRoles = new ArrayList<PartnerRole>();

	@NotNull
	private List<User> linkedUsers = new ArrayList<User>();


	/**
	 * Role specific partner details
	 */
	@NotNull
	private Map<PartnerRole, Object> details = new HashMap<PartnerRole, Object>();

	public Partner() {
	}

	/**
	 * Method has to return current type of partner. This method is used to
	 * behave different in case of partner type.
	 *
	 * @return partner type
	 */
	@NotNull
	public abstract PartnerType getPartnerType();

	/**
	 * Method returns full name of partner. Used to display in views.
	 *
	 * @return partner's full name
	 */
	@NotNull
	public abstract String getFullName();

	public Partner(Integer id) {
		super(id);

		//new partner -> let's add all possible partner roles' details
		//todo serso: think about it
		for (PartnerRole partnerRole : PartnerRole.values()) {
			Object partnerDetails = partnerRole.createPartnerDetails();
			if ( partnerDetails != null ) {
				this.details.put(partnerRole, partnerDetails);
			}
		}
	}

	@NotNull
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(@NotNull List<Address> addresses) {
		this.addresses = addresses;
	}

	@NotNull
	public List<PartnerRole> getPartnerRoles() {
		return partnerRoles;
	}

	public void setPartnerRoles(@Nullable List<PartnerRole> partnerRoles) {
		this.partnerRoles = CollectionsUtils.setNotNull(partnerRoles, this.partnerRoles);
	}

	public boolean isNaturalPerson() {
		return getPartnerType() == PartnerType.natural_person;
	}

	@NotNull
	public List<PartnerRole> getApplicablePartnerRoles() {
		return PartnerRole.getApplicablePartnerRoles(getPartnerType());
	}

	public boolean isActsAs(@NotNull PartnerRole partnerRole) {
		return partnerRoles.contains(partnerRole);
	}

	@NotNull
	public List<User> getLinkedUsers() {
		return linkedUsers;
	}

	public void setLinkedUsers(@Nullable List<User> linkedUsers) {
		this.linkedUsers = CollectionsUtils.setNotNull(linkedUsers, this.linkedUsers);
	}

	@NotNull
	public Map<PartnerRole, Object> getDetails() {
		return details;
	}

	@Nullable
	public Address getMainAddress () {
		Address result = null;
		for (Address address : addresses) {
			if ( address.isMain() ) {
				result = address;
				break;
			}
		}
		return result;
	}
}
