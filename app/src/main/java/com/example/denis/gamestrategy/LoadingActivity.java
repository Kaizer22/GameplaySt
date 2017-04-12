package com.example.denis.gamestrategy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.denis.gamestrategy.Gameplay.ComputerIntellect;
import com.example.denis.gamestrategy.Gameplay.DBHelper;
import com.example.denis.gamestrategy.Gameplay.DBManager;
import com.example.denis.gamestrategy.Gameplay.Player;
import com.example.denis.gamestrategy.Gameplay.PlayerIntellect;

public class LoadingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        DBHelper dbHelper = new DBHelper(this);

    }
}

