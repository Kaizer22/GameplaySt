package com.example.denis.gamestrategy;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.widget.Toast;

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
    int cellsInLine = 10; //пригодится для изменения масштаба
    int vmY, vmX = cellsInLine;
    int posXOnGlobalMap = 0 , posYOnGlobalMap = 0;




    public ScreenManager(Canvas canvas){                   // вычисляет параметры видимой карты
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
    public void chooseUnit(Cell cell,InfoBar ib){
        ib.message ="("+cell.unitOnIt.nameOfUnit + ") З(%)/А/Защ./Ш --- " + cell.unitOnIt.unitHP/cell.unitOnIt.unitMaxHP*100 +"/"+ cell.unitOnIt.unitAttack+"/"+cell.unitOnIt.unitDefence+"/"+ cell.unitOnIt.unitSteps;   ;

    }
    public void chooseCell(InfoBar infoBar,Canvas canvas,int x,int y){

        Cell c = visibleMap.getMap()[y/(canvas.getHeight()/vmY)][x/(canvas.getWidth()/vmX)];
        switch(c.getTerrain()){
            case WATER:
                infoBar.message = "Вода";
                break;
            case SAVANNAH:
                infoBar.message = "Саванна";
                break;
            case HILLS:
                infoBar.message = "Холмы";
                break;
            case PEAKS:
                infoBar.message = "Горы";
                break;
            case DESERT:
                infoBar.message = "Пустыня";
                break;
            case JUNGLE:
                infoBar.message = "Джунгли";
                break;
            }
        //Integer f = c.getcWidth();
        //infoBar.message = f.toString();
             if (c.unitOn){
                chooseUnit(c,infoBar);
             }

    }
}
