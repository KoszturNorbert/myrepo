package hu.norbisan.jpa.pizzafx.business.factory;

/*
 * #%L
 * business
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


import hu.norbisan.jpa.pizzafx.business.service.PurchaseService;
import hu.norbisan.jpa.pizzafx.business.service.impl.PurchaseServiceImpl;
import hu.norbisan.jpa.pizzafx.core.dao.PurchaseDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.PurchaseDaoFactory;

public class PurchaseServiceFactory {

	private String persistenceUnitName;
	private PurchaseDaoFactory factory;
	private PurchaseDao purchaseDao;

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	private PurchaseServiceFactory() {
	}

	public static PurchaseServiceFactory newInstance() {
		return new PurchaseServiceFactory();
	}

	public PurchaseService newPurchaseService() {

		if (this.persistenceUnitName != null) {
			factory = PurchaseDaoFactory.newInstance();
			factory.setPersistenceUnitName(persistenceUnitName);
			purchaseDao = factory.newPurchaseDao();
			return new PurchaseServiceImpl(purchaseDao);
		} else {
			return null;
		}
	}

	public void close() {
		this.factory.close();
	}

}
