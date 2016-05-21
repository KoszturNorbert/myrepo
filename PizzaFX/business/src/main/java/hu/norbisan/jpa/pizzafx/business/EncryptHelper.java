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


import org.mindrot.jbcrypt.BCrypt;

public class EncryptHelper {

	private static int rounds = 13;
	
	/**
	 * Jelszó titkosítására szolgáló metódus.
	 * Titkosítja a jelszót a BCrypt algoritmus alapján.
	 * 
	 * @param password A titkosítandó jelszó
	 * @return Egy {@code String} ami a paraméterként kapott jelszó titkosított alakját tartalmazza.
	 */
	public static String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(rounds));
	}
	
	/**
	 * Metódus egy jelszó összehasonlításához egy titkosított jelszóval.
	 * 
	 * @param pass A jelszó amit ellenőrzünk
	 * @param hashed A titkosított alakja a jelszónak
	 * @return A visszatérési érték {@code true} ha a jelszó megegyezik a titkosított jelszóval, különben {@code false} 
	 */
	public static boolean checkPass(String pass, String hashed){
		
		return BCrypt.checkpw(pass, hashed);
	}
}
