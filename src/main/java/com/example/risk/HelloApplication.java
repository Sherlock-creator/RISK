package com.example.risk;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLOutput;

public class HelloApplication extends Application {

	Button button;

	@Override
	public void start(Stage stage) throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//		stage.setTitle("Hello!");
//		stage.setScene(scene);
//		stage.show();
		stage.setTitle("Risk");
		button = new Button();
		button.setText("This Is a Button");
		button.setOnAction(e -> System.out.println("balls"));

		Group layout = new Group();
		layout.getChildren().add(button);

		Scene scene = new Scene(layout,500,500);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}