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
import hu.norbisan.jpa.pizzafx.business.converter.ProductConverter;
import hu.norbisan.jpa.pizzafx.business.service.DatabaseInitService;
import hu.norbisan.jpa.pizzafx.business.vo.CityVO;
import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;
import hu.norbisan.jpa.pizzafx.core.dao.DatabaseInitDao;

public class DatabaseInitServiceImpl implements DatabaseInitService {

	private DatabaseInitDao initDao;
	private ProductConverter productConverter;
	private CityConverter cityConverter;

	@Override
	public List<ProductVO> readProductsFromFile(String fileName) {
		return productConverter.toVO(initDao.readProductsFromFile(fileName));
	}

	@Override
	public List<CityVO> readCitiesFromFile(String fileName) {
		return cityConverter.toVO(initDao.readCitiesFromFile(fileName));
	}

	public DatabaseInitServiceImpl(DatabaseInitDao initDao) {
		super();
		this.initDao = initDao;
		this.productConverter = new ProductConverter();
		this.cityConverter = new CityConverter();
	}

}
