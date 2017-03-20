package com.example.denis.gamestrategy;

/**
 * Created by denis on 27.02.17.
 */

public abstract class Unit {
    private Texture texture;   // иконка юнита
    private Texture fraction;  // фон за иконкой, зависящии от племени

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

    public Unit(Texture t,Texture f,int y, int x){
        texture = t;
        fraction = f;
        posX = x;
        posY = y;
    }

    public void setSize(int width,int height){
        texture.resizeTexture(width,height);
        fraction.resizeTexture(width,height);
    }

    public Texture getTexture() {
        return texture;
    }

    public Texture getFraction() {
        return fraction;
    }

    public void move(Cell c1,Cell c2, int x, int y){
        c2.someMarkerOnIt = false;
        c1.unitOn = false;
        c1.unitOnIt = null;
        c2.unitOn = true;
        c2.unitOnIt = this;
        posX = x;
        posY = y;
    }
}
