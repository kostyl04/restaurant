package com.durovich.restaurant_admin.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.durovich.restaurant_admin.entity.Product;
import com.durovich.restaurant_admin.entity.ProductType;
import com.durovich.restaurant_admin.service.ProductService;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class AddProductController implements Initializable {
	@Autowired
	private ProductService service;
	@FXML
	private TextField searchField;
	private FilteredList<Product> products;
	@FXML
	private TableView<Product> productTable;
	@FXML
	private TableColumn<?, ?> nameColumn;
	@FXML
	private TableColumn<?, ?> costColumn;
	@FXML
	private TableColumn<?, ?> typeColumn;
	@FXML
	private TextField nameField;
	@FXML
	private ComboBox<ProductType> productTypeField;
	@FXML
	private TextField priceField;
	@FXML
	private Button deleteBTN;
	@FXML
	private Button addBTN;
	@FXML
	private Label errorMessagesLBL;

	@FXML
	public void deleteProduct() {
		Product p = productTable.getSelectionModel().selectedItemProperty().get();
		service.deleteProduct(p);
		updateProducts();
		if (products.isEmpty())
			deleteBTN.setDisable(true);
	}

	@FXML
	public void addProduct() {
		Product p = new Product();//
		String name = nameField.textProperty().get();
		if (name.trim().isEmpty()) {
			errorMessagesLBL.setText("Name cant be empty!");
			return;
		}
		Double cost = 0d;
		try {
			cost = Double.parseDouble(priceField.textProperty().get());
		} catch (NumberFormatException e) {
			errorMessagesLBL.setText("Check ur cost!");
			return;
		}
		p.setCost(cost);
		p.setName(name);
		p.setProductType(productTypeField.getValue());
		service.addProduct(p);
		nameField.clear();
		priceField.clear();
		updateProducts();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
		products = new FilteredList<Product>(FXCollections.observableArrayList(service.getAllProducts()), p -> true);

		productTable.setItems(products);
		productTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			deleteBTN.setDisable(false);
		});
		productTypeField.setItems(FXCollections.observableArrayList(ProductType.values()));
		productTypeField.getSelectionModel().selectFirst();
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			products.setPredicate(myObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name field in your object with
				// filter.
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
					// Filter matches first name.

				}

				return false; // Does not match.
			});
		});
		SortedList<Product> sortedData = new SortedList<>(products);
		sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
		productTable.setItems(sortedData);
	}

	private void updateProducts() {
		searchField.clear();
		products = new FilteredList<Product>(FXCollections.observableArrayList(service.getAllProducts()), p -> true);
		productTable.setItems(products);
		deleteBTN.setDisable(true);
	}

}
