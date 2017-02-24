package com.example.denis.gamestrategy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by denis on 19.02.17.
 */

public class GameView extends View{
    final AssetManager am;
    final  int moveMistake = 25;
    Drawer drawer = new Drawer();
    Map m = new Map();
    ScreenManager scM = new ScreenManager();
    int startEventX = 0,startEventY = 0,finalEventX = 0,finalEventY = 0;

    public GameView(Context context) {
        super(context);
        am = context.getAssets();
    }

    private Texture t0 = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.water)); //текстуры карты, хранить бы их как-нибудь по-другому
    private Texture t1 = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.stone));
    private Texture t2 = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.grass));

    public void prepareGameView(){
        m.loadMap(am,t0,t1,t2);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = 0,y = 0;

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startEventX = (int)event.getX();
                startEventY = (int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                finalEventX = (int)event.getX();
                finalEventY = (int)event.getY();

                if (finalEventX - startEventX > moveMistake)
                    x = -1;
                else if (finalEventX - startEventX < -1*moveMistake)
                    x = 1;
                if (finalEventY - startEventY > moveMistake)
                    y = -1;
                else if (finalEventY - startEventY < -1*moveMistake)
                    y = 1;
                onUpdate(x, y);
                break;
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        scM.calculateVisibleMap(canvas);
        scM.createVisibleMap(m);

        drawer.drawMap(canvas,scM.visibleMap);
        Paint mPaint = new Paint();
    }

    public void onUpdate(int x, int y ){

        if(( (scM.posXOnGlobalMap + x) >= 0 ) && ( (scM.posXOnGlobalMap + x) < (m.getMaxX()-scM.vmX)) ) {
            scM.posXOnGlobalMap += x;
        }

        if((scM.posYOnGlobalMap + y) >= 0 && (scM.posYOnGlobalMap + y ) < (m.getMaxY()-scM.vmY) ) {
            scM.posYOnGlobalMap += y;
        }
        invalidate();
    }
}
