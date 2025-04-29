package com.example.risk;

import javafx.scene.paint.Color;
import java.util.HashSet;

/**
 * Represents a player in the game
 * @author Bryce Peterson and Noah Jones
 */
public class Player {

	//region Variables

	/**
	 * Identifying string, can be the player's name
	 */
	private String id = "";

	/**
	 * Color that represents the player on the board
	 */
	private Color color = new Color(0, 0, 0, 0);

	//endregion

	//region Constructors

	/**
	 * Standard constructor, directly takes id and color and sets them as private variables
	 * @param id Identifying string that can be a players name
	 * @param color Color that will be associated with a players controlled territories
	 */
	public Player(String id, Color color){
		this.id = id;
		this.color = color;
	}

	/**
	 * Default constructor for stubs, id is "" and color is black
	 */
	public Player() {}

	//endregion

	//region Getters

	/**
	 * Returns id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns color
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Fetches from the main board which territories are controlled by the player
	 * @return set of controlled territories
	 */
	public HashSet<Territory> getControlledTerritories(Board board){
		HashSet<Territory> controlled = new HashSet<>();

		for (Territory t : board.getTerritories()) {
			if (!(t.getPlayer() == null) && t.getPlayer().id.equals(id)) {
				controlled.add(t);
			}
		}
		return controlled;
	}

	/**
	 * Calculates how many total troops the player can deploy this turn
	 * @return number of troops
	 */
	public int getDeployCount(Board board){
		int troops = 3;
		troops += getControlledTerritories(board).size() / 3;
		return troops;
	}

	//endregion

}
