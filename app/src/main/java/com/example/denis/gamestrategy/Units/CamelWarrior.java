package com.example.denis.gamestrategy.Units;

import com.example.denis.gamestrategy.Player;
import com.example.denis.gamestrategy.Texture;
import com.example.denis.gamestrategy.Unit;

/**
 * Created by denis on 03.03.17.
 */

public class CamelWarrior extends Unit {
    public CamelWarrior( Player.Fraction f, int y, int x) {
        super(TypeOfUnit.CAMEL_WARRIOR, f, y, x);
        unitMaxSteps = 2;
        unitSteps = unitMaxSteps;
        nameOfUnit = "Воин на верблюде";
        unitMaxHP = 50;
        unitHP = unitMaxHP;
        unitAttack = 15;
        unitDefence = 5;
        isShip = false;
    }
}
