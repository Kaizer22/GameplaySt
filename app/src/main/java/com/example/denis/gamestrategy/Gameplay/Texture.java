package com.example.denis.gamestrategy.Gameplay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by denis on 19.02.17.
 */

public class Texture {
    private Bitmap bitmap;
    public int bmX, bmY;

    public Texture (Bitmap bm){
        bitmap = bm;
        bmX = bitmap.getWidth();
        bmY = bitmap.getHeight();
    }


    public void resizeTexture(int width, int height){
        Bitmap newbitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);
        bitmap = newbitmap.copy(newbitmap.getConfig(),newbitmap.isMutable() ? true : false);
        newbitmap.recycle();
        bmX = bitmap.getWidth();
        bmY = bitmap.getHeight();
    }

    public void setBitmap(Bitmap bm){
        bitmap = bm;
        bitmap = Bitmap.createScaledBitmap(bitmap,bmX,bmY,false);

    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
