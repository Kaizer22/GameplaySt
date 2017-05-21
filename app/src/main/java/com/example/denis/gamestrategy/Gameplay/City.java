package com.example.denis.gamestrategy.Gameplay;

/**
 * Created by denis on 21.03.17.
 */

public class City {
    String name = "default";
    public Player.Fraction fraction;
    private Texture texture;
    int affectArea;
    int posX,posY;

    int cityHP = 10;
    int cityMaxHP = 10 ;

    public void setSize(int width,int height){
        texture.resizeTexture(width,height);

    }

    public Texture getTexture() {
        return texture;
    }



    public City(Texture t, Player.Fraction fr, int pX, int pY){
        fraction = fr;
        texture = t;
        affectArea = 2;
        posX = pX;
        posY = pY;
    }
}
