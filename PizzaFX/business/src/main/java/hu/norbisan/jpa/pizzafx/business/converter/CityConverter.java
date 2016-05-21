package hu.norbisan.jpa.pizzafx.business.converter;

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


import java.util.ArrayList;
import java.util.List;

import hu.norbisan.jpa.pizzafx.business.vo.CityVO;
import hu.norbisan.jpa.pizzafx.core.entity.City;

public class CityConverter {

	public City toEntity(CityVO vo) {
		if (vo == null) {
			return null;
		}
		City city = new City();
		city.setId(vo.getId());
		city.setName(vo.getName());
		city.setShippingCost(vo.getShippingCost());
		return city;
	}

	public CityVO toVO(City city) {

		if (city == null) {
			return null;
		}

		CityVO vo = new CityVO();
		vo.setId(city.getId());
		vo.setName(city.getName());
		vo.setShippingCost(city.getShippingCost());
		return vo;
	}

	public List<City> toEntity(List<CityVO> vos) {

		if (vos == null) {
			return null;
		}

		List<City> cities = new ArrayList<>();

		for (CityVO vo : vos) {
			cities.add(toEntity(vo));
		}
		return cities;
	}

	public List<CityVO> toVO(List<City> cities) {

		if (cities == null) {
			return null;
		}

		List<CityVO> vos = new ArrayList<>();

		for (City city : cities) {
			vos.add(toVO(city));
		}

		return vos;
	}

}
