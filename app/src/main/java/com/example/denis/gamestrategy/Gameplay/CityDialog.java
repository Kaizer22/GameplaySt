package com.example.denis.gamestrategy.Gameplay;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.denis.gamestrategy.Gameplay.Units.ArmoredVehicle;
import com.example.denis.gamestrategy.Gameplay.Units.CamelWarrior;
import com.example.denis.gamestrategy.Gameplay.Units.SmallBombard;
import com.example.denis.gamestrategy.Gameplay.Units.Spearmens;
import com.example.denis.gamestrategy.R;

/**
 * Created by denis on 29.05.17.
 */

public class CityDialog extends android.support.v4.app.DialogFragment {



    Player player;
    Cell[][] globalMap;
    City city;

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
        View v = inflater.inflate(R.layout.city_dialog, null);
        getDialog().getWindow().setDimAmount(0.6f);
        Button cdb = (Button) v.findViewById(R.id.city_dialog_back);
        cdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final ImageView mbg = (ImageView) v.findViewById(R.id.image_view_mn_box_g);
        final ImageView mbr = (ImageView) v.findViewById(R.id.image_view_mn_box_r);

        Button cav = (Button) v.findViewById(R.id.button_create_armored_vehicle);
        cav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbg.setImageResource(R.drawable.armored);
                if (!globalMap[city.posY][city.posX].unitOn){
                    player.units.put(city.posY+"_"+city.posX,new ArmoredVehicle(player.fr,city.posY,city.posX));
                    globalMap[city.posY][city.posX].unitOn = true;

                }else
                    Toast.makeText(v.getContext(),"Клетка занята",Toast.LENGTH_LONG).show();
            }
        });


        Button ccw = (Button) v.findViewById(R.id.button_create_camel_warrior);
        ccw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbr.setImageResource(R.drawable.camel_warrior);
                if (!globalMap[city.posY][city.posX].unitOn){
                    player.units.put(city.posY+"_"+city.posX,new CamelWarrior(player.fr,city.posY,city.posX));
                    globalMap[city.posY][city.posX].unitOn = true;
                }else
                    Toast.makeText(v.getContext(),"Клетка занята",Toast.LENGTH_LONG).show();
            }
        });

        Button csb = (Button) v.findViewById(R.id.button_create_small_bombard);
        csb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbr.setImageResource(R.drawable.small_bombard);
                if (!globalMap[city.posY][city.posX].unitOn){
                    player.units.put(city.posY+"_"+city.posX,new SmallBombard(player.fr,city.posY,city.posX));
                    globalMap[city.posY][city.posX].unitOn = true;
                }else
                    Toast.makeText(v.getContext(),"Клетка занята",Toast.LENGTH_LONG).show();
            }
        });

        Button cs = (Button) v.findViewById(R.id.button_create_spearmans);
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbr.setImageResource(R.drawable.spearmens);
                if (!globalMap[city.posY][city.posX].unitOn){
                    player.units.put(city.posY+"_"+city.posX,new Spearmens(player.fr,city.posY,city.posX));
                    globalMap[city.posY][city.posX].unitOn = true;
                }else
                    Toast.makeText(v.getContext(),"Клетка занята",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setLayout((int)(screenHight*0.8),(int)(screenWeight*0.9));
        window.setGravity(Gravity.CENTER);
    }
        public void setParam(Player pl, Cell[][] glMap, City c, int scH, int scW){
            player = pl;
            globalMap = glMap;
            city = c;
            screenHight = scH;
            screenWeight = scW;
        }

}
