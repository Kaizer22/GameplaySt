package com.example.denis.gamestrategy.Gameplay;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.denis.gamestrategy.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by denis on 19.02.17.
 */

public class GameView extends View{

    final AssetManager am;
    int screenWidth ;
    int screenHeight ;
    final  int moveMistake = 30;
    int mapSize = 64;
    int scale = 16;
    Drawer drawer;
    GlobalMap m ;
    ScreenManager scM;
    TextureManager txM ;
    int startEventMoveX = 0, startEventMoveY = 0,finalEventX = 0,finalEventY = 0, startEventX = 0, startEventY = 0;


    public InfoBar infoBar;
    public ResourceBar resourceBar;
    public NextTurnButton nextTurnButton;

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

    public GameView(Context context,int nCP) {
        super(context);
        noComputerPlayer = nCP;
        am = context.getAssets();
    }



    public void prepareGameView(){

        txM = new TextureManager();
        loadTextures();
        //computerIntellect2 = new ComputerIntellect();
        //computerIntellect3 = new ComputerIntellect();
       // playerIntellect1 = new PlayerIntellect();

       // player1 = new Player(Player.Fraction.BERBER,computerIntellect2);
        //player2 = new Player(Player.Fraction.REBELS,playerIntellect1);
        //player3 = new Player(Player.Fraction.ADVANSED_NATIONS,computerIntellect3);

        scM = new ScreenManager(screenWidth,screenHeight,scale);

        txM.resizeTextures(scM);

        drawer = new Drawer();
        m = new GlobalMap();
         //loadMap(am, mapTextures); при повторном запуске

        //createPlayers(); //loadPlayers(); при повторном запуске
        //loadPlayer(player1);
        //loadPlayer(player2);
        //loadPlayer(player3);
        createPlayers();

        m.generateMap(am,mapSize,players,txM.cityTextureEarly);

        infoBar = new InfoBar(screenWidth,screenHeight,scM,txM.infoBarTexture);
        resourceBar = new ResourceBar(screenWidth,scM,txM.resourceBarTexture,txM.eatScoreIcon,txM.populationScoreIcon,txM.powerScoreIcon,txM.happinessScoreIcon);
        nextTurnButton= new NextTurnButton();
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
                if (startEventX - finalEventX <= moveMistake && startEventX - finalEventX <= moveMistake){
                    if (finalEventX > 5.0/6*screenWidth && finalEventY > screenHeight-1.0/6*screenWidth ) {

                        nextTurnButton.makeAction();

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

        MyButton() {
            size = (int) (1.0 / 6 * screenWidth);
            x = screenWidth - size;
            y = screenHeight - size;
        }

        public void makeAction(){}

    }

    class NextTurnButton extends MyButton {

        NextTurnButton() {
            pressedButtonTexture = new Texture(BitmapFactory.decodeResource(getResources(), R.drawable.next_turn_dark));
            pressedButtonTexture.resizeTexture(size, size);

            buttonTexture = new

                    Texture(BitmapFactory.decodeResource(getResources(), R.drawable.next_turn));
            buttonTexture.resizeTexture(size, size);
        }

        @Override
        public  void makeAction(){
            GameLogic.nextTurn(players,m);
        }
    }
}

