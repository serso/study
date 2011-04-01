/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.html.Selectable;
import org.solovyev.study.model.partner.Partner;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: Apr 24, 2010
 * Time: 1:47:06 PM
 */
public class TestPartnerSelector {

	private List<Integer> partnerIndexes = new ArrayList<Integer>();

	@NotNull
	private List<Selectable<Partner>> partners = new ArrayList<Selectable<Partner>>();

	@Nullable
	private String action;

	private boolean available = false;

	public TestPartnerSelector(String action, boolean available) {
		this.action = action;
		this.available = available;
	}

	public TestPartnerSelector() {
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isMultiPartnerSelectAvailable() {
		return false;
	}

	public void setAction(@Nullable String action) {
		this.action = action;
	}

	@Nullable
	public String getAction() {
		return this.action;
	}

	@NotNull
	public List<Selectable<Partner>> getPartners() {
		return this.partners;
	}

	public void setPartners(@NotNull List<Selectable<Partner>> partners) {
		this.partners = partners;
	}

	public List<Integer> getPartnerIndexes() {
		return partnerIndexes;
	}

	public void setPartnerIndexes(List<Integer> partnerIndexes) {
		this.partnerIndexes = partnerIndexes;
	}
}