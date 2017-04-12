package com.example.denis.gamestrategy;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.denis.gamestrategy.Gameplay.Cell;
import com.example.denis.gamestrategy.Gameplay.DBHelper;
import com.example.denis.gamestrategy.Gameplay.GameLogic;
import com.example.denis.gamestrategy.Gameplay.GameplayActivity;
import com.example.denis.gamestrategy.Gameplay.GlobalMap;
import com.example.denis.gamestrategy.Gameplay.StringMaster;
import com.example.denis.gamestrategy.Gameplay.Unit;
import com.example.denis.gamestrategy.Gameplay.Units.ArmoredVehicle;
import com.example.denis.gamestrategy.Gameplay.Units.CamelWarrior;
import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    public AssetManager am;

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
        //showMeDatabase();
        finish();

    }

    public void continueGame (View view){

        Intent  gameplayActivity  = new Intent ( this, GameplayActivity.class);      //  gameplayActivity  = new Intent ( this, GameplayActivity.class);
        int isSomethingSaved;
        am = this.getAssets();

        try{

            InputStream in = am.open("launching_information");
            Scanner sc = new Scanner(in);
            isSomethingSaved = sc.nextInt();
            Log.d("AAAAAAAAAAAAAAA", isSomethingSaved+" ");
            if (isSomethingSaved == 0)
                gameplayActivity.putExtra("isNewGame",true);
            else
                gameplayActivity.putExtra("isNewGame",false);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startActivity(gameplayActivity);
        finish();
    }


    public void exit(View view){
        finish();
    }

    public void showMeDatabase() {

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor map_cursor = database.query(DBHelper.TABLE_MAP, null, null, null, null, null, null);

        map_cursor.moveToFirst();




        int cx;
        int cy;
        String terrain;
        String typeOfCell;
        String territoryOf;

        int idIndex = map_cursor.getColumnIndex(DBHelper.KEY_ID);

        int cellXIndex = map_cursor.getColumnIndex(DBHelper.KEY_CELL_X);
        int cellYIndex = map_cursor.getColumnIndex(DBHelper.KEY_CELL_Y);
        int terrainIndex = map_cursor.getColumnIndex(DBHelper.KEY_TERRAIN);
        int typeOfCellIndex = map_cursor.getColumnIndex(DBHelper.KEY_TYPE_OF_CELL);
        int territoryOfIndex = map_cursor.getColumnIndex(DBHelper.KEY_TERRITORY_OF);


        do {
            cx = map_cursor.getInt(cellXIndex);
            cy = map_cursor.getInt(cellYIndex);
            terrain = map_cursor.getString(terrainIndex);
            typeOfCell = map_cursor.getString(typeOfCellIndex);
            territoryOf = map_cursor.getString(territoryOfIndex);

            Log.d("Database values "+map_cursor.getInt(idIndex), "\n" +
                    "("+cy+" : " + cx+")"+ " " + terrain + "  "+ typeOfCell +" " + territoryOf);
        } while (map_cursor.moveToNext());

        map_cursor.close();
        Cursor units_cursor = database.query(DBHelper.TABLE_UNITS, null, null, null, null, null, null);
        units_cursor.moveToFirst();

        int unitXIndex;
        int unitYIndex;
        int typeOfUnitIndex;
        int fractionIndex;
        int unitHPIndex;
        int unitsStepsIndex;

        int unitX;
        int unitY;
        String typeOfUnit;
        String fraction;
        int unitHP;
        int unitSteps;

        unitXIndex = units_cursor.getColumnIndex(DBHelper.KEY_UNIT_X);
        unitYIndex = units_cursor.getColumnIndex(DBHelper.KEY_UNIT_Y);
        typeOfUnitIndex = units_cursor.getColumnIndex(DBHelper.KEY_TYPE_OF_UNIT);
        fractionIndex = units_cursor.getColumnIndex(DBHelper.KEY_FRACTION);
        unitHPIndex = units_cursor.getColumnIndex(DBHelper.KEY_UNIT_HP);
        unitsStepsIndex = units_cursor.getColumnIndex(DBHelper.KEY_UNIT_STEPS);

        do {
            unitX = units_cursor.getInt(unitXIndex);
            unitY = units_cursor.getInt(unitYIndex);
            typeOfUnit = units_cursor.getString(typeOfUnitIndex);
            fraction = units_cursor.getString(fractionIndex);
            unitHP = units_cursor.getInt(unitHPIndex);
            unitSteps = units_cursor.getInt(unitsStepsIndex);
            Log.d("Database units values " +units_cursor.getInt(idIndex), "\n" +
                     "("+unitY+" : " + unitX +")"+" "+typeOfUnit+" "+ fraction +" "+ unitHP + " " + unitSteps);


        } while (units_cursor.moveToNext());

        units_cursor.close();
        database.close();
    }
}

