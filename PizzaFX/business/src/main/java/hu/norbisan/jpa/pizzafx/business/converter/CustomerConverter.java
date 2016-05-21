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

import hu.norbisan.jpa.pizzafx.business.vo.CustomerVO;
import hu.norbisan.jpa.pizzafx.core.entity.Customer;

public class CustomerConverter {

	private AddressConverter converter;

	public CustomerVO toVO(Customer customer) {

		if (customer == null) {
			return null;
		}

		CustomerVO vo = new CustomerVO();
		vo.setId(customer.getId());
		vo.setEmail(customer.getEmail());
		vo.setPassword(customer.getPassword());
		vo.setPhoneNumber(customer.getPhoneNumber());
		vo.setName(customer.getName());
		vo.setOrdersCount(customer.getOrdersCount());
		vo.setAddress(converter.toVO(customer.getAddress()));

		return vo;
	}

	public Customer toEntity(CustomerVO vo) {

		if (vo == null) {
			return null;
		}

		Customer c = new Customer();
		c.setId(vo.getId());
		c.setEmail(vo.getEmail());
		c.setName(vo.getName());
		c.setPassword(vo.getPassword());
		c.setAddress(converter.toEntity(vo.getAddress()));
		c.setPhoneNumber(vo.getPhoneNumber());
		c.setOrdersCount(vo.getOrdersCount());

		return c;
	}

	public List<CustomerVO> toVO(List<Customer> customers) {

		if (customers == null) {
			return null;
		}

		List<CustomerVO> vos = new ArrayList<>();

		for (Customer c : customers) {
			vos.add(toVO(c));
		}

		return vos;
	}

	public List<Customer> toEntity(List<CustomerVO> vos) {

		if (vos == null) {
			return null;
		}

		List<Customer> customers = new ArrayList<>();

		for (CustomerVO vo : vos) {
			customers.add(toEntity(vo));
		}

		return customers;
	}

	public CustomerConverter() {
		this.converter = new AddressConverter();
	}

}
