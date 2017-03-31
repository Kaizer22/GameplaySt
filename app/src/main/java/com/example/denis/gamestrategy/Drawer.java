package com.example.denis.gamestrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by denis on 20.02.17.
 */

public class Drawer {
    private Paint paint;
    Drawer(){
        paint = new Paint();


    }

    public void drawCell(Canvas canvas,ScreenManager screenManager,Texture texture, int x , int y){    // x,y in array map[][]
        canvas.drawBitmap(texture.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
    }

    public void drawUnit(Canvas canvas, Texture t,Texture f,ScreenManager screenManager,int x, int y){
        canvas.drawBitmap(t.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
        canvas.drawBitmap(f.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
    }

    public void drawInfoRectangle(InfoBar ir, Canvas canvas){
        canvas.drawBitmap(ir.getTexture().getBitmap(),ir.x,ir.y,paint);
        canvas.drawText(ir.message,ir.x+canvas.getWidth()/20,ir.y+ir.textSize,paint);
    }
    public void setTextSize(int ts){
        paint.setTextSize(ts);
    }

    public void drawMarker(Canvas canvas,Texture marker, ScreenManager screenManager,int x,int y){
        canvas.drawBitmap(marker.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);

    }

    public void drawCity(Canvas canvas,City city, Texture cityFraction,ScreenManager screenManager,int x,int y) {
        canvas.drawBitmap(city.getTexture().getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);
        canvas.drawBitmap(cityFraction.getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);

    }
    public void drawFractionGround(Canvas canvas,Texture fractionGround,ScreenManager screenManager,int x,int y){
        canvas.drawBitmap(fractionGround.getBitmap(),x*screenManager.cellWidth,y*screenManager.cellHeight,paint);
    }

    public void drawVisibleMap(Canvas canvas, ScreenManager scM,TextureManager txM, GlobalMap glGlobalMap) { // вместо fractionGround будет массив

        Cell[][] m = glGlobalMap.getMap();
        int cx,cy;
        Texture mapTexture;
        Texture unitTexture;



        for (int i = scM.posYOnGlobalMap; i < (scM.vmY+scM.posYOnGlobalMap) ; i++) {
            for (int j = scM.posXOnGlobalMap; j < (scM.vmX+scM.posXOnGlobalMap) ; j++) {

                mapTexture = txM.getMapTextureByTerrainAndType(m[i][j].getTerrain(),m[i][j].getTypeOfCell());

                cx = j - scM.posXOnGlobalMap;
                cy = i - scM.posYOnGlobalMap;
                drawCell(canvas,scM,mapTexture,cx,cy);


                if(m[i][j].territoryOf != Player.Fraction.NONE){
                    Texture fractionTerritory = txM.getTextureByFraction(m[i][j].territoryOf, TextureManager.TypeOfFractionTexture.TERRITORY);
                    drawFractionGround(canvas,fractionTerritory,scM,cx,cy);
                }


                if (m[i][j].cityOn){
                    Texture fractionCity = txM.getTextureByFraction(m[i][j].cityOnIt.fraction, TextureManager.TypeOfFractionTexture.CITY);
                    m[i][j].cityOnIt.setSize(scM.cellWidth, scM.cellHeight);
                    drawCity(canvas,m[i][j].cityOnIt,fractionCity,scM,cx,cy);
                }


                if(m[i][j].unitOn) {
                    Texture fractionUnit = txM.getTextureByFraction(m[i][j].unitOnIt.fraction, TextureManager.TypeOfFractionTexture.UNIT);
                    unitTexture = txM.getUnitTextureByType(m[i][j].unitOnIt.getType());
                    drawUnit(canvas, unitTexture,fractionUnit, scM,cx,cy);

                }else if(m[i][j].someMarkerOnIt){
                    drawMarker(canvas,txM.moveOpportunityMarker,scM,cx,cy);
                }
            }
        }

    }
}
