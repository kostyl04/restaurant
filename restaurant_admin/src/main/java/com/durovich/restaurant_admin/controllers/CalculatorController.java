package com.durovich.restaurant_admin.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class CalculatorController implements Initializable {
	private static boolean lock = false;
	private CalculatorUtils utils;
	private static final ArrayList<String> rightValues = new ArrayList<String>(
			Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."));
	@FXML
	private TextField result;
	@FXML
	private Label value;
	@FXML
	private Label operation;

	@FXML
	public void handleInput(ActionEvent event) {
		Button btn = (Button) event.getSource();
		String val = btn.getText();
		if (rightValues.contains(val))
			utils.addToResult(val);
		else {
			reverseLock();
			try {
				switch (val) {
				case "<-":
					utils.backSpace();
					break;
				case "C":
					utils.doC();
					break;
				case "+/-":
					utils.inverse();
					break;
				case "+":
					utils.setOperator(val);
					utils.setValue();
					utils.clear();
					break;
				case "-":
					utils.setOperator(val);
					utils.setValue();
					utils.clear();
					break;
				case "/":
					utils.setOperator(val);
					utils.setValue();
					utils.clear();
					break;
				case "x":
					utils.setOperator(val);
					utils.setValue();
					utils.clear();
					break;
				case "=":
					utils.doCalc();
					utils.clear();
					break;
				}
			} catch (Exception e) {

			} finally {
				reverseLock();
			}

		}
	}

	private void reverseLock() {
		lock = !lock;
	}

	private void checkValue(String val, String oldValue) {
		System.out.println(val);
		if (!rightValues.contains(val)) {
			result.setText(oldValue);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		utils = new CalculatorUtils();
		result.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!lock)
				checkValue(String.valueOf(newValue.charAt(newValue.length() - 1)), oldValue);
		});
	}

	private class CalculatorUtils {
		private void inverse() {

			double d = Double.valueOf(result.getText()) * -1;
			result.setText(String.valueOf(d));

		}

		public void doCalc() {
			Double x1 = Double.valueOf(value.getText());
			
			Double x2 = Double.valueOf(result.getText());
			if(x1.isNaN())
				x1=0.0;
			String op = operation.getText();
			double res = 0;
			switch (op) {
			case "+":
				res = x1 + x2;
				break;
			case "-":
				res = x1 - x2;
				break;
			case "/":
				res = x1 / x2;
				break;
			case "x":
				res = x1 * x2;
				break;
			}
			value.setText(String.valueOf(res));
		}

		public void setOperator(String val) {
			operation.setText(val);

		}

		public void setValue() {
			System.out.println(value.getText().equals("NaN"));
			if (value.getText().equals("0")||value.getText().equals("NaN"))
				if (!result.getText().isEmpty())
					value.setText(result.getText());

		}

		private void clear() {
			result.clear();
		}
		private void doC(){
			clear();
			value.setText("0");
			operation.setText("+");
		}
		private void backSpace() {
			result.setText(result.getText().substring(0, result.getText().length() - 1));
		}

		private void addToResult(String s) {
			result.setText(result.getText() + s);
		}
	}
}
