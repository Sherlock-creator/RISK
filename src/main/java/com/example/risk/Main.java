package com.example.risk;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bryce Peterson and Noah Jones
 */
public class Main {
	public static void main(String[] args) throws Exception {
		String filename = "src/TestBoardManual.txt";
		Player[] playersArray = {new Player(), new Player()};
		ArrayList<Player> playersList = new ArrayList<>(List.of(playersArray));

		Board board = new Board();
		board = new Board(filename);
		board.addPlayer(playersList);

		System.out.println(board.getTerritories());
	}
}
