package com.example.denis.gamestrategy.Gameplay;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.denis.gamestrategy.R;

/**
 * Created by denis on 30.05.17.
 */

public class DecideDialog extends DialogFragment {
    Player player;
    Player attackedPlayer;
    GlobalMap globalMap;
    int attackedCityPosX;
    int attackedCityPosY;

    boolean isSomethigDecided;

    int screenHight;
    int screenWeight;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, 0);
        return super.onCreateDialog(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.decide_dialog, null);
        getDialog().getWindow().setDimAmount(0.8f);

        final Cell[][] glM = globalMap.getMap();
        Button destroy = (Button)v.findViewById(R.id.button_destroy);
        destroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameLogic.deleteCityTerritory(globalMap,attackedPlayer.cities.get(attackedCityPosY+"_"+attackedCityPosX));
                glM[attackedCityPosY][attackedCityPosX].cityOn = false;
                attackedPlayer.cities.remove(attackedCityPosY+"_"+attackedCityPosX);
                isSomethigDecided = true;
                dismiss();

            }
        });
        Button capture = (Button)v.findViewById(R.id.button_capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City bufCity = attackedPlayer.cities.get(attackedCityPosY+"_"+attackedCityPosX);
                GameLogic.changeTerritoryFraction(globalMap,bufCity,player.fr);
                bufCity.fraction = player.fr;
                attackedPlayer.cities.remove(attackedCityPosY+"_"+attackedCityPosX);
                bufCity.cityHP = 5;
                player.cities.put(attackedCityPosY+"_"+attackedCityPosX,bufCity);
                isSomethigDecided = true;
                dismiss();
            }
        });
                return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setLayout((int)(screenHight*0.7),(int)(screenWeight*0.5));
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onDestroy() {
        if (!isSomethigDecided){
            Cell[][] glM = globalMap.getMap();
            GameLogic.deleteCityTerritory(globalMap,attackedPlayer.cities.get(attackedCityPosY+"_"+attackedCityPosX));
            glM[attackedCityPosY][attackedCityPosX].cityOn = false;
            attackedPlayer.cities.remove(attackedCityPosY+"_"+attackedCityPosX);
            isSomethigDecided = true;
        }
        super.onDestroy();
    }

    public void setParam(Player pl, Player aPlayer, GlobalMap glMap, int aCPX, int aCPY, int scH, int scW){

        player = pl;
        attackedPlayer = aPlayer;
        globalMap = glMap;
        screenHight = scH;
        screenWeight = scW;
        attackedCityPosX = aCPX;
        attackedCityPosY = aCPY;
    }
}