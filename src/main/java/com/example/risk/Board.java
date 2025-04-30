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

	/**
	 * Holds the number of troops the current player has left to deploy in the deployment phase
	 */
	private int troopsToDeploy;

	/**
	 * During the conquer phase, this button will allow the current player to deselect the territory they are using to attack with
	 */
	private Button cancelButton;

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

		doneButton = new Button("Done");
		doneButton.relocate(0,50);
		doneButton.setOnAction(e -> onDoneButton());
		doneButton.setVisible(false);

		endTurnButton = new Button("End Turn");
		endTurnButton.relocate(0,50);
		endTurnButton.setOnAction(e -> onEndTurnButton());
		endTurnButton.setVisible(false);

		cancelButton = new Button("Cancel");
		cancelButton.relocate(0,25);
		cancelButton.setOnAction(e -> onCancelButton());
		cancelButton.setVisible(false);
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
			basement.getChildren().add(territory.getLabel());  // so I could call basement.getChildren() -Noah Jones
		}

		basement.getChildren().add(endTurnButton);
		basement.getChildren().add(doneButton);
		basement.getChildren().add(phaseName);
		basement.getChildren().add(cancelButton);

		return basement;
	}

	public void assignTerritories() {
		int numLoops = 0;
		for (Territory territory : territories) {
			territory.setPlayer(players.get((numLoops % players.size())));
			numLoops++;
		}

		troopsToDeploy = currentPlayer.getDeployCount(this);
		phaseName = new Label(currentPlayer.getId() + "'s " + phase + " phase\n" + troopsToDeploy + " troops left");
	}

	//endregion

	//region Game Logic

	/**
	 * Sets currentPlayer to the next player in players
	 */
	public void nextPlayer() {
		try {
			currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
		} catch (IndexOutOfBoundsException e) {
			currentPlayer = players.getFirst();
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
			if (currentPlayer.equals(territory.getPlayer())) {
				territory.addTroops(1);
				troopsToDeploy--;
				phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase\n" + troopsToDeploy + " troops left");

				if (troopsToDeploy == 0) {
					phase = "conquer";
					phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase");
					doneButton.setVisible(true);
				}
			}

		} else if (phase.equals("conquer")) {
			if (currentPlayer.equals(territory.getPlayer()) && selectedTerritory == null) {
				selectedTerritory = territory;
				cancelButton.setVisible(true);
			} else if (!(currentPlayer.equals(territory.getPlayer())) && selectedTerritory != null) {
				try {
					boolean success = selectedTerritory.conquer(territory);
					if (success) {
						selectedTerritory = null;
					}
				} catch (Exception _){}
			}
		} else if (phase.equals("move")){
			if (currentPlayer.equals(territory.getPlayer()) && selectedTerritory == null) {
				selectedTerritory = territory;
			} else if (currentPlayer.equals(territory.getPlayer()) && selectedTerritory != null) {
				selectedTerritory.moveTroops(territory, selectedTerritory.getTroops() - 1);
				selectedTerritory = null;
			}
		}
	}

	/**
	 * Ends the "conquer" phase and moves to the "move" phase
	 */
	public void onDoneButton() {
		cancelButton.setVisible(false);
		selectedTerritory = null;
		phase = "move";
		phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase");
		doneButton.setVisible(false);
		endTurnButton.setVisible(true);
	}

	/**
	 * Ends the turn and moves to the next person, going to the "deploy" phase if the player has any troops to deploy
	 */
	public void onEndTurnButton() {
		cancelButton.setVisible(false);
		selectedTerritory = null;
		nextPlayer();
		troopsToDeploy = currentPlayer.getDeployCount(this);
		phase = "deploy";
		phaseName.setText(currentPlayer.getId() + "'s " + phase + " phase\n" + troopsToDeploy + " troops left");
		endTurnButton.setVisible(false);
	}

	/**
	 * deselects the territory that is currently selected
	 */
	public void onCancelButton() {
		selectedTerritory = null;
		cancelButton.setVisible(false);
	}

	//endregion

	//region Read Board File

	/**
	 * <p>Opens and interprets a board file of the given filename.</p>
	 *
	 * <p><b>File format is:</b><br>
	 * ManualNeighbors/AutomaticNeighbors<br>
	 * [Range NeighborMax]<br>
	 * TerritoryName0 x y [Neighbor0 Neighbor1 Neighbor2]<br>
	 * TerritoryName1 x y [Neighbor0 Neighbor1 Neighbor2]<br>
	 * TerritoryName2 x y [Neighbor0 Neighbor1 Neighbor2]</p>
	 *
	 * <p>Neighbors should only be specified if ManualNeighbors is on.</p>
	 *
	 * <p>Range and NeighborMax are only used if mode is AutomaticNeighbors. AutomaticNeighbors determines whether
	 * territories are connected based on whether they're within Radius units of each other and limits the number of
	 * neighbors a given territory can have by NeighborMax. If NeighborMax is reached, the closest territories are
	 * picked over further ones.</p>
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

		// Parse range and neighborMax
		double range = 0;
		int neighborMax = 0;
		if (mode.equals("AutomaticNeighbors"))
			try {
				range = Double.parseDouble(scanner.next());
				neighborMax = Integer.parseInt(scanner.next());
				scanner.nextLine();
			} catch (Exception e) {
				throw new Exception("Range format is wrong for AutomaticNeighbors. Must be a number.");
			}

		// Map that will hold the neighbor relations between all the territories
		HashMap<String, ArrayList<String>> neighborMap = new HashMap<>();
		// Map that will allow for accessing territories by name
		HashMap<String, Territory> nameMap = new HashMap<>();

		// Loop through list of territories
		while (scanner.hasNextLine()) {
			Scanner lineScanner = new Scanner(scanner.nextLine());

			// Parse name
			String name = lineScanner.next();
			neighborMap.put(name, new ArrayList<>());

			// Parse coordinates
			double x;
			double y;
			try {
				x = Double.parseDouble(lineScanner.next());
				y = Double.parseDouble(lineScanner.next());
			} catch (Exception e) {
				throw new Exception("Coordinate format is wrong. Must be a pair of numbers.");
			}

			// Add neighbor names to map
			if (mode.equals("ManualNeighbors")) {
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
			for (Territory territory : territories) {

				for (Territory other : territories) {
					if (!territory.equals(other) && territory.getDistance(other) < range) {
						territory.addNeighbor(other);
					}
				}

				// Picks the closest territories in neighborList until neighborMax is reached
				while (territory.getNeighbors().size() > neighborMax) {
					Territory farthest = territory;
					for (Territory neighbor : territory.getNeighbors()) {
						if (territory.getDistance(neighbor) > territory.getDistance(farthest)) {
							farthest = neighbor;
						}
					}
					territory.removeNeighbor(farthest);
				}

				territory.getNeighbors().size();
			}
		}

		// Check if all neighboring is mutual, makes it mutual if it isn't
		for (Territory territory : territories) {
			for (Territory other : territory.getNeighbors()) {
				if (!other.getNeighbors().contains(territory)) {
					other.addNeighbor(territory);
					//System.out.printf("Warning: %s does not mutually neighbor %s.\n", other.getName(), territory.getName());
				}
			}
		}
	}

	//endregion

}
