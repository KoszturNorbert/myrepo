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


import java.util.List;

import hu.norbisan.jpa.pizzafx.business.vo.AddressVO;
import hu.norbisan.jpa.pizzafx.business.vo.CustomerVO;
import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;

public class PriceCalculator {

	/**
	 * Metódus egy szám kerekítéséhez a következő 5-tel osztható egészhez, a
	 * bolti kerekítésnek megfelelően. Ha a szám 0-ra vagy 5-re végződik, akkor
	 * nem módosítja a metódus. A szám utolsó számjegye alapján: 0-tól 2-ig
	 * lefelé kerekít, 3-tól 5-ig felfelé kerekít 5-re, 5-től 7-ig lefelé
	 * kerekít 5-re, 8-tól 10-ig felfelé kerekít.
	 * 
	 * @param number
	 *            A kerekítendő szám
	 * @return A metódus a kerekített értékkel tér vissza.
	 */
	public static Long roundToFive(long number) {
		long amount = number % 5;
		if (amount < 3) {
			return number - amount;
		} else {
			return number + 5 - amount;
		}
	}

	/**
	 * Egy megrendelés árát kiszámító metódus.
	 * 
	 * A metódus a paraméterek alapján kiszámolja az össz árat, majd a
	 * végösszeget kerekíti a bolti kerekítésnek megfelelően. Ha az ügyfél
	 * elérte a rendszerben beállított szükséges rendelési számot, akkor a
	 * metódus a választott termékek összárát csökkenti a rendszerben beállított
	 * százalékkal. Ha a paraméterként átadott cím olyan várost tartalmaz
	 * amelynek nullától nagyobb szállítási díja van, akkor a termékek árához
	 * hozzáadódik a szállítási díj.
	 * 
	 * @param customer
	 *            Az ügyfél, aki vásárolt. Nem lehet null!
	 * @param products
	 *            A vásárolt termékek listája. Nem lehet üres, és nem lehet
	 *            null!
	 * @param address
	 *            A szállítási cím. Nem lehet null!
	 * @return A metódus egy 3 elemű Long típusú tömbbel tér vissza, amelynek 0
	 *         indexén a kiszámított ár szerepel, 1 indexén a számított
	 *         kedvezmény, 2 indexén a kerekítés értéke.
	 */
	public static Long[] calculate(CustomerVO customer, List<ProductVO> products, AddressVO address) {
		if (customer == null) {
			throw new NullPointerException("A customer paraméter nem lehet null!");
		}
		if (products == null) {
			throw new NullPointerException("A products paraméter nem lehet null!");
		}
		if (address == null) {
			throw new NullPointerException("Az address paraméter nem lehet null!");
		}

		if (products.isEmpty()) {
			throw new IllegalArgumentException("A products lista nem lehet üres!");
		}

		long sum = products.stream().mapToInt(p -> p.getPrice()).sum();
		int shippingCost = address.getCity().getShippingCost();
		Integer orders_count_for_discount = PropertyReader.getOrdersCountForDiscount();
		long prices_percent = 0;
		if (orders_count_for_discount == null) {
			throw new IllegalStateException("Az orders_count_for_discount property nem elérhető!");
		} else if (customer.getOrdersCount() >= orders_count_for_discount) {
			Integer discount = PropertyReader.getDiscount();
			if (discount == null) {
				throw new IllegalStateException("A discount property nem elérhető!");
			} else {
				prices_percent = (long) ((sum * (discount / 100.0)) + 0.5);
			}
		}
		sum -= prices_percent;
		Long price = sum + shippingCost;
		Long[] result = new Long[3];
		result[0] = roundToFive(price);
		result[1] = prices_percent;
		result[2] = result[0] - price;
		return result;
	}
}
