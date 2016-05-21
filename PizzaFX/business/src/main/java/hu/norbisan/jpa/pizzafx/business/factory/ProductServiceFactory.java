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


import hu.norbisan.jpa.pizzafx.business.service.ProductService;
import hu.norbisan.jpa.pizzafx.business.service.impl.ProductServiceImpl;
import hu.norbisan.jpa.pizzafx.core.dao.ProductDao;
import hu.norbisan.jpa.pizzafx.core.dao.factory.ProductDaoFactory;

public class ProductServiceFactory {

	private String persistenceUnitName;
	private ProductDaoFactory factory;
	private ProductDao productDao;

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	private ProductServiceFactory() {
	}

	public static ProductServiceFactory newInstance() {
		return new ProductServiceFactory();
	}

	public ProductService newProductService() {

		if (this.persistenceUnitName != null) {
			factory = ProductDaoFactory.newInstance();
			factory.setPersistenceUnitName(persistenceUnitName);
			productDao = factory.newProductDao();
			return new ProductServiceImpl(productDao);
		} else {
			return null;
		}
	}

	public void close() {
		this.factory.close();
	}

}
