/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: Apr 27, 2010
 * Time: 11:12:58 PM
 */
public class Action {

	@Nullable
	String action;

	public Action() {
	}

	public Action(@Nullable String action) {
		this.action = action;
	}

	@Nullable
	public String getAction() {
		return action;
	}

	public void setAction(@Nullable String action) {
		this.action = action;
	}
}
