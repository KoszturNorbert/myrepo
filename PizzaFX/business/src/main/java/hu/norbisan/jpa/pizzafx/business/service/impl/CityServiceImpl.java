package hu.norbisan.jpa.pizzafx.business.service.impl;

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


import java.util.List;

import hu.norbisan.jpa.pizzafx.business.converter.CityConverter;
import hu.norbisan.jpa.pizzafx.business.service.CityService;
import hu.norbisan.jpa.pizzafx.business.vo.CityVO;
import hu.norbisan.jpa.pizzafx.core.dao.CityDao;

public class CityServiceImpl implements CityService {

	private CityDao cityDao;
	private CityConverter converter;

	@Override
	public CityVO saveCity(CityVO city) {
		return converter.toVO(cityDao.create(converter.toEntity(city)));
	}

	@Override
	public List<CityVO> saveAllCities(List<CityVO> cities) {
		return converter.toVO(cityDao.createAll(converter.toEntity(cities)));
	}

	@Override
	public CityVO findCityById(Long id) {
		return converter.toVO(cityDao.findOne(id));
	}

	@Override
	public List<CityVO> findAllCity() {
		return converter.toVO(cityDao.findAll());
	}

	public CityServiceImpl(CityDao cityDao) {
		super();
		this.cityDao = cityDao;
		this.converter = new CityConverter();
	}

}
