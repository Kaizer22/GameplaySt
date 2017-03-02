package com.example.denis.gamestrategy;

/**
 * Created by denis on 19.02.17.
 */

public class Cell {

    //Bitmap defaultBitmap = Bitmap.createBitmap(128,128,Bitmap.Config.ARGB_8888);
    private Texture texture; //= new Texture(defaultBitmap);
    private Terrain terrain;
    private int cWidth, cHeigth;
    public Unit unitOnIt;
    public boolean unitOn;


    public void setSize(int cW, int cH){
        cWidth = cW;
        cHeigth = cH;
        texture.resizeTexture(cWidth,cHeigth);
    }


    public void setTexture(Texture tx){                 //  утечка памяти
        texture = new Texture(tx.getBitmap());

    }

    public Texture getTexture(){return texture;}


    public void setcWidth(int cW){cWidth = cW;}

    public int getcWidth(){return cWidth;}

    public void setcHeight(int cH){cHeigth = cH;}

    public int getcHeight(){return cHeigth;}


    public enum Terrain {
        HILLS, PEAKS, DESERT, SAVANNAH, JUNGLE,

        HILLS_COAST, DESERT_COAST, SAVANNAH_COAST, JUNGLE_COAST
    }

}
