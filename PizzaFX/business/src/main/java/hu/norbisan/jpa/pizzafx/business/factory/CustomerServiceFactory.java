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


import hu.norbisan.jpa.pizzafx.business.service.CustomerService;
import hu.norbisan.jpa.pizzafx.business.service.impl.CustomerServiceImpl;
import hu.norbisan.jpa.pizzafx.core.dao.CustomerDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.CustomerDaoFactory;

public class CustomerServiceFactory {

	private String persistenceUnitName;
	private CustomerDaoFactory factory;
	private CustomerDao customerDao;

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	private CustomerServiceFactory() {
	}

	public static CustomerServiceFactory newInstance() {
		return new CustomerServiceFactory();
	}

	public CustomerService newCustomerService() {

		if (this.persistenceUnitName != null) {
			factory = CustomerDaoFactory.newInstance();
			factory.setPersistenceUnitName(persistenceUnitName);
			customerDao = factory.newCustomerDao();
			return new CustomerServiceImpl(customerDao);
		} else {
			return null;
		}
	}
	
	public void close(){
		this.factory.close();
	}
}
