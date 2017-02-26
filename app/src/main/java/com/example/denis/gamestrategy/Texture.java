package com.example.denis.gamestrategy;

import android.graphics.Bitmap;

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
        bitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);
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
