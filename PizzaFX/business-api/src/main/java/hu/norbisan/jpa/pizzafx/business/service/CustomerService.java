package hu.norbisan.jpa.pizzafx.business.service;

/*
 * #%L
 * business-api
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

import hu.norbisan.jpa.pizzafx.business.vo.CustomerVO;

public interface CustomerService {

	CustomerVO saveCustomer(CustomerVO customer);

	CustomerVO findCustomerById(Long id);

	CustomerVO findCustomerByEmail(String email);

	List<CustomerVO> findAllCustomer();
	
	CustomerVO incrementOrdersCount(CustomerVO customer);

}
