package com.example.risk;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;


public class HelloApplication extends Application {

	Territory territory1;
	Territory territory2;

	@Override
	public void start(Stage stage) throws IOException {
		stage.setTitle("Risk");
		HashSet<Territory> set1 = new HashSet<>();
		set1.add(territory2);
		HashSet<Territory> set2 = new HashSet<>();
		set2.add(territory1);

		territory1 = new Territory("test1",200,200, set1);
		territory2 = new Territory("test2", 300, 300, set2);

		Button addTroops = new Button("add 5 troops");
		addTroops.setOnAction(e -> territory1.addTroops(5));

		Group layout = new Group();
		layout.getChildren().add(territory1.getMenuButton());
		layout.getChildren().add(territory1.getMenu());
		layout.getChildren().add(addTroops);

		layout.getChildren().add(territory2.getMenuButton());
		layout.getChildren().add(territory2.getMenu());

		Scene scene = new Scene(layout,500,500);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}