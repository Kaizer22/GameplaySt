package com.example.denis.gamestrategy.Gameplay;

import android.content.res.AssetManager;

import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by denis on 19.02.17.
 */

public class GlobalMap {
    private Cell[][] map;
    private int maxX ,maxY ;


    GlobalMap(int size){
        maxX = size;
        maxY = size;
    }
    public int getMaxX(){return maxX;}
    public int getMaxY(){return maxY;}

    public void updateParam(){
        maxX = map[0].length;
        maxY = map.length;
    }



    public Cell[][] getMap(){return map;}

    public void setCell(int Y,int X, Cell c){
        map[Y][X] = c;
    }

    public void setMoveOpportunityMarker(int Y, int X, boolean t){
        if(!map[Y][X].unitOn)
            map[Y][X].moveOpportunityMarkerOnIt = t;
    }

    public void setAttackMarker(int Y, int X, boolean t){
            map[Y][X].attackMarkerOnIt = t;
    }

    public void setFraction(int Y, int X, Player.Fraction f){
            map[Y][X].territoryOf = f;
    }


    public void setLine(int y,Cell[] c) {
        map[y] = c;
    }

    public void newMap(){                   //!!!!

        map = new Cell[maxY][maxX];

        for (int i = 0; i < maxY ; i++) {
            for (int j = 0; j < maxX; j++) {
                map[i][j] = new Cell();
            }
        }
    }

    public void loadMap(final AssetManager am, final Texture[] textures) {

        //количество клеток в строке и в столбце  в текстовом документе
                try{

                    InputStream in = am.open("map");
                    Scanner sc = new Scanner(in);
                    int buf;

                    maxX = sc.nextInt();
                    sc.nextLine();
                    maxY = sc.nextInt();
                    sc.nextLine();
                    map = new Cell[maxY][maxX];

                    while(sc.hasNext()) {

                        for (int j = 0; j < maxY; j++) {
                            for (int i = 0; i < maxX; i++) {

                                buf = sc.nextInt();
                                map[j][i] = new Cell();
                                //switch(buf){map[j][i].setTerrain();}


                                }
                            }
                            sc.nextLine();
                        }
                    sc.close();
                    in.close();

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

    }

    public void generateMap(AssetManager am,Player[] players, Texture city){
        MapGenerator mg = new MapGenerator();
        newMap();
        mg.generateMap(am,maxY);
        updateParam();
        mg.generatePlayersInfrastructure(players, city, this);

    }

    public void setMap(Cell[][] m) {
        map = m;
        maxX = map[0].length;
        maxY = map.length;
    }


    private class MapGenerator {
        private LinkedList<TypeMask> allMasks =  fillMasks();

        private Cell[][] fillOneMask(Cell.Terrain t0,Cell.Terrain t1,Cell.Terrain t2,Cell.Terrain t3,Cell.Terrain t4,Cell.Terrain t5,Cell.Terrain t6,Cell.Terrain t7,Cell.Terrain t8){
            Cell[][] mask = new Cell[3][3];

            for (int i = 0; i < 3 ; i++) {
                for (int j = 0; j < 3; j++) {
                    mask[i][j] = new Cell();
                }
            }

            mask[0][0].setTerrain(t0);
            mask[0][1].setTerrain(t1);
            mask[0][2].setTerrain(t2);
            mask[1][0].setTerrain(t3);
            mask[1][1].setTerrain(t4);
            mask[1][2].setTerrain(t5);
            mask[2][0].setTerrain(t6);
            mask[2][1].setTerrain(t7);
            mask[2][2].setTerrain(t8);
            return mask;
        }

        private LinkedList<TypeMask> fillMasks(){
            LinkedList<TypeMask> masks = new LinkedList();
            masks.add(new TypeMask(Cell.TypeOfCell.DIAG_L_T_C,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.DIAG_L_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.DIAG_R_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER , Cell.Terrain.NOT_WATER , Cell.Terrain.NOT_WATER , Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.DIAG_R_T_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));

            masks.add(new TypeMask(Cell.TypeOfCell.COAST_T,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.COAST_D,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.COAST_L,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.COAST_R,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER)));

            masks.add(new TypeMask(Cell.TypeOfCell.BAY_T,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.BAY_D,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.BAY_R,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.BAY_L,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));

            masks.add(new TypeMask(Cell.TypeOfCell.PENINSULA_T,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.NOT_WATER,Cell.Terrain.DOESNT_MATTER)));
            masks.add(new TypeMask(Cell.TypeOfCell.PENINSULA_D,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.NOT_WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER)));
            masks.add(new TypeMask(Cell.TypeOfCell.PENINSULA_L,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER)));
            masks.add(new TypeMask(Cell.TypeOfCell.PENINSULA_R,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER)));

            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_L_T_C_Re,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_R_T_C_Re,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_R_T_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_R_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_R_D_C_Re,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_L_D_C_Re,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_L_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_L_T_C,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));

            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_R_T_C,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_R_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER)));
            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_L_D_C,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_L_T_C,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));

            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_REVERSE_R_T_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_REVERSE_R_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_REVERSE_L_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.CORNER_REVERSE_L_T_C,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));

            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_R_T_C_Re,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_R_T_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_R_D_C,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_R_D_C_Re,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.DOESNT_MATTER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_L_D_C_Re,fillOneMask(Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_L_D_C,fillOneMask(Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_L_T_C,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            masks.add(new TypeMask(Cell.TypeOfCell.SMALL_DIAG_REVERSE_L_T_C_Re,fillOneMask(Cell.Terrain.DOESNT_MATTER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER, Cell.Terrain.NOT_WATER)));
            return masks;
        }


        private void fillCell(Cell[][] map,int x,int y,int step,Cell.Terrain t){
            for (int i = y; i < y+step ; i++) {
                for (int j = x; j <x+step ; j++) {
                    map[i][j].setTerrain(t);
                }
            }

        }
        private void makePattern(final AssetManager am, Cell[][] map, int step){ //размеры карты должны быть кратны step
            try{

                InputStream in = am.open("map_pattern1");   //Pattern1
                Scanner sc = new Scanner(in);
                Cell.Terrain t ;
                int buf;
                for (int i = 0; i < map.length ; i += step) {
                    for (int j = 0; j < map[i].length; j += step) {
                        buf= sc.nextInt();
                        switch(buf){
                            case 0:
                                t = Cell.Terrain.WATER;
                                break;
                            case 1:
                                t = Cell.Terrain.HILLS;
                                break;
                            case 2:
                                t = Cell.Terrain.DESERT;
                                break;
                            case 3:
                                t = Cell.Terrain.SAVANNAH;
                                break;
                            case 4:
                                t = Cell.Terrain.JUNGLE;
                                break;
                            default:
                                t = Cell.Terrain.PEAKS;
                                break;
                        }
                        fillCell(map,j,i,step,t);

                    }

                }


            }catch(FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        private void makeFractal(Cell[][] map, int step){
            Cell.Terrain t;
            int cy,cx;

            for (int i = 0; i < map.length ; i += step) {
                for (int j = 0; j < map[i].length ; j += step) {

                    cy = i+ ((Math.random()<0.5)  ? 0 : step); // случайное смещение и получение значения ландшафта
                    cx = j+ ((Math.random()<0.5) ? 0 : step);

                    if (cy == map.length)
                        cy-=step;
                    if(cx == map[i].length)
                        cx-=step;

                    t = map[cy][cx].getTerrain();
                    fillCell(map,j,i,step,t);
                }
            }

            if (step > 1)
                makeFractal(map,step/2); //контроль детализации

        }

        private void makeCoast(Cell [][] map,int maxXY ){
            for (int i = 0; i < maxXY; i++) {
                for (int j = 0; j < maxXY; j++) {
                    map[i][j].setTypeOfCell(setCellType(map,j,i,maxXY));
                }
            }
        }

        private TypeMask createCurrentMask(Cell[][] m ,int x, int y){
            x-=1; // координаты левой верхней клетки в текущей маске
            y-=1;
            TypeMask currentMask;
            Cell[][] currentMaskCells = new Cell[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    currentMaskCells[i][j] = new Cell();
                    currentMaskCells[i][j].setTerrain((m[y+i][x+j].getTerrain() == Cell.Terrain.WATER ? Cell.Terrain.WATER: Cell.Terrain.NOT_WATER));
                }
            }
            currentMask = new TypeMask(Cell.TypeOfCell.DEFAULT,currentMaskCells);
            return currentMask;

        }

        private Cell.TypeOfCell setCellType(Cell[][] m ,int x, int y, int maxXY){
            if (m[y][x].getTerrain() != Cell.Terrain.WATER) {

                if ((x + 1 < maxXY) && (y + 1 < maxXY) && (x - 1 >= 0) && (y - 1 >= 0)) {

                    if ((m[y][x + 1].getTerrain() == Cell.Terrain.WATER) && (m[y + 1][x].getTerrain() == Cell.Terrain.WATER) && (m[y][x - 1].getTerrain() == Cell.Terrain.WATER) && (m[y - 1][x].getTerrain() == Cell.Terrain.WATER)){
                        return Cell.TypeOfCell.ISLAND;

                    } else if ((m[y][x + 1].getTerrain() == Cell.Terrain.WATER) || (m[y + 1][x].getTerrain() == Cell.Terrain.WATER) || (m[y][x - 1].getTerrain() == Cell.Terrain.WATER) || (m[y - 1][x].getTerrain() == Cell.Terrain.WATER)) {

                        TypeMask currentMask = createCurrentMask(m, x, y);
                        for (TypeMask mask : allMasks) {
                            if (mask.equals(currentMask))
                                return mask.typeOfMasc;
                        }
                    }
                }
            }
            return Cell.TypeOfCell.DEFAULT;
        }

        public void generateMap(AssetManager am,int maxXY) {
            int step = maxXY / 8;

            makePattern(am,map,step); //подготовка шаблона с заданной детализацией
            step /= 2;                //увеличение детализации
            makeFractal(map,step);    //рекурсивное построение фрактала
            makeCoast(map,maxXY);

        }

        public void generatePlayersInfrastructure(Player[] players, Texture city, GlobalMap glM){
            String cityKey;
            String unitKey;
            boolean cityIsCreated;
            int x;
            int y;
            for (int i = 0; i < players.length; i++) {
                cityIsCreated = false;
                while (!cityIsCreated) {
                    x = (int) (Math.random() * (maxX-2));
                    y = (int) (Math.random() * (maxY-2));
                    if (map[y][x].getTerrain() != Cell.Terrain.WATER && map[y][x].territoryOf == Player.Fraction.NONE && !map[y][x].cityOn) {
                        cityKey = y + "_" + x;
                        players[i].cityes.put(cityKey, new City(city, players[i].fr, x, y));
                        GameLogic.makeCityTerritory(glM, players[i].cityes.get(cityKey));
                        map[y][x].cityOn = true;
                        cityIsCreated = true;

                        x --;
                        y --;
                        unitKey = y+"_"+x;
                        players[i].units.put(unitKey,new Spearmens(players[i].fr, y, x)); //добавить больше юнитов
                        GameLogic.setUnitDefense(players[i].units.get(unitKey), map[y][x].cellCoeff);
                        GameLogic.setUnitAttack(players[i].units.get(unitKey) );
                        map[y][x].unitOn = true;

                    }
                }
                }
            }
        }
    }


