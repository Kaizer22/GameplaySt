package com.example.denis.gamestrategy.Gameplay;

/**
 * Created by denis on 21.03.17.
 */

public class City {
    String name;
    public Player.Fraction fraction;
    private Texture texture;
    int affectArea;
    int posX,posY;

    int cityHP;

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
