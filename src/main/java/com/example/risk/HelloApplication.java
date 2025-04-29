package com.example.risk;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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
		layout.getChildren().add(territory1.getButton());
		layout.getChildren().add(territory1.getLabel());
		layout.getChildren().add(addTroops);

		layout.getChildren().add(territory2.getButton());
		layout.getChildren().add(territory2.getLabel());

		Scene scene = new Scene(layout,500,500);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}