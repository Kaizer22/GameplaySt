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
    Bitmap b = Bitmap.createBitmap(displaymetrics.widthPixels, displaymetrics.heightPixels, Bitmap.Config.ARGB_8888); // отнимать высоту заголовка от высоты Bitmap
    Canvas myCanvas = new Canvas(b);
    Drawer drawer = new Drawer();
    Map m = new Map();
    ScreenManager scM = new ScreenManager();
    int startEventX = 0,startEventY = 0,finalEventX = 0,finalEventY = 0;

    public GameView(Context context) {
        super(context);
        am = context.getAssets();
    }
    private Texture[] textures;
    private Texture itexture = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.info_bar));
    private InfoBar infoBar = new InfoBar(itexture);

    public void prepareGameView(){
        textures = new Texture[]{
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.ocean_water)),                //0
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.savannah)),                   //1
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills)),                      //2
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.snow_peaks)),                 //3
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills_coast)),                //4
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills_diag)),                 //5
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.hills_small_diag)),           //6
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.desert)),                     //7
                                  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.ocean_desert_diag)),          //8

        };
        m.loadMap(am,textures);

        scM.calculateVisibleMap(myCanvas);               // нужно делать один раз , а не для каждого кадра!
        infoBar.calculateInfoBar(myCanvas,scM);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startEventX = (int)event.getX();
                startEventY = (int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                finalEventX = (int)event.getX();
                finalEventY = (int)event.getY();

                onUpdate();

                startEventX = (int)event.getX();
                startEventY = (int)event.getY();
                break;
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        scM.createVisibleMap(m);

        drawer.drawMap(canvas,scM.visibleMap);
        drawer.drawInfoRectangle(infoBar,canvas);


    }

    public void onUpdate(){
        int x = 0,y = 0;

        if (finalEventX - startEventX > moveMistake)
            x = -1;
        else if (finalEventX - startEventX < -1*moveMistake)
            x = 1;
        if (finalEventY - startEventY > moveMistake)
            y = -1;
        else if (finalEventY - startEventY < -1*moveMistake)
            y = 1;

        if(( (scM.posXOnGlobalMap + x) >= 0 ) && ( (scM.posXOnGlobalMap + x) < (m.getMaxX()-scM.vmX)) ) {
            scM.posXOnGlobalMap += x;
        }

        if((scM.posYOnGlobalMap + y) >= 0 && (scM.posYOnGlobalMap + y ) < (m.getMaxY()-scM.vmY) ) {
            scM.posYOnGlobalMap += y;
        }
        invalidate();
    }
}
