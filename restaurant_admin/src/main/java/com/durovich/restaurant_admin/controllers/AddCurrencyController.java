package com.durovich.restaurant_admin.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.durovich.restaurant_admin.entity.CurrencyExchange;
import com.durovich.restaurant_admin.entity.Product;
import com.durovich.restaurant_admin.service.CurrencyService;
import com.durovich.restaurant_admin.service.ProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class AddCurrencyController implements Initializable {
	@Autowired
	private CurrencyService service;
	private ObservableList<CurrencyExchange> currencies;
	@FXML
	private TableView<CurrencyExchange> currencyTable;
	@FXML
	private TableColumn<?, ?> nameColumn;
	@FXML
	private TableColumn<?, ?> rateColumn;
	@FXML
	private TextField nameField;
	@FXML
	private TextField rateField;
	@FXML
	private Button deleteBTN;
	@FXML
	private Button addBTN;
	@FXML
	private Label errorMessagesLBL;

	@FXML
	public void deleteCurrency() {
		CurrencyExchange c = currencyTable.getSelectionModel().selectedItemProperty().get();
		service.deleteCurrency(c);
		updateProducts();
		if (currencies.isEmpty())
			deleteBTN.setDisable(true);
	}

	@FXML
	public void addCurrency() {
		CurrencyExchange c = new CurrencyExchange();
		String name = nameField.textProperty().get();
		if (name.trim().isEmpty()) {
			errorMessagesLBL.setText("Name cant be empty!");
			return;
		}
		Double rate = 0d;
		try {
			rate = Double.parseDouble(rateField.textProperty().get());
		} catch (NumberFormatException e) {
			errorMessagesLBL.setText("Check ur cost!");
			return;
		}
		name="USD/"+name;
		c.setName(name);
		c.setRate(rate);
		service.addCurrency(c);
		nameField.clear();
		rateField.clear();
		updateProducts();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
		currencies = FXCollections.observableArrayList(service.getAllCurrencies());
                nameField.textProperty().addListener((observable, oldValue, newValue) -> { 
nameField.setText(newValue.toUpperCase()); 
});
		currencyTable.setItems(currencies);
		currencyTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			deleteBTN.setDisable(false);
                        
		});
                
	}

	private void updateProducts() {
		currencies = FXCollections.observableArrayList(service.getAllCurrencies());
		currencyTable.setItems(currencies);
		deleteBTN.setDisable(true);
	}

}
