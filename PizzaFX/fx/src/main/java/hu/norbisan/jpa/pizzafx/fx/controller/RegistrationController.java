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

import hu.norbisan.jpa.pizzafx.business.EncryptHelper;
import hu.norbisan.jpa.pizzafx.business.Validator;
import hu.norbisan.jpa.pizzafx.business.factory.AddressServiceFactory;
import hu.norbisan.jpa.pizzafx.business.factory.CustomerServiceFactory;
import hu.norbisan.jpa.pizzafx.business.service.AddressService;
import hu.norbisan.jpa.pizzafx.business.service.CustomerService;
import hu.norbisan.jpa.pizzafx.business.vo.AddressVO;
import hu.norbisan.jpa.pizzafx.business.vo.CityVO;
import hu.norbisan.jpa.pizzafx.business.vo.CustomerVO;
import hu.norbisan.jpa.pizzafx.fx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RegistrationController {

	private Stage stage;

	@FXML
	TextField tfName;
	@FXML
	TextField tfEmail;
	@FXML
	TextField tfPhoneNumber;
	@FXML
	PasswordField pfPassword;
	@FXML
	PasswordField pfPassword2;
	@FXML
	TextField tfPostCode;
	@FXML
	TextField tfStreet;
	@FXML
	TextField tfHouse;
	@FXML
	ComboBox<CityVO> cbxCity;
	@FXML
	Button btReg;
	@FXML
	Button btBack;

	@FXML
	public void btRegAction(ActionEvent event) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				AddressServiceFactory addressFactory = AddressServiceFactory.newInstance();
				addressFactory.setPersistenceUnitName(Main.PERSISTENCE_UNIT_NAME);
				AddressService addressService = addressFactory.newAddressService();

				AddressVO address = new AddressVO();
				address.setCity(cbxCity.getValue());
				address.setPostalCode(Integer.valueOf(tfPostCode.getText()));
				address.setStreet(tfStreet.getText());
				address.setHouse(tfHouse.getText());

				address = addressService.saveAddress(address);

				CustomerVO customer = new CustomerVO();
				customer.setName(tfName.getText());
				customer.setEmail(tfEmail.getText());
				customer.setAddress(address);
				// Jelszót titkosítom
				customer.setPassword(EncryptHelper.encrypt(pfPassword.getText()));
				customer.setPhoneNumber(tfPhoneNumber.getText());
				customer.setOrdersCount(0L);

				CustomerServiceFactory customerFactory = CustomerServiceFactory.newInstance();
				customerFactory.setPersistenceUnitName(Main.PERSISTENCE_UNIT_NAME);
				CustomerService customerService = customerFactory.newCustomerService();
				customerService.saveCustomer(customer);

				addressFactory.close();
				customerFactory.close();
			}
		});

		// Kitöltés ellenőrzés
		// Név
		if ("".equals(tfName.getText())) {
			showErrorAlert("A név megadása kötelező!");
			tfName.requestFocus();
			return;
		}
		// Email
		if ("".equals(tfEmail.getText())) {
			showErrorAlert("Az email cím megadása kötelező!");
			tfEmail.requestFocus();
			return;
		}

		// Email cím helyességének ellenőrzése
		if (!Validator.isEmail(tfEmail.getText())) {
			showErrorAlert("Az email cím nem megfelelő formátumú!");
			tfEmail.setStyle("-fx-border-color: 'red'");
			tfEmail.requestFocus();
			return;
		}

		// Telefon
		if ("".equals(tfPhoneNumber.getText())) {
			showErrorAlert("A telefonszám megadása kötelező!");
			tfPhoneNumber.requestFocus();
			return;
		}

		// Telefonszám helyességének ellenőrzése
		if (!Validator.isPhoneNumber(tfPhoneNumber.getText())) {
			showErrorAlert("A telefonszám nem megfelelő formátumú!");
			tfPhoneNumber.setStyle("-fx-border-color: 'red'");
			tfPhoneNumber.requestFocus();
			return;
		}

		// Jelszó
		if ("".equals(pfPassword.getText())) {
			showErrorAlert("A jelszó megadása kötelező!");
			pfPassword.requestFocus();
			return;
		}

		// Jelszavak megegyeznek-e
		if (!pfPassword.getText().equals(pfPassword2.getText())) {
			showErrorAlert("A jelszavak különböznek!");
			pfPassword.clear();
			pfPassword2.clear();
			pfPassword.requestFocus();
			return;
		}

		// Irányítószám
		if ("".equals(tfPostCode.getText())) {
			showErrorAlert("Az irányítószám megadása kötelező!");
			tfPostCode.requestFocus();
			return;
		}

		// Irányítószám helyességének ellenőrzése
		if (!Validator.isPostalCode(tfPostCode.getText())) {
			showErrorAlert("Az irányítószám nem megfelelő formátumú!");
			tfPostCode.setStyle("-fx-border-color: 'red'");
			tfPostCode.requestFocus();
			return;
		}

		// Utca
		if ("".equals(tfStreet.getText())) {
			showErrorAlert("Az utca megadása kötelező!");
			tfStreet.requestFocus();
			return;
		}

		// Házszám
		if ("".equals(tfHouse.getText())) {
			showErrorAlert("A házszám megadása kötelező!");
			tfHouse.requestFocus();
			return;
		}
		// Emailcím létezésének vizsgálata
		CustomerServiceFactory customerFactory = CustomerServiceFactory.newInstance();
		customerFactory.setPersistenceUnitName(Main.PERSISTENCE_UNIT_NAME);
		CustomerService customerService = customerFactory.newCustomerService();

		if (customerService.findCustomerByEmail(tfEmail.getText()) != null) {
			showErrorAlert("A megadott email cím már létezik a rendszerben! Adjon meg másikat!");
			tfEmail.requestFocus();
			customerFactory.close();
			return;
		}

		thread.start();
		loginWindowLoading();
		showInfoAlert("Sikeres regisztráció! Jelentkezzen be!");

	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR, message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	private void showInfoAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	@FXML
	public void btBackAction(ActionEvent event) {
		loginWindowLoading();

	}

	private void loginWindowLoading() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
			Parent root = loader.load();
			loader.<LoginController> getController().initData(this.stage);
			Scene scene = new Scene(root);
			Stage stage = (Stage) btBack.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Bejelentkezés");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void tfEmailOnKeyReleased(KeyEvent event) {
		if (!Validator.isEmail(tfEmail.getText())) {
			tfEmail.setStyle("-fx-border-color:'red'");
		} else {
			tfEmail.setStyle("");
		}
	}

	@FXML
	public void tfPhoneNumberOnKeyReleased(KeyEvent event) {
		if (!Validator.isPhoneNumber(tfPhoneNumber.getText())) {
			tfPhoneNumber.setStyle("-fx-border-color:'red'");
		} else {
			tfPhoneNumber.setStyle("");
		}
	}

	public void initData(Stage stage) {
		this.stage = stage;
		cbxCity.getItems().clear();
		cbxCity.getItems().addAll(Main.cities);
		cbxCity.getSelectionModel().select(0);
	}

	@FXML
	public void tfPostCodeKeyReleased(KeyEvent event) {
		if (!Validator.isPostalCode(tfPostCode.getText())) {
			tfPostCode.setStyle("-fx-border-color: 'red'");
		} else {
			tfPostCode.setStyle("");
		}
	}

}
