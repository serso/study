/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.model.address;

import org.jetbrains.annotations.NotNull;
import org.solovyev.study.model.partner.Partner;

import java.util.List;

/**
 * User: serso
 * Date: Jun 17, 2010
 * Time: 11:02:14 PM
 */
public interface AddressDao {

	/**
	 * Method loads all addresses which are belonged to partner with current partner id
	 *
	 * @param partnerId id of partner
	 * @return list of found addresses
	 */
	@NotNull
	List<Address> load(@NotNull Integer partnerId);

	/**
	 * Method deletes all addresses for current partner
	 *
	 * @param partnerId id of partner
	 * @return number of affected rows
	 */
	int delete(@NotNull Integer partnerId);

	/**
	 * Method inserts all addresses belonging to current partner.
	 * @param partner partner which addresses will be inserted
	 */
	void insert(@NotNull Partner partner);
}
