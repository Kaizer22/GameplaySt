package com.example.denis.gamestrategy.Gameplay;

import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;

/**
 * Created by denis on 11.04.17.
 */

public class StringConverter {

    public static String getTerrainOnRussian(Cell.Terrain terrain){
        switch (terrain) {
            case WATER:
                return "Вода";
            case SAVANNAH:
                return  "Саванна";
            case HILLS:
                return "Холмы";
            case PEAKS:
                return "Горы";
            case DESERT:
                return "Пустыня";
            case JUNGLE:
                return "Джунгли";
            default:
                return "";
        }
    }

    public static Cell.Terrain getTerrainByString(String s){
        Cell.Terrain[] allTerrains = Cell.Terrain.values();
        for (Cell.Terrain t: allTerrains) {
            if (t.toString().toLowerCase().equals(s))
                return t;
        }
        return Cell.Terrain.WATER;
    }

    public static Cell.TypeOfCell getTypeOfCellByString(String s){
        Cell.TypeOfCell[] allTypes = Cell.TypeOfCell.values();
        for (Cell.TypeOfCell t: allTypes) {
            if (t.toString().toLowerCase().equals(s))
                return t;
        }
        return Cell.TypeOfCell.DEFAULT;
    }

    public static Player.Fraction getFractionByString(String s){
        Player.Fraction[] allFractions = Player.Fraction.values();
        for (Player.Fraction t: allFractions) {
            if (t.toString().toLowerCase().equals(s))
                return t;
        }
        return Player.Fraction.REBELS;
    }

    public static Unit.TypeOfUnit getTypeOfUnitByString(String s){
        Unit.TypeOfUnit[] allTypesOfUnits = Unit.TypeOfUnit.values();
        for (Unit.TypeOfUnit t: allTypesOfUnits) {
            if (t.toString().toLowerCase().equals(s))
                return t;
        }
        return Unit.TypeOfUnit.SPEARMENS;
    }

    public static String getFractionOnRussian(Player.Fraction f ){
        switch(f){
            case ZULU:
                return "Зулусы";
            case BUSHMAN:
                return "Бушмены";
            case LUBA:
                return "Луба";
            case MASAI:
                return "Масаи";
            case PYGMY:
                return "Пигмеи";
            case NUER:
                return "Нуэр";
            case YORUBA:
                return "Йоруба";
            case ASHANTI:
                return "Ашанти";
            case NUBA:
                return "Нуба";
            case HAUSA:
                return "Хауса";
            case DOGON:
                return "Догоны";
            case AMHARA:
                return "Амхара";
            case FULBE:
                return "Фульбе";
            case TUAREG:
                return  "Туареги";
            case BERBER:
                return "Берберы";
            case ADVANSED_NATIONS:
                return "Развитые нации";
            case REBELS:
                return "Мятежники";
            default:
                return "";
        }
    }
}
