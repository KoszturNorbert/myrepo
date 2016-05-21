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


import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hu.norbisan.jpa.pizzafx.core.dao.PurchaseDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.PurchaseDaoFactory;
import hu.norbisan.jpa.pizzafx.core.entity.Purchase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PurchaseDaoTest {

	private static final String PERSISTENCE_UNIT_NAME = "hu.norbisan.jpa.pizzafx_test_pu";
	private Purchase purchase1;
	private Purchase purchase2;
	private Long purchaseId1;
	private Long purchaseId2;
	private PurchaseDaoFactory factory;
	private PurchaseDao purchaseDao;

	@Test
	public void test1Create() {
		factory = PurchaseDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		purchaseDao = factory.newPurchaseDao();

		purchase1 = new Purchase();
		purchase1.setDate(LocalDateTime.now());
		purchase1.setTotalPrice(1000L);
		Assert.assertNull("Az id mentés előtt null kell legyen!", purchase1.getId());
		Purchase p1 = purchaseDao.create(purchase1);
		Assert.assertNotNull("Az id mentés után nem lehet null!", p1.getId());

		factory.close();

	}

	@Test
	public void test2FindOne() {
		factory = PurchaseDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		purchaseDao = factory.newPurchaseDao();

		purchase1 = new Purchase();
		LocalDateTime date = LocalDateTime.now();
		purchase1.setDate(date);
		purchase1.setTotalPrice(1000L);
		purchaseDao.create(purchase1);
		purchaseId1 = purchase1.getId();
		
		Purchase p = purchaseDao.findOne(purchaseId1);
		Assert.assertEquals("Az id " + purchaseId1 + " helyett. " + p.getId(),purchaseId1, p.getId());
		Assert.assertEquals("A dátum " + date + " helyett: " + p.getDate(),date, p.getDate());
		Assert.assertEquals("A total price 1000 helyett: " + p.getTotalPrice(),new Long(1000), p.getTotalPrice());
		
		factory.close();
	}

	@Test
	public void test3FindAll() {
		factory = PurchaseDaoFactory.newInstance();
		factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		purchaseDao = factory.newPurchaseDao();

		purchase1 = new Purchase();
		purchase1.setTotalPrice(1200L);
		purchaseDao.create(purchase1);
		purchaseId1 = purchase1.getId();
		
		purchase2 = new Purchase();
		purchase2.setTotalPrice(500L);
		purchaseDao.create(purchase2);
		purchaseId2 = purchase2.getId();
		
		List<Purchase> purchases = purchaseDao.findAll();
		
		Assert.assertTrue("A találat lista elemszáma 4 helyett: " + purchases.size() ,4 == purchases.size());
		for(Purchase p : purchases){
			if(purchaseId1.equals(p.getId())){
				Assert.assertEquals(new Long(1200), p.getTotalPrice());
			}
			else if(purchaseId2.equals(p.getTotalPrice())){
				Assert.assertEquals(new Long(500), p.getTotalPrice());
			}
		}
		
		factory.close();
	}

}
