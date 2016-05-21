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


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	private static final String FILE_NAME = "options.properties";

	public static Integer getDiscount() {

		return getProperty("discount");
	}

	public static Integer getOrdersCountForDiscount() {

		return getProperty("orders_count_for_discount");
	}

	private static Integer getProperty(String key) {

		Properties prop = new Properties();
		try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(FILE_NAME)){
			prop.load(input);
			return Integer.valueOf(prop.getProperty(key));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		return null;
	}

}
