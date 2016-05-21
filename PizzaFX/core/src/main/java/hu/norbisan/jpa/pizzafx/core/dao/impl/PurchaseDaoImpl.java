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

import hu.norbisan.jpa.pizzafx.core.dao.PurchaseDao;
import hu.norbisan.jpa.pizzafx.core.entity.Purchase;

public class PurchaseDaoImpl implements PurchaseDao {

	private EntityManager manager;

	@Override
	public Purchase create(Purchase purchase) {
		
		manager.getTransaction().begin();
		manager.persist(purchase);
		manager.getTransaction().commit();
		return purchase;
	}

	@Override
	public Purchase findOne(Long id) {
		Purchase p;
		p = manager.find(Purchase.class, id);
		return p;
	}

	@Override
	public List<Purchase> findAll() {
		List<Purchase> purchases;
		TypedQuery<Purchase> query = manager.createNamedQuery("Purchase.findAll", Purchase.class);
		purchases = query.getResultList();
		return purchases;
	}

	public PurchaseDaoImpl(EntityManager manager) {
		super();
		this.manager = manager;
	}

}
