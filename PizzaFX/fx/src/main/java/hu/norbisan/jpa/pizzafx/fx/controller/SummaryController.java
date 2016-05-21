package hu.norbisan.jpa.pizzafx.fx.controller;

/*
 * #%L
 * fx
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
import java.time.LocalDateTime;

import hu.norbisan.jpa.pizzafx.business.PriceCalculator;
import hu.norbisan.jpa.pizzafx.business.factory.AddressServiceFactory;
import hu.norbisan.jpa.pizzafx.business.factory.CustomerServiceFactory;
import hu.norbisan.jpa.pizzafx.business.factory.PurchaseServiceFactory;
import hu.norbisan.jpa.pizzafx.business.service.AddressService;
import hu.norbisan.jpa.pizzafx.business.service.CustomerService;
import hu.norbisan.jpa.pizzafx.business.service.PurchaseService;
import hu.norbisan.jpa.pizzafx.business.vo.AddressVO;
import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;
import hu.norbisan.jpa.pizzafx.fx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SummaryController {

	@FXML
	TableView<ProductVO> tvTable;
	@FXML
	TextField tfShippingCost;
	@FXML
	TextField tfTotalPrice;
	@FXML
	TextField tfPostCode;
	@FXML
	TextField tfCity;
	@FXML
	TextField tfStreet;
	@FXML
	TextField tfHouse;
	@FXML
	TextField tfDiscount;
	@FXML
	TextField tfRoundingAmount;
	@FXML
	Button btOrder;
	@FXML
	Button btCalcel;

	private Long[] totals;

	@FXML
	public void btOrderAction(ActionEvent event) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// Teljes ár beállítása
				Main.purchase.setTotalPrice(SummaryController.this.totals[0]);
				// Rendelések számának növelése
				CustomerServiceFactory customerFactory = CustomerServiceFactory.newInstance();
				customerFactory.setPersistenceUnitName(Main.PERSISTENCE_UNIT_NAME);
				CustomerService customerService = customerFactory.newCustomerService();
				Main.customer = customerService.incrementOrdersCount(Main.customer);
				customerFactory.close();
				Main.purchase.setCustomer(Main.customer);
				// Szállítási cím adatbázisba mentése ha új cím
				if (Main.purchase.getShipping_address().getId() == null) {

					AddressServiceFactory addressFactory = AddressServiceFactory.newInstance();
					addressFactory.setPersistenceUnitName(Main.PERSISTENCE_UNIT_NAME);
					AddressService addressService = addressFactory.newAddressService();

					AddressVO shippingAddress = addressService.saveAddress(Main.purchase.getShipping_address());
					Main.purchase.setShipping_address(shippingAddress);
					addressFactory.close();
				}

				// Rendelés adatbázisba mentése

				PurchaseServiceFactory purchaseFactory = PurchaseServiceFactory.newInstance();
				purchaseFactory.setPersistenceUnitName(Main.PERSISTENCE_UNIT_NAME);
				PurchaseService purchaseService = purchaseFactory.newPurchaseService();
				// Rendelési dátum beállítása
				Main.purchase.setDate(LocalDateTime.now());
				// Mentés
				purchaseService.savePurchase(Main.purchase);
				purchaseFactory.close();
			}
		});

		thread.start();

		Alert alert = new Alert(AlertType.INFORMATION, "Rendelés elküldve!");
		alert.setHeaderText(null);
		alert.showAndWait();
		purchaseWindowLoading();
	}

	@FXML
	public void btCalcelAction(ActionEvent event) {
		Main.purchase = null;
		purchaseWindowLoading();
	}

	private void purchaseWindowLoading() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Purchase.fxml"));
			AnchorPane root = loader.load();
			loader.<PurchaseController> getController().initData();
			Scene scene = new Scene(root);
			// Stage stage = this.stage;
			Stage stage = (Stage) btCalcel.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Pizzák kiválasztása");
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initData() {
		// Táblázat feltöltése
		tvTable.getItems().clear();
		tvTable.getItems().addAll(Main.purchase.getProducts());

		// Mezők kitöltése
		tfPostCode.setText(Main.purchase.getShipping_address().getPostalCode().toString());
		tfCity.setText(Main.purchase.getShipping_address().getCity().getName());
		tfStreet.setText(Main.purchase.getShipping_address().getStreet());
		tfHouse.setText(Main.purchase.getShipping_address().getHouse());
		tfShippingCost.setText(Main.purchase.getShipping_address().getCity().getShippingCost().toString());
		totals = PriceCalculator.calculate(Main.purchase.getCustomer(), Main.purchase.getProducts(),
				Main.purchase.getShipping_address());
		// Végösszeg, kedvezmény kiírása
		tfTotalPrice.setText(totals[0].toString());
		tfDiscount.setText(totals[1].toString());
		tfRoundingAmount.setText(totals[2].toString());
	}

}
