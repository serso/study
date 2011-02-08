/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.solovyev.study.model.AddressContainer;

/**
 * User: serso
 * Date: May 30, 2010
 * Time: 1:36:02 AM
 */
public class AddressContainerValidator implements Validator {
	@Override
	public boolean supports(Class clazz) {
		return AddressContainer.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AddressContainer addressContainer = (AddressContainer)target;

		if ( addressContainer.getMainAddressIndex() >= addressContainer.getAddresses().size() ) {
			errors.rejectValue("mainAddressIndex", "incorrect.value", new Object[]{"Main address index"}, "");
		}
	}
}
