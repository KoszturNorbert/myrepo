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


import org.junit.Assert;
import org.junit.Test;

import hu.norbisan.jpa.pizzafx.business.PropertyReader;


public class PropertyReaderTest {

	@Test
	public void testGetDiscount() {
		Integer discount = PropertyReader.getDiscount();
		Assert.assertEquals(new Integer(20), discount);
	}

	@Test
	public void testGetOrdersCountForDiscount() {
		Integer ordersCountForDiscount = PropertyReader.getOrdersCountForDiscount();
		Assert.assertEquals(new Integer(10), ordersCountForDiscount);
	}

}
