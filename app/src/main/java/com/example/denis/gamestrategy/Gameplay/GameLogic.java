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
    public static void deleteCityTerritory(GlobalMap glM,City city){
        int maxY = glM.getMaxY() , maxX = glM.getMaxX() ;
        Cell[][] map = glM.getMap();
        for (int i = 0; i <= city.affectArea  ; i++) {
            if (city.posY + i < maxY  && city.posX + i < maxX) {
                if (map[city.posY + i][city.posX + i].territoryOf == city.fraction)
                    glM.setFraction(city.posY + i, city.posX + i, Player.Fraction.NONE);

            }
            if (city.posY + i < maxY) {
                if (map[city.posY + i][city.posX].territoryOf == city.fraction)
                    glM.setFraction(city.posY + i, city.posX, Player.Fraction.NONE);
            }
            if (city.posY - i >= 0 && city.posX + i < maxX) {
                if (map[city.posY - i][city.posX + i].territoryOf == city.fraction){
                    glM.setFraction(city.posY - i, city.posX + i, Player.Fraction.NONE);
                }
            }
            if (city.posY - i >= 0) {
                if (map[city.posY - i][city.posX].territoryOf == city.fraction){
                    glM.setFraction(city.posY - i, city.posX, Player.Fraction.NONE);
                }
            }
            if (city.posY - i >= 0 && city.posX - i >= 0) {
                if (map[city.posY - i][city.posX - i].territoryOf == city.fraction){
                    glM.setFraction(city.posY - i, city.posX - i, Player.Fraction.NONE);
                }
            }
            if (city.posX - i >= 0) {
                if (map[city.posY][city.posX - i].territoryOf == city.fraction) {
                    glM.setFraction(city.posY, city.posX - i, Player.Fraction.NONE);
                }
            }
            if (city.posY + i< maxY && city.posX - i >= 0) {
                if (map[city.posY + i][city.posX - i].territoryOf == city.fraction) {
                    glM.setFraction(city.posY + i, city.posX - i, Player.Fraction.NONE);
                }
            }
            if (city.posX + i < maxX) {
                if (map[city.posY][city.posX + i].territoryOf == city.fraction) {
                    glM.setFraction(city.posY, city.posX + i, Player.Fraction.NONE);
                }
            }
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

        }else {

            unit2.unitHP -= (unit1.unitAttack - unit2.unitDefense);
        }
    }

    public static void getDamage(Unit unit, City city){
        unit.unitHP -= city.cityDefense;
        city.cityHP -= unit.unitAttack;
    }

    public static void nextTurn(Player[] players, GlobalMap glM){
        Unit unit;
        Cell[][] glMap = glM.getMap();
        for (int i = 0; i <players.length ; i++) {
            for (Map.Entry<String, Unit> playersUnit: players[i].units.entrySet()) // дописать восстановление здоровья на дружественной территории
            {
                unit = playersUnit.getValue();
                unit.unitSteps = unit.unitMaxSteps;

                if (unit.fraction == glMap[unit.posY][unit.posX].territoryOf){
                    if (unit.unitHP < unit.unitMaxHP)
                        unit.unitHP += 1.0/10*unit.unitMaxHP;
                        setUnitAttack(unit);
                        setUnitDefense(unit,glMap[unit.posY][unit.posX].cellCoeff);
                    if (unit.unitHP > unit.unitMaxHP)
                        unit.unitHP = unit.unitMaxHP;

                }
            }
            players[i].makeTurn(glM);
        }
    }

}
