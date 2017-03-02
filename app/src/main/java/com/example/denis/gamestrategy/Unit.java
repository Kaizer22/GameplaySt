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

    public void moveTo(int x, int y){}
}
