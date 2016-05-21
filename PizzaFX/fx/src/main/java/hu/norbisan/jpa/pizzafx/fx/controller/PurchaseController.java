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

import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;
import hu.norbisan.jpa.pizzafx.business.vo.PurchaseVO;
import hu.norbisan.jpa.pizzafx.fx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PurchaseController {

	@FXML
	TableView<ProductVO> tvAllTable;
	@FXML
	Button btAdd;
	@FXML
	Button btLogout;
	@FXML
	TableView<ProductVO> tvSelectedTable;
	@FXML
	Button btDelete;
	@FXML
	Button btNext;

	@FXML
	public void btAddAction(ActionEvent event) {
		ProductVO vo = tvAllTable.getSelectionModel().getSelectedItem();
		tvSelectedTable.getItems().add(vo);
	}

	@FXML
	public void btLogoutAction(ActionEvent event) {
		Main.customer = null;
		Main.purchase = null;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
			Parent root = loader.load();
			loader.<MainController> getController().initData();
			Scene scene = new Scene(root);
			Stage stage = (Stage) btLogout.getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Főoldal");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void btDeleteAction(ActionEvent event) {
		int selected = tvSelectedTable.getSelectionModel().getSelectedIndex();
		if (selected >= 0) {
			tvSelectedTable.getItems().remove(selected);
		}
	}

	@FXML
	public void btNextAction(ActionEvent event) {
		if(tvSelectedTable.getItems().size() == 0){
			Alert alert = new Alert(AlertType.ERROR, "Nincs pizza hozzáadva!");
			alert.setHeaderText(null);
			alert.show();
			return;
		}
		Main.purchase = new PurchaseVO();
		Main.purchase.setCustomer(Main.customer);
		Main.purchase.setProducts(tvSelectedTable.getItems().subList(0, tvSelectedTable.getItems().size()));
		//cím ablak megnyitása
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Address.fxml"));
			AnchorPane root = loader.load();
			loader.<AddressController>getController().initData((Stage)btNext.getScene().getWindow());
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Szállítási cím megadása");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void initData() {
		tvAllTable.getItems().clear();
		tvAllTable.getItems().addAll(Main.products);
		tvAllTable.setPlaceholder(new Label("Nincs pizza"));
		if (tvAllTable.getItems().size() > 0) {
			tvAllTable.getSelectionModel().select(0);
		}
		tvSelectedTable.getItems().clear();
		tvSelectedTable.setPlaceholder(new Label("Nincs pizza hozzáadva"));
	}

}
