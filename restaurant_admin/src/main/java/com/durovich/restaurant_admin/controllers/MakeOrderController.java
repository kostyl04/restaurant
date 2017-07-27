package com.durovich.restaurant_admin.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.durovich.restaurant_admin.entity.CurrencyExchange;
import com.durovich.restaurant_admin.entity.Order;
import com.durovich.restaurant_admin.entity.OrderPosition;
import com.durovich.restaurant_admin.entity.Product;
import com.durovich.restaurant_admin.entity.ProductType;
import com.durovich.restaurant_admin.service.CurrencyService;
import com.durovich.restaurant_admin.service.ProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class MakeOrderController implements Initializable {
	@Autowired
	private ProductService productService;
	@Autowired
	private CurrencyService currencyService;
	
	private ObservableList<OrderPosition> mealPositions;
	private ObservableList<OrderPosition> drinkPositions;
	@FXML
	private TableView<OrderPosition> mealTable;
	@FXML
	private TableView<OrderPosition> drinkTable;
	@FXML
	private ComboBox<Product> productPicker;
	@FXML
	private TextField countField;
	@FXML
	private TableColumn<?, ?> nameColumn;
	@FXML
	private TableColumn<?, ?> costColumn;
	@FXML
	private TableColumn<?, ?> countColumn;
	@FXML
	private TableColumn<?, ?> perOneColumn;
	@FXML
	private TableColumn<?, ?> nameColumn1;
	@FXML
	private TableColumn<?, ?> costColumn1;
	@FXML
	private TableColumn<?, ?> countColumn1;
	@FXML
	private TableColumn<?, ?> perOneColumn1;
	@FXML
	private Label mealTotal;
	@FXML
	private Label drinkTotal;
	@FXML
	private Label totalAmount;
	@FXML
	private Label convertAmount;
	@FXML
	private ComboBox<CurrencyExchange> exchangePicker;

	@FXML
	public void addProduct() {
		int count = 1;
		try {
			count = Integer.valueOf(countField.getText());
		} catch (NumberFormatException e) {
		}
		Product p = productPicker.getValue();
		OrderPosition orderPos = new OrderPosition();
		orderPos.setProduct(p);
		orderPos.setCount(count);
		orderPos.setCost(count * p.getCost());
		orderPos.setCostPerOne(p.getCost());
		if (p.getProductType().equals(ProductType.Meal))
			mealPositions.add(orderPos);
		else
			drinkPositions.add(orderPos);
		update();
		///
	}

	private void update() {
		double meatSumm = mealPositions.stream().mapToDouble(p -> p.getCost()).sum();
		double drinkSumm = drinkPositions.stream().mapToDouble(p -> p.getCost()).sum();
		mealTotal.setText(String.valueOf(meatSumm));
		drinkTotal.setText(String.valueOf(drinkSumm));
		totalAmount.setText(String.valueOf(meatSumm + drinkSumm));
		updateConvertAmount();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
		countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
		perOneColumn.setCellValueFactory(new PropertyValueFactory<>("costPerOne"));
		nameColumn1.setCellValueFactory(new PropertyValueFactory<>("product"));
		costColumn1.setCellValueFactory(new PropertyValueFactory<>("cost"));
		countColumn1.setCellValueFactory(new PropertyValueFactory<>("count"));
		perOneColumn1.setCellValueFactory(new PropertyValueFactory<>("costPerOne"));
		mealPositions = FXCollections.observableArrayList(new ArrayList<OrderPosition>());
		drinkPositions = FXCollections.observableArrayList(new ArrayList<OrderPosition>());
		drinkTable.setItems(drinkPositions);
		mealTable.setItems(mealPositions);
		productPicker.setItems(FXCollections.observableArrayList(productService.getAllProducts()));
		productPicker.getSelectionModel().selectFirst();
		exchangePicker.setItems(FXCollections.observableArrayList(currencyService.getAllCurrencies()));
		exchangePicker.getSelectionModel().selectFirst();
		exchangePicker.getSelectionModel().selectedItemProperty().addListener(e -> {
			updateConvertAmount();
		});

		
	}

	private void updateConvertAmount() {
		Double rate = exchangePicker.getSelectionModel().selectedItemProperty().get().getRate();
		double total = Double.valueOf(totalAmount.getText());
		convertAmount.setText(String.valueOf(total * rate));

	}

	@FXML
	public void addOrder() {
		Order o=new Order();
		List<OrderPosition> positions=new ArrayList();
		positions.addAll(mealPositions);
		positions.addAll(drinkPositions);
		o.setTotalAmount(Double.valueOf(totalAmount.getText()));
		o.setPositions(positions);
		productService.addOrder(o);
	}

}
