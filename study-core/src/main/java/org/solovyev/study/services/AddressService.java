package org.solovyev.study.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.solovyev.study.model.Address;
import org.solovyev.study.model.Partner;
import org.solovyev.study.model.dao.AddressDao;
import org.solovyev.study.model.dao.AddressDaoImpl;

import javax.sql.DataSource;
import java.util.List;

/**
 * User: serso
 * Date: May 12, 2010
 * Time: 12:54:34 AM
 */

@Service
public class AddressService {

	private AddressDao addressDao;

	/**
	 * Method loads all addresses for current partner
	 *
	 * @param partnerId  id of partner
	 * @param dataSource data source
	 * @return list of addresses
	 */
	@NotNull
	public List<Address> load(@NotNull Integer partnerId, @NotNull DataSource dataSource) {
		return addressDao.load(partnerId);
	}

	public void update(@NotNull Partner partner, @NotNull DataSource dataSource) {
		//first delete all
		addressDao.delete(partner.getId());

		//then insert new ones
		addressDao.insert(partner);
	}

	@Autowired
	public void setAddressDao(AddressDaoImpl addressDao) {
		this.addressDao = addressDao;
	}
}
