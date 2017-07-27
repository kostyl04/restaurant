package com.durovich.restaurant_admin.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Component
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DBconfig.class);
		FXMLLoader loader = ctx.getBean(FXMLLoaderProvider.class).getLoader("/main.fxml");
		BorderPane rootLayout = loader.load();

		primaryStage.setTitle("Demo Restauran");
		primaryStage.setScene(new Scene(rootLayout));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
