package com.durovich.restaurant_admin.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.durovich.restaurant_admin.entity.Order;
import com.durovich.restaurant_admin.entity.Product;
import com.durovich.restaurant_admin.entity.ProductType;
import com.durovich.restaurant_admin.service.OrdersService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class OrdersController implements Initializable {
	@Autowired
	private OrdersService service;
	@FXML
	private ComboBox<String> statusBox;
	private FilteredList<Order> orders;
	@FXML
	private TableView<Order> orderTable;
	@FXML
	private ComboBox<String> filterBox;

	@FXML
	private TableColumn<?, ?> orderNumberColumn;
	@FXML
	private TableColumn<?, ?> tableNumberColumn;
	@FXML
	private TableColumn<?, ?> timeColumn;
	@FXML
	private TableColumn<?, ?> statusColumn;
	@FXML
	private Button updateBTN;
	
	@FXML
	public void updateStatus() {
		Order o = orderTable.getSelectionModel().getSelectedItem();
		o.setStatus(statusBox.getSelectionModel().getSelectedItem());
		service.updateStatus(o);
		orderTable.refresh();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
		orders = new FilteredList<Order>(FXCollections.observableArrayList(service.getAllOrders()));
		
		orderTable.setItems(orders);
		orderTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			updateBTN.setDisable(false);
		});
		statusBox.setItems(FXCollections.observableArrayList(Arrays.asList("Pending", "Closed", "Declined")));
		statusBox.getSelectionModel().selectFirst();
		filterBox.setItems(FXCollections.observableArrayList(Arrays.asList("All", "Pending", "Closed", "Declined")));
		filterBox.getSelectionModel().selectFirst();
		filterBox.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String status) {
				orders.setPredicate(o -> {
					
					
					if (status.equals("All"))
						return true;
					else if (status.equals(o.getStatus()))
						return true;
					return false;

				});

			}

		});
		
	}

}
