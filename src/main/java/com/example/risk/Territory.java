package com.example.risk;

import javafx.scene.control.*;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Contains information necessary to take, control, and attack from a territory in risk
 * @author Bryce Peterson and Noah Jones
 */
public class Territory {

	//region Variables

	/**
	 * Name of territory, displayed on the map and referenced when creating board from file
	 */
	private String name;

	/**
	 * Number of troops in this territory
	 */
	private int troops;

	/**
	 * Player that controls this territory
	 */
	private Player player;

	/**
	 * Board that this territory is in
	 */
	private Board board;

	/**
	 * X position of this territory in the window
	 */
	private double x ;

	/**
	 * Y position of this territory in the window
	 */
	private double y;

	/**
	 * Set of all neighboring territories
	 */
	private HashSet<Territory> neighbors;

	/**
	 * Radius, is the same for all circles
	 */
	static private double radius = 100;

	/**
	 * Circle for graphics
	 */
	private Circle circle;

	private Button button;

	private Label label;

	//endregion

	//region Constructors

	/**
	 * Default constructor
	 */
	public Territory() {
		name = "";
		troops = 1;
		player = null;
		board = null;
		x = 0;
		y = 0;
		neighbors = new HashSet<>();

		circle = new Circle(x, y, radius);
		button = new Button();
		label = new Label();
	}

	/**
	 * Standard constructor, name, x, y, and board are directly set as private variables
	 * Neighbors and player are initially null, they will be added by Board
	 * @param name the name of the territory that will be displayed, should be unique
	 * @param x the x position of the territory in the window
	 * @param y the y position of the territory in the window
	 * @param board the board that this territory is on
	 */
	public Territory(String name, double x, double y, Board board) {
		this();
		this.name = name;
		this.x = x;
		this.y = y;
		this.board = board;

		label.relocate(x, y-20);
		label.setText(name);

		button.relocate(x, y);
		button.setText(((Integer)getTroops()).toString());
		button.setOnAction(e -> board.onTerritoryButton(this));
	}

	//endregion

	//region Getters

	/**
	 *
	 * @return name of territory
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns player that controls this territory
	 * @return reference to the player object that controls this territory
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns number of troops in this territory
	 * @return number of troops in territory
	 */
	public int getTroops(){
		return troops;
	}

	/**
	 * Returns x position of this territory in the window
	 * @return x position in the window
	 */
	public double getX() {
		return x;
	}

	/**
	 * Return y position of this territory in the window
	 * @return y position in the window
	 */
	public double getY() {
		return y;
	}

	/**
	 * Return set of all neighboring territories
	 * @return neighbors -HashSet of Territory objects
	 */
	public HashSet<Territory> getNeighbors() {
		return neighbors;
	}

	/**
	 * @return the reference to the circle representing the territory
	 */
	public Circle getCircle() {
		return circle;
	}

	/**
	 * @return the reference to the button tied to the territory
	 */
	public Button getButton() {
		return button;
	}

	public Label getLabel() {
		return label;
	}

	//endregion

	//region Setters

	/**
	 * For setting the radius of all territories at runtime
	 * @param r radius
	 */
	static public void setRadius(double r) {
		radius = r;
	}

	/**
	 * Sets the player reference that controls the territory
	 * @param player player object reference
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Sets the number of troops in a territory
	 * @param amt amount of troops to set the territory to
	 */
	public void setTroops(int amt) {
		this.troops = amt;
		button.setText(((Integer)troops).toString());
	}

	public void addNeighbor(Territory neighbor) {
		neighbors.add(neighbor);
	}

	//endregion

	//region Move Logic

	/**
	 * Moves the specified number of troops in this territory to the specified territory
	 * @param other the territory that the troops will be moved to
	 * @param amt number of troops to be moved
	 */
	public void moveTroops(Territory other, int amt){
		if (canMoveTroops(other)) {
			addTroops(-amt);
			other.addTroops(amt);
		}
	}

	/**
	 * Returns a boolean that is true if troops are able to move from this territory to the specified territory
	 * @param other the territory that troops are attempting to be moved to
	 * @return boolean -true if troops can be moved
	 */
	public boolean canMoveTroops(Territory other){
		//loops through the HashSet that is returned by getConnected and if t is in their return true
		for (Territory territory : getConnected()) {
			if (territory == other) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets a HashSet of Territory objects that are connected and controlled by the same Player as this Territory
	 * @return HashSet of Territory objects
	 */
	public HashSet<Territory> getConnected(){
		HashSet<Territory> connected = new HashSet<>();
		updateConnected(connected);
		return connected;
	}

	/**
	 * This is the bulk of the logic for getConnected().
	 * Recursively updates the connected HashSet with same player, connected territories
	 * @param connected The current set of connected territories
	 */
	private void updateConnected(HashSet<Territory> connected) {

		// Iterate through neighbors
		for (Territory neighbor : neighbors) {
			if (connected.contains(neighbor) || neighbor.player != this.player) {
				continue; // Skip this neighbor if it has already been checked
			}

			connected.add(neighbor);

			// Since the same connected HashSet is being passed as an argument, it's updated
			// as part of the method body. No return value or copying is necessary, because
			// there's only one object here: connected.
			neighbor.updateConnected(connected);
		}
	}

	//endregion

	//region Conquer Logic

	/**
	 * Wages war on a neighboring opponent controlled territory and, if successful in destroying the enemy troops, moves the troops into that territory and conquers it
	 * @param defender Territory to wage war on
	 * @throws Exception if t is not a neighbor of this territory
	 */
	public void conquer(Territory defender) throws Exception{
		if (!canConquer(defender)) {
			throw new Exception();
		}

		int attackerDice;
		int defenderDice;

		//sets attacker dice total
		attackerDice = Math.min(troops, 3);

		//sets defender dice total
		defenderDice = Math.min(defender.troops, 2);

		Random r = new Random();
		ArrayList<Integer> attackerRolls = new ArrayList<>();
		for (int i = 0; i < attackerDice; i++) {
			attackerRolls.add((r.nextInt(6)+1));
		}

		ArrayList<Integer> defenderRolls = new ArrayList<>();
		for (int i = 0; i < defenderDice; i++) {
			defenderRolls.add((r.nextInt(6)+1));
		}

		attackerRolls = sortRolls(attackerRolls);
		defenderRolls = sortRolls(defenderRolls);

		int attackerLosses = 0;
		int defenderLosses = 0;
		while ((!attackerRolls.isEmpty()) && (!defenderRolls.isEmpty())) {
			if (attackerRolls.getFirst() > defenderRolls.getFirst()) {
				defenderLosses--;
			}
			else {
				attackerLosses--;
			}
			attackerRolls.removeFirst();
			defenderRolls.removeFirst();
		}

		addTroops(attackerLosses);
		defender.addTroops(defenderLosses);
	}

	/**
	 * Sorts an arraylist from highest to lowest
	 * @param rolls Arraylist of ints
	 * @return sorted arraylist
	 */
	private ArrayList<Integer> sortRolls(ArrayList<Integer> rolls) {
		ArrayList<Integer> sorted = new ArrayList<>();
		int size = rolls.size();
		for (int i = 0; i < size; i++) {
			int max = 0;
			for (int roll : rolls) {
				if (roll > max) {
					max = roll;
				}
			}
			sorted.add(max);
			rolls.remove((Object) max);
		}
		return sorted;
	}

	/**
	 * Returns a boolean that is true if it is possible to engage in a war with a neighboring territory
	 * @param defender Territory that is being attempted to be conquered
	 * @return boolean -true if territory can be conquered
	 */
	public boolean canConquer(Territory defender) {
		//loops through the territories neighbors and checks if t is one of them
		for (Territory territory : neighbors) {
			if (territory == defender) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Adds the specified number of troops to the territories troop total
	 * @param amt the amount of troops to add
	 * @throws IllegalArgumentException if amt is negative and has a greater absolute value than the current value of troops + 1
	 */
	public void addTroops(int amt) throws IllegalArgumentException{
		// check if the amount being added will reduce the troop count in the territory bellow 1
		if (amt < 0 && ((-1*amt) + 1) > troops) {
			throw new IllegalArgumentException();
		}
		troops += amt;
		button.setText(((Integer)troops).toString());
	}

	//endregion

	//region Buttons



	//endregion

}
