package com.example.denis.gamestrategy.Gameplay;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 02.03.17.
 */

public class Player {
    public Map<String, Unit> units;
    public Map <String, City> cityes;
    Intellect intellect ;

    Fraction fr; //текстура в зависимости от fr


    public Player(Fraction f,Intellect i){
        fr = f;
        intellect = i;
        units = new HashMap<>();
        cityes = new HashMap<>();

    }

    public String getFractionName(){
        return fr.toString().toLowerCase();

    }

    public  void moveUnit(Unit unit, Cell[][] glM, int x, int y){
        intellect.moveUnit(units,unit,glM,x,y);
    }

    public void makeTurn(GlobalMap glM){
        intellect.makeTurn(units, glM);
    }

    public enum Fraction{

        ZULU,BUSHMAN,LUBA,MASAI,PYGMY,
        NUER,YORUBA,ASHANTI,NUBA,HAUSA,DOGON,
        AMHARA,FULBE,TUAREG,BERBER,

        ADVANSED_NATIONS,REBELS,

        NONE

    }
}
