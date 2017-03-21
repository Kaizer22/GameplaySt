package com.example.denis.gamestrategy;

/**
 * Created by denis on 21.03.17.
 */

public class City {
    String name;
    private Texture fraction;
    private Texture texture;
    int affectArea;
    int posX,posY;

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

    public City(Texture f, Texture t, int pX, int pY){
        fraction = f;
        texture = t;
        affectArea = 2;
        posX = pX;
        posY = pY;
    }
}
