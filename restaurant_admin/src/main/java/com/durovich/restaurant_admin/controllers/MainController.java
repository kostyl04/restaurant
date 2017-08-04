package com.durovich.restaurant_admin.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.durovich.restaurant_admin.config.FXMLLoaderProvider;
import com.durovich.restaurant_admin.service.LoginService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@Component
public class MainController {
	private final static String ADD_PRODUCT_VIEW_NAME = "/addProduct.fxml";
	private final static String ADD_CURRENCY_VIEW_NAME = "/addCurrency.fxml";
	private final static String CALCULATOR_VIEW_NAME = "/calculator.fxml";
	private final static String MAKE_ORDER_VIEW_NAME = "/makeOrder.fxml";
	private final static String MAIN_VIEW_NAME = "/main.fxml";
	@FXML
	private MenuBar menu;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Label errorLabel;
	@FXML
	private AnchorPane loginPane;
	@Autowired
	private FXMLLoaderProvider provider;
	@Autowired
	private LoginService loginService;
	@FXML
	private BorderPane rootLayout;

	@FXML
	public void AddProductBtnClick() throws IOException {
		//////
		FXMLLoader loader = provider.getLoader(ADD_PRODUCT_VIEW_NAME);
		AnchorPane page = loader.load();
		rootLayout.setCenter(page);

	}

	@FXML
	public void AddCurrencyBtnClick() throws IOException {
		FXMLLoader loader = provider.getLoader(ADD_CURRENCY_VIEW_NAME);
		AnchorPane page = loader.load();
		rootLayout.setCenter(page);

	}
	@FXML
	public void CalculatorBtnClick() throws IOException {
		FXMLLoader loader = provider.getLoader(CALCULATOR_VIEW_NAME);
		AnchorPane page = loader.load();
		rootLayout.setCenter(page);//

	}

	@FXML
	public void MakeOrderBtnClick() throws IOException {
		FXMLLoader loader = provider.getLoader(MAKE_ORDER_VIEW_NAME);
		AnchorPane page = loader.load();
		rootLayout.setCenter(page);

	}

	@FXML
	public void login() throws IOException {
		
		String username = this.username.getText();
		String password = this.password.getText();

		if (loginService.login(password, username)) {
			FXMLLoader loader = provider.getLoader(MAKE_ORDER_VIEW_NAME);
			AnchorPane page = loader.load();
			rootLayout.setCenter(page);
			menu.setDisable(false);
		}else{
			errorLabel.setText("Invalid credentials");
		}

	}

	@FXML
	public void logout() throws IOException {
		menu.setDisable(true);
		rootLayout.setCenter(loginPane);

	}

}
