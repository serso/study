/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.study.model.partner.PartnerRole;
import org.springframework.security.core.GrantedAuthority;

/**
 * User: serso
 * Date: Apr 1, 2010
 * Time: 11:20:45 PM
 */
public enum UserRole implements GrantedAuthority {

	//only for system
	system(null),

	//for users
	administrator(null),
	developer(null),
	user(null),
	approved_user(null),
	student(PartnerRole.student),
	teacher(PartnerRole.teacher),
	school_employee(PartnerRole.school_employee);

	private PartnerRole linkedPartnerRole;

	UserRole(PartnerRole linkedPartnerRole) {
		this.linkedPartnerRole = linkedPartnerRole;
	}

	@Override
	public @NotNull String getAuthority() {
		return this.name();
	}

	@Nullable
	public PartnerRole getLinkedPartnerRole () {
		return this.linkedPartnerRole;	
	}
}