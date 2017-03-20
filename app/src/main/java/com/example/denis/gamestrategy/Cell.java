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

    public String getInfoAboutCell(){
        String info;
        switch (terrain) {
            case WATER:
                info = "Вода";
                break;
            case SAVANNAH:
                info = "Саванна";
                break;
            case HILLS:
                info = "Холмы";
                break;
            case PEAKS:
                info = "Горы";
                break;
            case DESERT:
                info = "Пустыня";
                break;
            case JUNGLE:
                info = "Джунгли";
                break;
            default:
                info = "";
        }
        return info;
    }


    public enum Terrain {
        HILLS, PEAKS, DESERT, SAVANNAH, JUNGLE, WATER,

        HILLS_COAST, DESERT_COAST, SAVANNAH_COAST, JUNGLE_COAST
    }

}
