package com.example.denis.gamestrategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by denis on 02.03.17.
 */

public class Player {
    public Map<String, Unit> units;
    public Map <String, City> cityes;
    //Intellsect intellect;

    Fraction fr; //текстура в зависимости от fr


    public Player(Fraction f){
        fr = f;
        units = new HashMap<>();
        cityes = new HashMap<>();

    }

    public String getFractionName(){
        return fr.toString().toLowerCase();

    }

    public  void moveUnit(Unit unit,Cell[][] glM,int x,int y){
        units.remove(unit.posY+"_"+unit.posX);
        unit.move(glM[unit.posY][unit.posX], glM[y][x], x, y);
        units.put(unit.posY+"_"+unit.posX,unit);
    }



    public enum Fraction{

        BERBER,NUER,NUBA,DOGON,TUAREG,ZULU,BUSHMAN,LUBA,
        MASAI,PYGMY,ASHANTI,AMHARA,FULBE,HAUSA,YORUBA,

        ADVANSED_NATIONS,REBELS,

        NONE

    }
}
