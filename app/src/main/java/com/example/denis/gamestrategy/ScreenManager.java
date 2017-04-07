package com.example.denis.gamestrategy;

import android.content.res.AssetManager;
import android.util.Log;

import java.util.Map;


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
        String fileName = player.getFractionName()+"_city_map";
        String cityKey;
        try {

            InputStream in = am.open(fileName );
            Scanner sc = new Scanner(in);
            int buf;

            for (int i = 0; i < glM.getMaxY(); i++) {
                for (int j = 0; j < glM.getMaxX(); j++) { //прим. размер карты городов должен совпадать с размером карты!
                    buf = sc.nextInt();

                    if (buf == 1) {
                        cityKey = i+"_"+j;
                        player.cityes.put(cityKey,new City(city,player.fr,j,i));; //добавить больше юнитов
                        GameLogic.makeCityTerritory(glM, player.cityes.get(cityKey));
                        map[i][j].cityOn = true;
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






    public void loadUnitMap(Player player,GlobalMap glM, Map <String,Texture> unitTextures, AssetManager am) {
        Cell[][] map = glM.getMap();
        String fileName = player.getFractionName()+"_"+"unit_map";
        String unitKey;
        try {

            InputStream in = am.open(fileName);
            Scanner sc = new Scanner(in);
            int buf;

            for (int i = 0; i < glM.getMaxY(); i++) {
                for (int j = 0; j < glM.getMaxX(); j++) { //прим. размер карты юнитов должен совпадать с размером карты!
                    buf = sc.nextInt();
                    unitKey = i+"_"+j;
                    switch (buf) {
                        case 0: //разобраться с индексами для текстур юнитов!
                            player.units.put(unitKey,new ArmoredVehicle(player.fr, i, j)); //добавить больше юнитов
                            GameLogic.setUnitDefense(player.units.get(unitKey), map[i][j].cellCoeff);
                            GameLogic.setUnitAttack(player.units.get(unitKey) );
                            map[i][j].unitOn = true;
                            break;
                        case 1:
                            player.units.put(unitKey,new CamelWarrior(player.fr, i, j)); //добавить больше юнитов
                            GameLogic.setUnitDefense(player.units.get(unitKey), map[i][j].cellCoeff);
                            GameLogic.setUnitAttack(player.units.get(unitKey) );
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


    public void chooseUnit(GlobalMap glMap,Player player,int cx,int cy, InfoBar ib){
        String unitKey = cy+"_"+cx;
        Unit unit = player.units.get(unitKey);

        if (choosenUnit!= null && unit.isChoosen){

            choosenUnit.isChoosen = false;
            createMarkers(glMap,player,false);
            choosenUnit = null;
        }else {
            if(choosenUnit!= null) {
                createMarkers(glMap,player, false);
            }
            choosenUnit = unit;
            choosenUnit.isChoosen = true;

            if( choosenUnit.unitSteps > 0) {
                createMarkers(glMap,player,true);
            }
        }


        unit.posXOnScreen = cx;
        unit.posYOnScreen = cy;
        ib.message ="("+unit.nameOfUnit + ") З(%)/А/Защ./Ш --- " + (int)(unit.unitHP/unit.unitMaxHP*100) +"/"+ (int)(unit.unitAttack)+"/"+(int)(unit.unitDefense) +"/"+ unit.unitSteps;   ;

    }

    public void chooseCell(Player player,GlobalMap glMap, InfoBar infoBar,int screenWidth,int screenHeight,int x,int y){
        Cell[][] glM = glMap.getMap();

        int cx = x/(screenWidth/vmX);

        int cy = y/(screenHeight/vmY) ;

        Cell c = glM[posYOnGlobalMap+cy][posXOnGlobalMap+cx];

        Integer glcX = posXOnGlobalMap + cx;
        Integer glcY = posYOnGlobalMap + cy;

        String cellKey = glcY+"_"+glcX;
        if(c.attackMarkerOnIt){

            Unit attacked = player.units.get(cellKey);

            createMarkers(glMap,player,false);
            choosenUnit.attack(player.units.get(cellKey),glM,cy,cx);

            int attackedUnitX = attacked.posX;
            int attackedUnitY = attacked.posY;

            if (player.units.get(cellKey).unitHP <= 0) {
                glM[attackedUnitY][attackedUnitX].unitOn = false;
                player.moveUnit(choosenUnit,glM,attackedUnitX,attackedUnitY);//choosenUnit.move(glM[choosenUnit.posY][choosenUnit.posX], glM[attackedUnitY][attackedUnitX], attackedUnitX, attackedUnitY);
                GameLogic.setUnitAttack(choosenUnit);
            }

            else if (choosenUnit.unitHP<=0) {
                glM[choosenUnit.posY][choosenUnit.posX].unitOn = false;
                GameLogic.setUnitAttack(attacked);
                GameLogic.setUnitDefense(choosenUnit,glM[attacked.posY][attacked.posX].cellCoeff);

            }else{
                GameLogic.setUnitDefense(choosenUnit, glM[choosenUnit.posY][choosenUnit.posX].cellCoeff);
                GameLogic.setUnitAttack(choosenUnit);
                GameLogic.setUnitDefense(choosenUnit, glM[attacked.posY][attacked.posX].cellCoeff);
                GameLogic.setUnitAttack(attacked);
            }



        }else if (c.unitOn) {
            chooseUnit(glMap,player, glcX, glcY, infoBar);

        }else if(c.moveOpportunityMarkerOnIt) {
            createMarkers(glMap,player, false);
            player.moveUnit(choosenUnit,glM,glcX,glcY);
            GameLogic.setUnitDefense(choosenUnit,c.cellCoeff);

        }else{
            infoBar.message = c.getInfoAboutCell();
            infoBar.message += "   ("+glcX.toString()+";"+glcY.toString()+") "+c.cityOn;
        }

        //Integer f = c.getcWidth();
        //infoBar.message = f.toString();


    }




    public void createMarkers(GlobalMap glMap,Player player,boolean isMarker) {
        int maxY = glMap.getMaxY() , maxX = glMap.getMaxX() ;
        Cell[][] map = glMap.getMap();
        Unit unit;
        int ux,uy;

        if (choosenUnit.isShip){


        }else{
            ux = choosenUnit.posX;
            uy = choosenUnit.posY;
            for (int i = choosenUnit.unitSteps; i >= 0; i--) {

                if (uy + i < maxY  && ux + i < maxX ) {
                    unit = player.units.get((uy+i)+"_"+(ux+i));
                    if (map[uy + i][ux + i].unitOn  && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy + i, ux + i, isMarker);
                    else {
                        glMap.setMoveOpportunityMarker(uy + i, ux + i, isMarker);
                    }
                }
                if (uy + i < maxY) {
                    unit = player.units.get((uy+i)+"_"+(ux));
                    if (map[uy + i][ux].unitOn  && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy + i, ux , isMarker);
                    else
                        glMap.setMoveOpportunityMarker(uy + i, ux, isMarker);
                }
                if (uy - i >= 0 && ux + i < maxX) {
                    unit = player.units.get( (uy-i) + "_" + (ux+i) );
                    if (map[uy - i][ux + i].unitOn && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy - i, ux + i, isMarker);
                    else
                        glMap.setMoveOpportunityMarker(uy - i, ux + i, isMarker);
                }
                if (uy - i >= 0) {
                    unit = player.units.get((uy-i)+"_"+(ux));
                    if (map[uy - i][ux].unitOn && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy - i, ux, isMarker);
                    else
                        glMap.setMoveOpportunityMarker(uy - i, ux, isMarker);
                }
                if (uy - i >= 0 && ux - i >= 0) {
                    unit = player.units.get((uy-i)+"_"+(ux-i));
                    if (map[uy - i][ux - i].unitOn && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy - i, ux - i, isMarker);
                    else
                        glMap.setMoveOpportunityMarker(uy - i, ux - i, isMarker);
                }
                if (ux - i >= 0) {
                    unit = player.units.get((uy)+"_"+(ux-i));
                    if (map[uy][ux - i].unitOn && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy, ux - i, isMarker);
                    else
                        glMap.setMoveOpportunityMarker(uy, ux - i, isMarker);
                }
                if (uy + i< maxY && ux - i >= 0) {
                    unit = player.units.get((uy+i)+"_"+(ux-i));
                    if (map[uy + i][ux - i].unitOn && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy + i, ux - i, isMarker);
                    else
                        glMap.setMoveOpportunityMarker(uy + i, ux - i, isMarker);
                }
                if (ux + i < maxX) {
                    unit = player.units.get((uy)+"_"+(ux+i));
                    if (map[uy][ux + i].unitOn && unit.fraction != choosenUnit.fraction)
                        glMap.setAttackMarker(uy, ux + i, isMarker);
                    else
                        glMap.setMoveOpportunityMarker(uy , ux + i, isMarker);
                }
            }
        }
    }



}
