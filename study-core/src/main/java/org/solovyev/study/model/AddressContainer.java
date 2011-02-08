/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.AutoPopulatingList;
import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Min;
import org.solovyev.common.utils.CollectionsUtils;

import java.util.List;

/**
 * User: serso
 * Date: May 29, 2010
 * Time: 11:54:41 PM
 */
public class AddressContainer {

	@NotNull
	@CascadeValidation
	private List<Address> addresses = new AutoPopulatingList<Address>(Address.class);

	@NotNull
	@Min(value = 0, inclusive = true)
	private Integer mainAddressIndex = 0;

	public AddressContainer() {
	}

	@NotNull
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(@Nullable List<Address> addresses) {
		this.addresses = CollectionsUtils.setNotNull(addresses, this.addresses);
	}

	@NotNull
	public Integer getMainAddressIndex() {
		return mainAddressIndex;
	}

	public void setMainAddressIndex(@NotNull Integer mainAddressIndex) {
		this.mainAddressIndex = mainAddressIndex;
	}
}
