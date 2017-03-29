package com.example.denis.gamestrategy;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by denis on 25.03.17.
 */

public class TextureManager {
    public Map <String, Texture> mapTextures;
    public Map <String, Texture> mapHalfTextures;
    public Map <String, Texture> unitTextures;

    public Texture cityTextureEarly;
    public Texture cityTextureLate;

    public Texture infoBarTexture;

    public Texture fractionUnit_test;
    public Texture fractionCity_test; // заменить массивом текстур фракций
    public Texture fractionGround_test;

    public Texture moveOpportunityMarker;

    public TextureManager(){
        mapTextures = new HashMap<>();
        mapHalfTextures = new HashMap<>();
        unitTextures = new HashMap<>();
    }


    public void resizeTextures(ScreenManager scM){
        resizeTextureArray(mapTextures,scM);
        resizeTextureArray(unitTextures,scM);
        fractionGround_test.resizeTexture(scM.cellWidth,scM.cellHeight);
        fractionUnit_test.resizeTexture(scM.cellWidth,scM.cellHeight);
        moveOpportunityMarker.resizeTexture(scM.cellWidth, scM.cellHeight);
    }

    public void resizeTextureArray(Map <String,Texture> map, ScreenManager scM){
        for (Texture t:map.values()) {
            t.resizeTexture(scM.cellWidth,scM.cellHeight);
        }
    }



    public Texture returnMapTexture(Cell.Terrain t, Cell.TypeOfCell type){
        String terrain = t.toString().toLowerCase();
        if (type ==  Cell.TypeOfCell.DEFAULT)
            return mapTextures.get(terrain);
        return mapTextures.get(terrain + "_"+ type.toString().toLowerCase());
    }



    public Texture returnUnitTexture(Unit.TypeOfUnit t){
        switch(t){
            case ARMORED_VEHICLE:
                return unitTextures.get("armored_vehicle");
            default:
                return unitTextures.get("camel_warrior");
        }
    }
}
