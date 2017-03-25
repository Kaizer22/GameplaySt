package com.example.denis.gamestrategy;

import android.content.res.AssetManager;
import java.util.Map;
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


    int scale; // (cells In Line X)
    int vmY, vmX;
    public int cellWidth, cellHeight;
    int posXOnGlobalMap = 0 , posYOnGlobalMap = 0;
    Unit choosenUnit;
    //City choosenCity;




    public ScreenManager(int screenWidth,int screenHeight, int s){             // вычисляет параметры видимой карты
        scale = s;
        vmX = scale;
        vmY = screenHeight / (screenWidth / vmX)+1;

        cellWidth = screenWidth/vmX;
        cellHeight = screenHeight/vmY;


    }


  //  public void createVisibleMap(GlobalMap glM){                           // построение видимой карты

       // visibleMap.updateParam();
       // Cell[][] glCellMap = glM.getMap();
        //int vsI = 0, vsJ = 0;

        //for (int glI = posYOnGlobalMap; vsI < vmY; glI++,vsI++) {
            //for (int glJ = posXOnGlobalMap; vsJ < vmX ; glJ++,vsJ++) {
               //visibleMap.setCell(vsI,vsJ,glCellMap[glI][glJ]);
           // }
           // vsJ = 0;

       // }
    //}

    public void loadCityMap(Player player,GlobalMap glM, Texture city, AssetManager am) {
        Cell[][] map = glM.getMap();
        String d = player.getFractionName();
        try {

            InputStream in = am.open("fraction_"+ d +"_city_map");
            Scanner sc = new Scanner(in);
            int buf;

            for (int i = 0; i < glM.getMaxY(); i++) {
                for (int j = 0; j < glM.getMaxX(); j++) { //прим. размер карты городов должен совпадать с размером карты!
                    buf = sc.nextInt();
                    if (buf == 1) {
                        // player.units.add(new ArmoredVehicle(unitTextures[buf], player.fraction, i, j)); //добавить больше юнитов
                        map[i][j].cityOnIt = new City(player.cityFraction,city, j, i); //player.units.get(player.units.size()-1);
                        map[i][j].cityOn = true;
                        makeCityTerritory(glM,map[i][j].cityOnIt,true);
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

    private void makeCityTerritory(GlobalMap glM, City city, boolean isTerritory) {
        int maxY = glM.getMaxY() , maxX = glM.getMaxX() ;
        for (int i = 0; i <= city.affectArea  ; i++) {
            if (city.posY + i < maxY  && city.posX + i < maxX) {
                glM.setTerritory(city.posY + i, city.posX + i, isTerritory);
            }
            if (city.posY + i < maxY) {
                glM.setTerritory(city.posY + i, city.posX, isTerritory);
            }
            if (city.posY - i >= 0 && city.posX + i < maxX) {
                glM.setTerritory(city.posY - i, city.posX + i, isTerritory);
            }
            if (city.posY - i >= 0) {
                glM.setTerritory(city.posY - i, city.posX, isTerritory);
            }
            if (city.posY - i >= 0 && city.posX - i >= 0) {
                glM.setTerritory(city.posY - i, city.posX - i, isTerritory);
            }
            if (city.posX - i >= 0) {
                glM.setTerritory(city.posY, city.posX - i, isTerritory);
            }
            if (city.posY + i< maxY && city.posX - i >= 0) {
                glM.setTerritory(city.posY + i, city.posX - i, isTerritory);
            }
            if (city.posX + i < maxX) {
                glM.setTerritory(city.posY , city.posX + i, isTerritory);
            }
        }
    }




    public void loadUnitMap(Player player,GlobalMap glM, Map <String,Texture> unitTextures, AssetManager am) {
        Cell[][] map = glM.getMap();
        try {

            InputStream in = am.open("unit_map");
            Scanner sc = new Scanner(in);
            int buf;

            for (int i = 0; i < glM.getMaxY(); i++) {
                for (int j = 0; j < glM.getMaxX(); j++) { //прим. размер карты юнитов должен совпадать с размером карты!
                    buf = sc.nextInt();
                    switch (buf) {
                        case 0: //разобраться с индексами для текстур юнитов!
                           // player.units.add(new ArmoredVehicle(unitTextures[buf], player.fraction, i, j)); //добавить больше юнитов
                            map[i][j].unitOnIt =  new ArmoredVehicle(unitTextures.get("armored_vehicle"), player.unitFraction, i, j); //player.units.get(player.units.size()-1);
                            map[i][j].unitOn = true;
                            break;
                        case 1:
                            //player.units.add(new CamelWarrior(unitTextures[buf], player.fraction, i, j));
                            map[i][j].unitOnIt = new CamelWarrior(unitTextures.get("cmael_warrior"), player.unitFraction, i, j); //player.units.get(player.units.size()-1);
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


    public void chooseUnit(GlobalMap glMap,Cell cell,int cx,int cy, InfoBar ib){

        if (choosenUnit!= null && cell.unitOnIt.isChoosen){

            choosenUnit.isChoosen = false;
            createMarkers(glMap,false);
            choosenUnit = null;
        }else {
            if(choosenUnit!= null)
                createMarkers(glMap,false);

            choosenUnit = cell.unitOnIt;
            choosenUnit.isChoosen = true;
            if( choosenUnit.unitSteps > 0) {
                createMarkers(glMap,true);
            }
        }


        cell.unitOnIt.posXOnScreen = cx;
        cell.unitOnIt.posYOnScreen = cy;
        ib.message ="("+cell.unitOnIt.nameOfUnit + ") З(%)/А/Защ./Ш --- " + cell.unitOnIt.unitHP/cell.unitOnIt.unitMaxHP*100 +"/"+ cell.unitOnIt.unitAttack+"/"+cell.unitOnIt.unitDefence+"/"+ cell.unitOnIt.unitSteps;   ;

    }
    public void chooseCell(GlobalMap glMap, InfoBar infoBar,int screenWidth,int screenHeight,int x,int y){
        Cell[][] glM = glMap.getMap();

        int cx = x/(screenWidth/vmX);        //тут иногда за границы массива выходит
        if (cx == vmX) {
            cx--;
        }else if(cx < 0){
            cx = 0;
        }

        int cy = y/(screenHeight/vmY) ;
        if (cy == vmY) {
            cy--;
        }else if(cx < 0) {
            cy = 0;
        }

        Cell c = glM[posYOnGlobalMap+cy][posXOnGlobalMap+cx];

        Integer glcX = posXOnGlobalMap + cx;
        Integer glcY = posYOnGlobalMap + cy;



        if (c.unitOn) {
            chooseUnit(glMap, c, cx, cy, infoBar);

        }else if(c.someMarkerOnIt ){
            createMarkers(glMap,false);
            choosenUnit.move(glM[choosenUnit.posY][choosenUnit.posX],c,glcX,glcY);
        }else {
            infoBar.message = c.getInfoAboutCell();
            infoBar.message += "   ("+glcX.toString()+";"+glcY.toString()+") "+c.cityOn;
        }

        //Integer f = c.getcWidth();
        //infoBar.message = f.toString();


    }




    public void createMarkers(GlobalMap glMap,boolean isMarker) {
        int maxY = glMap.getMaxY() , maxX = glMap.getMaxX() ;
        for (int i = choosenUnit.unitSteps; i >= 0; i--) {
            if (choosenUnit.posY + i < maxY  && choosenUnit.posX + i < maxX) {
                glMap.setMarker(choosenUnit.posY + i, choosenUnit.posX + i, isMarker);
            }
            if (choosenUnit.posY + i < maxY) {
                glMap.setMarker(choosenUnit.posY + i, choosenUnit.posX, isMarker);
            }
            if (choosenUnit.posY - i >= 0 && choosenUnit.posX + i < maxX) {
                glMap.setMarker(choosenUnit.posY - i, choosenUnit.posX + i, isMarker);
            }
            if (choosenUnit.posY - i >= 0) {
                glMap.setMarker(choosenUnit.posY - i, choosenUnit.posX, isMarker);
            }
            if (choosenUnit.posY - i >= 0 && choosenUnit.posX - i >= 0) {
                glMap.setMarker(choosenUnit.posY - i, choosenUnit.posX - i, isMarker);
            }
            if (choosenUnit.posX - i >= 0) {
                glMap.setMarker(choosenUnit.posY, choosenUnit.posX - i, isMarker);
            }
            if (choosenUnit.posY + i< maxY && choosenUnit.posX - i >= 0) {
                glMap.setMarker(choosenUnit.posY + i, choosenUnit.posX - i, isMarker);
            }
            if (choosenUnit.posX + i < maxX) {
                glMap.setMarker(choosenUnit.posY , choosenUnit.posX + i, isMarker);
            }
        }
    }
}
