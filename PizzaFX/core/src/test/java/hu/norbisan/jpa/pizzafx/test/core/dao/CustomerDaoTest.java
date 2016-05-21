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


import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hu.norbisan.jpa.pizzafx.core.dao.CustomerDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.CustomerDaoFactory;
import hu.norbisan.jpa.pizzafx.core.entity.Customer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerDaoTest {
	
	private static final String PERSISTENCE_UNIT_NAME = "hu.norbisan.jpa.pizzafx_test_pu";
	private Customer customer1;
	private Customer customer2;
	private Long customerId1;
	private Long customerId2;
	private CustomerDaoFactory factory;
	private CustomerDao customerDao;

	@Test
	public void test1Create() {
		factory = CustomerDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		customerDao = factory.newCustomerDao();
		
		customer1 = new Customer();
		customer1.setName("Kiss János");
		customer1.setEmail("jani@email.hu");
		customer1.setOrdersCount(1L);
		customer1.setPassword("abc");
		customer1.setPhoneNumber("1234567");
		Assert.assertNull("Az id null kell legyen mentés előtt!", customer1.getId());
		Customer c1 = customerDao.create(customer1);
		Assert.assertNotNull("Az id mentés után nem lehet null!", c1.getId());
		factory.close();
	}

	@Test
	public void test2FindOne() {
		factory = CustomerDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		customerDao = factory.newCustomerDao();
		
		customer1 = new Customer();
		customer1.setName("Kiss János");
		customer1.setEmail("janos@email.hu");
		customer1.setOrdersCount(1L);
		customer1.setPassword("abc");
		customer1.setPhoneNumber("1234567");
		customerDao.create(customer1);
		
		customerId1 = customer1.getId();
		Customer c = customerDao.findOne(customerId1);
		Assert.assertEquals("A név Kiss János helyett: " + c.getName(),"Kiss János", c.getName());
		Assert.assertEquals("Az email janos@email.hu helyett: " + c.getEmail(),"janos@email.hu", c.getEmail());
		Assert.assertEquals("Az ordersCunt 1 helyett: " + c.getOrdersCount() ,new Long(1), c.getOrdersCount());
		Assert.assertEquals("A jelszó abc helyett: " + c.getPassword() ,"abc", c.getPassword());
		Assert.assertEquals("A telefonszám 1234567 helyett: " + c.getPhoneNumber(), "1234567", c.getPhoneNumber());
		factory.close();
	}

	@Test
	public void test3FindAll() {
		factory = CustomerDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		customerDao = factory.newCustomerDao();
		
		customer1 = new Customer();
		customer1.setName("Ferenc");
		customerDao.create(customer1);
		customerId1 = customer1.getId();
		
		customer2 = new Customer();
		customer2.setName("József");
		customerDao.create(customer2);
		customerId2 = customer2.getId();
		
		List<Customer> customers = customerDao.findAll();
		Assert.assertTrue("A lista elemszáma 4 helyett: " + customers.size(), 4 == customers.size());
		for(Customer c : customers){
			if(customerId1.equals(c.getId())){
				Assert.assertEquals("A név Ferenc helyett: " + c.getName(),"Ferenc", c.getName());
			}
			else if(customerId2.equals(c.getId())){
				Assert.assertEquals("A név József helyett: " + c.getName(),"József", c.getName());
			}
		}
		factory.close();
	}

	@Test
	public void test4FindByEmail() {
		factory = CustomerDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		customerDao = factory.newCustomerDao();
		
		customer1 = new Customer();
		customer1.setName("Ferenc");
		customer1.setEmail("ferenc@email.hu");
		customerDao.create(customer1);
		customerId1 = customer1.getId();
		
		customer2 = new Customer();
		customer2.setName("József");
		customer2.setEmail("jozsef@email.hu");
		customerDao.create(customer2);
		customerId2 = customer2.getId();
		
		Customer c = customerDao.findByEmail("ferenc@email.hu");
		Assert.assertNotNull("Az entitás nem lehet null lekérdezés után!", c);
		Assert.assertEquals("Az id " + customerId1 + " helyett: " + c.getId() ,customerId1, c.getId());
		c = null;
		Assert.assertNull(c);
		
		c = customerDao.findByEmail("jozsef@email.hu");
		Assert.assertNotNull("Az entitás nem lehet null lekérdezés után!", c);
		Assert.assertEquals("Az id " + customerId2 + " helyett: " + c.getId() ,customerId2, c.getId());
		
		factory.close();
	}

}
