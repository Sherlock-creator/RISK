package com.example.risk;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApplication extends Application {

	Territory territory1;
	Territory territory2;


	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Risk");

		Board board = new Board("src/BoardFile.txt");
		board.addPlayer("Player1", new Color(0,.5,1,.9));
		board.addPlayer("Player2", new Color(1,0,0,.9));

		board.assignTerritories();

		Group group = board.getGroup();

		Scene scene = new Scene(group,500,500);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}
}