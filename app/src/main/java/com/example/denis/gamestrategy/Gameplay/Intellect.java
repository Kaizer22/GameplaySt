package com.example.denis.gamestrategy.Gameplay;

import java.util.Map;

/**
 * Created by denis on 09.04.17.
 */

public abstract class Intellect {
    public void makeTurn(Map<String,Unit> units,GlobalMap map){}

    public  void moveUnit(Map<String,Unit> units,Unit unit, Cell[][] glM, int x, int y){
        units.remove(unit.posY+"_"+unit.posX);
        unit.move(glM[unit.posY][unit.posX], glM[y][x], x, y);
        units.put(unit.posY+"_"+unit.posX,unit);
    }
}
