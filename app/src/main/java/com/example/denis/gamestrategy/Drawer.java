package com.example.denis.gamestrategy;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by denis on 20.02.17.
 */

public class Drawer {
    Paint paint = new Paint();

    public void drawCell(Canvas canvas,Cell cell, int x , int y){    // x,y in array map[][]


        canvas.drawBitmap(cell.getTexture().getBitmap(),x*cell.getcWidth(),y*cell.getcHeight(),paint);
    }

    public void drawMap(Canvas canvas, Map map) {
        Cell[][] m = map.getMap();

        int my = map.getMaxY(),
            mx = map.getMaxX();

        int     cH = canvas.getHeight()/my,
                cW = canvas.getWidth()/mx;

        for (int i = 0; i < my ; i++) {
            for (int j = 0; j < mx ; j++) {
                m[i][j].setSize(cW,cH);
                drawCell(canvas,m[i][j],j,i);
            }
        }

    }

    public void drawVisibleMap(Canvas canvas){}
}
