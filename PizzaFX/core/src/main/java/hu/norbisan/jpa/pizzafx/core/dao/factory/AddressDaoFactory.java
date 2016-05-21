package hu.norbisan.jpa.pizzafx.core.dao.factory;

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


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.norbisan.jpa.pizzafx.core.dao.AddressDao;
import hu.norbisan.jpa.pizzafx.core.dao.impl.AddressDaoImpl;

public class AddressDaoFactory {

	private String persistenceUnitName;
	private EntityManagerFactory factory;
	private EntityManager manager;

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	private AddressDaoFactory() {
	}

	public static AddressDaoFactory newInstance() {
		return new AddressDaoFactory();
	}

	public AddressDao newAddressDao() {
		if (persistenceUnitName != null) {
			factory = Persistence.createEntityManagerFactory(persistenceUnitName);
			manager = factory.createEntityManager();
			return new AddressDaoImpl(manager);
		} else {
			return null;
		}
	}

	public void close() {
		if (manager != null) {
			manager.close();
		}

		if (factory != null) {
			factory.close();
		}
	}

}
