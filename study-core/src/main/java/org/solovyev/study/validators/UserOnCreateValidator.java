/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.validators;

import org.solovyev.study.model.user.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.solovyev.study.services.UserService;

import javax.sql.DataSource;

/**
 * User: serso
 * Date: Mar 27, 2010
 * Time: 5:28:27 PM
 */
public class UserOnCreateValidator extends DataSourceValidator{

	public UserOnCreateValidator(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.invokeValidator(new UserValidator(), target, errors);
		if ( !errors.hasErrors() ) {
			if ( !UserService.checkAvailabilityOfUsername((User)target, this.dataSource) ) {
				errors.rejectValue("username", "username.unique", "This username is already used in system!");
			}

			if ( !UserService.checkAvailabilityOfEmail((User)target, this.dataSource)) {
				errors.rejectValue("email", "email.unique", "This email is already used in system!");				
			}
		}
	}
}
