package com.example.denis.gamestrategy.Gameplay;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.denis.gamestrategy.Gameplay.Units.ArmoredVehicle;
import com.example.denis.gamestrategy.Gameplay.Units.CamelWarrior;
import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;
import com.example.denis.gamestrategy.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by denis on 19.02.17.
 */

public class GameView extends View{

    final AssetManager am;

    boolean isNewGame;
    int screenWidth ;
    int screenHeight ;
    final  int moveMistake = 30;

    final int nextTurnButtonSize = 6;
    final int saveExitButtonSize = 15; // size = 1.0/ эту переменную * screenWidth;
    int mapSize = 64;
    int scale = 16;              //количество видимых клеток по оси x на экране
    Drawer drawer;
    GlobalMap m ;

    DBHelper dbHelper;

    ScreenManager scM;
    TextureManager txM ;
    int startEventMoveX = 0, startEventMoveY = 0,finalEventX = 0,finalEventY = 0, startEventX = 0, startEventY = 0;


    public InfoBar infoBar;
    public ResourceBar resourceBar;
    public NextTurnButton nextTurnButton;
    public SaveExitButton saveExitButton;

    int noComputerPlayer;
    //public Player player1;
    //public Player player2;
    //public Player player3;

    //public PlayerIntellect playerIntellect1;
    //public ComputerIntellect computerIntellect2;
    //public ComputerIntellect computerIntellect3;

    public Player[] players = new Player[17];

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;

        prepareGameView();


    }

    public GameView(Context context,int nCP, boolean iNG) {
        super(context);
        dbHelper = new DBHelper(context);
        noComputerPlayer = nCP;
        isNewGame = iNG;
        am = context.getAssets();
    }



    public void prepareGameView() {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        txM = new TextureManager();
        loadTextures();


        scM = new ScreenManager(screenWidth, screenHeight, scale);

        txM.resizeTextures(scM);

        drawer = new Drawer();
        m = new GlobalMap();

        createPlayers();


        int isSomethingSaved = 0;
        try{
            InputStream in = am.open("launching_information");
            Scanner sc = new Scanner(in);
            isSomethingSaved = sc.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isNewGame || isSomethingSaved == 0) {
            createPlayers();
            m.generateMap(am, mapSize, players, txM.cityTextureEarly);
        } else{
            loadGameFromDatabase(database);
        }



        infoBar = new InfoBar(screenWidth,screenHeight,scM,txM.infoBarTexture);
        resourceBar = new ResourceBar(screenWidth,scM,txM.resourceBarTexture,txM.eatScoreIcon,txM.populationScoreIcon,txM.powerScoreIcon,txM.happinessScoreIcon);
        nextTurnButton = new NextTurnButton(nextTurnButtonSize);
        saveExitButton = new SaveExitButton(saveExitButtonSize);
        drawer.setTextSize(infoBar.textSize);


    }

    public void createPlayers(){
        Player.Fraction[] fractions = Player.Fraction.values();
        for (int i = 0; i < fractions.length - 1 ; i++) {
            if (i == noComputerPlayer) {
                players[i] = new Player(fractions[i],new PlayerIntellect());
            }else
                players[i] = new Player(fractions[i],new ComputerIntellect());
        }
    }

    public void loadPlayer(Player player){
        scM.loadUnitMap(player,m,am);
        scM.loadCityMap(player,m,txM.cityTextureEarly,am); // в этом методе в зависимости от развития игрока можно менять текстуру города
    }




    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startEventMoveX = (int)event.getX();
                startEventMoveY = (int)event.getY();
                startEventX = (int)event.getX();
                startEventY = (int)event.getY();
                if (startEventX > screenWidth-nextTurnButton.size && startEventY > screenHeight-nextTurnButton.size )
                    nextTurnButton.isPressed = true;
                else if (finalEventX > saveExitButton.x && finalEventY > saveExitButton.y && finalEventX < saveExitButton.x + saveExitButton.size & finalEventY < saveExitButton.y + saveExitButton.size)
                    saveExitButton.isPressed = true;
                break;
            case MotionEvent.ACTION_MOVE:
                finalEventX = (int)event.getX();
                finalEventY = (int)event.getY();

                onUpdate();

                startEventMoveX = (int)event.getX();
                startEventMoveY = (int)event.getY();
                break;

            case MotionEvent.ACTION_UP:
                finalEventX = (int)event.getX();
                finalEventY = (int)event.getY();
                nextTurnButton.isPressed = false;
                saveExitButton.isPressed = false;
                if (startEventX - finalEventX <= moveMistake && startEventX - finalEventX <= moveMistake){
                    if (finalEventX > nextTurnButton.x && finalEventY > nextTurnButton.y ) {
                        nextTurnButton.makeAction();

                    }else if (finalEventX > saveExitButton.x && finalEventY > saveExitButton.y && finalEventX < saveExitButton.x + saveExitButton.size & finalEventY < saveExitButton.y + saveExitButton.size){
                        saveExitButton.makeAction();

                    }else
                        scM.chooseCell(players,players[noComputerPlayer],m,infoBar,screenWidth,screenHeight,finalEventX,finalEventY);
                    //scale+=2;
                    //scM = new ScreenManager(screenWidth,screenHeight,scale);
                }
                onUpdate();
        }

        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawer.drawVisibleMap(players,canvas,scM,txM,m);
        drawer.drawInfoRectangle(infoBar,canvas);
        drawer.drawResourceBar(resourceBar,canvas);
        drawer.drawNextTurnButton(nextTurnButton,canvas);
        drawer.drawSaveExitButton(saveExitButton,canvas);


    }

    public void onUpdate(){
        int x = 0,y = 0;

        if (finalEventX - startEventMoveX > moveMistake)
            x = -1;
        else if (finalEventX - startEventMoveX < -1*moveMistake)
            x = 1;
        if (finalEventY - startEventMoveY > moveMistake)
            y = -1;
        else if (finalEventY - startEventMoveY < -1*moveMistake)
            y = 1;

        if(( (scM.posXOnGlobalMap + x) >= 0 ) && ( (scM.posXOnGlobalMap + x) <= (m.getMaxX()-scM.vmX)) ) {
            scM.posXOnGlobalMap += x;
        }

        if((scM.posYOnGlobalMap + y) >= 0 && (scM.posYOnGlobalMap + y ) <= (m.getMaxY()-scM.vmY) ) {
            scM.posYOnGlobalMap += y;
        }
        invalidate();
    }



    private void loadFractionsTexturesFromAssets(){
        try {

            InputStream ims ;
            Bitmap b;
            String s;
            Player.Fraction[] fractions = Player.Fraction.values();
            TextureManager.TypeOfFractionTexture[] types = TextureManager.TypeOfFractionTexture.values();

            for (int i = 0; i < fractions.length-1; i++) {
                for (int j = 0; j < types.length; j++) {
                    s = fractions[i].toString().toLowerCase()+"_"+types[j].toString().toLowerCase()+".png";
                    //Log.d("Something  ",s);
                    ims = am.open(s);
                    b = BitmapFactory.decodeStream(ims);
                    s = fractions[i].toString().toLowerCase()+"_"+ types[j].toString().toLowerCase();
                    txM.fractionsTextures.put(s,new Texture(b));


                }
            }



        }
        catch(IOException ex) {
           // Log.d("Something","Wrong");
            return;
        }
    }
    private void loadMapTexturesFromAssets() {
        try {

            InputStream ims ;
            Bitmap b;
            String s;
            Cell.Terrain[] terrain = Cell.Terrain.values();
            Cell.TypeOfCell[] type = Cell.TypeOfCell.values();


            for (int i = 0; i < terrain.length - 2; i++) {
                for (int j = 0; j < type.length; j++) {
                    if (terrain[i] == Cell.Terrain.PEAKS || terrain[i] == Cell.Terrain.WATER)
                        j = type.length - 1;
                    s = terrain[i].toString().toLowerCase()+"_"+type[j].toString().toLowerCase()+".png";
                    Log.d("Something  ",s);
                    ims = am.open(s);
                    b = BitmapFactory.decodeStream(ims);
                    s = terrain[i].toString().toLowerCase()+"_"+type[j].toString().toLowerCase();

                    txM.mapTextures.put(s,new Texture(b));


                }
            }



        }
        catch(IOException ex) {
            Log.d("Something","Wrong");
            return;
        }
    }

    private void loadGameFromDatabase(SQLiteDatabase db){
        Cursor map_cursor = db.query(DBHelper.TABLE_MAP,null,null,null,null,null,null);
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



        do{
            cx = map_cursor.getInt(cellXIndex);
            cy = map_cursor.getInt(cellYIndex);
            terrain = map_cursor.getString(terrainIndex);
            typeOfCell = map_cursor.getString(typeOfCellIndex);
            territoryOf = map_cursor.getString(territoryOfIndex);


            glMap[cy][cx].setTerrain(StringMaster.getTerrainByString(terrain));
            glMap[cy][cx].setTypeOfCell(StringMaster.getTypeOfCellByString(typeOfCell));
            glMap[cy][cx].territoryOf = StringMaster.getFractionByString(territoryOf);

        }while(map_cursor.moveToNext());
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

            units_cursor =  db.query(DBHelper.TABLE_UNITS,null,selection,null,null,null,null);
            units_cursor.moveToFirst();

            unitXIndex = units_cursor.getColumnIndex(DBHelper.KEY_UNIT_X);
            unitYIndex = units_cursor.getColumnIndex(DBHelper.KEY_UNIT_Y);
            typeOfUnitIndex = units_cursor.getColumnIndex(DBHelper.KEY_TYPE_OF_UNIT);
            fractionIndex = units_cursor.getColumnIndex(DBHelper.KEY_FRACTION);
            unitHPIndex = units_cursor.getColumnIndex(DBHelper.KEY_UNIT_HP);
            unitsStepsIndex =  units_cursor.getColumnIndex(DBHelper.KEY_UNIT_STEPS);

            do {
                unitX = units_cursor.getInt(unitXIndex);
                unitY = units_cursor.getInt(unitYIndex);
                typeOfUnit = units_cursor.getString(typeOfUnitIndex);
                fraction = units_cursor.getString(fractionIndex);
                unitHP = units_cursor.getInt(unitHPIndex);
                unitSteps = units_cursor.getInt(unitsStepsIndex);

                String unitKey;
                unitKey = unitX + " " + unitY;

                Unit bufferUnit;
                switch(StringMaster.getTypeOfUnitByString(typeOfUnit)) {
                    case ARMORED_VEHICLE:
                        players[i].units.put(unitKey,new ArmoredVehicle(StringMaster.getFractionByString(fraction),unitX,unitY));
                        bufferUnit = players[i].units.get(unitKey);
                        bufferUnit.unitHP = unitHP;
                        bufferUnit.unitHP = unitSteps;
                        GameLogic.setUnitAttack(bufferUnit);
                        GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                        break;
                    case SPEARMENS:
                        players[i].units.put(unitKey,new Spearmens(StringMaster.getFractionByString(fraction),unitX,unitY));
                        bufferUnit = players[i].units.get(unitKey);
                        bufferUnit.unitHP = unitHP;
                        bufferUnit.unitHP = unitSteps;
                        GameLogic.setUnitAttack(bufferUnit);
                        GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                        break;
                    case CAMEL_WARRIOR:
                        players[i].units.put(unitKey,new CamelWarrior(StringMaster.getFractionByString(fraction),unitX,unitY));
                        bufferUnit = players[i].units.get(unitKey);
                        bufferUnit.unitHP = unitHP;
                        bufferUnit.unitHP = unitSteps;
                        GameLogic.setUnitAttack(bufferUnit);
                        GameLogic.setUnitDefense(bufferUnit, glMap[unitY][unitX].cellCoeff);
                        break;
                }
            }while(units_cursor.moveToNext());
            units_cursor.close();
            db.close();
        }
    }


    private void loadTextures(){
        txM.infoBarTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.info_bar));

        txM.cityTextureEarly = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.city_early));
        txM.cityTextureLate = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.city_late));

        txM.moveOpportunityMarker = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.move_opportunity));
        txM.attackOpportunityMarker = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.attack_opportunity));

        txM.resourceBarTexture = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.resource_bar));




        txM.populationScoreIcon = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.population_score));
        txM.powerScoreIcon = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.power_score));
        txM.eatScoreIcon =  new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.eat));
        txM.happinessScoreIcon = new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.happiness_score));

        loadMapTexturesFromAssets();
        loadFractionsTexturesFromAssets();

        txM.unitTextures.put("armored_vehicle",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.armored)));
        txM.unitTextures.put("camel_warrior",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.camel_warrior)));
        txM.unitTextures.put("spearmens",new Texture(BitmapFactory.decodeResource(getResources(),R.drawable.spearmens)));
    }

    class MyButton{
        boolean isPressed;

        int x,y;
        int size;
        Texture pressedButtonTexture;
        Texture buttonTexture;

        MyButton(int i) {
            size = (int) (1.0 / i * screenWidth);

        }

        public void makeAction(){}

    }
    class NextTurnButton extends MyButton {

        NextTurnButton(int i) {
            super(i);
            x = screenWidth - size;
            y = screenHeight - size;
            pressedButtonTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.next_turn_dark));
            pressedButtonTexture.resizeTexture(size, size);

            buttonTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.next_turn));
            buttonTexture.resizeTexture(size, size);
        }

        @Override
        public  void makeAction(){
            GameLogic.nextTurn(players,m);
        }
    }
    class SaveExitButton  extends MyButton{
        SaveExitButton(int i) {
            super(i);
            x = resourceBar.padding;
            y = screenHeight - infoBar.height - size;

            pressedButtonTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.save_and_exit_pressed));
            pressedButtonTexture.resizeTexture(size, size);

            buttonTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.save_and_exit));
            buttonTexture.resizeTexture(size, size);
        }
        @Override
        public  void makeAction(){

        }
    }



}

