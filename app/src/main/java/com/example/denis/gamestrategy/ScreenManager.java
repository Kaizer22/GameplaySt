package com.example.denis.gamestrategy;

import android.content.res.AssetManager;
import android.graphics.Canvas;

import com.example.denis.gamestrategy.Units.ArmoredVehicle;
import com.example.denis.gamestrategy.Units.CamelWarrior;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by denis on 20.02.17.
 */

public class ScreenManager {

    Map visibleMap = new Map();
    final int cellsInLine = 10; //пригодится для изменения масштаба
    int vmY, vmX = cellsInLine;
    int posXOnGlobalMap = 0 , posYOnGlobalMap = 0;



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
        Cell[][] map = m.getMap();
        try {

            InputStream in = am.open("unit_map");
            Scanner sc = new Scanner(in);
            int buf;

            for (int i = 0; i < m.getMaxY(); i++) {
                for (int j = 0; j < m.getMaxX(); j++) { //прим. размер карты юнитов должен совпадать с размером карты!
                    buf = sc.nextInt();
                    switch (buf) {
                        case 0: //разобраться с индексами для текстур юнитов!
                            player.units.add(new ArmoredVehicle(unitTextures[buf], player.fraction, i, j)); //добавить больше юнитов
                            map[i][j].unitOnIt = player.units.get(player.units.size()-1);
                            map[i][j].unitOn = true;
                            break;
                        case 1:
                            player.units.add(new CamelWarrior(unitTextures[buf], player.fraction, i, j));
                            map[i][j].unitOnIt = player.units.get(player.units.size()-1);
                            map[i][j].unitOn = true;
                            break;
                    }
                }
                sc.nextLine();

            }
            in.close();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
