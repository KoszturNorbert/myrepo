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

import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;
import hu.norbisan.jpa.pizzafx.core.entity.Product;

public class ProductConverter {

	public Product toEntity(ProductVO vo) {

		if (vo == null) {
			return null;
		}

		Product p = new Product();
		p.setId(vo.getId());
		p.setName(vo.getName());
		p.setPrice(vo.getPrice());
		p.setToppings(vo.getToppings());

		return p;
	}

	public ProductVO toVO(Product product) {

		if (product == null) {
			return null;
		}

		ProductVO vo = new ProductVO();
		vo.setId(product.getId());
		vo.setName(product.getName());
		vo.setPrice(product.getPrice());
		vo.setToppings(product.getToppings());

		return vo;
	}

	public List<Product> toEntity(List<ProductVO> vos) {

		if (vos == null) {
			return null;
		}

		List<Product> products = new ArrayList<>();

		for (ProductVO vo : vos) {
			products.add(toEntity(vo));
		}

		return products;
	}

	public List<ProductVO> toVO(List<Product> products) {

		if (products == null) {
			return null;
		}

		List<ProductVO> vos = new ArrayList<>();

		for (Product p : products) {
			vos.add(toVO(p));
		}

		return vos;
	}

}
