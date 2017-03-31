package com.example.denis.gamestrategy;

/**
 * Created by denis on 27.02.17.
 */

public abstract class Unit {
    private TypeOfUnit type;

    public Player.Fraction fraction;  // фон за иконкой, зависящии от племени

    public boolean isChoosen;
    public boolean isShip;

    public  String nameOfUnit;
    public int posX,posY;
    public int posXOnScreen, posYOnScreen;
    public int unitHP;
    public int unitMaxHP;
    public int unitMaxSteps;
    public int unitSteps;
    public int unitAttack;
    public int unitDefence;
    public int unitProductionPrice;
    public int unitPopulatinPrice;

    public Unit(TypeOfUnit t, Player.Fraction f, int y, int x){
        type = t;
        fraction = f;
        posX = x;
        posY = y;
    }





    public TypeOfUnit getType(){return type;}

    public void move(Cell c1,Cell c2, int x, int y){
        c2.someMarkerOnIt = false;
        c1.unitOn = false;
        c1.unitOnIt = null;
        c2.unitOn = true;
        c2.unitOnIt = this;
        posX = x;
        posY = y;
    }

    public enum TypeOfUnit {
        ARMORED_VEHICLE, CAMEL_WARRIOR
    }
}
