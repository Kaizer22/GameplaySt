package com.example.denis.gamestrategy.Gameplay;

import android.util.Log;

/**
 * Created by denis on 21.03.17.
 */

public class City {
    String name = "city_name";
    public Player.Fraction fraction;
    private Texture texture;
    int affectArea;
    int posX,posY;

    double cityHP ;
    double cityMaxHP = 20 ;

    int cityDefense;


    public void setSize(int width,int height){
        texture.resizeTexture(width,height);

    }

    public Texture getTexture() {
        return texture;
    }

    public String getInfoAboutCity(){
        return "("+ name + ")  " + StringConverter.getFractionOnRussian(fraction) + " З - " + (int)(cityHP/cityMaxHP*100)+"%" + " Защ. - " + cityDefense ;
    }

    public City(Texture t, Player.Fraction fr, int pX, int pY){
        cityDefense = 3;
        cityHP = cityMaxHP;
        fraction = fr;
        texture = t;
        affectArea = 2;
        posX = pX;
        posY = pY;
    }
}
