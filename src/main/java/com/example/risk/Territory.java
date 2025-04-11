package com.example.risk;

import java.util.HashSet;

/**
 *
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
     * @param x
     * @param y
     */
    public Territory(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns player that controls this territory
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns number of troops in this territory
     * @return troops
     */
    public int getTroops(){
        return troops;
    }

    /**
     * Returns x position of this territory in the window
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Return y position of this territory in the window
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Return set of all neighboring territories
     * @return neighbors
     */
    public HashSet<Territory> getNeighbors() {
        return neighbors;
    }

    /**
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }


    public void setTroops(int troops) {
        this.troops = troops;
    }

    public void draw(){

    }

    public HashSet<Territory> getConnected(){
        return new HashSet<>();
    }

    public void moveTroops(Territory t){

    }

    public boolean canMoveTroops(Territory t){
        return false;
    }

    public void conquer(Territory t){

    }

    public boolean canConquer(Territory t){
        return false;
    }

    public void addTroops(int amt){

    }
}
