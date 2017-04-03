package com.example.denis.gamestrategy;


import android.util.Log;

public abstract class  GameLogic {
    public static void setUnitAttack(Unit unit){
        unit.unitAttack = unit.unitBaseAttack*(unit.unitHP/unit.unitMaxHP);
    }
    public static void setUnitDefense(Cell cell){
        cell.unitOnIt.unitDefense = (int)(cell.unitOnIt.unitBaseDefense * cell.cellCoeff * (cell.unitOnIt.unitHP/ cell.unitOnIt.unitMaxHP));
    }

    public static double getCellCoeffByTerrain(Cell.Terrain t){
        switch(t){
            case HILLS:
                return   1.25;
            case JUNGLE:
                return   1.5;
            case DESERT:
                return   1;
            case SAVANNAH:
                return   1.1;
            case PEAKS:
                return   2.5;
            default:
                return   2;
        }
    }

    public static void makeCityTerritory(GlobalMap glM, City city) {
        int maxY = glM.getMaxY() , maxX = glM.getMaxX() ;
        for (int i = 0; i <= city.affectArea  ; i++) {
            if (city.posY + i < maxY  && city.posX + i < maxX) {
                glM.setFraction(city.posY + i, city.posX + i, city.fraction);
            }
            if (city.posY + i < maxY) {
                glM.setFraction(city.posY + i, city.posX, city.fraction);
            }
            if (city.posY - i >= 0 && city.posX + i < maxX) {
                glM.setFraction(city.posY - i, city.posX + i, city.fraction);
            }
            if (city.posY - i >= 0) {
                glM.setFraction(city.posY - i, city.posX, city.fraction);
            }
            if (city.posY - i >= 0 && city.posX - i >= 0) {
                glM.setFraction(city.posY - i, city.posX - i, city.fraction);
            }
            if (city.posX - i >= 0) {
                glM.setFraction(city.posY, city.posX - i, city.fraction);
            }
            if (city.posY + i< maxY && city.posX - i >= 0) {
                glM.setFraction(city.posY + i, city.posX - i, city.fraction);
            }
            if (city.posX + i < maxX) {
                glM.setFraction(city.posY , city.posX + i, city.fraction);
            }
        }
    }

    public static void getDamage(Unit unit1, Unit unit2){ //Attacking, Attacked

        if (unit2.unitDefense/unit1.unitAttack > 1) {

            unit2.unitHP -=  unit1.unitAttack;
            Double f = unit2.unitHP;
        }else {
            Double attack = unit1.unitAttack - unit2.unitDefense;
            if(unit2.unitDefense == 0)
                unit2.unitDefense = unit1.unitAttack;
            unit2.unitHP -= (unit1.unitAttack - unit2.unitDefense);
            Double f = unit2.unitHP;
        }
    }

}
