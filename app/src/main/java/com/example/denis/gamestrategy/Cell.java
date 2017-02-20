package com.example.denis.gamestrategy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by denis on 19.02.17.
 */

public class Cell {
    private Texture texture ;

    private int cWidth, cHeigth;

    public void setSize(int cW, int cH){
        cWidth = cW;
        cHeigth = cH;
        texture.resizeTexture(cWidth,cHeigth);
    }


    public void setTexture(Texture tx){
        texture = new Texture(tx.getBitmap());

    }

    public Texture getTexture(){return texture;}


    public void setcWidth(int cW){cWidth = cW;}

    public int getcWidth(){return cWidth;}

    public void setcHeight(int cH){cHeigth = cH;}

    public int getcHeight(){return cHeigth;}

    //public void drawCell(Canvas canvas, int x , int y){    // x,y in array map[][]

       // Paint paint = new Paint();
      //  canvas.drawBitmap(texture.getBitmap(),x*cWidth,y*cHeigth,paint);
    //}
}
