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


import javax.persistence.EntityManager;

import hu.norbisan.jpa.pizzafx.core.dao.AddressDao;
import hu.norbisan.jpa.pizzafx.core.entity.Address;

public class AddressDaoImpl implements AddressDao {

	private EntityManager manager;

	@Override
	public Address create(Address address) {
		manager.getTransaction().begin();
		manager.persist(address);
		manager.getTransaction().commit();
		return address;
	}

	@Override
	public Address findOne(Long id) {
		Address address;
		address = manager.find(Address.class, id);
		return address;
	}

	public AddressDaoImpl(EntityManager manager) {
		super();
		this.manager = manager;
	}

}
