package com.example.denis.gamestrategy;

/**
 * Created by denis on 02.03.17.
 */

public class Player {
    //ArrayList<Unit> units = new ArrayList();
    //Intellsect intellect;

    Fraction fr; //текстура в зависимости от fr


    public Player(Fraction f){
        fr = f;

    }

    public String getFractionName(){
        return fr.toString().toLowerCase();
    }



    public enum Fraction{

        BERBER,NUER,NUBA,DOGON,TUAREG,ZULU,BUSHMAN,LUBA,
        MASAI,PYGMY,ASHANTI,AMHARA,FULBE,HAUSA,YORUBA,

        ADVANSED_NATIONS,REBELS,

        NONE

    }
}
