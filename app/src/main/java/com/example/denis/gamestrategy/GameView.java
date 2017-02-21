package com.example.denis.gamestrategy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by denis on 19.02.17.
 */

public class GameView extends View{
    final AssetManager am;
    public GameView(Context context) {
        super(context);
        am = context.getAssets();
    }

    private Texture t0 = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.water)); //текстуры карты, хранить бы их как-нибудь по-другому
    private Texture t1 = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.stone));
    private Texture t2 = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.grass));

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawer drawer = new Drawer();
        Map m = new Map();
        ScreenManager scM = new ScreenManager();

        m.loadMap(am,t0,t1,t2);
        scM.calculateVisibleMap(canvas);
        scM.createVisibleMap(m);

        drawer.drawMap(canvas,scM.visibleMap);
        Paint mPaint = new Paint();
    }

    public void onUpdate(){}
}
