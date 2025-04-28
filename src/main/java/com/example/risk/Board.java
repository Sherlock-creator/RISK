package com.example.risk;

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

	//endregion

	//region Constructors

	/**
	 * Standard constructor, players and territories are directly set as private variables,
	 * currentPlayer is set as the first player in players
	 * @param players list of players in the game
	 * @param territories set of territories that comprise the board
	 */
	public Board(ArrayList<Player> players, HashSet<Territory> territories){
		this.players = players;
		this.territories = territories;
		currentPlayer = players.getFirst();
	}

	/**
	 * Default constructor for stubs, players is two default Player objects, territories is initially empty,
	 * currentPlayer is set as the first player in players
	 */
	public Board() {
		Player[] playersArray = new Player[]{new Player(), new Player()};
		players = new ArrayList<>();
		territories = new HashSet<>();
		currentPlayer = players.getFirst();
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

	public void drawAll() {
		for (Territory territory : territories) {
			territory.draw();
		}
	}

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
			ArrayList<String> neighborStrings = new ArrayList<>();
			;
			if (mode.equals("ManualNeighbors")) { // Parse neighbor names
				lineScanner.useDelimiter(",");
				while (lineScanner.hasNext()) {
					neighborStrings.add(lineScanner.next());
				}

			}

			// Create Territory object, add it to this board's list
			Territory territory = new Territory(name, x, y);
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
}
