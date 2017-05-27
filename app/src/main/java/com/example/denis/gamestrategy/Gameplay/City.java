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

    double cityHP ;
    double cityMaxHP = 100 ;


    public void setSize(int width,int height){
        texture.resizeTexture(width,height);

    }

    public Texture getTexture() {
        return texture;
    }

    public String getInfoAboutCity(){
        return "("+ name + ")  " + StringConverter.getFractionOnRussian(fraction) + " З - " + (int)(cityMaxHP/cityHP*100)+"%" ;
    }

    public City(Texture t, Player.Fraction fr, int pX, int pY){
        cityHP = cityMaxHP;
        fraction = fr;
        texture = t;
        affectArea = 2;
        posX = pX;
        posY = pY;
    }
}
