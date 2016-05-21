package hu.norbisan.jpa.pizzafx.core.dao.impl;

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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import hu.norbisan.jpa.pizzafx.core.dao.DatabaseInitDao;
import hu.norbisan.jpa.pizzafx.core.entity.City;
import hu.norbisan.jpa.pizzafx.core.entity.Product;

public class DatabaseInitDaoXmlImpl implements DatabaseInitDao {

	@Override
	public List<Product> readProductsFromFile(String fileName) {
		List<Product> ps = new ArrayList<>();
		Product p;
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
			Element root = doc.getDocumentElement();
			NodeList products = root.getElementsByTagName("pizza");

			for (int i = 0; i < products.getLength(); i++) {
				p = new Product();
				p.setName(((Element) products.item(i)).getAttribute("name"));
				p.setPrice(Integer.valueOf(((Element) products.item(i)).getAttribute("price")));
				p.setToppings(((Element) products.item(i)).getElementsByTagName("toppings").item(0).getTextContent());
				ps.add(p);
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return new ArrayList<Product>();
		}

		return ps;
	}

	@Override
	public List<City> readCitiesFromFile(String fileName) {
		List<City> cs = new ArrayList<>();
		City c;

		Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
			Element root = doc.getDocumentElement();
			NodeList cities = root.getElementsByTagName("city");

			for (int i = 0; i < cities.getLength(); i++) {
				c = new City();
				c.setName(((Element) cities.item(i)).getAttribute("name"));
				c.setShippingCost(Integer.valueOf(
						((Element) cities.item(i)).getElementsByTagName("shippingCost").item(0).getTextContent()));
				cs.add(c);
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return new ArrayList<City>();
		}

		return cs;
	}

}
