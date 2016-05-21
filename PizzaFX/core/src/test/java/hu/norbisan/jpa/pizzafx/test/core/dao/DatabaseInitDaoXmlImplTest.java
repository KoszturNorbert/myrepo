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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hu.norbisan.jpa.pizzafx.core.dao.DatabaseInitDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.DataBaseInitDaoFactory;
import hu.norbisan.jpa.pizzafx.core.dao.factory.DataBaseInitDaoFactory.SourceTypes;
import hu.norbisan.jpa.pizzafx.core.entity.City;
import hu.norbisan.jpa.pizzafx.core.entity.Product;

public class DatabaseInitDaoXmlImplTest {

	private static final String CITIES_FILE_NAME = "Cities.xml";
	private static final String PRODUCTS_FILE_NAME = "Products.xml";
	private DataBaseInitDaoFactory factory;
	private DatabaseInitDao initDao;

	@Test
	public void testReadProductsFromFile() {
		factory = DataBaseInitDaoFactory.newInstance();
		factory.setSourceType(SourceTypes.XML);
		initDao = factory.newDatabaseInitDao();

		List<Product> products = initDao
				.readProductsFromFile(getClass().getClassLoader().getResource(PRODUCTS_FILE_NAME).getFile());

		Assert.assertTrue("A lista mérete 2 helyett: " + products.size(), 2 == products.size());
		assertTrue("A pizza név nem megfelelő!",
				"Margaréta".equals(products.get(0).getName()) || "Sonkás".equals(products.get(0).getName()));
		assertTrue("A pizza név nem megfelelő!",
				"Margaréta".equals(products.get(1).getName()) || "Sonkás".equals(products.get(1).getName()));

		for (Product p : products) {
			if ("Margaréta".equals(p.getName())) {
				assertEquals("Az ár 600 helyett: " + p.getPrice(), new Integer(600), p.getPrice());
				assertEquals("A feltétlista nem megfelelő!", "Paradicsom szósz, sajt", p.getToppings());
			} else if ("Sonkás".equals(p.getName())) {
				assertEquals("Az ár 890 helyett: " + p.getPrice(), new Integer(890), p.getPrice());
				assertEquals("A feltétlista nem megfelelő!", "Paradicsom szósz, sajt, sonka", p.getToppings());
			}
		}
	}

	@Test
	public void testReadCitiesFromFile() {
		factory = DataBaseInitDaoFactory.newInstance();
		factory.setSourceType(SourceTypes.XML);
		initDao = factory.newDatabaseInitDao();

		List<City> cities = initDao
				.readCitiesFromFile(getClass().getClassLoader().getResource(CITIES_FILE_NAME).getFile());

		Assert.assertTrue("A lista mérete 2 helyett: " + cities.size(), 2 == cities.size());
		assertTrue("A város név nem megfelelő!",
				"Debrecen".equals(cities.get(0).getName()) || "Debrecen-Bánk".equals(cities.get(0).getName()));
		assertTrue("A város név nem megfelelő!",
				"Debrecen".equals(cities.get(1).getName()) || "Debrecen-Bánk".equals(cities.get(1).getName()));

		for (City c : cities) {

			if ("Debrecen".equals(c.getName())) {
				assertEquals("A szállítási díj 0 helyett: " + c.getShippingCost(), new Integer(0), c.getShippingCost());
			} else if ("Debrecen-Bánk".equals(c.getName())) {
				assertEquals("A szállítási díj 400 helyett: " + c.getShippingCost(), new Integer(400),
						c.getShippingCost());
			}
		}
	}

}
