package com.example.denis.gamestrategy;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * Created by denis on 19.02.17.
 */

public class GameView extends View{

    final AssetManager am;
    int screenWidth ;
    int screenHeight ;
    final  int moveMistake = 30;
    int mapSize = 64;
    int scale = 16;
    Drawer drawer;
    GlobalMap m ;
    ScreenManager  scM;
    TextureManager txM ;
    int startEventMoveX = 0, startEventMoveY = 0,finalEventX = 0,finalEventY = 0, startEventX = 0, startEventY = 0;

    public InfoBar infoBar;
    public Player player;

    public LinkedList<Player> players;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;

        prepareGameView();


    }

    public GameView(Context context) {
        super(context);
        am = context.getAssets();
    }



    public void prepareGameView(){

        txM = new TextureManager();
        loadTextures();


        player = new Player(Player.Fraction.BERBER);
        scM = new ScreenManager(screenWidth,screenHeight,scale);

        txM.resizeTextures(scM);

        drawer = new Drawer();
        m = new GlobalMap();
        m.generateMap(am,mapSize);  //loadMap(am, mapTextures); при повторном запуске

        //createPlayers(); //loadPlayers(); при повторном запуске

        scM.loadUnitMap(player,m,txM.unitTextures,am);
        scM.loadCityMap(player,m,txM.cityTextureEarly,am);
        infoBar = new InfoBar(screenWidth,screenHeight,scM,txM.infoBarTexture);
        drawer.setTextSize(infoBar.textSize);


    }

    public void createPlayers(){
        Player.Fraction[] fractions = Player.Fraction.values();
        for (int i = 0; i < fractions.length - 1 ; i++) {
            players.add(new Player(fractions[i]));
        }
    }
    public void loadPlayers(){}




    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startEventMoveX = (int)event.getX();
                startEventMoveY = (int)event.getY();
                startEventX = (int)event.getX();
                startEventY = (int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                finalEventX = (int)event.getX();
                finalEventY = (int)event.getY();

                onUpdate();

                startEventMoveX = (int)event.getX();
                startEventMoveY = (int)event.getY();
                break;

            case MotionEvent.ACTION_UP:
                finalEventX = (int)event.getX();
                finalEventY = (int)event.getY();
                if (startEventX - finalEventX <= moveMistake && startEventX - finalEventX <= moveMistake){
                    scM.chooseCell(m,infoBar,screenWidth,screenHeight,finalEventX,finalEventY);
                    //scale+=2;
                    //scM = new ScreenManager(screenWidth,screenHeight,scale);
                }
        }

        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawer.drawVisibleMap(canvas,scM,txM,m);
        drawer.drawInfoRectangle(infoBar,canvas);
    }

    public void onUpdate(){
        int x = 0,y = 0;

        if (finalEventX - startEventMoveX > moveMistake)
            x = -1;
        else if (finalEventX - startEventMoveX < -1*moveMistake)
            x = 1;
        if (finalEventY - startEventMoveY > moveMistake)
            y = -1;
        else if (finalEventY - startEventMoveY < -1*moveMistake)
            y = 1;

        if(( (scM.posXOnGlobalMap + x) >= 0 ) && ( (scM.posXOnGlobalMap + x) <= (m.getMaxX()-scM.vmX)) ) {
            scM.posXOnGlobalMap += x;
        }

        if((scM.posYOnGlobalMap + y) >= 0 && (scM.posYOnGlobalMap + y ) <= (m.getMaxY()-scM.vmY) ) {
            scM.posYOnGlobalMap += y;
        }
        invalidate();
    }



    private void loadFractionsTexturesFromAssets(){
        try {

            InputStream ims ;
            Bitmap b;
            String s;
            Player.Fraction[] fractions = Player.Fraction.values();
            TextureManager.TypeOfFractionTexture[] types = TextureManager.TypeOfFractionTexture.values();

            for (int i = 0; i < fractions.length-1; i++) {
                for (int j = 0; j < types.length; j++) {
                    s = fractions[i].toString().toLowerCase()+"_"+types[j].toString().toLowerCase(); //+".png";
                    Log.d("Something  ",s);
                    ims = am.open(s);
                    b = BitmapFactory.decodeStream(ims);
                    //s = terrain[i].toString().toLowerCase()+"_"+type[j].toString().toLowerCase();
                    txM.fractionsTextures.put(s,new Texture(b));


                }
            }



        }
        catch(IOException ex) {
            Log.d("Something","Wrong");
            return;
        }
    }
    private void loadMapTexturesFromAssets() {
        try {

            InputStream ims ;
            Bitmap b;
            String s;
            Cell.Terrain[] terrain = Cell.Terrain.values();
            Cell.TypeOfCell[] type = Cell.TypeOfCell.values();


            for (int i = 0; i < terrain.length - 2; i++) {
                for (int j = 0; j < type.length; j++) {
                    if (terrain[i] == Cell.Terrain.PEAKS || terrain[i] == Cell.Terrain.WATER)
                        j = type.length - 1;
                    s = terrain[i].toString().toLowerCase()+"_"+type[j].toString().toLowerCase()+".png";
                    Log.d("Something  ",s);
                    ims = am.open(s);
                    b = BitmapFactory.decodeStream(ims);
                    s = terrain[i].toString().toLowerCase()+"_"+type[j].toString().toLowerCase();

                    txM.mapTextures.put(s,new Texture(b));


                }
            }



        }
        catch(IOException ex) {
            Log.d("Something","Wrong");
            return;
        }
    }

    private void loadTextures(){
        txM.infoBarTexture = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.info_bar));

        txM.cityTextureEarly = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.city_early));
        txM.cityTextureLate = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.city_late));

        txM.moveOpportunityMarker = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.move_opportunity));

        loadMapTexturesFromAssets();
        loadFractionsTexturesFromAssets();

        txM.unitTextures.put("armored_vehicle",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.armored)));
        txM.unitTextures.put("camel_warrior",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.camel_warrior)));
    }


}
