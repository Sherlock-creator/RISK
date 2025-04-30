package com.example.risk;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents the current state of the board
 * @author Bryce Peterson and Noah Jones
 */
public class Board {

	//region Variables

	/**
	 * List of players currently in the game
	 */
	private ArrayList<Player> players;

	/**
	 * The player whose turn it is currently
	 */
	private Player currentPlayer;

	/**
	 * Set of Territory objects that make up the spaces on the board
	 */
	private HashSet<Territory> territories;

	/**
	 * Reference to the currently selected territory
	 */
	private Territory selectedTerritory;

	/**
	 * Represents what part of the turn you're in
	 */
	private String phase;

	/**
	 * During the "conquer" state, allows you to move on to the "move" state
	 */
	private Button doneButton;

	/**
	 * During the "move" state, allows you to move on to the next players turn
	 */
	private Button endTurnButton;

	/**
	 * Represents the phase a players turn is in
	 */
	private Label phaseName;

	private int troopsToDeploy;

	//endregion

	//region Constructors

	/**
	 * <p>Default constructor, players is two default Player objects, territories is initially empty,
	 * currentPlayer is set as the first player in players</p>
	 * <p>Contains all default values that will always be true at the start of a game</p>
	 */
	public Board() {
		players = new ArrayList<>();
		territories = new HashSet<>();

		selectedTerritory = null;
		phase = "deploy";

		doneButton = new Button();
		doneButton.setText("Done");
		doneButton.relocate(0,0);
		doneButton.setOnAction(e -> onDoneButton());
		doneButton.setVisible(false);

		endTurnButton = new Button();
		endTurnButton.setText("End Turn");
		endTurnButton.relocate(0,50);
		endTurnButton.setOnAction(e -> onEndTurnButton());
		endTurnButton.setVisible(false);
	}

	/**
	 * Manual, non-file based constructor, players and territories are directly set as private variables,
	 * currentPlayer is set as the first player in players
	 * @param territories set of territories that comprise the board
	 */
	public Board(HashSet<Territory> territories) {
		this();
		this.territories = territories;
	}

	/**
	 * <p>File based constructor, reads given filename and parses it to get the list of territories, currentPlayer is
	 * set as the first player in players </p>
	 * <p>Contains all things that will be calculated at runtime, like initial troopsToDeploy and players</p>
	 * @param filename Name of file that will be parsed to generate the board
	 * @throws Exception Throws various exceptions based on what goes wrong during file parsing
	 */
	public Board(String filename) throws Exception {
		this();
		readBoardFile(filename);
	}

	//endregion

	//region Setters

	/**
	 * Adds the player to this board. If this is the first player added, board will set currentPlayer to it
	 * @param player the player to add
	 */
	public void addPlayer(Player player) {
		if (players.contains(player)) return;
		players.add(player);
		if (players.size() == 1) {
			currentPlayer = player;
			troopsToDeploy = player.getDeployCount(this);
			phaseName = new Label(currentPlayer.getId() + "'s " + phase + " phase\n" + troopsToDeploy + " troops left");
		}
	}

	/**
	 * Creates a player with the given id and color and adds it to the board. If this is the first player added,
	 * board will set currentPlayer to it
	 * @param id the player's id
	 * @param color the player's color
	 */
	public void addPlayer(String id, Color color) {
		addPlayer(new Player(id, color));
	}

	/**
	 * Adds all players in the list to the board. If the board previously had no players, currentPlayer will be
	 * set to the first player in the list
	 * @param playerList list of players
	 */
	public void addPlayer(ArrayList<Player> playerList) {
		for (Player player : playerList) {
			addPlayer(player);
		}
	}

	//endregion

	//region Getters

	/**
	 * Returns the reference of the list of players still in the game
	 * @return ArrayList of players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Returns the reference of the set of territories that are on the board
	 * @return HashSet of Territories
	 */
	public HashSet<Territory> getTerritories() {
		return territories;
	}

	/**
	 * Returns the reference of the player whose turn it currently is
	 * @return player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Group getGroup() {
		Group basement = new Group();

		// for each territory, add its button and label to the group
		ArrayList<Territory> territoryArrayList = new ArrayList<>(territories);
		for (int i = 0; i < territories.size(); i++) {
			Territory territory = territoryArrayList.get(i);

			for (int j = i+1; j < territories.size(); j++) {
				Territory other = territoryArrayList.get(j);
				if (territory.getNeighbors().contains(other)) {
					basement.getChildren().add(new Line(territory.getX(),territory.getY(),other.getX(),other.getY()));
				}
			}

			basement.getChildren().add(territory.getButton()); // Why yes, I did call this variable basement purely
			basement.getChildren().add(territory.getLabel());  // so I could call basement.getChildren()
		}

		basement.getChildren().add(endTurnButton);
		basement.getChildren().add(doneButton);
		basement.getChildren().add(phaseName);

		return basement;
	}

	//endregion

	//region Game Logic

	/**
	 * Sets currentPlayer to the next player in players
	 */
	public void nextPlayer() {
		for (int i = 0; i < players.size(); i++) {
			if (currentPlayer == players.get(i)) {
				currentPlayer = players.get((i+1) % players.size());
			}
		}
	}

	/**
	 * Removes a Player from players
	 * @param player the player you want to remove
	 */
	public void removePlayer(Player player) {
		players.remove(player);
	}

	//endregion

	//region Buttons

	/**
	 * Advances game logic based on what territory's button was clicked
	 * Varies based on what phase you're in
	 * @param territory the territory that the button was clicked on
	 */
	public void onTerritoryButton(Territory territory) {
		if (phase.equals("deploy")) {
			phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase\n" + troopsToDeploy + " troops left");
			if (currentPlayer.getControlledTerritories(this).contains(territory)) {
				territory.addTroops(1);
				troopsToDeploy--;

				if (troopsToDeploy == 0) {
					phase = "conquer";
					phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase");
					doneButton.setVisible(true);
				}
			}

		} else if (phase.equals("conquer")) {
			//TODO
		} else if (phase.equals("move")){

		}
	}

	/**
	 * Ends the "conquer" phase and moves to the "move" phase
	 */
	public void onDoneButton() {
		phase = "move";
		phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase");
		doneButton.setVisible(false);
		endTurnButton.setVisible(true);
	}

	/**
	 * Ends the turn and moves to the next person, going to the "deploy" phase if the player has any troops to deploy
	 */
	public void onEndTurnButton() {
		nextPlayer();
		troopsToDeploy = currentPlayer.getDeployCount(this);
		phase = "deploy";
		phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase");
		endTurnButton.setVisible(false);
	}

	//endregion

	//region Read Board File

	/**
	 * <p>Opens and interprets a board file of the given filename.</p>
	 *
	 * <p><b>File format is:</b><br>
	 * ManualNeighbors/AutomaticNeighbors<br>
	 * [Range]<br>
	 * TerritoryName0 y x [Neighbor0,Neighbor1,Neighbor2]<br>
	 * TerritoryName1 y x [Neighbor0,Neighbor1,Neighbor2]<br>
	 * TerritoryName2 y x [Neighbor0,Neighbor1,Neighbor2]</p>
	 *
	 * <p><b>Take note that y and x are in unconventional order for coordinates, and that y starts at 0 at the top of
	 * the screen and increases going downwards. This is to be consistent with JavaFX.</b></p>
	 *
	 * <p>Neighbors should only be specified if ManualNeighbors is on.</p>
	 *
	 * <p>Range is only used if mode is AutomaticNeighbors. AutomaticNeighbors determines whether territories
	 * are connected based on whether they're within Radius units of each other.</p>
	 *
	 * @param filename the board file that will be read
	 * @throws FileNotFoundException if the file is not found
	 */
	public void readBoardFile(String filename) throws FileNotFoundException, Exception {
		territories = new HashSet<>();
		Scanner scanner = new Scanner(new File(filename));

		String mode = scanner.nextLine();
		assert mode.equals("ManualNeighbors") || mode.equals("AutomaticNeighbors")
				: "Invalid file mode. Must be ManualNeighbors or AutomaticNeighbors";

		double range;
		if (mode.equals("AutomaticNeighbors"))
			try {
				range = Double.parseDouble(scanner.nextLine());
			} catch (Exception e) {
				throw new Exception("Range format is wrong for AutomaticNeighbors. Must be a number.");
			}

		// Map that will hold the neighbor relations between all the territories
		HashMap<String, ArrayList<String>> neighborMap = new HashMap<>();
		// Map that will allow for accessing territory by name
		HashMap<String, Territory> nameMap = new HashMap<>();

		// Loop through list of territories
		while (scanner.hasNextLine()) {
			Scanner lineScanner = new Scanner(scanner.nextLine());

			// Parse name
			String name = lineScanner.next();
			neighborMap.put(name, new ArrayList<>());

			// Parse coordinates
			double y;
			double x;
			try {
				y = Double.parseDouble(lineScanner.next());
				x = Double.parseDouble(lineScanner.next());
			} catch (Exception e) {
				throw new Exception("Coordinate format is wrong. Must be a pair of numbers.");
			}

			// Add neighbors
			if (mode.equals("ManualNeighbors")) { // Parse neighbor names
				while (lineScanner.hasNext()) {
					String neighbor = lineScanner.next();
					neighborMap.get(name).add(neighbor);
				}
			}

			// Create Territory object, add it to this board's list
			Territory territory = new Territory(name, x, y, this);
			territories.add(territory);
			nameMap.put(name, territory);
		}

		// Add neighbors to each territory
		// This needs to happen last, because nameMap isn't full until the whole file is read through
		// (So not all the territories exist yet and can't be added as neighbors)
		if (mode.equals("ManualNeighbors")) {
			for (Territory territory : territories) {
				for (String neighborName : neighborMap.get(territory.getName())) {
					territory.addNeighbor(nameMap.get(neighborName));
				}
			}
		} else if (mode.equals("AutomaticNeighbors")) {
			//TODO optional, make a system for automatically adding neighbors based on range variable
		}
	}

	//endregion

}
