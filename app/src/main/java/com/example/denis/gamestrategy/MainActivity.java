package com.example.denis.gamestrategy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.denis.gamestrategy.Gameplay.GameplayActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void newGame(View view){
        Intent chooseActivity = new Intent(this, ChooseActivity.class);
        startActivity(chooseActivity);
        finish();
    }

    public void options(View view){
        Intent optionActivity = new Intent(this, OptionActivity.class);
        startActivity(optionActivity);
        finish();

    }

    public void continueGame (View view){
        Intent gameplayActivity  = new Intent ( this, GameplayActivity.class);
        gameplayActivity.putExtra("isNewGame",false);
        startActivity(gameplayActivity);
        finish();
    }


    public void exit(View view){
        finish();
    }
}

