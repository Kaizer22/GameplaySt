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
        for (int j = 0; j < keyes.size() ; j++) {
            deltaX = randomShift[(int)(Math.random()*2)];
            deltaY = randomShift[(int)(Math.random()*2)];
            ux = units.get(keyes.get(j)).posX + deltaX;
            uy = units.get(keyes.get(j)).posY + deltaY;
           if (ux >= 0 &&  ux < glMap.getMaxX() &&  uy >= 0 &&  uy < glMap.getMaxY() && !glM[uy][ux].unitOn & glM[uy][ux].getTerrain() != Cell.Terrain.WATER )
               moveUnit(units,units.get(keyes.get(j)), glM, ux, uy);
        }
    }


}
