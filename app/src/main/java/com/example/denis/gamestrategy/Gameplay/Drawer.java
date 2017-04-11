package com.example.denis.gamestrategy.Gameplay;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 20.02.17.
 */

public class Drawer {
    private Paint paint;

    Drawer() {
        paint = new Paint();


    }

    public void drawCell(Canvas canvas, ScreenManager screenManager, Texture texture, int x, int y) {    // x,y in array map[][]
        canvas.drawBitmap(texture.getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);
    }

    public void drawUnit(Canvas canvas, Texture t, Texture f, ScreenManager screenManager, int x, int y) {
        canvas.drawBitmap(t.getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);
        canvas.drawBitmap(f.getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);
    }

    public void drawInfoRectangle(InfoBar ir, Canvas canvas) {
        canvas.drawBitmap(ir.getTexture().getBitmap(), ir.x, ir.y, paint);
        canvas.drawText(ir.message, ir.x + canvas.getWidth() / 20, ir.y + ir.textSize, paint);
    }
    public void drawResourceBar(ResourceBar rb, Canvas canvas) {
        int drx = rb.padding;
        int dry = (int)(1.0/6*rb.height);
        int dryText = dry+rb.textSize/2+rb.textSize/4;
        canvas.drawBitmap(rb.getResourceBarTexture().getBitmap(), rb.x, rb.y, paint);
        canvas.drawBitmap(rb.getHappinessScoreIcon().getBitmap(), drx, dry, paint);
        drx+=rb.iconSize;
        canvas.drawText(rb.happinessScore+" ",drx,dryText,paint);
        drx+=rb.iconSize+rb.padding*3;
        canvas.drawBitmap(rb.getEatScoreIcon().getBitmap(), drx, dry, paint);
        drx+=rb.iconSize;
        canvas.drawText(rb.eatScore+" ",drx,dryText,paint);
        drx+=rb.iconSize+rb.padding*3;
        canvas.drawBitmap(rb.getPopulationScoreIcon().getBitmap(), drx, dry, paint);
        drx+=rb.iconSize;
        canvas.drawText(rb.populationScore+" ",drx,dryText,paint);
        drx+=rb.iconSize+rb.padding*3;
        canvas.drawBitmap(rb.getPowerScoreIcon().getBitmap(), drx, dry, paint);
        drx+=rb.iconSize;
        canvas.drawText(rb.powerScore+" ",drx,dryText,paint);
        //canvas.drawText(ir.message, ir.x + canvas.getWidth() / 20, ir.y + ir.textSize, paint);
    }

    public void drawNextTurnButton(GameView.NextTurnButton ntb, Canvas canvas){
        if (ntb.isPressed)
            canvas.drawBitmap(ntb.pressedButtonTexture.getBitmap(),ntb.x,ntb.y,paint);
        else
            canvas.drawBitmap(ntb.buttonTexture.getBitmap(),ntb.x,ntb.y,paint);
    }

    public void drawSaveExitButton(GameView.SaveExitButton seb, Canvas canvas){
        if (seb.isPressed)
            canvas.drawBitmap(seb.pressedButtonTexture.getBitmap(),seb.x,seb.y,paint);
        else
            canvas.drawBitmap(seb.buttonTexture.getBitmap(),seb.x,seb.y,paint);
    }


    public void setTextSize(int ts) {
        paint.setTextSize(ts);
    }

    public void drawMarker(Canvas canvas, Texture marker, ScreenManager screenManager, int x, int y) {
        canvas.drawBitmap(marker.getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);

    }

    public void drawCity(Canvas canvas, City city, Texture cityFraction, ScreenManager screenManager, int x, int y) {
        canvas.drawBitmap(city.getTexture().getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);
        canvas.drawBitmap(cityFraction.getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);

    }

    public void drawFractionGround(Canvas canvas, Texture fractionGround, ScreenManager screenManager, int x, int y) {
        canvas.drawBitmap(fractionGround.getBitmap(), x * screenManager.cellWidth, y * screenManager.cellHeight, paint);
    }

    public void drawVisibleMap(Player[] players, Canvas canvas, ScreenManager scM, TextureManager txM, GlobalMap glGlobalMap) { // вместо fractionGround будет массив
        Map<String, Unit> allUnits = new HashMap<>();
        Map <String, City> allCityes =  new HashMap<>();
        for (int i = 0; i < players.length ; i++) {
            allUnits.putAll(players[i].units);
            allCityes.putAll(players[i].cityes);
        }

        Cell[][] m = glGlobalMap.getMap();
        int cx, cy;
        Texture mapTexture;
        Texture unitTexture;

        String cellKey;
        for (int i = scM.posYOnGlobalMap; i < (scM.vmY + scM.posYOnGlobalMap); i++) {
            for (int j = scM.posXOnGlobalMap; j < (scM.vmX + scM.posXOnGlobalMap); j++) {

                cellKey = i+"_"+j;
                mapTexture = txM.getMapTextureByTerrainAndType(m[i][j].getTerrain(), m[i][j].getTypeOfCell());

                cx = j - scM.posXOnGlobalMap;
                cy = i - scM.posYOnGlobalMap;
                drawCell(canvas, scM, mapTexture, cx, cy);


                if (m[i][j].territoryOf != Player.Fraction.NONE) {
                    Texture fractionTerritory = txM.getTextureByFraction(m[i][j].territoryOf, TextureManager.TypeOfFractionTexture.TERRITORY);
                    drawFractionGround(canvas, fractionTerritory, scM, cx, cy);
                }


                if (m[i][j].cityOn) {
                    City city = allCityes.get(cellKey);
                    Texture fractionCity = txM.getTextureByFraction(city.fraction, TextureManager.TypeOfFractionTexture.CITY);
                    city.setSize(scM.cellWidth, scM.cellHeight);
                    drawCity(canvas, city, fractionCity, scM, cx, cy);
                }


                if (m[i][j].unitOn) {
                    Unit unit = allUnits.get(cellKey);
                    Texture fractionUnit = txM.getTextureByFraction(unit.fraction, TextureManager.TypeOfFractionTexture.UNIT);
                    unitTexture = txM.getUnitTextureByType(unit.getType());
                    drawUnit(canvas, unitTexture, fractionUnit, scM, cx, cy);
                    if (m[i][j].attackMarkerOnIt)
                        drawMarker(canvas, txM.attackOpportunityMarker, scM, cx, cy);

                } else if (m[i][j].moveOpportunityMarkerOnIt) {
                    drawMarker(canvas, txM.moveOpportunityMarker, scM, cx, cy);
                }
            }

        }
    }
}