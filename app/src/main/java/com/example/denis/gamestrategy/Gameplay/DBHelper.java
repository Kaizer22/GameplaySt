package com.example.denis.gamestrategy.Gameplay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by denis on 11.04.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gameplayDB";

    public static final String TABLE_MAP = "map";
    public static final String TABLE_UNITS = "units";
    public static final String TABLE_CITIES = "cities";

    public static final String KEY_ID = "_id";

    public static final String KEY_CELL_X = "x";
    public static final String KEY_CELL_Y = "y";
    public static final String KEY_TERRAIN = "terrain";
    public static final String KEY_TYPE_OF_CELL = "type_of_cell";
    public static final String KEY_TERRITORY_OF = "territory_of";

    public static final String KEY_UNIT_X = "x";
    public static final String KEY_UNIT_Y = "y";
    public static final String KEY_TYPE_OF_UNIT = "type_of_unit";
    public static final String KEY_UNIT_FRACTION = "fraction";
    public static final String KEY_UNIT_HP = "unit_hp";
    public static final String KEY_UNIT_STEPS = "unit_steps";

    public static final String KEY_CITY_X = "x";
    public static final String KEY_CITY_Y = "y";
    public static final String KEY_CITY_NAME = "name";
    public static final String KEY_CITY_FRACTION = "fraction";
    public static final String KEY_AFFECT_AREA = "affect_area";
    public static final String KEY_CITY_HP = "city_hp";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMap = "CREATE TABLE " + TABLE_MAP +
                "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_CELL_X +" INTEGER NOT NULL, "+
                KEY_CELL_Y +" INTEGER NOT NULL, "+
                KEY_TERRAIN +" TEXT NOT NULL, "+
                KEY_TYPE_OF_CELL +" TEXT NOT NULL, "+
                KEY_TERRITORY_OF +" TEXT NOT NULL)";


        String createTableUnits = "CREATE TABLE " + TABLE_UNITS +
                "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_UNIT_X +" INTEGER NOT NULL, "+
                KEY_UNIT_Y +" INTEGER NOT NULL, "+
                KEY_TYPE_OF_UNIT +" TEXT NOT NULL, "+
                KEY_UNIT_FRACTION +" TEXT NOT NULL, "+
                KEY_UNIT_HP +" REAL NOT NULL, "+
                KEY_UNIT_STEPS +" INTEGER NOT NULL)";

        String createTableCityes = "CREATE TABLE " + TABLE_CITIES +
                "("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_CITY_X + " INTEGER NOT NULL, "+
                KEY_CITY_Y + " INTEGER NOT NULL, "+
                KEY_CITY_NAME + " TEXT NOT NULL, "+
                KEY_CITY_FRACTION + " TEXT NOT NULL, " +
                KEY_AFFECT_AREA + " INTEGER NOT NULL, " +
                KEY_CITY_HP + " INTEGER NOT NULL)";


        db.execSQL(createTableMap);
        db.execSQL(createTableUnits);
        db.execSQL(createTableCityes);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);

        onCreate(db);

    }
}
