package com.example.denis.gamestrategy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

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

    //@Override
    //protected void onSizeChanged(int w, int h, int oldw, int oldh){
        //screenWidth = w;
        //screenHeight = h;
       // super.onSizeChanged(w, h, oldw, oldh);

    //}

    public GameView(Context context,int w,int h) {
        super(context);
        screenWidth = w;
        screenHeight = h-145;
        am = context.getAssets();
    }


    //добавить текстуры карты
    public void prepareGameView(){

        txM = new TextureManager();
        loadTextures();


        player = new Player(Player.Fraction.BERBER,txM.fractionUnit_test,txM.fractionCity_test,txM.fractionGround_test);
        scM = new ScreenManager(screenWidth,screenHeight,scale);

        txM.resizeTextures(scM);

        drawer = new Drawer(txM.moveOpportunityMarker);
        m = new GlobalMap();
        m.generateMap(am,mapSize); //loadMap(am, mapTextures);

        scM.loadUnitMap(player,m,txM.unitTextures,am);
        scM.loadCityMap(player,m,txM.cityTextureEarly,am);
        infoBar = new InfoBar(screenWidth,screenHeight,scM,txM.infoBarTexture);
        drawer.setTextSize(infoBar.textSize);


    }




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
                    //scM = new ScreenManager(myCanvas,scale);
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

    private void loadTextures(){
        txM.infoBarTexture = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.info_bar));

        txM.cityTextureEarly = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.city_early));
        txM.cityTextureLate = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.city_late));

        txM.fractionUnit_test = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.fraction_unit_test));
        txM.fractionCity_test  = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.fraction_city_test));
        txM.fractionGround_test = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.fraction_ground_test));

        txM.moveOpportunityMarker = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.move_opportunity));

        txM.mapTextures.put("water",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.ocean_water)));
        txM.mapTextures.put("savannah",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.savannah)));
        txM.mapTextures.put("hills",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills)));
        txM.mapTextures.put("peaks",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.snow_peaks)));
        txM.mapTextures.put("desert",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.desert)));
        txM.mapTextures.put("jungle",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.jungle)));

        //txM.

        txM.unitTextures.put("armored_vehicle",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.armored)));
        txM.unitTextures.put("camel_warrior",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.camel_warrior)));
    }
}
