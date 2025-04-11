package com.example.risk;

import java.util.HashSet;

/**
 *
 * @author Bryce Peterson and Noah Jones
 */
public class Territory {

    private int troops;

    private Player player;

    private double x;

    private double y;

    private HashSet<Territory> neighbors;

    public Territory(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTroops(){
        return troops;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public HashSet<Territory> getNeighbors() {
        return neighbors;
    }

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
