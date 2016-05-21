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


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import hu.norbisan.jpa.pizzafx.core.dao.ProductDao;
import hu.norbisan.jpa.pizzafx.core.entity.Product;

public class ProductDaoImpl implements ProductDao {

	private EntityManager manager;

	@Override
	public Product create(Product product) {

		manager.getTransaction().begin();
		manager.persist(product);
		manager.getTransaction().commit();
		return product;
	}

	@Override
	public List<Product> createAll(List<Product> products) {
		
		if(products == null){
			return null;
		}
		
		List<Product> result = new ArrayList<Product>();
		manager.getTransaction().begin();
		for(Product p : products){
			manager.persist(p);
			result.add(p);
		}
		manager.getTransaction().commit();
		return result;
	}

	@Override
	public Product findOne(Long id) {
		Product p;
		p = manager.find(Product.class, id);
		return p;
	}

	@Override
	public List<Product> findAll() {

		List<Product> products;
		TypedQuery<Product> query = manager.createNamedQuery("Product.findAll", Product.class);
		products = query.getResultList();
		return products;
	}

	public ProductDaoImpl(EntityManager manager) {
		super();
		this.manager = manager;
	}

}
