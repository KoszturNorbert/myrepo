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

import hu.norbisan.jpa.pizzafx.business.EncryptHelper;

public class EncryptHelperTest {

	@Test
	public void testEncryptNotNullString() {
		String word = "alma";
		String encrypted = EncryptHelper.encrypt(word);
		Assert.assertNotEquals("A titkosított szöveg nem egyezhet meg az eredetivel!",word, encrypted);
	}
	@Test
	public void testEncryptNull(){
		String encrypted = EncryptHelper.encrypt(null);
		Assert.assertNotNull("A titkosított szöveg nem lehet null!", encrypted);
	}
	@Test
	public void testEncryptEmptyString(){
		String word = "";
		String encrypted = EncryptHelper.encrypt(word);
		Assert.assertNotNull("A titkosított szöveg nem lehet null!", encrypted);
	}
	

	@Test
	public void testCheckPassNotNullSourceStringTrue() {
		String word = "valami";
		String encrypted = EncryptHelper.encrypt(word);
		boolean decision = EncryptHelper.checkPass(word, encrypted);
		Assert.assertTrue("Igaznak kell lennie!", decision);
	}
	
	@Test
	public void testCheckPassNotNullSourceStringFalse() {
		String word = "valami";
		String encrypted = EncryptHelper.encrypt(word);
		boolean decision = EncryptHelper.checkPass("other", encrypted);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}
	
	@Test
	public void testCheckPassNullSourceStringTrue() {
		String encrypted = EncryptHelper.encrypt(null);
		boolean decision = EncryptHelper.checkPass(null, encrypted);
		Assert.assertTrue("Igaznak kell lennie!", decision);
	}
	
	@Test
	public void testCheckPassNullSourceStringFalse() {
		String encrypted = EncryptHelper.encrypt(null);
		boolean decision = EncryptHelper.checkPass("word", encrypted);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}
	
	@Test
	public void testCheckPassEmptySourceStringTrue() {
		String word = "";
		String encrypted = EncryptHelper.encrypt(word);
		boolean decision = EncryptHelper.checkPass(word, encrypted);
		Assert.assertTrue("Igaznak kell lennie!", decision);
	}
	
	@Test
	public void testCheckPassEmptySourceStringFalse() {
		String word = "";
		String encrypted = EncryptHelper.encrypt(word);
		boolean decision = EncryptHelper.checkPass("other", encrypted);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}
	
	@Test
	public void testCheckPassEmptySourceStringAndNullCheckStringFalse() {
		String word = "";
		String encrypted = EncryptHelper.encrypt(word);
		boolean decision = EncryptHelper.checkPass(null, encrypted);
		Assert.assertFalse("Hamisnak kell lennie!", decision);
	}

}
