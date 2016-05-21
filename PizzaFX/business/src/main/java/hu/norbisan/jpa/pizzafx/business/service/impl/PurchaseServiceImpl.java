package hu.norbisan.jpa.pizzafx.business.service.impl;

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

import hu.norbisan.jpa.pizzafx.business.converter.PurchaseConverter;
import hu.norbisan.jpa.pizzafx.business.service.PurchaseService;
import hu.norbisan.jpa.pizzafx.business.vo.PurchaseVO;
import hu.norbisan.jpa.pizzafx.core.dao.PurchaseDao;

public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseDao purchaseDao;
	private PurchaseConverter converter;

	@Override
	public PurchaseVO savePurchase(PurchaseVO purchase) {
		return converter.toVO(purchaseDao.create(converter.toEntity(purchase)));
	}

	@Override
	public PurchaseVO findPurchaseById(Long id) {
		return converter.toVO(purchaseDao.findOne(id));
	}

	@Override
	public List<PurchaseVO> findAllPurchase() {
		return converter.toVO(purchaseDao.findAll());
	}

	public PurchaseServiceImpl(PurchaseDao purchaseDao) {
		super();
		this.purchaseDao = purchaseDao;
		this.converter = new PurchaseConverter();
	}

}
