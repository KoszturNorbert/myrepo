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


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hu.norbisan.jpa.pizzafx.core.dao.CityDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.CityDaoFactory;
import hu.norbisan.jpa.pizzafx.core.entity.City;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CityDaoTest {

	private static final String PERSISTENCE_UNIT_NAME = "hu.norbisan.jpa.pizzafx_test_pu";
	private City city1;
	private City city2;
	private Long cityId1;
	private Long cityId2;
	private CityDaoFactory factory;
	private CityDao cityDao;

	@Test
	public void test1Create() {
		factory = CityDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		cityDao = factory.newCityDao();

		city1 = new City();
		city1.setName("Debrecen");
		city1.setShippingCost(0);
		Assert.assertNull("Az Id null kell legyen mentés előtt!", city1.getId());
		City c1 = cityDao.create(city1);
		Assert.assertNotNull("Az Id nem lehet null mentés után!", c1.getId());
		factory.close();
	}

	@Test
	public void test2FindOne() {
		factory = CityDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		cityDao = factory.newCityDao();

		city1 = new City();
		city1.setName("Debrecen");
		city1.setShippingCost(200);
		cityDao.create(city1);
		cityId1 = city1.getId();

		City c = cityDao.findOne(cityId1);
		System.out.println("Város Id: " + c.getId());
		System.out.println("Város név: " + c.getName());
		System.out.println("Város szállítási díj: " + c.getShippingCost());
		System.out.println();
		Assert.assertEquals("A város neve Debrecen helyett: " + c.getName(), "Debrecen", c.getName());
		Assert.assertEquals("A szállítási díj 200 helyett: ", new Integer(200), c.getShippingCost());

		factory.close();
	}

	@Test
	public void test3FindAll() {

		factory = CityDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		cityDao = factory.newCityDao();

		city1 = new City();
		city1.setName("Hosszúpályi");
		city1.setShippingCost(200);
		cityDao.create(city1);
		cityId1 = city1.getId();

		city2 = new City();
		city2.setName("Hajdúböszörmény");
		city2.setShippingCost(1000);
		cityDao.create(city2);
		cityId2 = city2.getId();

		List<City> cities = cityDao.findAll();
		// Az előző tesztek is beszúrtak 2 elemet az adatbázisba!
		Assert.assertTrue("A lista elemszáma 4 helyett: " + cities.size(), 4 == cities.size());
		for (City c : cities) {
			if (c.getId().equals(cityId1)) {
				Assert.assertEquals("A város neve Hosszúpályi helyett: " + c.getName(), "Hosszúpályi", c.getName());
				Assert.assertEquals("A város szállítási díja 200 helyett: " + c.getShippingCost(), new Integer(200),
						c.getShippingCost());
			}
			if (c.getId().equals(cityId2)) {
				Assert.assertEquals("A város neve Hajdúböszörmény helyett: " + c.getName(), "Hajdúböszörmény",
						c.getName());
				Assert.assertEquals("A város szállítási díja 1000 helyett: " + c.getShippingCost(), new Integer(1000),
						c.getShippingCost());
			}
		}

		factory.close();
	}
	
	@Test
	public void test4CreateAll() {
		factory = CityDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		cityDao = factory.newCityDao();
		List<City> cities = new ArrayList<>();
		city1 = new City();
		city1.setName("Debrecen");
		city1.setShippingCost(0);
		cities.add(city1);

		city2 = new City();
		city2.setName("Hosszúpályi");
		city2.setShippingCost(500);
		cities.add(city2);
		for (City c : cities) {
			Assert.assertNull("Az id mentés előtt null kell legyen!", c.getId());
		}

		List<City> persisted_cities = cityDao.createAll(cities);

		for (City c : persisted_cities) {
			Assert.assertNotNull("Az id mentés után nem lehet null!", c.getId());
			if ("Hosszúpályi".equals(c.getName())) {
				Assert.assertTrue(new Integer(500).equals(c.getShippingCost()));
			} else if ("Debrecen".equals(c.getName())) {
				Assert.assertTrue(new Integer(0).equals(c.getShippingCost()));
			} else {
				Assert.fail("Az entitások nem megfelelőek!");
			}
		}

		factory.close();

	}

}
