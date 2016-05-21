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

import hu.norbisan.jpa.pizzafx.business.converter.ProductConverter;
import hu.norbisan.jpa.pizzafx.business.service.ProductService;
import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;
import hu.norbisan.jpa.pizzafx.core.dao.ProductDao;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	private ProductConverter converter;

	@Override
	public ProductVO saveProduct(ProductVO product) {
		return converter.toVO(productDao.create(converter.toEntity(product)));
	}

	@Override
	public List<ProductVO> saveAllProducts(List<ProductVO> products) {
		return converter.toVO(productDao.createAll(converter.toEntity(products)));
	}

	@Override
	public ProductVO findProductById(Long id) {
		return converter.toVO(productDao.findOne(id));
	}

	@Override
	public List<ProductVO> findAllProduct() {
		return converter.toVO(productDao.findAll());
	}

	public ProductServiceImpl(ProductDao productDao) {
		super();
		this.productDao = productDao;
		this.converter = new ProductConverter();
	}

}
