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


import hu.norbisan.jpa.pizzafx.business.service.AddressService;
import hu.norbisan.jpa.pizzafx.business.service.impl.AddressServiceImpl;
import hu.norbisan.jpa.pizzafx.core.dao.AddressDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.AddressDaoFactory;

public class AddressServiceFactory {

	private String persistenceUnitName;
	private AddressDaoFactory factory;
	private AddressDao addressDao;

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	private AddressServiceFactory() {
	}

	public static AddressServiceFactory newInstance() {
		return new AddressServiceFactory();
	}
	
	public AddressService newAddressService(){
		
		if(this.persistenceUnitName != null){
			factory = AddressDaoFactory.newInstance();
			factory.setPersistenceUnitName(persistenceUnitName);
			addressDao = factory.newAddressDao();
			return new AddressServiceImpl(addressDao);
		}
		else{
			return null;
		}
	}
	
	public void close(){
		factory.close();
	}

}
