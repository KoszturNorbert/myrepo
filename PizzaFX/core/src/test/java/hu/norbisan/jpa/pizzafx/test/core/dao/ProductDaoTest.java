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

import hu.norbisan.jpa.pizzafx.core.dao.ProductDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.ProductDaoFactory;
import hu.norbisan.jpa.pizzafx.core.entity.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest {

	private static final String PERSISTENCE_UNIT_NAME = "hu.norbisan.jpa.pizzafx_test_pu";
	private Product product1;
	private Product product2;
	private Long productId1;
	private Long productId2;
	private ProductDaoFactory factory;
	private ProductDao productDao;

	@Test
	public void test1Create() {
		factory = ProductDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		productDao = factory.newProductDao();

		product1 = new Product();
		product1.setName("Valami");
		product1.setPrice(1000);
		product1.setToppings("Paradicsom, hús");
		Assert.assertNull("Az id null kell legyen mentés előtt!", product1.getId());
		Product p1 = productDao.create(product1);
		Assert.assertNotNull("Az id nem lehet null mentés után!", p1.getId());
		factory.close();
	}

	@Test
	public void test2FindOne() {
		factory = ProductDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		productDao = factory.newProductDao();
		product1 = new Product();
		product1.setName("Pizza");
		product1.setPrice(900);
		product1.setToppings("Paradicsom, hús, sajt");
		productDao.create(product1);
		productId1 = product1.getId();

		Product p = productDao.findOne(productId1);
		Assert.assertEquals("Az id " + productId1 + " helyett: " + p.getId(), productId1, p.getId());
		Assert.assertEquals("A terméknév Pizza helyett: " + p.getName(), "Pizza", p.getName());
		Assert.assertEquals("Az ár 900 helyett: " + p.getPrice(), new Integer(900), p.getPrice());
		Assert.assertEquals("A feltétlista nem megfelelő!", "Paradicsom, hús, sajt", p.getToppings());

		factory.close();
	}

	@Test
	public void test3FindAll() {

		factory = ProductDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		productDao = factory.newProductDao();

		product1 = new Product();
		product1.setName("Pizza3");
		productDao.create(product1);
		productId1 = product1.getId();
		
		product2 = new Product();
		product2.setName("Pizza4");
		productDao.create(product2);
		productId2 = product2.getId();
		
		List<Product> products = productDao.findAll();
		
		for(Product p : products){
			if(productId1.equals(p.getId())){
				Assert.assertEquals("A terméknév Pizza3 helyett: " + p.getName(),"Pizza3", p.getName());
			}
			else if(productId2.equals(p.getId())){
				Assert.assertEquals("A terméknév Pizza4 helyett: " + p.getName(),"Pizza4", p.getName());
			}
		}
		factory.close();
	}
	
	@Test
	public void test4CreateAll(){
		factory = ProductDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		productDao = factory.newProductDao();
		List<Product> products = new ArrayList<>();
		product1 = new Product();
		product1.setName("Sonkás");
		product1.setPrice(800);
		products.add(product1);
		
		product2 = new Product();
		product2.setName("Sajtos");
		product2.setPrice(900);
		products.add(product2);
		
		for(Product p : products){
			Assert.assertNull("Az id null kell legyen mentés előtt!", p.getId());
		}
		
		List<Product> persisted_products = productDao.createAll(products);
		
		for(Product p : persisted_products){
			Assert.assertNotNull("Az id nem lehet null mentés után!",p.getId());
			
			if("Sajtos".equals(p.getName())){
				Assert.assertEquals(new Integer(900), p.getPrice());
			}
			else if("Sonkás".equals(p.getName())){
				Assert.assertEquals(new Integer(800), p.getPrice());
			}
			else{
				Assert.fail("Az entitások nem megfelelőek!");
			}
		}
		factory.close();
	}

}
