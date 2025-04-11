package com.example.risk;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents the current state of the board
 * @author Bryce Peterson and Noah Jones
 */
public class Board {

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
     * Standard Constructor, players and territories are directly set as private variables
     * @param players list of players in the game
     * @param territories set of territories that comprise the board
     */
    public Board(ArrayList<Player> players, HashSet<Territory> territories){
        this.players = players;
        this.territories = territories;
        currentPlayer = players.getFirst();
    }

    /**
     * Returns the list of players still in the game
     * @return ArrayList of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the set of territories that are on the board
     * @return HashSet of Territories
     */
    public HashSet<Territory> getTerritories() {
        return territories;
    }

    /**
     * Returns the player whose turn it currently is
     * @return player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets currentPlayer to the next player in players
     */
    public void nextPlayer(){
        //TODO
    }
}
