package com.durovich.restaurant_admin.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.durovich.restaurant_admin.config.FXMLLoaderProvider;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@Component
public class MainController {
	private final static String ADD_PRODUCT_VIEW_NAME="/addProduct.fxml";
	private final static String ADD_CURRENCY_VIEW_NAME="/addCurrency.fxml";
	private final static String MAKE_ORDER_VIEW_NAME="/makeOrder.fxml";
	@Autowired
	private FXMLLoaderProvider provider;
	@FXML
	private BorderPane rootLayout;
	@FXML
	public void AddProductBtnClick() throws IOException {
		//////
		FXMLLoader loader = provider.getLoader(ADD_PRODUCT_VIEW_NAME);
        AnchorPane page= loader.load();
        rootLayout.setCenter(page);
      
		
	}
	@FXML
	public void AddCurrencyBtnClick() throws IOException {
		FXMLLoader loader = provider.getLoader(ADD_CURRENCY_VIEW_NAME);
        AnchorPane page= loader.load();
        rootLayout.setCenter(page);
      ////
		
	}
	@FXML
	public void MakeOrderBtnClick() throws IOException {
		FXMLLoader loader = provider.getLoader(MAKE_ORDER_VIEW_NAME);
        AnchorPane page= loader.load();
        rootLayout.setCenter(page);
      
		
	}
	
}
