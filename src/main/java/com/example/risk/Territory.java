package com.example.risk;

import java.util.HashSet;

/**
 * Contains information necessary to take, control, and attack from a territory in risk
 * @author Bryce Peterson and Noah Jones
 */
public class Territory {

    /**
     * Number of troops in this territory
     */
    private int troops;

    /**
     * Player that controls this territory
     */
    private Player player;

    /**
     * X position of this territory in the window
     */
    private double x;

    /**
     * Y position of this territory in the window
     */
    private double y;

    /**
     * Set of all neighboring territories
     */
    private HashSet<Territory> neighbors;

    /**
     * Standard constructor, x and y are directly set as private variables
     * Neighbors and player are initially null, they will be added by Board
     * @param x the x position of the territory in the window
     * @param y the y position of the territory in the window
     */
    public Territory(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns player that controls this territory
     * @return player -reference to the player object that controls this territory
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns number of troops in this territory
     * @return troops -int of troops in territory
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
     * Sets the player reference that controls the territory
     * @param player player object reference
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the number of troops in a territory
     * @param troops amount of troops to set the territory to
     */
    public void setTroops(int troops) {
        this.troops = troops;
    }

    /**
     * Draws the territory in the window
     */
    public void draw(){
        //TODO
    }

    /**
     * Gets a list of territories that are connected and controlled by the same player
     * @return HashSet of Territories
     */
    public HashSet<Territory> getConnected(){
        //TODO
        return new HashSet<>();
    }

    /**
     * Moves the specified number of troops in this territory to the specified territory
     * @param t the territory that the troops will be moved to
     * @param troops number of troops to be moved
     */
    void moveTroops(Territory t, int troops){
        //TODO
    }

    /**
     * Returns a boolean that is true if troops are able to move from this territory to the specified territory
     * @param t the territory that troops are attempting to be moved to
     * @return boolean -true if troops can be moved
     */
    public boolean canMoveTroops(Territory t){
        //TODO
        return false;
    }

    /**
     * Wages war on a neighboring opponent controlled territory and, if successful in destroying the enemy troops, moves the troops into that territory and conquers it
     * @param t Territory to wage war on
     */
    public void conquer(Territory t){
        //TODO
    }

    /**
     * Returns a boolean that is true if it is possible to engage in a war with a neighboring territory
     * @param t Territory that is being attempted to be conquered
     * @return boolean -true if territory can be conquered
     */
    public boolean canConquer(Territory t){
        //TODO
        return false;
    }

    /**
     * Adds the specified number of troops to the territories troop total
     * @param amt the amount of troops to add
     */
    public void addTroops(int amt){
        //TODO
    }
}
