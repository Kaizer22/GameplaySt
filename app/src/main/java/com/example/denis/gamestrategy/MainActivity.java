package com.example.denis.gamestrategy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GameView game = new GameView(this);
        game.prepareGameView();
        setContentView(game);
    }
}
