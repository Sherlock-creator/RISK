package com.example.risk;

import javafx.scene.paint.Color;
import java.util.HashSet;

/**
 * Represents a player in the game
 * @author Bryce Peterson and Noah Jones
 */
public class Player {

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
        // TODO
        return new HashSet<>();
    }

    /**
     * Either calculates how many total troops the player can have or how many the player can deploy this turn
     * TBD which of these two behaviors it will have
     * @return number of troops
     */
    public int getDeployCount(){
        // TODO
        return -1;
    }
}
