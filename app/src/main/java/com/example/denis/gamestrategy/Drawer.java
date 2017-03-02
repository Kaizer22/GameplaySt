package com.example.denis.gamestrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by denis on 20.02.17.
 */

public class Drawer {
    private Paint paint = new Paint();

    public void drawCell(Canvas canvas,Cell cell, int x , int y){    // x,y in array map[][]
        canvas.drawBitmap(cell.getTexture().getBitmap(),x*cell.getcWidth(),y*cell.getcHeight(),paint);
    }

    public void drawUnit(Canvas canvas, Unit unit,Cell cell){
        canvas.drawBitmap(unit.getFraction().getBitmap(),unit.posX*cell.getcWidth(),unit.posY*cell.getcHeight(),paint);
        canvas.drawBitmap(unit.getTexture().getBitmap(),unit.posX*cell.getcWidth(),unit.posY*cell.getcHeight(),paint);
    }

    public void drawInfoRectangle(InfoBar ir, Canvas canvas){
        canvas.drawBitmap(ir.getTexture().getBitmap(),ir.x,ir.y,paint);
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
                if(m[i][j].unitOn) {
                    m[i][j].unitOnIt.setSize(cW, cH);
                    drawUnit(canvas, m[i][j].unitOnIt, m[i][j]);
                }
            }
        }

    }
}
