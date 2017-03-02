package com.example.denis.gamestrategy;

import android.content.res.AssetManager;
import android.graphics.Canvas;

import com.example.denis.gamestrategy.Units.Spearmans;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

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
        visibleMap.loadMap(vmY,vmX); //!!!!!!!!!!!
    }


    public void createVisibleMap(Map glM){                           // построение видимой карты

        visibleMap.updateParam();
        Cell[][] glCellMap = glM.getMap();
        int vsI = 0, vsJ = 0;

        for (int glI = posYOnGlobalMap; vsI < vmY; glI++,vsI++) {
            for (int glJ = posXOnGlobalMap; vsJ < vmX ; glJ++,vsJ++) {
               visibleMap.setCell(vsI,vsJ,glCellMap[glI][glJ]);
            }
            vsJ = 0;

        }
    }

    public void loadUnitMap(Player player,Map m,Texture[] unitTextures, AssetManager am) {
        int maxX, maxY;
        Cell[][] map = m.getMap();
        try {

            InputStream in = am.open("unit_map");
            Scanner sc = new Scanner(in);
            int buf;
            maxX = sc.nextInt();
            sc.nextLine();
            maxY = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    buf = sc.nextInt();
                    switch (buf) {
                        case 1:
                            player.units.add(new Spearmans(unitTextures[buf], player.fraction, i, j)); //добавить больше юнитов
                            map[i][j].unitOnIt = player.units.get(player.units.size()-1);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
