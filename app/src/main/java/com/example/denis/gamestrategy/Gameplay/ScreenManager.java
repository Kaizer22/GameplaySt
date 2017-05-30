package com.example.denis.gamestrategy.Gameplay;

import android.support.v4.app.FragmentManager;
import android.content.Context;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 20.02.17.
 */

public class ScreenManager {


    int scale; // (cells In Line X)
    int vmY, vmX;
    public int cellWidth, cellHeight;
    int posXOnGlobalMap = 0 , posYOnGlobalMap = 0;
    Unit choosenUnit;
    Context maincontext;
    //City choosenCity;
    FragmentManager fM;



    public ScreenManager(int screenWidth,int screenHeight, int s, Context ct, FragmentManager fm){             // вычисляет параметры видимой карты
        scale = s;
        vmX = scale;
        vmY = screenHeight / (screenWidth / vmX)+1;
        maincontext = ct;
        fM = fm;


        cellWidth = screenWidth/vmX;
        cellHeight = screenHeight/vmY;


    }




    public void chooseUnit(Unit unit, InfoBar ib){
        ib.message = unit.getInfoAboutUnit();
    }
    public void chooseUnit(GlobalMap glMap,Player player,Map <String,Unit> allUnits,Map <String,City> allCityes,int cx,int cy, InfoBar ib){
        String unitKey = cy+"_"+cx;
        Unit unit = player.units.get(unitKey);

        if (choosenUnit!= null && unit.isChoosen){

            choosenUnit.isChoosen = false;
            createMarkers(glMap,allUnits,allCityes,false);
            choosenUnit = null;
        }else {
            if(choosenUnit!= null) {
                createMarkers(glMap,allUnits,allCityes, false);
            }
            choosenUnit = unit;
            choosenUnit.isChoosen = true;

            if( choosenUnit.unitSteps > 0) {
                createMarkers(glMap,allUnits,allCityes,true);
            }
        }


        unit.posXOnScreen = cx;
        unit.posYOnScreen = cy;
        ib.message = unit.getInfoAboutUnit();

    }

    public void chooseComputerCity(City city,InfoBar infoBar){
        infoBar.message = city.getInfoAboutCity();
    }

    public void choosePlayerCity(Player player,Cell[][] globalMap,City city, InfoBar infoBar){
        CityDialog cityDialog = getUseableCityDialog(player,globalMap,city);
        cityDialog.show(fM,city.name);
        infoBar.message = "";
    }

    public void chooseCell(Player[] players,Player player,GlobalMap glMap, InfoBar infoBar,int screenWidth,int screenHeight,int x,int y){
        Cell[][] glM = glMap.getMap();
        Map<String, Player> allPlayers = new HashMap<>();
        Map<String, Unit> allUnits = new HashMap<>();
        Map <String, City> allCityes =  new HashMap<>();

        for (int i = 0; i < players.length ; i++) {
            allUnits.putAll(players[i].units);
            allCityes.putAll(players[i].cities);
            allPlayers.put(players[i].fr.toString().toLowerCase(),players[i]);
        }

        int cx = x/(screenWidth/vmX);

        int cy = y/(screenHeight/vmY) ;

        Cell c = glM[posYOnGlobalMap+cy][posXOnGlobalMap+cx];

        Integer glcX = posXOnGlobalMap + cx;
        Integer glcY = posYOnGlobalMap + cy;

        String cellKey = glcY+"_"+glcX;

        if(c.attackMarkerOnIt){
            if (c.unitOn) {
                Unit attacked = allUnits.get(cellKey);
                Player attackedPlayer = allPlayers.get(attacked.fraction.toString().toLowerCase());

                createMarkers(glMap, allUnits,allCityes, false);
                choosenUnit.attack(attacked, glM, cy, cx);

                int attackedUnitX = attacked.posX;
                int attackedUnitY = attacked.posY;

                if (attacked.unitHP <= 0) {
                    glM[attackedUnitY][attackedUnitX].unitOn = false;
                    attackedPlayer.units.remove(cellKey);
                    player.moveUnit(choosenUnit, glM, attackedUnitX, attackedUnitY);
                    GameLogic.setUnitAttack(choosenUnit);
                    GameLogic.setUnitDefense(choosenUnit, glM[attackedUnitY][attackedUnitX].cellCoeff);
                } else if (choosenUnit.unitHP <= 0) {
                    glM[choosenUnit.posY][choosenUnit.posX].unitOn = false;
                    player.units.remove(choosenUnit.posY + "_" + choosenUnit.posX);
                    GameLogic.setUnitAttack(attacked);
                    GameLogic.setUnitDefense(attacked, glM[attackedUnitY][attackedUnitX].cellCoeff);

                } else {
                    GameLogic.setUnitDefense(choosenUnit, glM[choosenUnit.posY][choosenUnit.posX].cellCoeff);
                    GameLogic.setUnitAttack(choosenUnit);
                    GameLogic.setUnitDefense(attacked, glM[attacked.posY][attacked.posX].cellCoeff);
                    GameLogic.setUnitAttack(attacked);
                }
            }else if(c.cityOn){
                City attacked = allCityes.get(cellKey);
                Player attackedPlayer = allPlayers.get(attacked.fraction.toString().toLowerCase());

                createMarkers(glMap, allUnits,allCityes, false);
                choosenUnit.attack(attacked);

                int attackedCityX = attacked.posX;
                int attackedCityY = attacked.posY;

                if (attacked.cityHP <= 0) {


                    DecideDialog decideDialog = getUseableDecideDialog(player,attackedPlayer,glMap,attackedCityX,attackedCityY);
                    decideDialog.show(fM,attacked.name);


                    //attackedPlayer.cities.remove(cellKey);
                    player.moveUnit(choosenUnit, glM, attackedCityX, attackedCityY);
                    //GameLogic.deleteCityTerritory(glMap,attacked);
                    GameLogic.setUnitAttack(choosenUnit);
                    GameLogic.setUnitDefense(choosenUnit, glM[attackedCityY][attackedCityX].cellCoeff);
                } else if (choosenUnit.unitHP <= 0) {
                    glM[choosenUnit.posY][choosenUnit.posX].unitOn = false;
                    player.units.remove(choosenUnit.posY + "_" + choosenUnit.posX);


                } else {
                    GameLogic.setUnitDefense(choosenUnit, glM[choosenUnit.posY][choosenUnit.posX].cellCoeff);
                    GameLogic.setUnitAttack(choosenUnit);
                }
            }


        }else if (c.unitOn) {
            Unit unit = allUnits.get(cellKey);
            if (unit.fraction == player.fr)
                chooseUnit(glMap,player,allUnits,allCityes, glcX, glcY, infoBar);
            else
                chooseUnit(unit,infoBar);

        }else if(c.moveOpportunityMarkerOnIt) {
            createMarkers(glMap, allUnits,allCityes, false);
            player.moveUnit(choosenUnit, glM, glcX, glcY);
            GameLogic.setUnitDefense(choosenUnit, c.cellCoeff);
        }else if (c.cityOn){
            City city = allCityes.get(cellKey);
            if (city.fraction == player.fr)
                choosePlayerCity(player,glM,city,infoBar);
            else
                chooseComputerCity(city,infoBar);


        }else{
            infoBar.message = c.getInfoAboutCell();
            infoBar.message += "  ("+glcX.toString()+";"+glcY.toString()+")";
        }

        //Integer f = c.getcWidth();
        //infoBar.message = f.toString();


    }

    public void createMarkers(GlobalMap glMap,Map <String,Unit> allUnits,Map <String,City> allCityes,boolean isMarker) {
        int maxY = glMap.getMaxY() , maxX = glMap.getMaxX() ;
        Cell[][] map = glMap.getMap();
        Unit unit;
        City city;
        int ux,uy;

        if (choosenUnit.isShip){


        }else{
            ux = choosenUnit.posX;
            uy = choosenUnit.posY;

            //проверка клеток на n вокруг юнита
            // где n - кол-во ходов
            //    X X X
            //     XXX
            //    XX0XX
            //     XXX
            //    X   X
            // n(unitSteps) = 2
            for (int i = choosenUnit.unitSteps; i >= 0; i--) {

                if (uy + i < maxY  && ux + i < maxX && map[uy+i][ux+i].getTerrain() != Cell.Terrain.WATER ) {
                    if (map[uy+i][ux + i].unitOn){
                        unit = allUnits.get((uy+i)+"_"+(ux+i));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy + i, ux + i, isMarker);
                    }else if(map[uy+i][ux+i].cityOn){
                        city = allCityes.get((uy+i)+"_"+(ux+i));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy + i, ux + i, isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy + i, ux + i, isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy + i, ux + i, isMarker);

                }
                if (uy + i < maxY && map[uy+i][ux].getTerrain() != Cell.Terrain.WATER) {
                    if (map[uy+i][ux].unitOn){
                        unit = allUnits.get((uy+i)+"_"+(ux));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy + i, ux, isMarker);
                    }else if(map[uy+i][ux].cityOn){
                        city = allCityes.get((uy+i)+"_"+(ux));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy + i, ux, isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy + i, ux, isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy + i, ux, isMarker);
                }
                if (uy - i >= 0 && ux + i < maxX && map[uy-i][ux+i].getTerrain() != Cell.Terrain.WATER) {
                    if (map[uy-i][ux + i].unitOn){
                        unit = allUnits.get((uy-i)+"_"+(ux+i));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy - i, ux + i, isMarker);
                    }else if(map[uy-i][ux+i].cityOn){
                        city = allCityes.get((uy-i)+"_"+(ux+i));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy - i, ux + i, isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy - i, ux + i, isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy - i, ux + i, isMarker);
                }
                if (uy - i >= 0 && map[uy-i][ux].getTerrain() != Cell.Terrain.WATER) {
                    if (map[uy-i][ux].unitOn){
                        unit = allUnits.get((uy-i)+"_"+(ux));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy - i, ux, isMarker);
                    }else if(map[uy-i][ux].cityOn){
                        city = allCityes.get((uy-i)+"_"+(ux));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy -i, ux, isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy - i, ux, isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy - i, ux , isMarker);
                }
                if (uy - i >= 0 && ux - i >= 0 && map[uy-i][ux-i].getTerrain() != Cell.Terrain.WATER) {
                    if (map[uy-i][ux - i].unitOn){
                        unit = allUnits.get((uy-i)+"_"+(ux-i));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy - i, ux - i, isMarker);
                    }else if(map[uy-i][ux-i].cityOn){
                        city = allCityes.get((uy-i)+"_"+(ux-i));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy - i, ux - i, isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy - i, ux - i, isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy - i, ux- i, isMarker);
                }
                if (ux - i >= 0 && map[uy][ux-i].getTerrain() != Cell.Terrain.WATER) {
                    if (map[uy][ux - i].unitOn){
                        unit = allUnits.get((uy)+"_"+(ux-i));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy, ux - i, isMarker);
                    }else if(map[uy][ux-i].cityOn){
                        city = allCityes.get((uy)+"_"+(ux-i));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy, ux - i, isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy, ux - i, isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy, ux - i, isMarker);
                }
                if (uy + i< maxY && ux - i >= 0 && map[uy+i][ux-i].getTerrain() != Cell.Terrain.WATER) {
                    if (map[uy+i][ux-i].unitOn){
                        unit = allUnits.get((uy+i)+"_"+(ux-i));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy + i, ux-i, isMarker);
                    }else if(map[uy+i][ux-i].cityOn){
                        city = allCityes.get((uy+i)+"_"+(ux-i));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy + i, ux-i , isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy + i, ux-i , isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy + i, ux-i, isMarker);
                }
                if (ux + i < maxX && map[uy][ux+i].getTerrain() != Cell.Terrain.WATER) {
                    if (map[uy][ux + i].unitOn){
                        unit = allUnits.get((uy)+"_"+(ux+i));
                        if (unit.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy , ux + i, isMarker);
                    }else if(map[uy][ux+i].cityOn){
                        city = allCityes.get((uy)+"_"+(ux+i));
                        if (city.fraction != choosenUnit.fraction)
                            glMap.setAttackMarker(uy, ux + i, isMarker);
                        else
                            glMap.setMoveOpportunityMarker(uy, ux + i, isMarker);
                    }else
                        glMap.setMoveOpportunityMarker(uy, ux + i, isMarker);
                }
            }
        }
    }

    public CityDialog getUseableCityDialog(Player player, Cell[][] glM, City city){ // создавать CityDialog только так! иначе NullPointer
        CityDialog cityDialog = new CityDialog();
        cityDialog.setParam(player,glM,city,cellHeight*vmX,cellWidth*vmY);
        return cityDialog;
    }

    public DecideDialog getUseableDecideDialog(Player player,Player aPlayer, GlobalMap glM,int aCPX,int aCPY){ // создавать CityDialog только так! иначе NullPointer
        DecideDialog decideDialog = new DecideDialog();
        decideDialog.setParam(player,aPlayer,glM,aCPX,aCPY,cellHeight*vmX,cellWidth*vmY);
        return decideDialog;
    }



}
