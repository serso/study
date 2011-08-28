/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.partner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.study.model.PartnerType;
import org.springmodules.validation.bean.conf.loader.annotation.handler.InThePast;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

import java.util.Date;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 5:01:50 PM
 */
public class LegalPerson extends Partner{

	@NotBlank
	@Length(max = 255)
	@NotNull
	private String companyName = "";

	@InThePast
	@Nullable
	private Date incorporationDate;

	public LegalPerson(Integer id) {
		super(id);
	}

	public LegalPerson() {
	}

	@NotNull
	@Override
	public PartnerType getPartnerType() {
		return PartnerType.legal_person;
	}

	@NotNull
	@Override
	public String getFullName() {
		return getCompanyName();
	}

	@NotNull
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(@NotNull String companyName) {
		this.companyName = companyName;
	}

	@Nullable
	public Date getIncorporationDate() {
		return incorporationDate;
	}

	public void setIncorporationDate(@Nullable Date incorporationDate) {
		this.incorporationDate = incorporationDate;
	}
}
