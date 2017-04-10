package com.example.denis.gamestrategy.Gameplay;

/**
 * Created by denis on 29.03.17.
 */

public class TypeMask {
    Cell.TypeOfCell typeOfMasc;
    Cell[][] mask1 = new Cell[3][3];

    TypeMask(Cell.TypeOfCell t, Cell[][] m){
        typeOfMasc = t;
        mask1 = m;
    }

    boolean equals(TypeMask mask2){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if( mask2.mask1[i][j].getTerrain() != this.mask1[i][j].getTerrain() && this.mask1[i][j].getTerrain() != Cell.Terrain.DOESNT_MATTER )
                    return false;
            }
        }
        return true;
    }
}
