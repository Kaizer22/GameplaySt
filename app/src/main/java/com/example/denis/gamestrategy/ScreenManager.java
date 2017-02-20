package com.example.denis.gamestrategy;

import android.graphics.Canvas;

/**
 * Created by denis on 20.02.17.
 */

public class ScreenManager {
    Map visibleMap;
    final int cellsInLine = 10;
    int vmY, vmX = cellsInLine;
    int posXOnGlobalMap , posYOnGlobalMap;

    public void calculateVisibleMap(Canvas canvas){
        vmY = canvas.getHeight() / (canvas.getWidth() / cellsInLine);
        visibleMap.setMap(new Cell[vmY][vmX]);
    }

    public void createVisibleMap(Map glM){
        for (int i = posYOnGlobalMap; i < vmY; i++) {
            for (int j = posXOnGlobalMap; j < vmY; j++) {
                visibleMap.setMap();
            }
        }
    }



}
