package com.example.denis.gamestrategy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by denis on 19.02.17.
 */

public class GameView extends View{

    DisplayMetrics displaymetrics = getResources().getDisplayMetrics();


    final AssetManager am;
    final  int moveMistake = 30;
    //int infoBarScale =6;
    int mapSize = 64;
    int scale = 16;
    Bitmap b = Bitmap.createBitmap(displaymetrics.widthPixels, displaymetrics.heightPixels - 145, Bitmap.Config.ARGB_8888); // отнимать высоту заголовка от высоты Bitmap(а не 145)
    Canvas myCanvas = new Canvas(b);
    Drawer drawer;
    Map m ;
    ScreenManager scM ;
    int startEventMoveX = 0, startEventMoveY = 0,finalEventX = 0,finalEventY = 0, startEventX = 0, startEventY = 0;

    public GameView(Context context) {
        super(context);
        am = context.getAssets();
    }

    private Texture[] mapTextures;
    private Texture[] unitTextures;
    private Texture infoBarTexture;
    private Texture fraction_test;
    private Texture moveOpportunityMarker;

    private InfoBar infoBar;
    private Player player;
    //добавить текстуры карты
    public void prepareGameView(){
        infoBarTexture = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.info_bar));

        fraction_test = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.fraction_test));
        player = new Player(fraction_test);
        scM = new ScreenManager(myCanvas,scale);

        moveOpportunityMarker = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.move_opportunity));

        mapTextures = new Texture[]{
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.ocean_water)),                //0              //заменить ассоциативным массивом
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.savannah)),                   //1
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills)),                      //2
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.snow_peaks)),                 //3
                                  //new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills_coast)),                //4
                                  //new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills_diag)),                 //5
                                  //new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills_small_diag)),           //6
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.desert)),                     //4
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.jungle))                      //5
                                    //new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.ocean_desert_diag)),          //8

        };
        unitTextures = new Texture[]{
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.armored)),
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.camel_warrior))
        };
        resizeTextureArray(mapTextures,scM);
        resizeTextureArray(unitTextures,scM);
        moveOpportunityMarker.resizeTexture(scM.cellWidth, scM.cellHeight);
        drawer = new Drawer(moveOpportunityMarker);
        m = new Map();
        m.generateMap(am,mapSize); //loadMap(am, mapTextures);

        scM.loadUnitMap(player,m,unitTextures,am);
        infoBar = new InfoBar(myCanvas,scM,infoBarTexture);
        drawer.setTextSize(infoBar.textSize);


    }


    public void resizeTextureArray(Texture[] textures,ScreenManager scM){
        for (Texture t:textures) {
            t.resizeTexture(scM.cellWidth,scM.cellHeight);
        }
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
                    scM.chooseCell(m,infoBar,myCanvas,finalEventX,finalEventY);
                    //scale++;
                    //scM = new ScreenManager(myCanvas,scale);
                }
        }

        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        scM.createVisibleMap(m);

        drawer.drawVisibleMap(canvas,scM,mapTextures,scM.visibleMap,m);
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
}
