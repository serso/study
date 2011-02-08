/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.Nullable;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Min;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * User: serso
 * Date: May 7, 2010
 * Time: 11:54:20 PM
 */
public class SchoolDetails {

	@NotNull
	@Min(value = 1)
	private Integer schoolNumber;

	public SchoolDetails() {
	}

	public Integer getSchoolNumber() {
		return schoolNumber;
	}

	public void setSchoolNumber(Integer schoolNumber) {
		this.schoolNumber = schoolNumber;
	}
}
