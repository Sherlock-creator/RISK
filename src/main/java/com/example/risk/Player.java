package com.example.risk;

import javafx.scene.paint.Color;
import java.util.HashSet;

/**
 *
 * @author Bryce Peterson and Noah Jones
 */
public class Player {

    private String id;

    private Color color;

    private Board board;

    public Player(String id, Color color){
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public HashSet<Territory> getControlledTerritories(){
        return new HashSet<>();
    }

    public int getDeployCount(){
        return -1;
    }
}
