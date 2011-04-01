/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.validators;

import org.solovyev.study.model.PartnerType;
import org.solovyev.study.model.partner.Partner;
import org.solovyev.study.model.partner.PartnerRole;
import org.springframework.validation.Errors;

import javax.sql.DataSource;
import java.util.List;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 9:40:48 PM
 */
public class PartnerOnCreateValidator extends DataSourceValidator {

	public PartnerOnCreateValidator(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public boolean supports(Class clazz) {
		return Partner.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Partner partner = (Partner) target;
		List<PartnerRole> applicablePartnerRoles = PartnerRole.getApplicablePartnerRoles(partner.getPartnerType());
		for (PartnerRole partnerRole : partner.getPartnerRoles()) {
			//check if partner role can be applied to partner with current partner type
			if (!applicablePartnerRoles.contains(partnerRole)) {
				errors.rejectValue("partnerRoles", "partnerRole.is.not.applyable.for.partner.type", new Object[]{partnerRole.name(), partner.getPartnerType().name()}, "partnerRole.is.not.applyable.for.partner.type");
			}
		}

		//for legal entity now only one role can be applied
		if (partner.getPartnerType() == PartnerType.legal_person) {
			if (partner.getPartnerRoles().size() > 1) {
				errors.rejectValue("partnerRoles", "only.one.partner.role.for.legal.person");
			}
		}
	}
}
