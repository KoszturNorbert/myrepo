package hu.norbisan.jpa.pizzafx.business;

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


public class Validator {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_NUMBER_REGEX = "^(\\+36|06)[0-9]{7,9}$";
	private static final String POSTAL_CODE_REGEX = "^[1-9][0-9]{3}$";

	/**
	 * Metódus egy email cím érvényességének ellenőrzéséhez.
	 * 
	 * A metódus ellenőrzi az email cím formátumát.
	 * 
	 * @param email
	 *            Az ellenőrizni kívánt email cím.
	 * @return A visszatérési érték {@code true}, ha az email cím formátuma
	 *         megfelelő, különben {@code false}
	 */
	public static boolean isEmail(String email) {
		if (email.matches(EMAIL_REGEX)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metódus egy telefonszám érvényességének ellenőrzéséhez.
	 * 
	 * A metódus elfogadja a +36 kezdetű telefonszámokat, és a 06 kezdetűeket
	 * is.
	 * 
	 * @param phone
	 *            Az ellenőrizni kívánt telefonszám. A telefonszámban nem
	 *            szerepelhet szóköz, és {@code -} karakter sem!
	 * @return A visszatérési érték {@code true}, ha a telefonszám formátuma
	 *         megfelelő, különben {@code false}
	 */
	public static boolean isPhoneNumber(String phone) {
		if (phone.matches(PHONE_NUMBER_REGEX)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metódus egy postai irányítószám ellenőrzéséhez.
	 * 
	 * A metódus ellenőrzi a paraméterként kapott irányítószámot, hogy
	 * megfelel-e a magyar postai iránytószámok szabályainak
	 * 
	 * @param postalCode
	 *            Az ellenőrizni kívánt irányítószám
	 * @return A visszatérési érték {@code true}, ha az irányítószám megfelelő
	 *         formátumú, különben {@code false}
	 */
	public static boolean isPostalCode(String postalCode) {
		if (postalCode.matches(POSTAL_CODE_REGEX)) {
			return true;
		} else {
			return false;
		}
	}
}
