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

import hu.norbisan.jpa.pizzafx.core.dao.CityDao;
import hu.norbisan.jpa.pizzafx.core.entity.City;

public class CityDaoImpl implements CityDao {

	private EntityManager manager;

	@Override
	public City create(City city) {

		manager.getTransaction().begin();
		manager.persist(city);
		manager.getTransaction().commit();
		return city;
	}

	@Override
	public List<City> createAll(List<City> cities) {
		if(cities == null){
			return null;
		}
		
		List<City> result = new ArrayList<>();
		manager.getTransaction().begin();
		for(City c : cities){
			manager.persist(c);
			result.add(c);
		}
		manager.getTransaction().commit();
		return result;
	}

	@Override
	public City findOne(Long id) {
		City city = manager.find(City.class, id);
		return city;
	}

	@Override
	public List<City> findAll() {
		List<City> cities;
		TypedQuery<City> query = manager.createNamedQuery("City.findAll", City.class);
		cities = query.getResultList();
		return cities;
	}

	public CityDaoImpl(EntityManager manager) {
		super();
		this.manager = manager;
	}

}
