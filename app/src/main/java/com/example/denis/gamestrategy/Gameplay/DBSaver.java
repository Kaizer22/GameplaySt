package com.example.denis.gamestrategy.Gameplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 12.04.17.
 */

public class DBSaver extends AsyncTask<Void,Void,Void> {
    DBHelper dbHelper;
    Player[] players;
    GlobalMap m;
    Context maincontext;


    public DBSaver(Context context, DBHelper dbH, Player[] allPlayers, GlobalMap glM){
        dbHelper = dbH;
        maincontext = context;
        players = allPlayers;
        m = glM;

    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(maincontext,"Сохраняю, дождитесь сообщения о завершении...",Toast.LENGTH_LONG).show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void ...params) {
            saveGameToDatabase();
        return null;
    }

    @Override
    protected void onPostExecute(Void o) {
        Toast.makeText(maincontext,"Сохранено",Toast.LENGTH_LONG).show();
        super.onPostExecute(o);
    }


    public void  saveGameToDatabase(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(database, dbHelper.DATABASE_VERSION, dbHelper.DATABASE_VERSION);

        ContentValues map_contentValues = new ContentValues();

        Cell[][] glMap = m.getMap();
        String terrain;
        String typeOfCell;
        String territoryOf;


        for (int y = 0; y < m.getMaxY() ; y++) {
            for (int x = 0; x < m.getMaxX(); x++) {
                terrain = glMap[y][x].getTerrain().toString().toLowerCase();
                typeOfCell = glMap[y][x].getTypeOfCell().toString().toLowerCase();
                territoryOf = glMap[y][x].territoryOf.toString().toLowerCase();

                map_contentValues.put(DBHelper.KEY_CELL_Y,y);
                map_contentValues.put(DBHelper.KEY_CELL_X,x);
                map_contentValues.put(DBHelper.KEY_TERRAIN,terrain);
                map_contentValues.put(DBHelper.KEY_TYPE_OF_CELL,typeOfCell);
                map_contentValues.put(DBHelper.KEY_TERRITORY_OF,territoryOf);

                database.insert(DBHelper.TABLE_MAP,null,map_contentValues);

                map_contentValues.clear();
            }
        }




        ContentValues units_contentValues = new ContentValues();

        String typeOfUnit;
        String fraction;



        Map<String, Unit> allUnits = new HashMap<>();
        Map<String, City> allCityes = new HashMap<>();
        Unit bufferUnit;

        for (int i = 0; i < players.length ; i++) {
            allUnits.putAll(players[i].units);
            allCityes.putAll(players[i].cities);
        }

        for (Map.Entry<String, Unit> playersUnit: allUnits.entrySet()) // дописать восстановление здоровья на дружественной территории
        {
            bufferUnit = playersUnit.getValue();
            typeOfUnit = bufferUnit.getType().toString().toLowerCase();
            fraction = bufferUnit.fraction.toString().toLowerCase();

            units_contentValues.put(DBHelper.KEY_UNIT_Y,bufferUnit.posY);
            units_contentValues.put(DBHelper.KEY_UNIT_X,bufferUnit.posX);
            units_contentValues.put(DBHelper.KEY_TYPE_OF_UNIT, typeOfUnit);
            units_contentValues.put(DBHelper.KEY_UNIT_FRACTION,fraction);
            units_contentValues.put(DBHelper.KEY_UNIT_HP,bufferUnit.unitHP);
            units_contentValues.put(DBHelper.KEY_UNIT_STEPS,bufferUnit.unitSteps);

            database.insert(DBHelper.TABLE_UNITS,null,units_contentValues);

            units_contentValues.clear();

        }


        ContentValues cities_contentValues = new ContentValues();

        String cityFraction;

        City bufferCity;

        for (Map.Entry<String, City> playersCity: allCityes.entrySet()) // дописать восстановление здоровья на дружественной территории
        {
            bufferCity = playersCity.getValue();
            cityFraction = bufferCity.fraction.toString().toLowerCase();

            cities_contentValues.put(DBHelper.KEY_CITY_X, bufferCity.posX);
            cities_contentValues.put(DBHelper.KEY_CITY_Y, bufferCity.posY);
            cities_contentValues.put(DBHelper.KEY_CITY_NAME, bufferCity.name);
            cities_contentValues.put(DBHelper.KEY_CITY_FRACTION, cityFraction);
            cities_contentValues.put(DBHelper.KEY_AFFECT_AREA, bufferCity.affectArea);
            cities_contentValues.put(DBHelper.KEY_CITY_HP, bufferCity.cityHP);

            database.insert(DBHelper.TABLE_CITIES,null,cities_contentValues);

            cities_contentValues.clear();
        }


        database.close();


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String info  =1 + "" ;
        byte[] buffer = info.getBytes();
        try {
            bos.write(buffer);
        } catch(Exception e) {
            e.printStackTrace();
        }


        try{
            FileOutputStream fos = new FileOutputStream("launching_information.txt");
            bos.writeTo(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

