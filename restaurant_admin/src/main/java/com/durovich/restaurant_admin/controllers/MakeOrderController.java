package com.durovich.restaurant_admin.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.durovich.restaurant_admin.entity.CurrencyExchange;
import com.durovich.restaurant_admin.entity.Order;
import com.durovich.restaurant_admin.entity.OrderPosition;
import com.durovich.restaurant_admin.entity.PaymentType;
import com.durovich.restaurant_admin.entity.Product;
import com.durovich.restaurant_admin.entity.ProductType;
import com.durovich.restaurant_admin.service.CurrencyService;
import com.durovich.restaurant_admin.service.ProductService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class MakeOrderController implements Initializable {
	@Autowired
	private ProductService productService;
	@Autowired
	private CurrencyService currencyService;
	private List<Product> products;
	@FXML
	private Button printBtn;
	private ObservableList<OrderPosition> positions;
	@FXML
	private TableView<OrderPosition> positionsTable;

	@FXML
	private TableColumn<?, ?> nameColumn;
	@FXML
	private TableColumn<?, ?> costColumn;
	@FXML
	private TableColumn<?, ?> countColumn;
	@FXML
	private TableColumn<?, ?> perOneColumn;
	@FXML
	private ComboBox<Product> foodPicker;
	@FXML
	private ComboBox<Product> drinksPicker;

	@FXML
	private ComboBox<Product> barPicker;
	@FXML
	private TextField foodCount;
	@FXML
	private TextField barCount;
	@FXML
	private TextField drinksCount;
	@FXML
	private TextField discount;
	@FXML
	private Label totalAmount;
	@FXML
	private ComboBox<CurrencyExchange> exchangePicker;
	@FXML
	private Label convertedPrice;
	@FXML
	private ComboBox<PaymentType> paymentTypePicker;
	@FXML
	private TextArea billArea;
	@FXML
	private TextField tableNumberField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
		countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
		perOneColumn.setCellValueFactory(new PropertyValueFactory<>("costPerOne"));
		positions = FXCollections.observableArrayList(new ArrayList<OrderPosition>());
		positionsTable.setItems(positions);
		products = productService.getAllEnableProducts();
		foodPicker.setItems(FXCollections.observableArrayList(products.stream()
				.filter(p -> p.getProductType().equals(ProductType.Food)).collect(Collectors.toList())));
		drinksPicker.setItems(FXCollections.observableArrayList(products.stream()
				.filter(p -> p.getProductType().equals(ProductType.Drink)).collect(Collectors.toList())));
		barPicker.setItems(FXCollections.observableArrayList(products.stream()
				.filter(p -> p.getProductType().equals(ProductType.Bar)).collect(Collectors.toList())));
		exchangePicker.setItems(FXCollections.observableArrayList(currencyService.getAllCurrencies()));
		exchangePicker.valueProperty().addListener(new ChangeListener<CurrencyExchange>() {

			@Override
			public void changed(ObservableValue<? extends CurrencyExchange> observable, CurrencyExchange oldValue,
					CurrencyExchange newValue) {
				double d = Double.valueOf(totalAmount.getText());
				convertedPrice.setText(String.valueOf(d * newValue.getRate()));
			}
		});
		paymentTypePicker.setItems(FXCollections.observableArrayList(PaymentType.values()));
		paymentTypePicker.getSelectionModel().select(0);//
		totalAmount.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				double d = Double.valueOf(totalAmount.getText());
				try {
					convertedPrice.setText(
							String.valueOf(d * exchangePicker.getSelectionModel().getSelectedItem().getRate()));
				} catch (Exception e) {
					convertedPrice.setText("0");
				}
			}
		});
		billArea.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.isEmpty())
					printBtn.setDisable(true);
				else
					printBtn.setDisable(false);
			}
		});
		discount.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.isEmpty())
					newValue="0";
				else try{
					double d=Double.valueOf(newValue);
				}catch(Exception e){
					newValue=oldValue;
				}
				discount.setText(newValue);
				updateInfo();
			}

		});
	}

	@FXML
	public void addFood() {
		Product p = foodPicker.getSelectionModel().getSelectedItem();
		int count = Integer.valueOf(foodCount.getText());
		OrderPosition o = new OrderPosition();
		o.setCost(p.getCost() * count);
		o.setCostPerOne(p.getCost());
		o.setCount(count);
		o.setProduct(p);
		positions.add(o);
		updateInfo();

	}

	@FXML
	public void addDrinks() {
		Product p = drinksPicker.getSelectionModel().getSelectedItem();
		int count = Integer.valueOf(drinksCount.getText());
		OrderPosition o = new OrderPosition();
		o.setCost(p.getCost() * count);
		o.setCostPerOne(p.getCost());
		o.setCount(count);
		o.setProduct(p);
		positions.add(o);
		updateInfo();
	}

	@FXML
	public void addBar() {
		Product p = barPicker.getSelectionModel().getSelectedItem();
		int count = Integer.valueOf(barCount.getText());
		OrderPosition o = new OrderPosition();
		o.setCost(p.getCost() * count);
		o.setCostPerOne(p.getCost());
		o.setCount(count);
		o.setProduct(p);
		positions.add(o);
		updateInfo();
	}

	private void updateInfo() {
		double summ = 0d;
		for (OrderPosition o : positions) {
			summ += o.getCost();
		}
		summ -= Double.valueOf(discount.textProperty().get());
		summ=summ<0?0:summ;
		totalAmount.setText(String.valueOf(summ));//
	}

	@FXML
	private void makeOrder() {
		Order o = new Order();
		o.setPositions(positions);
		o.setTotalAmount(Double.valueOf(totalAmount.getText()));
		o.setPaymentType(paymentTypePicker.getSelectionModel().getSelectedItem().toString());
		o.setTableNumber(Integer.valueOf(tableNumberField.getText()));
		long id = productService.addOrder(o).getId();
		o.setId(id);
		billArea.setText(o.getBill());
	}

	@FXML
	private void reset() {
		System.out.println("asd");
		positions.removeAll(positions);
		foodPicker.getSelectionModel().select(0);
		drinksPicker.getSelectionModel().select(0);
		barPicker.getSelectionModel().select(0);
		barCount.setText("");
		foodCount.setText("");
		drinksCount.setText("");
		tableNumberField.setText("1");
		paymentTypePicker.getSelectionModel().select(0);
		totalAmount.setText("0");
		billArea.clear();
		discount.setText("0");
	}
	@FXML
	public void deletePosition(){
		List <OrderPosition> list=positionsTable.getSelectionModel().getSelectedItems();
		positions.removeAll(list);
		updateInfo();
		
	}

	@FXML
	private void print() throws PrintException, IOException {
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		// prints the famous hello world! plus a form feed
		try (InputStream is = new ByteArrayInputStream(billArea.getText().getBytes("utf-8"));) {
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(new Copies(1));
			Doc doc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
			DocPrintJob job = service.createPrintJob();
			job.print(doc, pras);
		}

	}

}
