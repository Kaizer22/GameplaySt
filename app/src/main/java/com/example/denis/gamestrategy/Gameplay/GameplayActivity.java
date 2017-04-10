package com.example.denis.gamestrategy.Gameplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GameplayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        int noComputerPlayer =  i.getIntExtra("choosenTribe",0);
        Log.d("AAAAAAAAAAAAAAAAAAAAA",noComputerPlayer+" ");
        GameView game = new GameView(this,noComputerPlayer);
        setContentView(game);
    }
}
