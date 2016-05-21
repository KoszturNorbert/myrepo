package hu.norbisan.jpa.pizzafx.business.vo;

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


import java.time.LocalDateTime;
import java.util.List;

public class PurchaseVO {

	private Long id;
	private AddressVO shipping_address;
	private CustomerVO customer;
	private List<ProductVO> products;
	private LocalDateTime date;
	private Long totalPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AddressVO getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(AddressVO shipping_address) {
		this.shipping_address = shipping_address;
	}

	public CustomerVO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}

	public List<ProductVO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductVO> products) {
		this.products = products;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

}
