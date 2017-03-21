package com.example.denis.gamestrategy;

/**
 * Created by denis on 02.03.17.
 */

public class Player {
    //ArrayList<Unit> units = new ArrayList();
    //Intellsect intellect;

    Fraction fr;
    Texture unitFraction;
    Texture cityFraction;
    Texture groundFraction;

    public Player(Fraction f,Texture uf,Texture cf,Texture gf){
        fr = f;
        unitFraction = uf;
        cityFraction = cf;
        groundFraction = gf;
    }

    public String getFractionName(){
        switch(fr){
            case BERBER:
                return "berber";
            case NUER:
                return "nuer";
            case NUBA:
                return "nuba";
            case DOGON:
                return "dogon";
            case TUAREG:
                return "tuareg";
            case ZULU:
                return "zulu";
            case BUSHMAN:
                return "bushman";
            case LUBA:
                return "luba";
            case MASAI:
                return "masai";
            case PYGMY:
                return "pygmy";
            case ASHANTI:
                return "ashanti";
            case AMHARA:
                return "amhara";
            case FULBE:
                return "fulbe";
            case HAUSA:
                return "hausa";
            case YORUBA:
                return "yoruba";
            case ADVANSED_NATIONS:
                return "advansed_nations";
            case REBELS:
                return "rebels";
            default:
                return "";
        }
    }


    public enum Fraction{

        BERBER,NUER,NUBA,DOGON,TUAREG,ZULU,BUSHMAN,LUBA,
        MASAI,PYGMY,ASHANTI,AMHARA,FULBE,HAUSA,YORUBA,

        ADVANSED_NATIONS,REBELS

    }
}
