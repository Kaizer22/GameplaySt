package com.example.denis.gamestrategy;

/**
 * Created by denis on 19.02.17.
 */

public class Cell {
    private Terrain terrain;
    public Unit unitOnIt ;
    //public City cityOnIt;
    //public boolean cityOn;
    public boolean unitOn;
    public boolean someMarkerOnIt;


    public void setTerrain(Terrain t){
        terrain = t;
    }
    public Terrain getTerrain(){
        return terrain;
    }


    public enum Terrain {
        HILLS, PEAKS, DESERT, SAVANNAH, JUNGLE, WATER,

        HILLS_COAST, DESERT_COAST, SAVANNAH_COAST, JUNGLE_COAST
    }

}
