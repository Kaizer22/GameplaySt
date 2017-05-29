package com.example.denis.gamestrategy.Gameplay;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.denis.gamestrategy.Gameplay.Units.ArmoredVehicle;
import com.example.denis.gamestrategy.Gameplay.Units.CamelWarrior;
import com.example.denis.gamestrategy.Gameplay.Units.SmallBombard;
import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;
import com.example.denis.gamestrategy.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class GameplayActivity extends AppCompatActivity {
    public Player[] players;
    public GlobalMap globalMap;
    int mapSize;
    int mapPattern = 1;
    Texture city;


    int noComputerPlayer;
    boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        isNewGame = i.getBooleanExtra("isNewGame",false);

        city = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.city_early));

        Log.d("AAAAAAAAAAAAAAAAAAAAA",noComputerPlayer+" ");
        setContentView(R.layout.activity_gameplay);

        FragmentManager fragmentManager = getSupportFragmentManager();


        if (!isNewGame) {



            Integer mp = mapSize;
            Log.d("AAAAAAAAAAAAASSSSSSSSSS", mp.toString());


            DBHelper dbHelper = new DBHelper(this);
            DBLoader dbLoader = new DBLoader(dbHelper);
            dbLoader.execute();

        }else {

            mapSize = i.getIntExtra("choosenSize",32);
            mapPattern = i.getIntExtra("choosenPattern",1);
            noComputerPlayer =  i.getIntExtra("choosenTribe",0);

            createPlayers();

            globalMap = new GlobalMap(mapSize);
            globalMap.setMapPattern(mapPattern);

            GameView game = new GameView(this, noComputerPlayer, isNewGame, globalMap, players,fragmentManager);
            setContentView(game);
        }
    }

    public void startGame(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        GameView game = new GameView(this,noComputerPlayer,isNewGame,globalMap,players,fragmentManager);
        setContentView(game);
    }

    public void createPlayers(){
        Player.Fraction[] fractions = Player.Fraction.values();
        players = new Player[fractions.length-1];
        for (int i = 0; i < fractions.length - 1 ; i++) {
            if (i == noComputerPlayer) {
                players[i] = new Player(fractions[i],new PlayerIntellect());
            }else
                players[i] = new Player(fractions[i],new ComputerIntellect());
        }
    }



    public class DBLoader extends AsyncTask<Void,Void,Void> {
        DBHelper dbHelper;


        public DBLoader(DBHelper dbH){
            dbHelper = dbH;


        }

        @Override
        protected Void doInBackground(Void ...params) {
            loadGameFromDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);

            ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar3);
            progressBar.setVisibility(View.INVISIBLE);
            Button button = (Button) findViewById(R.id.start_game_button);
            button.setVisibility(View.VISIBLE);
        }


        public void loadGameFromDatabase() {
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            Cursor cursor = database.query(DBHelper.TABLE_INFO, null, null, null, null, null, null);
            cursor.moveToFirst();
            int noComputerPlayerIndex = cursor.getColumnIndex(DBHelper.KEY_NO_COMPUTER_PLAYER);
            int mapSizeIndex = cursor.getColumnIndex(DBHelper.KEY_MAP_SIZE);

            mapSize = cursor.getInt(mapSizeIndex);

            globalMap = new GlobalMap(mapSize);
            globalMap.newMap();
            noComputerPlayer = cursor.getInt(noComputerPlayerIndex);

            createPlayers();


            cursor = database.query(DBHelper.TABLE_MAP, null, null, null, null, null, null);
            cursor.moveToFirst();


            Cell[][] glMap = globalMap.getMap();
            int cx;
            int cy;
            String terrain;
            String typeOfCell;
            String territoryOf;


            int cellXIndex = cursor.getColumnIndex(DBHelper.KEY_CELL_X);
            int cellYIndex = cursor.getColumnIndex(DBHelper.KEY_CELL_Y);
            int terrainIndex = cursor.getColumnIndex(DBHelper.KEY_TERRAIN);
            int typeOfCellIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE_OF_CELL);
            int territoryOfIndex = cursor.getColumnIndex(DBHelper.KEY_TERRITORY_OF);


            do {
                cx = cursor.getInt(cellXIndex);
                cy = cursor.getInt(cellYIndex);
                terrain = cursor.getString(terrainIndex);
                typeOfCell = cursor.getString(typeOfCellIndex);
                territoryOf = cursor.getString(territoryOfIndex);


                glMap[cy][cx].setTerrain(StringConverter.getTerrainByString(terrain));
                glMap[cy][cx].setTypeOfCell(StringConverter.getTypeOfCellByString(typeOfCell));
                glMap[cy][cx].territoryOf = StringConverter.getFractionByString(territoryOf);

                Log.d("Database map values ", "\n" +
                        "("+cy+" : " + cx+")"+ " " + terrain + "  "+ typeOfCell +" " + territoryOf);

            } while (cursor.moveToNext());


            String selection;
            char zn ='"';

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
            double unitHP;
            int unitSteps;

            for (int i = 0; i < players.length; i++) {



                selection = DBHelper.KEY_UNIT_FRACTION + " = " + zn + players[i].fr.toString().toLowerCase()+ zn ;

                cursor = database.query(DBHelper.TABLE_UNITS, null, selection, null, null, null, null);
                cursor.moveToFirst();

                unitXIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_X);
                unitYIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_Y);
                typeOfUnitIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE_OF_UNIT);
                fractionIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_FRACTION);
                unitHPIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_HP);
                unitsStepsIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_STEPS);

                do {
                    try {
                        unitX = cursor.getInt(unitXIndex);
                        unitY = cursor.getInt(unitYIndex);
                        typeOfUnit = cursor.getString(typeOfUnitIndex);
                        fraction = cursor.getString(fractionIndex);
                        unitHP = cursor.getDouble(unitHPIndex);
                        unitSteps = cursor.getInt(unitsStepsIndex);

                        Log.d("Database units values ", "\n" +
                                "(" + unitY + " : " + unitX + ")" + " " + typeOfUnit + " " + fraction + " " + unitHP + " " + unitSteps);

                        String unitKey;
                        unitKey = unitY + "_" + unitX;

                        Unit bufferUnit;
                        switch (StringConverter.getTypeOfUnitByString(typeOfUnit)) {
                            case ARMORED_VEHICLE:
                                bufferUnit = new ArmoredVehicle(StringConverter.getFractionByString(fraction), unitY, unitX);
                                bufferUnit.unitHP = unitHP;
                                bufferUnit.unitSteps = unitSteps;
                                GameLogic.setUnitAttack(bufferUnit);
                                GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                                players[i].units.put(unitKey, bufferUnit);

                                break;
                            case SPEARMENS:
                                bufferUnit = new Spearmens(StringConverter.getFractionByString(fraction), unitY, unitX);
                                bufferUnit.unitHP = unitHP;
                                bufferUnit.unitSteps = unitSteps;
                                GameLogic.setUnitAttack(bufferUnit);
                                GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                                players[i].units.put(unitKey, bufferUnit);
                                break;
                            case CAMEL_WARRIOR:
                                bufferUnit = new CamelWarrior(StringConverter.getFractionByString(fraction), unitY, unitX);
                                bufferUnit.unitHP = unitHP;
                                bufferUnit.unitSteps = unitSteps;
                                GameLogic.setUnitAttack(bufferUnit);
                                GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                                players[i].units.put(unitKey, bufferUnit);
                                break;
                            case SMALL_BOMBARD:
                                bufferUnit = new SmallBombard(StringConverter.getFractionByString(fraction), unitY, unitX);
                                bufferUnit.unitHP = unitHP;
                                bufferUnit.unitSteps = unitSteps;
                                GameLogic.setUnitAttack(bufferUnit);
                                GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                                players[i].units.put(unitKey, bufferUnit);
                                break;
                        }
                        glMap[unitY][unitX].unitOn = true;
                    }catch (CursorIndexOutOfBoundsException e){}
                } while (cursor.moveToNext());
            }


            int cityXIndex;
            int cityYIndex;
            int cityNameIndex;
            int cityFractionIndex;
            int cityAffectAreaIndex;
            int cityHPIndex;

            int cityX;
            int cityY;
            String cityName;
            String cityFraction;
            int cityAffectArea;
            int cityHP;

            for (int i = 0; i < players.length ; i++) {
                selection = DBHelper.KEY_CITY_FRACTION + " = " + zn + players[i].fr.toString().toLowerCase()+ zn ;

                cursor = database.query(DBHelper.TABLE_CITIES, null, selection, null, null, null, null);
                cursor.moveToFirst();

                cityXIndex = cursor.getColumnIndex(DBHelper.KEY_CITY_X);
                cityYIndex = cursor.getColumnIndex(DBHelper.KEY_CITY_Y);
                cityNameIndex =  cursor.getColumnIndex(DBHelper.KEY_CITY_NAME);
                cityFractionIndex =  cursor.getColumnIndex(DBHelper.KEY_CITY_FRACTION);
                cityAffectAreaIndex =  cursor.getColumnIndex(DBHelper.KEY_AFFECT_AREA);
                cityHPIndex =  cursor.getColumnIndex(DBHelper.KEY_CITY_HP);

                do{
                    try {
                        cityX = cursor.getInt(cityXIndex);
                        cityY = cursor.getInt(cityYIndex);
                        cityName = cursor.getString(cityNameIndex);
                        cityFraction = cursor.getString(cityFractionIndex);
                        cityAffectArea = cursor.getInt(cityAffectAreaIndex);
                        cityHP = cursor.getInt(cityHPIndex);

                        String cityKey;
                        cityKey = cityY + "_" + cityX;

                        City bufferCity = new City(city, StringConverter.getFractionByString(cityFraction), cityX, cityY);
                        bufferCity.name = cityName;
                        bufferCity.affectArea = cityAffectArea;
                        bufferCity.cityHP = cityHP;

                        players[i].cities.put(cityKey, bufferCity);
                        glMap[cityY][cityX].cityOn = true;
                    }catch (CursorIndexOutOfBoundsException e){}
                }while (cursor.moveToNext());
            }

            cursor.close();
            database.close();
        }

    }

}
