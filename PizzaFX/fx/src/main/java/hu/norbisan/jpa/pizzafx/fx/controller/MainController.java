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
import hu.norbisan.jpa.pizzafx.fx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

	@FXML
	TableView<ProductVO> tvTabla;
	@FXML
	Button btLogin;
	
	private Stage stage;

	@FXML
	public void btLoginAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			loader.<LoginController> getController().initData((Stage)btLogin.getScene().getWindow());
			Scene scene = new Scene(root);
			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Bejelentkezés");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initData(){
		tvTabla.getItems().clear();
		tvTabla.getItems().addAll(Main.products);
	}

}
