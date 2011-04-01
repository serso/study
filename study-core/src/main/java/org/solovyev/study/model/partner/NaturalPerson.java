/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.partner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.study.model.Gender;
import org.solovyev.study.model.PartnerType;
import org.springmodules.validation.bean.conf.loader.annotation.handler.InThePast;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

import java.util.Date;

/**
 * User: serso
 * Date: Apr 17, 2010
 * Time: 4:45:58 PM
 */
public class NaturalPerson extends Partner {

	@NotBlank
	@Length(max = 255)
	@NotNull
	private String firstName = "";
	@NotBlank
	@Length(max = 255)
	@NotNull
	private String lastName = "";
	@InThePast
	@Nullable
	private Date birthdate;
	@org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull
	@NotNull
	private Gender gender = Gender.male;


	public NaturalPerson(Integer id) {
		super(id);
	}

	@NotNull
	public PartnerType getPartnerType() {
		return PartnerType.natural_person;
	}

	@NotNull
	@Override
	public String getFullName() {
	    String result = "";
		if( !getFirstName().isEmpty()) {
			result += getFirstName();
			if ( !getLastName().isEmpty() ) {
				result += " ";
			}
		}

		if ( !getLastName().isEmpty() ) {
			result += getLastName();
		}

		return result;
	}

	@NotNull
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NotNull String firstName) {
		this.firstName = firstName;
	}

	@NotNull
	public String getLastName() {
		return lastName;
	}

	public void setLastName(@NotNull String lastName) {
		this.lastName = lastName;
	}

	@Nullable
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(@Nullable Date birthdate) {
		this.birthdate = birthdate;
	}

	@NotNull
	public Gender getGender() {
		return gender;
	}

	public void setGender(@NotNull Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("NaturalPerson");
		sb.append("{firstName='").append(firstName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", birthdate=").append(birthdate);
		sb.append(", gender=").append(gender);
		sb.append('}');
		sb.append('\'').append("super class:").append('\'').append(super.toString());
		return sb.toString();
	}
}
