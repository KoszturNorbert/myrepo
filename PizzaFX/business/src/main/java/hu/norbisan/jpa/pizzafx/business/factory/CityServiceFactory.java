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


import hu.norbisan.jpa.pizzafx.business.service.CityService;
import hu.norbisan.jpa.pizzafx.business.service.impl.CityServiceImpl;
import hu.norbisan.jpa.pizzafx.core.dao.CityDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.CityDaoFactory;

public class CityServiceFactory {

	private String persistenceUnitName;
	private CityDaoFactory factory;
	private CityDao cityDao;

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	private CityServiceFactory() {
	}

	public static CityServiceFactory newInstance() {
		return new CityServiceFactory();
	}
	
	public CityService newCityService(){
		if(this.persistenceUnitName != null){
			factory = CityDaoFactory.newInstance();
			factory.setPersistenceUnitName(this.persistenceUnitName);
			cityDao = factory.newCityDao();
			return new CityServiceImpl(cityDao);
		}
		else{
			return null;
		}
	}
	
	public void close(){
		factory.close();
	}

}
