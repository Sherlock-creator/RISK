package com.example.risk;

import java.util.*;

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
}
