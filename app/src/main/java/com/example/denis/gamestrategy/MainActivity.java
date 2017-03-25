package com.example.denis.gamestrategy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    public static int WIDTH_DEVICE;
    public static int HEIGTH_DEVICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        WIDTH_DEVICE = display.getWidth();
        HEIGTH_DEVICE = display.getHeight();
        final GameView game = new GameView(this,WIDTH_DEVICE,HEIGTH_DEVICE);
        game.prepareGameView();
        setContentView(game);
    }
}
