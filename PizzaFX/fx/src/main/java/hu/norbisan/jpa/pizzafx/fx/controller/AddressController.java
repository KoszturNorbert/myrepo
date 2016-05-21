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

import hu.norbisan.jpa.pizzafx.business.Validator;
import hu.norbisan.jpa.pizzafx.business.vo.AddressVO;
import hu.norbisan.jpa.pizzafx.business.vo.CityVO;
import hu.norbisan.jpa.pizzafx.fx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;

public class AddressController {

	private Stage stage;

	@FXML
	TextField tfPostCode;
	@FXML
	TextField tfStreet;
	@FXML
	TextField tfHouse;
	@FXML
	ComboBox<CityVO> cbxCity;
	@FXML
	CheckBox cbToRegAddress;
	@FXML
	Button btNext;

	@FXML
	Tooltip ttAddressToolTip;

	@FXML
	public void tfPostCodeKeyReleased(KeyEvent event) {
		if (!Validator.isPostalCode(tfPostCode.getText())) {
			tfPostCode.setStyle("-fx-border-color: 'red'");
		} else {
			tfPostCode.setStyle("");
		}
	}

	@FXML
	public void cbToRegAddressAction(ActionEvent event) {
		if (cbToRegAddress.isSelected()) {
			tfPostCode.setDisable(true);
			cbxCity.setDisable(true);
			tfStreet.setDisable(true);
			tfHouse.setDisable(true);
		} else {
			tfPostCode.setDisable(false);
			cbxCity.setDisable(false);
			tfStreet.setDisable(false);
			tfHouse.setDisable(false);
		}
	}

	@FXML
	public void btNextAction(ActionEvent event) {
		// Regisztrált cím beállítása szállítási címnek
		if (cbToRegAddress.isSelected()) {
			AddressVO address = Main.customer.getAddress();
			Main.purchase.setShipping_address(address);
		} else {
			// Mezők kitöltésének ellenőrzése
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

			// Szállítási cím beállítása
			AddressVO address = new AddressVO();
			address.setPostalCode(Integer.valueOf(tfPostCode.getText()));
			address.setCity(cbxCity.getValue());
			address.setStreet(tfStreet.getText());
			address.setHouse(tfHouse.getText());
			Main.purchase.setShipping_address(address);
		}

		// Összesítő ablak megnyitása

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Summary.fxml"));
			AnchorPane root = loader.load();
			loader.<SummaryController> getController().initData();
			Scene scene = new Scene(root);
			this.stage.setScene(scene);
			this.stage.setTitle("Összegzés");
			this.stage.setResizable(false);
			this.stage.show();
			Stage stage = (Stage) btNext.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR, message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public void initData(Stage stage) {
		this.stage = stage;
		cbxCity.getItems().clear();
		cbxCity.getItems().addAll(Main.cities);
		cbxCity.getSelectionModel().select(0);
		ttAddressToolTip.setText(Main.customer.getAddress().toString());
	}

}
