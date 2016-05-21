package hu.norbisan.jpa.pizzafx.test.business;

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


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hu.norbisan.jpa.pizzafx.business.PriceCalculator;
import hu.norbisan.jpa.pizzafx.business.vo.AddressVO;
import hu.norbisan.jpa.pizzafx.business.vo.CityVO;
import hu.norbisan.jpa.pizzafx.business.vo.CustomerVO;
import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;

public class PriceCalculatorTest {

	@Test
	public void testRoundToFiveRoundDown() {
		long number = 101;
		long result = PriceCalculator.roundToFive(number);
		Assert.assertEquals("Az eredmény 100 helyett: " + result, 100, result);
	}

	@Test
	public void testRoundToFiveRoundUp() {
		long number = 13;
		long result = PriceCalculator.roundToFive(number);
		Assert.assertEquals("Az eredmény 15 helyett: " + result, 15, result);
	}

	@Test
	public void testRoundToFiveRound() {
		long number = 100;
		long result = PriceCalculator.roundToFive(number);
		Assert.assertEquals("Az eredmény 100 helyett: " + result, 100, result);
	}

	@Test
	public void testCalculateDiscount0ShippingCost0() {
		CityVO city = new CityVO();
		city.setShippingCost(0);

		AddressVO address = new AddressVO();
		address.setCity(city);

		CustomerVO customer = new CustomerVO();
		customer.setOrdersCount(0L);

		ProductVO product1 = new ProductVO();
		product1.setName("Finom Pizza");
		product1.setPrice(1000);

		ProductVO product2 = new ProductVO();
		product2.setName("Más Pizza");
		product2.setPrice(900);

		List<ProductVO> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);

		Long[] result = PriceCalculator.calculate(customer, products, address);
		assertEquals("A teljes ár 1900 helyett: " + result[0], new Long(1900), result[0]);
		assertEquals("A kedvezmény 0 helyett: " + result[1], new Long(0), result[1]);
		assertEquals("A kerekítés 0 helyett: " + result[2], new Long(0), result[2]);
	}
	
	@Test
	public void testCalculateDiscountNot0ShippingCost0() {
		CityVO city = new CityVO();
		city.setShippingCost(0);

		AddressVO address = new AddressVO();
		address.setCity(city);

		CustomerVO customer = new CustomerVO();
		customer.setOrdersCount(11L);

		ProductVO product1 = new ProductVO();
		product1.setName("Finom Pizza");
		product1.setPrice(1000);

		ProductVO product2 = new ProductVO();
		product2.setName("Más Pizza");
		product2.setPrice(900);

		List<ProductVO> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);

		Long[] result = PriceCalculator.calculate(customer, products, address);
		assertEquals("A teljes ár 1520 helyett: " + result[0], new Long(1520), result[0]);
		assertEquals("A kedvezmény 380 helyett: " + result[1], new Long(380), result[1]);
		assertEquals("A kerekítés 0 helyett: " + result[2], new Long(0), result[2]);
	}
	
	@Test
	public void testCalculateDiscountNot0ShippingCostNot0() {
		CityVO city = new CityVO();
		city.setShippingCost(500);

		AddressVO address = new AddressVO();
		address.setCity(city);

		CustomerVO customer = new CustomerVO();
		customer.setOrdersCount(11L);

		ProductVO product1 = new ProductVO();
		product1.setName("Finom Pizza");
		product1.setPrice(1000);

		ProductVO product2 = new ProductVO();
		product2.setName("Más Pizza");
		product2.setPrice(900);

		List<ProductVO> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);

		Long[] result = PriceCalculator.calculate(customer, products, address);
		assertEquals("A teljes ár 2020 helyett: " + result[0], new Long(2020), result[0]);
		assertEquals("A kedvezmény 380 helyett: " + result[1], new Long(380), result[1]);
		assertEquals("A kerekítés 0 helyett: " + result[2], new Long(0), result[2]);
	}
	
	@Test
	public void testCalculateDiscountNot0ShippingCostNot0WithRound() {
		CityVO city = new CityVO();
		city.setShippingCost(500);

		AddressVO address = new AddressVO();
		address.setCity(city);

		CustomerVO customer = new CustomerVO();
		customer.setOrdersCount(11L);

		ProductVO product1 = new ProductVO();
		product1.setName("Finom Pizza");
		product1.setPrice(1000);

		ProductVO product2 = new ProductVO();
		product2.setName("Más Pizza");
		product2.setPrice(990);

		List<ProductVO> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);

		Long[] result = PriceCalculator.calculate(customer, products, address);
		assertEquals("A teljes ár 2090 helyett: " + result[0], new Long(2090), result[0]);
		assertEquals("A kedvezmény 398 helyett: " + result[1], new Long(398), result[1]);
		assertEquals("A kerekítés -2 helyett: " + result[2], new Long(-2), result[2]);
	}
}
