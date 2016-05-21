package hu.norbisan.jpa.pizzafx.business.converter;

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


import java.util.ArrayList;
import java.util.List;

import hu.norbisan.jpa.pizzafx.business.vo.AddressVO;
import hu.norbisan.jpa.pizzafx.core.entity.Address;

public class AddressConverter {

	private CityConverter converter;

	public Address toEntity(AddressVO vo) {

		if (vo == null) {
			return null;
		}

		Address address = new Address();
		address.setId(vo.getId());
		address.setCity(converter.toEntity(vo.getCity()));
		address.setHouse(vo.getHouse());
		address.setPostalCode(vo.getPostalCode());
		address.setStreet(vo.getStreet());
		return address;

	}

	public AddressVO toVO(Address address) {

		if (address == null) {
			return null;
		}

		AddressVO vo = new AddressVO();
		vo.setId(address.getId());
		vo.setCity(converter.toVO(address.getCity()));
		vo.setPostalCode(address.getPostalCode());
		vo.setStreet(address.getStreet());
		vo.setHouse(address.getHouse());
		return vo;
	}

	public List<Address> toEntity(List<AddressVO> vos) {

		if (vos == null) {
			return null;
		}

		List<Address> addresses = new ArrayList<>();

		for (AddressVO vo : vos) {
			addresses.add(toEntity(vo));
		}
		return addresses;
	}

	public List<AddressVO> toVO(List<Address> addresses) {

		if (addresses == null) {
			return null;
		}

		List<AddressVO> vos = new ArrayList<>();

		for (Address address : addresses) {
			vos.add(toVO(address));
		}

		return vos;
	}

	public AddressConverter() {
		this.converter = new CityConverter();
	}

}
