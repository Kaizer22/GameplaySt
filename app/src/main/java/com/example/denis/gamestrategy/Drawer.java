package com.example.denis.gamestrategy;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by denis on 20.02.17.
 */

public class Drawer { // можно  сделать чтобы  все текстуры хранились тут
    Texture moveOpportunityMarker;
    private Paint paint;
    Drawer(Texture mOM){
        paint = new Paint();
        moveOpportunityMarker = mOM;

    }

    public void drawCell(Canvas canvas,ScreenManager screenManager,Texture texture, int x , int y){    // x,y in array map[][]
        canvas.drawBitmap(texture.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
    }

    public void drawUnit(Canvas canvas, Unit unit,ScreenManager screenManager,int x, int y){
        canvas.drawBitmap(unit.getTexture().getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
        canvas.drawBitmap(unit.getFraction().getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
    }

    public void drawInfoRectangle(InfoBar ir, Canvas canvas){
        canvas.drawBitmap(ir.getTexture().getBitmap(),ir.x,ir.y,paint);
        canvas.drawText(ir.message,ir.x+canvas.getWidth()/20,ir.y+ir.textSize,paint);
    }
    public void setTextSize(int ts){
        paint.setTextSize(ts);
    }

    public void drawMarker(Canvas canvas, ScreenManager screenManager,int x,int y){
        canvas.drawBitmap(moveOpportunityMarker.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);

    }

    public void drawCity(Canvas canvas,City city,ScreenManager screenManager,int x,int y) {
        canvas.drawBitmap(city.getTexture().getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);
        canvas.drawBitmap(city.getFraction().getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);

    }
    public void drawFractionGround(Canvas canvas,Texture fractionGround,ScreenManager screenManager,int x,int y){
        canvas.drawBitmap(fractionGround.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
    }

    public void drawVisibleMap(Canvas canvas, ScreenManager scM,Texture fractionGround, Texture[] mapTextures, Map map,Map glMap) { // вместо fractionGround будет массив

        Cell[][] m = map.getMap();
        Texture mapTexture;





        for (int i = 0; i < scM.vmY ; i++) {
            for (int j = 0; j < scM.vmX ; j++) {
                switch(m[i][j].getTerrain()){            //заменить ассоциативным массивом
                 case WATER:
                     mapTexture = mapTextures[0];
                  break;
                 case SAVANNAH:
                     mapTexture = mapTextures[1];
                  break;
                 case HILLS:
                     mapTexture = mapTextures[2];
                  break;
                 case PEAKS:
                     mapTexture = mapTextures[3];
                  break;
                 case DESERT:
                     mapTexture = mapTextures[4];
                 break;
                 default:
                     mapTexture = mapTextures[5];
                 break;
                }


                   drawCell(canvas,scM,mapTexture,j,i);


                if(m[i][j].isSomeonsTerritory){
                    drawFractionGround(canvas,fractionGround,scM,j,i);
                }

                if (m[i][j].cityOn){
                    m[i][j].cityOnIt.setSize(scM.cellWidth, scM.cellHeight);
                    drawCity(canvas,m[i][j].cityOnIt,scM,j,i);
                }


                if(m[i][j].unitOn) {
                    m[i][j].unitOnIt.setSize(scM.cellWidth, scM.cellHeight);
                    drawUnit(canvas, m[i][j].unitOnIt, scM,j,i);
                }else if(m[i][j].someMarkerOnIt){
                    drawMarker(canvas,scM,j,i);
                }
            }
        }

    }
}
