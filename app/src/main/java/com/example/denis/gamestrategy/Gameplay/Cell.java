package com.example.denis.gamestrategy.Gameplay;

/**
 * Created by denis on 19.02.17.
 */

public class Cell {
    private Terrain terrain;
    private TypeOfCell typeOfCell;

    public Player.Fraction territoryOf;

    public double cellCoeff;


    public boolean cityOn;
    public boolean unitOn;
    public boolean moveOpportunityMarkerOnIt;
    public boolean attackMarkerOnIt;


    public Cell(){
        territoryOf = Player.Fraction.NONE;
    }


    public void setTerrain(Terrain t){
        terrain = t;
        cellCoeff = GameLogic.getCellCoeffByTerrain(t);
    }

    public void setTypeOfCell(TypeOfCell t){typeOfCell = t;}

    public Terrain getTerrain(){
        return terrain;
    }
    public TypeOfCell getTypeOfCell() {
        return typeOfCell;
    }

    public String getInfoAboutCell(){
        String info;
        info = StringConverter.getTerrainOnRussian(terrain);
        info += "  " + StringConverter.getFractionOnRussian(territoryOf);
        return info;
    }

    public enum TypeOfCell{                                     //L-left  R-right  T-Top     Re - reversed    C-corner
        DIAG_L_T_C,DIAG_R_T_C,DIAG_R_D_C,DIAG_L_D_C,
        COAST_T,COAST_D,COAST_L,COAST_R,
        BAY_T,BAY_D,BAY_L,BAY_R,
        PENINSULA_T,PENINSULA_D,PENINSULA_L,PENINSULA_R,
        SMALL_DIAG_L_T_C,SMALL_DIAG_L_T_C_Re,SMALL_DIAG_R_T_C,SMALL_DIAG_R_T_C_Re,SMALL_DIAG_R_D_C,SMALL_DIAG_R_D_C_Re,SMALL_DIAG_L_D_C,SMALL_DIAG_L_D_C_Re,
        SMALL_DIAG_REVERSE_L_T_C,SMALL_DIAG_REVERSE_L_T_C_Re, SMALL_DIAG_REVERSE_R_T_C,SMALL_DIAG_REVERSE_R_T_C_Re,SMALL_DIAG_REVERSE_R_D_C, SMALL_DIAG_REVERSE_R_D_C_Re,SMALL_DIAG_REVERSE_L_D_C, SMALL_DIAG_REVERSE_L_D_C_Re,
        CORNER_L_T_C,CORNER_R_T_C,CORNER_R_D_C,CORNER_L_D_C,
        CORNER_REVERSE_L_T_C,CORNER_REVERSE_R_T_C,CORNER_REVERSE_R_D_C,CORNER_REVERSE_L_D_C,
        ISLAND,DEFAULT
    }
    public enum Terrain {
        HILLS,  DESERT, SAVANNAH, JUNGLE, PEAKS, WATER,

        NOT_WATER, DOESNT_MATTER
    }

}
