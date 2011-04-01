/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.validators;

import org.solovyev.common.utils.CollectionsUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.solovyev.study.model.partner.Partner;
import org.solovyev.study.model.User;
import org.solovyev.study.model.UserRole;

import java.util.Collection;
import java.util.List;

/**
 * User: serso
 * Date: Mar 20, 2010
 * Time: 1:07:57 AM
 */
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmpty(errors, "userRoles", "not.empty");

		if (!UserValidator.isValidUserRolesSet(user.getUserRoles())) {
			errors.rejectValue("userRoles", "incorrect.user.roles.set");
		} else {
			UserValidator.validateLinkedPartners(user, errors);
		}
	}

	public static boolean isValidUserRolesSet(Collection<UserRole> userRoles) {
		boolean result = true;

		if (CollectionsUtils.notEmpty(userRoles)) {
			if (userRoles.contains(UserRole.user) || userRoles.contains(UserRole.approved_user)) {
				//if set contains user or approved_user role
				//then other roles must not be available
				if (userRoles.size() > 1) {
					result = false;
				}
			}
		}

		return result;
	}

	public static void validateLinkedPartners(User user, Errors errors) {
		if (user.getLinkedPartners().size() > 1) {
			errors.rejectValue("linkedPartners", "too.many.linked.partners");			
		}

		List<Partner> linkedPartners;
		for (UserRole userRole : user.getUserRoles()) {
			if (userRole.getLinkedPartnerRole() != null) {
				linkedPartners = user.getLinkedPartners(userRole.getLinkedPartnerRole());
				if (linkedPartners.isEmpty()) {
					//noinspection ConstantConditions
					errors.rejectValue("linkedPartners", "user.role.and.linked.partner", new Object[]{userRole.name(), userRole.getLinkedPartnerRole().name()}, "user.role.and.linked.partner");
				}
			}
		}
	}
}
