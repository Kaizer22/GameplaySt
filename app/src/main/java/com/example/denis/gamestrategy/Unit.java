package com.example.denis.gamestrategy;

/**
 * Created by denis on 27.02.17.
 */

public abstract class Unit {
    private Texture texture;   // иконка юнита
    private Texture fraction;  // фон за иконкой, зависящии от племени

    public int posX,posY;
    int     unitHP, unitMaxHP,
            unitMaxSteps, unitSteps,
            unitAttack, unitDefence,
            unitProductionPrice, unitPopulatinPrice;

    Unit(Texture t0,Texture t1,int x, int y){
        texture = t0;
        fraction = t1;
        posX = x;
        posY = y;
    }

    public void moveTo(int x,int y){}
}
