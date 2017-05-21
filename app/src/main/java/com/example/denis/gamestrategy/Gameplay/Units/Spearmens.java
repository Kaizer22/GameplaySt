package com.example.denis.gamestrategy.Gameplay.Units;

import com.example.denis.gamestrategy.Gameplay.Player;
import com.example.denis.gamestrategy.Gameplay.Unit;

/**
 * Created by denis on 10.04.17.
 */

public class Spearmens extends Unit {
    public Spearmens(Player.Fraction f, int y, int x) {
        super(TypeOfUnit.SPEARMENS, f, y, x);
        unitMaxSteps = 1;
        unitSteps = unitMaxSteps;
        nameOfUnit = "Копейщики";
        unitMaxHP = 20;
        unitHP = unitMaxHP;
        unitBaseAttack = 10 ;
        unitBaseDefense = 5;
        isShip = false;
    }
}
