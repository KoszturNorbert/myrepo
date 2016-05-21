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

import hu.norbisan.jpa.pizzafx.business.vo.PurchaseVO;
import hu.norbisan.jpa.pizzafx.core.entity.Purchase;

public class PurchaseConverter {

	private CustomerConverter customerConverter;
	private AddressConverter addressConverter;
	private ProductConverter productConverter;

	public Purchase toEntity(PurchaseVO vo) {

		if (vo == null) {
			return null;
		}

		Purchase p = new Purchase();
		p.setId(vo.getId());
		p.setDate(vo.getDate());
		p.setTotalPrice(vo.getTotalPrice());
		p.setCustomer(customerConverter.toEntity(vo.getCustomer()));
		p.setShipping_address(addressConverter.toEntity(vo.getShipping_address()));
		p.setProducts(productConverter.toEntity(vo.getProducts()));

		return p;
	}

	public PurchaseVO toVO(Purchase purchase) {

		if (purchase == null) {
			return null;
		}

		PurchaseVO vo = new PurchaseVO();
		vo.setId(purchase.getId());
		vo.setDate(purchase.getDate());
		vo.setTotalPrice(purchase.getTotalPrice());
		vo.setCustomer(customerConverter.toVO(purchase.getCustomer()));
		vo.setShipping_address(addressConverter.toVO(purchase.getShipping_address()));
		vo.setProducts(productConverter.toVO(purchase.getProducts()));

		return vo;
	}

	public List<Purchase> toEntity(List<PurchaseVO> vos) {

		if (vos == null) {
			return null;
		}

		List<Purchase> ps = new ArrayList<>();

		for (PurchaseVO vo : vos) {
			ps.add(toEntity(vo));
		}

		return ps;
	}

	public List<PurchaseVO> toVO(List<Purchase> purchases) {

		if (purchases == null) {
			return null;
		}

		List<PurchaseVO> vos = new ArrayList<>();

		for (Purchase p : purchases) {
			vos.add(toVO(p));
		}

		return vos;
	}

	public PurchaseConverter() {
		this.customerConverter = new CustomerConverter();
		this.addressConverter = new AddressConverter();
		this.productConverter = new ProductConverter();
	}

}
