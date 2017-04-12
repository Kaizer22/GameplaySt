package com.example.denis.gamestrategy.Gameplay;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.example.denis.gamestrategy.Gameplay.Units.ArmoredVehicle;
import com.example.denis.gamestrategy.Gameplay.Units.CamelWarrior;
import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 12.04.17.
 */

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
    }


    public void loadGameFromDatabase() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor map_cursor = database.query(DBHelper.TABLE_MAP, null, null, null, null, null, null);
        Cursor units_cursor;

        map_cursor.moveToFirst();


        Cell[][] glMap = m.getMap();
        int cx;
        int cy;
        String terrain;
        String typeOfCell;
        String territoryOf;


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


            glMap[cy][cx].setTerrain(StringMaster.getTerrainByString(terrain));
            glMap[cy][cx].setTypeOfCell(StringMaster.getTypeOfCellByString(typeOfCell));
            glMap[cy][cx].territoryOf = StringMaster.getFractionByString(territoryOf);

        } while (map_cursor.moveToNext());
        map_cursor.close();


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

            selection = DBHelper.KEY_FRACTION + "=" + players[i].fr.toString().toLowerCase();

            units_cursor = database.query(DBHelper.TABLE_UNITS, null, selection, null, null, null, null);
            units_cursor.moveToFirst();

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

                String unitKey;
                unitKey = unitY + " " + unitX;

                Unit bufferUnit;
                switch (StringMaster.getTypeOfUnitByString(typeOfUnit)) {
                    case ARMORED_VEHICLE:
                        players[i].units.put(unitKey, new ArmoredVehicle(StringMaster.getFractionByString(fraction), unitX, unitY));
                        bufferUnit = players[i].units.get(unitKey);
                        bufferUnit.unitHP = unitHP;
                        bufferUnit.unitHP = unitSteps;
                        GameLogic.setUnitAttack(bufferUnit);
                        GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                        break;
                    case SPEARMENS:
                        players[i].units.put(unitKey, new Spearmens(StringMaster.getFractionByString(fraction), unitX, unitY));
                        bufferUnit = players[i].units.get(unitKey);
                        bufferUnit.unitHP = unitHP;
                        bufferUnit.unitHP = unitSteps;
                        GameLogic.setUnitAttack(bufferUnit);
                        GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                        break;
                    case CAMEL_WARRIOR:
                        players[i].units.put(unitKey, new CamelWarrior(StringMaster.getFractionByString(fraction), unitX, unitY));
                        bufferUnit = players[i].units.get(unitKey);
                        bufferUnit.unitHP = unitHP;
                        bufferUnit.unitHP = unitSteps;
                        GameLogic.setUnitAttack(bufferUnit);
                        GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                        break;
                }
                glMap[unitY][unitX].unitOn = true;
            } while (units_cursor.moveToNext());
            units_cursor.close();
            database.close();
        }
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

