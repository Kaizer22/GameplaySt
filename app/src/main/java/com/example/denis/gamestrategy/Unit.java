package com.example.denis.gamestrategy;

import android.util.Log;

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

    public void move(Cell c1,Cell c2, int x, int y){
        c2.moveOpportunityMarkerOnIt = false;
        c2.unitOn = true;
        c2.unitOnIt = c1.unitOnIt;
        c1.unitOn = false;
        c1.unitOnIt = null;

        posX = x;
        posY = y;
        GameLogic.setUnitDefense(c2);
    }

    public void attack(Unit attacked,Cell [][] glM){
        Integer attackedUnitDamage;
        Integer attackingUnitDamage;
         GameLogic.getDamage(this,attacked);
         GameLogic.getDamage(attacked,this);
        //Log.d("Whatch here",attackedUnitDamage.toString()+"____________"+attackingUnitDamage.toString());
        if (attacked.unitHP <= 0) {
            glM[attacked.posY][attacked.posX].unitOnIt = null;
            move(glM[posY][posX], glM[attacked.posY][attacked.posX], attacked.posY, attacked.posX);
            GameLogic.setUnitAttack(this);
        }
        else if (this.unitHP<=0) {
            glM[this.posY][this.posX].unitOnIt = null;
            glM[this.posY][this.posX].unitOn = false;
            GameLogic.setUnitAttack(attacked);
            GameLogic.setUnitDefense(glM[attacked.posY][attacked.posX]);
        }else{
            GameLogic.setUnitDefense(glM[this.posY][this.posX]);
            GameLogic.setUnitAttack(this);
            GameLogic.setUnitDefense(glM[attacked.posY][attacked.posX]);
            GameLogic.setUnitAttack(attacked);
        }


    }

    public enum TypeOfUnit {
        ARMORED_VEHICLE, CAMEL_WARRIOR
    }
}
