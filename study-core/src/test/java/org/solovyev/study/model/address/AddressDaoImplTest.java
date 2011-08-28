package org.solovyev.study.model.address;

import org.junit.Test;
import org.solovyev.study.model.AddressType;
import org.solovyev.study.model.partner.NaturalPerson;
import org.solovyev.study.model.partner.Partner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;

/**
 * User: serso
 * Date: 4/2/11
 * Time: 8:31 PM
 */

@ContextConfiguration("/test-context.xml")
public class AddressDaoImplTest extends AbstractTransactionalJUnit38SpringContextTests {

	final AddressDaoImpl addressDao = new AddressDaoImpl();

/*	@Override
	public void setUp() throws Exception {
		super.setUp();

		addressDao.setSessionFactory(sessionFactory);
	}*/

	@Test
	public void testLoad() throws Exception {
		addressDao.load(1);
		addressDao.load(2);
		addressDao.load(3);
		addressDao.load(4);
		addressDao.load(5);
	}

	public void testInsert() throws Exception {
		final Partner partner = new NaturalPerson(-1);

		Address address = new Address();
		address.setAddressType(AddressType.office);
		address.setApartment("22");
		address.setCity("test_city");
		address.setCountry("test_country");
		address.setEmail("test_email@test.com");
		address.setHouse("2");
		address.setMain(true);
		address.setPartnerId(partner.getId());
		address.setPhoneNumber("999-22-11");
		address.setPostalCode("test_postal_code");
		address.setStreet("test_street");

		partner.getAddresses().add(address);

		addressDao.insert(partner);

	}
}
