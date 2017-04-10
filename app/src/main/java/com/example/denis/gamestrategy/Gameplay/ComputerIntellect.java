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

        List keyes = new LinkedList<>(units.keySet());
        for (int j = 0; j < keyes.size() ; j++) {
            deltaX = randomShift[(int)(Math.random()*2)];
            deltaY = randomShift[(int)(Math.random()*2)];
           if (units.get(keyes.get(j)).posX + deltaX >= 0 &&  units.get(keyes.get(j)).posX + deltaX < glMap.getMaxX() &&  units.get(keyes.get(j)).posY+deltaY >= 0 &&  units.get(keyes.get(j)).posY+deltaY < glMap.getMaxY() )
               moveUnit(units,units.get(keyes.get(j)),glMap.getMap(),units.get(keyes.get(j)).posX + deltaX,units.get(keyes.get(j)).posY + deltaY);
        }
    }


}
