package com.example.denis.gamestrategy.Gameplay;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 09.04.17.
 */

public class ComputerIntellect extends Intellect {

    @Override
    public void makeTurn(Map<String,Unit> units, GlobalMap glMap){
        int[] randomShift = {-1,1};
        int deltaX;
        int deltaY;
        Cell[][] glM = glMap.getMap();
        List keyes = new LinkedList<>(units.keySet());
        int ux;
        int uy;
        Unit bufUnit;
        for (int j = 0; j < keyes.size() ; j++) {
            bufUnit = units.get(keyes.get(j));
            while (bufUnit.unitSteps > 0) {
                deltaX = randomShift[(int) (Math.random() * 2)];
                deltaY = randomShift[(int) (Math.random() * 2)];
                ux = bufUnit.posX + deltaX;
                uy = bufUnit.posY + deltaY;
                if (ux >= 0 && ux < glMap.getMaxX() && uy >= 0 && uy < glMap.getMaxY() && !glM[uy][ux].unitOn & glM[uy][ux].getTerrain() != Cell.Terrain.WATER)
                    moveUnit(units, bufUnit, glM, ux, uy);
            }
        }
    }


}
