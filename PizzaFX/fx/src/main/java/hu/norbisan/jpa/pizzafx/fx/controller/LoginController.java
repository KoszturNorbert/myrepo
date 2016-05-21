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
import hu.norbisan.jpa.pizzafx.business.factory.CustomerServiceFactory;
import hu.norbisan.jpa.pizzafx.business.service.CustomerService;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	private Stage stage;

	@FXML
	TextField tfEmail;

	@FXML
	Button btLogin;

	@FXML
	PasswordField pfPassword;

	@FXML
	Button btReg;

	@FXML
	public void btLoginAction(ActionEvent event) {
		if (!Validator.isEmail(tfEmail.getText())) {
			showEmailPasswordAlert();
			pfPassword.clear();
			tfEmail.requestFocus();
			return;
		}
		
		CustomerServiceFactory customerFactory = CustomerServiceFactory.newInstance();
		customerFactory.setPersistenceUnitName(Main.PERSISTENCE_UNIT_NAME);
		CustomerService customerService = customerFactory.newCustomerService();
		CustomerVO cust = customerService.findCustomerByEmail(tfEmail.getText());
		customerFactory.close();

		if (cust == null) {
			showEmailPasswordAlert();
			pfPassword.clear();
			tfEmail.requestFocus();
			return;
		} else if (!EncryptHelper.checkPass(pfPassword.getText(), cust.getPassword())) {
			showEmailPasswordAlert();
			pfPassword.clear();
			tfEmail.requestFocus();
			return;
		}

		Main.customer = cust;

		// Másik ablak megnyitása
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Purchase.fxml"));
		try {
			Parent root = loader.load();
			loader.<PurchaseController> getController().initData();
			Scene scene = new Scene(root);
			this.stage.setScene(scene);
			this.stage.setTitle("Pizzák kiválasztása");
			
			
			this.stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Ablak bezárása

		Stage stage = (Stage) btLogin.getScene().getWindow();
		stage.close();

	}

	private void showEmailPasswordAlert() {
		Alert alert = new Alert(AlertType.ERROR, "Az email cím, vagy a jelszó nem megfelelő!");
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	@FXML
	public void btRegAction(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Registration.fxml"));
		try {
			Parent root = loader.load();
			loader.<RegistrationController> getController().initData(this.stage);
			Scene scene = new Scene(root);
			Stage stage = (Stage) btReg.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Regisztráció");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initData(Stage stage) {
		this.stage = stage;
	}

}
