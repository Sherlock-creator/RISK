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
	private String id;

	/**
	 * Color that represents the player on the board
	 */
	private Color color;

	/**
	 * Gives the player's methods access to the board the player's in
	 */
	private Board board;

	//endregion

	//region Constructors

	/**
	 * Default constructor for stubs, id is "" and color is black
	 */
	public Player() {
		Color color = new Color(0, 0, 0, 0);
		String id = "";
	}

	/**
	 * Standard constructor, directly takes id and color and sets them as private variables
	 * @param id Identifying string that can be a players name
	 * @param color Color that will be associated with a players controlled territories
	 */
	public Player(String id, Color color, Board board){
		this();
		this.id = id;
		this.color = color;
		this.board = board;
	}



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
	public HashSet<Territory> getControlledTerritories(){
		HashSet<Territory> controlled = new HashSet<>();

		for (Territory t : board.getTerritories()) {
			if (t.getPlayer().id.equals(id)) {
				controlled.add(t);
			}
		}
		return controlled;
	}

	/**
	 * Calculates how many total troops the player can deploy this turn
	 * @return number of troops
	 */
	public int getDeployCount(){
		int troops = 3;
		troops += getControlledTerritories().size() / 3;
		return troops;
	}

	//endregion

}
