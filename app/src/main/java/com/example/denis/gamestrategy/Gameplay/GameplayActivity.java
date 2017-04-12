package com.example.denis.gamestrategy.Gameplay;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.denis.gamestrategy.Gameplay.Units.ArmoredVehicle;
import com.example.denis.gamestrategy.Gameplay.Units.CamelWarrior;
import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;
import com.example.denis.gamestrategy.R;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameplayActivity extends AppCompatActivity {
    Player[] players;
    GlobalMap globalMap;
    int mapSize = 64;

    int noComputerPlayer;
    boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        isNewGame = i.getBooleanExtra("isNewGame",false);
        noComputerPlayer =  i.getIntExtra("choosenTribe",0);

        createPlayers();

        globalMap = new GlobalMap(mapSize);

        Log.d("AAAAAAAAAAAAAAAAAAAAA",noComputerPlayer+" ");
        setContentView(R.layout.activity_gameplay);

        if (!isNewGame) {
            globalMap.newMap();
            DBHelper dbHelper = new DBHelper(this);
            DBManager dbManager = new DBManager(dbHelper, players, globalMap, !isNewGame);
            dbManager.execute();
        }else {
            GameView game = new GameView(this, noComputerPlayer, isNewGame, globalMap, players);
            setContentView(game);
        }
    }

    public void startGame(View view){

        GameView game = new GameView(this,noComputerPlayer,isNewGame,globalMap,players);
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



    public class DBManager extends AsyncTask<Void,Void,Void> {
        DBHelper dbHelper;
        Player[] players;
        GlobalMap m;
        boolean toLoad;

        public DBManager(DBHelper dbH,Player[] allPlayers, GlobalMap glM, boolean isL){
            dbHelper = dbH;
            players = allPlayers;
            m = glM;
            toLoad = isL;
        }

        @Override
        protected Void doInBackground(Void ...params) {
            if (toLoad)
                loadGameFromDatabase();
            else
                saveGameToDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);

            Button button = (Button) findViewById(R.id.start_game_button);
            button.setVisibility(View.VISIBLE);
        }


        public void loadGameFromDatabase() {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(DBHelper.TABLE_MAP, null, null, null, null, null, null);
            cursor.moveToFirst();


            Cell[][] glMap = m.getMap();
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


                glMap[cy][cx].setTerrain(StringMaster.getTerrainByString(terrain));
                glMap[cy][cx].setTypeOfCell(StringMaster.getTypeOfCellByString(typeOfCell));
                glMap[cy][cx].territoryOf = StringMaster.getFractionByString(territoryOf);

                Log.d("Database map values ", "\n" +
                        "("+cy+" : " + cx+")"+ " " + terrain + "  "+ typeOfCell +" " + territoryOf);

            } while (cursor.moveToNext());


            String selection;

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

            for (int i = 0; i < players.length; i++) {

                char zn ='"';

                selection = DBHelper.KEY_FRACTION + " = " + zn + players[i].fr.toString().toLowerCase()+ zn ;

                cursor = database.query(DBHelper.TABLE_UNITS, null, selection, null, null, null, null);
                cursor.moveToFirst();

                unitXIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_X);
                unitYIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_Y);
                typeOfUnitIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE_OF_UNIT);
                fractionIndex = cursor.getColumnIndex(DBHelper.KEY_FRACTION);
                unitHPIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_HP);
                unitsStepsIndex = cursor.getColumnIndex(DBHelper.KEY_UNIT_STEPS);

                do {
                    unitX = cursor.getInt(unitXIndex);
                    unitY = cursor.getInt(unitYIndex);
                    typeOfUnit = cursor.getString(typeOfUnitIndex);
                    fraction = cursor.getString(fractionIndex);
                    unitHP = cursor.getInt(unitHPIndex);
                    unitSteps = cursor.getInt(unitsStepsIndex);

                    Log.d("Database units values " , "\n" +
                            "("+unitY+" : " + unitX +")"+" "+typeOfUnit+" "+ fraction +" "+ unitHP + " " + unitSteps);

                    String unitKey;
                    unitKey = unitY + "_" + unitX;

                    Unit bufferUnit;
                    switch (StringMaster.getTypeOfUnitByString(typeOfUnit)) {
                        case ARMORED_VEHICLE:
                            players[i].units.put(unitKey, new ArmoredVehicle(StringMaster.getFractionByString(fraction), unitY, unitX));
                            bufferUnit = players[i].units.get(unitKey);
                            bufferUnit.unitHP = unitHP;
                            bufferUnit.unitHP = unitSteps;
                            GameLogic.setUnitAttack(bufferUnit);
                            GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                            break;
                        case SPEARMENS:
                            players[i].units.put(unitKey, new Spearmens(StringMaster.getFractionByString(fraction), unitY, unitX));
                            bufferUnit = players[i].units.get(unitKey);
                            bufferUnit.unitHP = unitHP;
                            bufferUnit.unitHP = unitSteps;
                            GameLogic.setUnitAttack(bufferUnit);
                            GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                            break;
                        case CAMEL_WARRIOR:
                            players[i].units.put(unitKey, new CamelWarrior(StringMaster.getFractionByString(fraction), unitY, unitX));
                            bufferUnit = players[i].units.get(unitKey);
                            bufferUnit.unitHP = unitHP;
                            bufferUnit.unitHP = unitSteps;
                            GameLogic.setUnitAttack(bufferUnit);
                            GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                            break;
                    }
                    glMap[unitY][unitX].unitOn = true;
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
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
            Unit bufferUnit;

            for (int i = 0; i < players.length ; i++) {
                allUnits.putAll(players[i].units);

            }

            for (Map.Entry<String, Unit> playersUnit: allUnits.entrySet()) // дописать восстановление здоровья на дружественной территории
            {
                bufferUnit = playersUnit.getValue();
                typeOfUnit = bufferUnit.getType().toString().toLowerCase();
                fraction = bufferUnit.fraction.toString().toLowerCase();

                units_contentValues.put(DBHelper.KEY_UNIT_Y,bufferUnit.posY);
                units_contentValues.put(DBHelper.KEY_UNIT_X,bufferUnit.posX);
                units_contentValues.put(DBHelper.KEY_TYPE_OF_UNIT, typeOfUnit);
                units_contentValues.put(DBHelper.KEY_FRACTION,fraction);
                units_contentValues.put(DBHelper.KEY_UNIT_HP,bufferUnit.unitHP);
                units_contentValues.put(DBHelper.KEY_UNIT_STEPS,bufferUnit.unitSteps);

                database.insert(DBHelper.TABLE_UNITS,null,units_contentValues);

                units_contentValues.clear();

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

}
