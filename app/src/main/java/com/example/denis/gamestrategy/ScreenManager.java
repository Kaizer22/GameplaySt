package com.example.denis.gamestrategy;

import android.graphics.Canvas;

/**
 * Created by denis on 20.02.17.
 */

public class ScreenManager {
    Map visibleMap = new Map();
    final int cellsInLine = 10;
    int vmY, vmX = cellsInLine;
    int posXOnGlobalMap = 4 , posYOnGlobalMap = 2;



    public void calculateVisibleMap(Canvas canvas){                   // вычисляет параметры видимой карты
        vmY = canvas.getHeight() / (canvas.getWidth() / cellsInLine)+1;
    }


    public void createVisibleMap(Map glM){                           // построение видимой карты
        visibleMap.loadMap(vmY,vmX);
        visibleMap.updateParam();

        Cell[][] glCellMap = glM.getMap();
        int vsI = 0, vsJ = 0;

        for (int glI = posYOnGlobalMap; vsI < vmY; glI++,vsI++) { //&& glI < glCellMap.length + добавить дефолтную текстуру
            for (int glJ = posXOnGlobalMap; vsJ < vmX ; glJ++,vsJ++) { //&& glJ < glCellMap[0].length
               visibleMap.setCell(vsI,vsJ,glCellMap[glI][glJ]);
            }
            vsJ = 0;

        }
    }



}