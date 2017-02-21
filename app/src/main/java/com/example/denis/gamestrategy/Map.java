package com.example.denis.gamestrategy;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by denis on 19.02.17.
 */

public class Map {
    private Cell[][] map;
    private int maxX ,maxY ;


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

    public void setLine(int y,Cell[] c) {
        map[y] = c;
    }

    public void loadMap(int mY, int mX){

        map = new Cell[mY][mX];

        for (int i = 0; i < mY ; i++) {
            for (int j = 0; j < mX; j++) {
                map[i][j] = new Cell();
            }
        }
    }

    public void loadMap(final AssetManager am, final Texture t0 ,final Texture t1,final Texture t2) { //количество клеток в строке и в столбце  в текстовом документе
                                                                                                     // запилить хотя бы массивом текстур
        //class MapLoader extends AsyncTask<Void, Void, Void> {
           // @Override
           // protected void onPreExecute() {super.onPreExecute();}

            //@Override
            //protected Void doInBackground(Void... params) {
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

                                switch (buf){
                                    case(0):
                                        map[j][i].setTexture(t0);
                                        break;
                                    case(1):
                                        map[j][i].setTexture(t1);
                                        break;
                                    case(2):
                                        map[j][i].setTexture(t2);
                                        break;
                                }
                            }
                            sc.nextLine();
                        }
                    }

                    sc.close();
                    in.close();

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               //return null;
          // }

          //  @Override
           // protected void onPostExecute(Void o) {super.onPostExecute(o);}
       // }
       // new MapLoader().execute();
    }

    public void setMap(Cell[][] m) {
        map = m;
        maxX = map[0].length;
        maxY = map.length;
    }
}
