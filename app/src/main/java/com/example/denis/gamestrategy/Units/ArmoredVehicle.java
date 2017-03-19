package com.example.denis.gamestrategy.Units;

import com.example.denis.gamestrategy.Texture;
import com.example.denis.gamestrategy.Unit;

/**
 * Created by denis on 02.03.17.
 */

public class ArmoredVehicle extends Unit {

    public ArmoredVehicle(Texture t, Texture f, int y, int x) {
        super(t, f, y, x);
        unitMaxSteps = 2;
        unitSteps = unitMaxSteps;
        nameOfUnit = "Бронеповозка";
        unitMaxHP = 100;
        unitHP = unitMaxHP;
        unitAttack = 15;
        unitDefence = 20;

    }
}
