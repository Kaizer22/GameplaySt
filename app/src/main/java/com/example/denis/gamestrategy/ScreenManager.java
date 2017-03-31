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


             //методы, начинающиеся с load нужно будет перенести в класс, работающий с БД

    public ScreenManager(int screenWidth,int screenHeight, int s){             // вычисляет параметры видимой карты
        scale = s;
        vmX = scale;
        vmY = screenHeight / (screenWidth / vmX)+1;

        cellWidth = screenWidth/vmX;
        cellHeight = screenHeight/vmY;


    }



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
                        map[i][j].cityOnIt = new City(city,player.fr,j,i); //player.units.get(player.units.size()-1);
                        map[i][j].cityOn = true;
                        makeCityTerritory(glM,map[i][j].cityOnIt);
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

    private void makeCityTerritory(GlobalMap glM, City city) {
        int maxY = glM.getMaxY() , maxX = glM.getMaxX() ;
        for (int i = 0; i <= city.affectArea  ; i++) {
            if (city.posY + i < maxY  && city.posX + i < maxX) {
                glM.setFraction(city.posY + i, city.posX + i, city.fraction);
            }
            if (city.posY + i < maxY) {
                glM.setFraction(city.posY + i, city.posX, city.fraction);
            }
            if (city.posY - i >= 0 && city.posX + i < maxX) {
                glM.setFraction(city.posY - i, city.posX + i, city.fraction);
            }
            if (city.posY - i >= 0) {
                glM.setFraction(city.posY - i, city.posX, city.fraction);
            }
            if (city.posY - i >= 0 && city.posX - i >= 0) {
                glM.setFraction(city.posY - i, city.posX - i, city.fraction);
            }
            if (city.posX - i >= 0) {
                glM.setFraction(city.posY, city.posX - i, city.fraction);
            }
            if (city.posY + i< maxY && city.posX - i >= 0) {
                glM.setFraction(city.posY + i, city.posX - i, city.fraction);
            }
            if (city.posX + i < maxX) {
                glM.setFraction(city.posY , city.posX + i, city.fraction);
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
                            map[i][j].unitOnIt =  new ArmoredVehicle(player.fr, i, j); //player.units.get(player.units.size()-1);
                            map[i][j].unitOn = true;
                            break;
                        case 1:
                            //player.units.add(new CamelWarrior(unitTextures[buf], player.fraction, i, j));
                            map[i][j].unitOnIt = new CamelWarrior( player.fr, i, j); //player.units.get(player.units.size()-1);
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
        Cell[][] map = glMap.getMap();
        int ux,uy;

        if (choosenUnit.isShip){


        }else{
            for (int i = choosenUnit.unitSteps; i >= 0; i--) {
                ux = choosenUnit.posX + i;
                uy = choosenUnit.posY + i;
                if (uy + i < maxY  && ux + i < maxX ) {

                    glMap.setMarker(uy + i, ux + i, isMarker);
                }
                if (uy + i < maxY) {
                    glMap.setMarker(uy + i, ux, isMarker);
                }
                if (uy - i >= 0 && ux + i < maxX) {
                    glMap.setMarker(uy - i, ux + i, isMarker);
                }
                if (uy - i >= 0) {
                    glMap.setMarker(uy - i, ux, isMarker);
                }
                if (uy - i >= 0 && ux - i >= 0) {
                    glMap.setMarker(uy - i, ux - i, isMarker);
                }
                if (ux - i >= 0) {
                    glMap.setMarker(uy, ux - i, isMarker);
                }
                if (uy + i< maxY && ux - i >= 0) {
                    glMap.setMarker(uy + i, ux - i, isMarker);
                }
                if (ux + i < maxX) {
                    glMap.setMarker(uy , ux + i, isMarker);
                }
            }
        }
    }
}
