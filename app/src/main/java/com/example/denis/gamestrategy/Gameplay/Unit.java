package com.example.denis.gamestrategy.Gameplay;

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
    public double unitHP;
    public int unitMaxHP;
    public int unitMaxSteps;
    public int unitSteps;
    public int unitBaseAttack;
    public double unitAttack;
    public int unitBaseDefense;
    public double unitDefense;
    public int unitProductionPrice;
    public int unitPopulatinPrice;

    public Unit(TypeOfUnit t, Player.Fraction f, int y, int x){
        type = t;
        fraction = f;
        posX = x;
        posY = y;
    }





    public TypeOfUnit getType(){return type;}

    public void move(Cell c1, Cell c2, int x, int y){


        c2.unitOn = true;
        c1.unitOn = false;

        int deltaSteps;
        deltaSteps = Math.abs(posX-x) >= Math.abs(posY-y) ? Math.abs(posX-x):Math.abs(posY-y);
        unitSteps -= deltaSteps;

        posX = x;
        posY = y;

    }

    public void attack(Unit attacked,Cell [][] glM, int cy,int cx){

        GameLogic.getDamage(this,attacked);
        GameLogic.getDamage(attacked,this);
        //Log.d("Whatch here",




    }

    public enum TypeOfUnit {
        ARMORED_VEHICLE, CAMEL_WARRIOR, SPEARMENS
    }
}
