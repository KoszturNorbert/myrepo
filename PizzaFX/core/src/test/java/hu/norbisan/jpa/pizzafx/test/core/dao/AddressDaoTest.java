package hu.norbisan.jpa.pizzafx.test.core.dao;

/*
 * #%L
 * core
 * %%
 * Copyright (C) 2016 Kosztur Norbert
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hu.norbisan.jpa.pizzafx.core.dao.AddressDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.AddressDaoFactory;
import hu.norbisan.jpa.pizzafx.core.entity.Address;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressDaoTest {

	private static final String PERSISTENCE_UNIT_NAME = "hu.norbisan.jpa.pizzafx_test_pu";
	private Address address1;
	private Address address2;
	private Long addressId1;
	private Long addressId2;
	private AddressDao addressDao;
	private AddressDaoFactory factory;

	@Test
	public void test1Create() {
		factory = AddressDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		addressDao = factory.newAddressDao();

		address1 = new Address();
		address1.setPostalCode(1234);
		address1.setStreet("Bocskai u.");
		address1.setHouse("1");
		//Az id automatikusan állítódik be mentés után!
		Assert.assertNull("Az Id null kellene legyen!", address1.getId());
		Address ad1 = addressDao.create(address1);
		Assert.assertNotNull("Az Id nem lehet null!" ,ad1.getId());

		address2 = new Address();
		address2.setPostalCode(3468);
		address2.setStreet("Kossuth u.");
		address2.setHouse("5");
		//Az id automatikusan állítódik be mentés után!
		Assert.assertNull("Az Id null kellene legyen!", address2.getId());
		Address ad2 = addressDao.create(address2);
		Assert.assertNotNull("Az Id nem lehet null!" ,ad2.getId());
		
		addressId1 = ad1.getId();
		addressId2 = ad2.getId();

		System.out.println("AddressId1: " + addressId1);
		System.out.println("AddressId2: " + addressId2);

		factory.close();
	}

	@Test
	public void test2FindOne() {
		factory = AddressDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		addressDao = factory.newAddressDao();

		address1 = new Address();
		address1.setPostalCode(1234);
		address1.setStreet("Bocskai u.");
		address1.setHouse("1");
		addressDao.create(address1);

		address2 = new Address();
		address2.setPostalCode(3468);
		address2.setStreet("Kossuth u.");
		address2.setHouse("5");
		addressDao.create(address2);
		
		addressId1 = address1.getId();
		addressId2 = address2.getId();

		Address ad1 = addressDao.findOne(addressId1);
		System.out.println("PostalCode: " + ad1.getPostalCode());
		System.out.println("City: " + ad1.getCity());
		System.out.println("Street: " + ad1.getStreet());
		System.out.println("House: " + ad1.getHouse());
		
		Assert.assertEquals(new Integer(1234), ad1.getPostalCode());
		Assert.assertEquals("Bocskai u.", ad1.getStreet());
		Assert.assertEquals("1", ad1.getHouse());
		Assert.assertNull(ad1.getCity());

		Address ad2 = addressDao.findOne(addressId2);
		
		System.out.println("PostalCode: " + ad2.getPostalCode());
		System.out.println("City: " + ad2.getCity());
		System.out.println("Street: " + ad2.getStreet());
		System.out.println("House: " + ad2.getHouse());
		
		Assert.assertEquals(new Integer(3468), ad2.getPostalCode());
		Assert.assertEquals("Kossuth u.", ad2.getStreet());
		Assert.assertEquals("5", ad2.getHouse());
		Assert.assertNull(ad2.getCity());

		factory.close();
	}

}
