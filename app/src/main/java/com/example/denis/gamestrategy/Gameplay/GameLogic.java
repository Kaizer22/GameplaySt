package com.example.denis.gamestrategy.Gameplay;



import java.util.Map;

public abstract class  GameLogic {
    public static void setUnitAttack(Unit unit){
        unit.unitAttack = unit.unitBaseAttack*(unit.unitHP/unit.unitMaxHP);
    }
    public static void setUnitDefense(Unit unit,double cellCoeff){
        unit.unitDefense = (int)(unit.unitBaseDefense * cellCoeff * (unit.unitHP/ unit.unitMaxHP));
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
        unit1.unitSteps = 0;

        if (unit2.unitDefense/unit1.unitAttack > 1) {

            unit2.unitHP -=  unit1.unitAttack;
            //Double f = unit2.unitHP;
        }else {
            //Double attack = unit1.unitAttack - unit2.unitDefense;
            unit2.unitHP -= (unit1.unitAttack - unit2.unitDefense);
            //Double f = unit2.unitHP;
        }
    }

    public static void nextTurn(Player[] players, GlobalMap glM){
        Unit unit;
        for (int i = 0; i <players.length ; i++) {
            for (Map.Entry<String, Unit> playersUnit: players[i].units.entrySet()) // дописать восстановление здоровья на дружественной территории
            {
                unit = playersUnit.getValue();
                unit.unitSteps = unit.unitMaxSteps;
            }
            players[i].makeTurn(glM);
        }
    }

}
