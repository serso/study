package org.solovyev.study.model.address;

import org.jetbrains.annotations.NotNull;
import org.solovyev.study.model.partner.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: serso
 * Date: 3/17/11
 * Time: 10:37 AM
 */

@Service("addressBo")
public class AddressBoImpl implements AddressBo {

	@Autowired
	private AddressDao addressDao;

	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@NotNull
	@Override
	public List<Address> load(@NotNull Integer partnerId) {
		return this.addressDao.load(partnerId);
	}

	@Override
	public int delete(@NotNull Integer partnerId) {
		return this.addressDao.delete(partnerId);
	}

	@Override
	public void insert(@NotNull Partner partner) {
		this.addressDao.insert(partner);
	}

	public void doSomeStuff(String test) {

	}
}
