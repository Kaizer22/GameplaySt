package com.example.denis.gamestrategy.Gameplay.Units;

import com.example.denis.gamestrategy.Gameplay.Player;
import com.example.denis.gamestrategy.Gameplay.Unit;

/**
 * Created by denis on 29.05.17.
 */

public class SmallBombard extends Unit {
    public SmallBombard(Player.Fraction f, int y, int x) {
        super(TypeOfUnit.SMALL_BOMBARD, f, y, x);
        unitMaxSteps = 1;
        unitSteps = unitMaxSteps;
        nameOfUnit = "Малая бомбарда";
        unitMaxHP = 30;
        unitHP = unitMaxHP;
        unitBaseAttack = 30;
        unitBaseDefense = 5;
        isShip = false;
    }
}
