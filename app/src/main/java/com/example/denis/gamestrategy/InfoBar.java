package com.example.denis.gamestrategy;

import android.graphics.Canvas;

/**
 * Created by denis on 28.02.17.
 */

public class InfoBar {

    public String message = new String();
    public int infomassage;
    public int width,height;
    public int x = 0,y;
    public  int textSize;
    private Texture texture;


    public InfoBar(Canvas canvas, ScreenManager scM, int scale,Texture tx){
        width = canvas.getWidth();
        height = canvas.getHeight() / scale ;
        y = canvas.getHeight() - height;
        textSize = height / 3;
        texture = tx;
        texture.resizeTexture(width,height);
        texture = new Texture(tx.getBitmap());
    }

    public Texture getTexture(){return texture;}
}
