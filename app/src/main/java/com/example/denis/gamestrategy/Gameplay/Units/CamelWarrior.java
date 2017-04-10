package com.example.denis.gamestrategy.Gameplay.Units;


import com.example.denis.gamestrategy.Gameplay.Player;
import com.example.denis.gamestrategy.Gameplay.Unit;

/**
 * Created by denis on 03.03.17.
 */

public class CamelWarrior extends Unit {
    public CamelWarrior(Player.Fraction f, int y, int x) {
        super(TypeOfUnit.CAMEL_WARRIOR, f, y, x);
        unitMaxSteps = 2;
        unitSteps = unitMaxSteps;
        nameOfUnit = "Воин на верблюде";
        unitMaxHP = 50;
        unitHP = unitMaxHP;
        unitBaseAttack = 30;
        unitBaseDefense = 5;
        isShip = false;
    }
}
