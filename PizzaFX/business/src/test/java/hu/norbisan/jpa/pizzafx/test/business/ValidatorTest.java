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

import hu.norbisan.jpa.pizzafx.business.Validator;

public class ValidatorTest {

	@Test
	public void testIsEmailTrue() {
		String email = "valaki@email.hu";
		boolean decision = Validator.isEmail(email);
		Assert.assertTrue("Igaznak kell lennie!", decision);
	}

	@Test
	public void testIsEmailEmptyFalse() {
		String email = "";
		boolean decision = Validator.isEmail(email);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}

	@Test
	public void testIsEmailNotEmptyFalse() {
		String email = "valaki@emailhu";
		boolean decision = Validator.isEmail(email);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}

	@Test
	public void testIsPhoneNumberNationalTrue() {
		String phone = "+36301234567";
		boolean decision = Validator.isPhoneNumber(phone);
		Assert.assertTrue("Igaznak kell lennie!", decision);
	}

	@Test
	public void testIsPhoneNumberNotNationalTrue() {
		String phone = "06201234567";
		boolean decision = Validator.isPhoneNumber(phone);
		Assert.assertTrue("Igaznak kell lennie!", decision);
	}

	@Test
	public void testIsPhoneNumberFalse() {
		String phone = "123-456";
		boolean decision = Validator.isPhoneNumber(phone);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}

	@Test
	public void testIsPostalCodeTrue() {
		String postCode = "1234";
		boolean decision = Validator.isPostalCode(postCode);
		Assert.assertTrue("Igaznak kell lennie!", decision);
	}

	@Test
	public void testIsPostalCodeLongNumberFalse() {
		String postCode = "123456";
		boolean decision = Validator.isPhoneNumber(postCode);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}

	@Test
	public void testIsPostalCodeShortNumberFalse() {
		String postCode = "123";
		boolean decision = Validator.isPhoneNumber(postCode);
		Assert.assertFalse("Hamisnak kell lennie!", decision);	
	}
	
	@Test
	public void testIsPostalCodeNotNumberFalse() {
		String postCode = "valami";
		boolean decision = Validator.isPhoneNumber(postCode);
		Assert.assertFalse("Hamisnak kell lennie!",decision);
	}

}
