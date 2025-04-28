package com.example.risk;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

	//region Constructor Tests

	@Test
	public void defaultConstructorTerritory() {
		Territory territory = new Territory();

		assertEquals(0, territory.getTroops());
		assertEquals(new Player(), territory.getPlayer());
		assertEquals(new HashSet<>(), territory.getConnected());
		assertEquals(0, territory.getX());
		assertEquals(0, territory.getY());
		assertEquals(new HashSet<>(), territory.getNeighbors());
	}

	@Test
	public void defaultConstructorPlayer() {
		Player player = new Player();

		assertEquals(new Color(0,0,0,0), player.getColor());
		assertEquals("", player.getId());
		assertEquals(new HashSet<>(), player.getControlledTerritories());
		assertEquals(0, player.getDeployCount());
	}

	@Test
	public void defaultConstructorBoard() {
		Board board = new Board();

		assertEquals(new Player(), board.getCurrentPlayer());
		assertEquals(new HashSet<>(), board.getTerritories());

		Player[] playersArray = {new Player(), new Player()};
		ArrayList<Player> players = new ArrayList<>(List.of(playersArray));
		assertEquals(players, board.getPlayers());
	}

	@Test
	public void fileManualConstructorBoard() {
		String filename = "TestBoardManual.txt";
		Player[] playersArray = {new Player(), new Player()};
		ArrayList<Player> playersList = new ArrayList<>(List.of(playersArray));

		try {
			Board board = new Board(playersList, filename);
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	//endregion

	//region Game Logic Tests

	@Test
	public void getConnected() {
		// TODO
	}

	//endregion
}
