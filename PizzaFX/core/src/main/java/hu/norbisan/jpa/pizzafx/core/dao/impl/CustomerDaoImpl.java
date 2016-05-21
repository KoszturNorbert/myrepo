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


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import hu.norbisan.jpa.pizzafx.core.dao.CustomerDao;
import hu.norbisan.jpa.pizzafx.core.entity.Customer;

public class CustomerDaoImpl implements CustomerDao {

	private EntityManager manager;

	@Override
	public Customer create(Customer customer) {

		manager.getTransaction().begin();
		manager.persist(customer);
		manager.getTransaction().commit();
		return customer;
	}

	@Override
	public Customer findOne(Long id) {
		Customer c = null;
		try {
			c = manager.find(Customer.class, id);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return c;
	}

	@Override
	public List<Customer> findAll() {
		List<Customer> customers;

		TypedQuery<Customer> query = manager.createNamedQuery("Customer.findAll", Customer.class);
		customers = query.getResultList();
		return customers;
	}

	@Override
	public Customer findByEmail(String email) {
		Customer c = null;
		try {
			TypedQuery<Customer> query = manager.createNamedQuery("Customer.findByEmail", Customer.class);
			query.setParameter("email", email);
			c = query.getSingleResult();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return c;
	}

	@Override
	public Customer update(Long id, Customer customer) {
		manager.getTransaction().begin();
		Customer c = manager.find(Customer.class, id);
		c.setAddress(customer.getAddress());
		c.setEmail(customer.getEmail());
		c.setName(customer.getName());
		c.setOrdersCount(customer.getOrdersCount());
		c.setPassword(customer.getPassword());
		c.setPhoneNumber(customer.getPhoneNumber());
		manager.getTransaction().commit();
		return c;
	}

	public CustomerDaoImpl(EntityManager manager) {
		super();
		this.manager = manager;
	}

}
