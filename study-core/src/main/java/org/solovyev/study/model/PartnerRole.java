/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 5:04:06 PM
 */
public enum PartnerRole {

	//roles for natural person
	student(PartnerType.natural_person),
	teacher(PartnerType.natural_person),
	school_employee(PartnerType.natural_person),

	//roles for legal person
	school(PartnerType.legal_person, SchoolDetails.class),
	institute(PartnerType.legal_person),
	university(PartnerType.legal_person);

	@NotNull
	private final PartnerType applyablePartnerType;

	@Nullable
	private final Class partnerDetailsClass;

	PartnerRole(@NotNull PartnerType applyablePartnerType) {
		this(applyablePartnerType, null);
	}

	PartnerRole(@NotNull PartnerType applyablePartnerType, @Nullable Class partnerDetailsClass) {
		this.applyablePartnerType = applyablePartnerType;
		this.partnerDetailsClass = partnerDetailsClass;
	}

	@NotNull
	public PartnerType getApplyablePartnerType() {
		return applyablePartnerType;
	}

	public boolean isApplyable(PartnerType partnerType) {
		return this.applyablePartnerType == partnerType;
	}

	@NotNull
	public static List<PartnerRole> getApplyablePartnerRoles(PartnerType partnerType) {
		final List<PartnerRole> partnerRoles = new ArrayList<PartnerRole>();

		for (PartnerRole partnerRole : PartnerRole.values()) {
			if (partnerRole.isApplyable(partnerType)) {
				partnerRoles.add(partnerRole);
			}
		}

		return partnerRoles;
	}

	@Nullable
	public Object createPartnerDetails() {

		if (this.partnerDetailsClass != null) {
			try {
				return this.partnerDetailsClass.newInstance();
			} catch (InstantiationException e) {
				Logger.getLogger(PartnerRole.class).error(e);
			} catch (IllegalAccessException e) {
				Logger.getLogger(PartnerRole.class).error(e);
			}
		}

		return null;
	}

	public boolean isPartnerDetailsApplicable () {
		return this.partnerDetailsClass != null;
	}
}
