package hu.norbisan.jpa.pizzafx.fx;

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


import java.util.List;

import hu.norbisan.jpa.pizzafx.business.factory.CityServiceFactory;
import hu.norbisan.jpa.pizzafx.business.factory.DatabaseInitServiceFactory;
import hu.norbisan.jpa.pizzafx.business.factory.ProductServiceFactory;
import hu.norbisan.jpa.pizzafx.business.service.CityService;
import hu.norbisan.jpa.pizzafx.business.service.DatabaseInitService;
import hu.norbisan.jpa.pizzafx.business.service.ProductService;
import hu.norbisan.jpa.pizzafx.business.vo.CityVO;
import hu.norbisan.jpa.pizzafx.business.vo.CustomerVO;
import hu.norbisan.jpa.pizzafx.business.vo.ProductVO;
import hu.norbisan.jpa.pizzafx.business.vo.PurchaseVO;
import hu.norbisan.jpa.pizzafx.core.dao.factory.DataBaseInitDaoFactory.SourceTypes;
import hu.norbisan.jpa.pizzafx.fx.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static final String PERSISTENCE_UNIT_NAME ="hu.norbisan.jpa.pizzafx_hsql_pu";
	public static List<ProductVO> products;
	public static List<CityVO> cities;
	public static CustomerVO customer;
	public static PurchaseVO purchase;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
			AnchorPane root = loader.load();
			loader.<MainController>getController().initData();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Főoldal");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		super.init();

		ProductServiceFactory productFactory = ProductServiceFactory.newInstance();
		productFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		ProductService productService = productFactory.newProductService();
		
		Main.products = productService.findAllProduct();
		if(Main.products.size() == 0){
			DatabaseInitServiceFactory initServiceFactory = DatabaseInitServiceFactory.newInstance();
			initServiceFactory.setSourceType(SourceTypes.XML);
			DatabaseInitService initService = initServiceFactory.newDatabaseInitService();
			List<ProductVO> ps = initService.readProductsFromFile(getClass().getClassLoader().getResource("Products.xml").getFile());
			Main.products = productService.saveAllProducts(ps);
		}
		
		CityServiceFactory cityFactory = CityServiceFactory.newInstance();
		cityFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		CityService cityService = cityFactory.newCityService();
		
		Main.cities = cityService.findAllCity();
		if(Main.cities.size() == 0){
			DatabaseInitServiceFactory initServiceFactory = DatabaseInitServiceFactory.newInstance();
			initServiceFactory.setSourceType(SourceTypes.XML);
			DatabaseInitService initService = initServiceFactory.newDatabaseInitService();
			List<CityVO> cs = initService.readCitiesFromFile(getClass().getClassLoader().getResource("Cities.xml").getFile());
			Main.cities = cityService.saveAllCities(cs);
		}
		
		productFactory.close();
		cityFactory.close();
	}
	
	
}
