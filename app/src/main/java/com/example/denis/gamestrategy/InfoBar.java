package com.example.denis.gamestrategy;

import android.graphics.Canvas;

/**
 * Created by denis on 28.02.17.
 */

public class InfoBar {

    public String message;
    public int width,height;
    public int x = 0,y;
    private Texture texture;

    InfoBar(Texture tx){
        texture = new Texture(tx.getBitmap());
    }

    public void calculateInfoBar(Canvas canvas, ScreenManager scM){
        width = canvas.getWidth();
        height = canvas.getHeight() / scM.vmY / 2;
        y = canvas.getHeight() - height;
        texture.resizeTexture(width,height);


    }

    public Texture getTexture(){return texture;}
}
