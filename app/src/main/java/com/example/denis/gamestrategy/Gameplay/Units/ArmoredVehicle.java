package com.example.denis.gamestrategy.Gameplay.Units;


import com.example.denis.gamestrategy.Gameplay.Player;
import com.example.denis.gamestrategy.Gameplay.Unit;

/**
 * Created by denis on 02.03.17.
 */

public class ArmoredVehicle extends Unit {
    public ArmoredVehicle(Player.Fraction f, int y, int x) {
        super(TypeOfUnit.ARMORED_VEHICLE, f, y, x);
        unitMaxSteps = 1;
        unitSteps = unitMaxSteps;
        nameOfUnit = "Бронеповозка";
        unitMaxHP = 100;
        unitHP = unitMaxHP;
        unitBaseAttack = 80;
        unitBaseDefense = 20;
        isShip = false;


    }
}
