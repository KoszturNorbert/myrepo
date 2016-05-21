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

import hu.norbisan.jpa.pizzafx.business.converter.CustomerConverter;
import hu.norbisan.jpa.pizzafx.business.service.CustomerService;
import hu.norbisan.jpa.pizzafx.business.vo.CustomerVO;
import hu.norbisan.jpa.pizzafx.core.dao.CustomerDao;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;
	private CustomerConverter converter;

	@Override
	public CustomerVO saveCustomer(CustomerVO customer) {
		return converter.toVO(customerDao.create(converter.toEntity(customer)));
	}

	@Override
	public CustomerVO findCustomerById(Long id) {
		return converter.toVO(customerDao.findOne(id));
	}

	@Override
	public CustomerVO findCustomerByEmail(String email) {
		return converter.toVO(customerDao.findByEmail(email));
	}

	@Override
	public List<CustomerVO> findAllCustomer() {
		return converter.toVO(customerDao.findAll());
	}

	@Override
	public CustomerVO incrementOrdersCount(CustomerVO customer) {
		customer.setOrdersCount(customer.getOrdersCount() + 1);
		return converter.toVO(customerDao.update(customer.getId(), converter.toEntity(customer)));
	}

	public CustomerServiceImpl(CustomerDao customerDao) {
		super();
		this.customerDao = customerDao;
		this.converter = new CustomerConverter();
	}

}
