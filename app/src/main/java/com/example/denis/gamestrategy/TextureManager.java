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

    public Map<String, Texture>  fractionsTextures;
    //public Map<String, Texture>  fractionsForCity;
    //public Map<String, Texture>  fractionForG

    public Texture cityTextureEarly;
    public Texture cityTextureLate;

    public Texture infoBarTexture;
    public Texture resourceBarTexture;


    public Texture eatScoreIcon;
    public Texture populationScoreIcon;
    public Texture powerScoreIcon;
    public Texture happinessScoreIcon;


    public Texture moveOpportunityMarker;
    public Texture attackOpportunityMarker;

    public TextureManager(){
        mapTextures = new HashMap<>();
        mapHalfTextures = new HashMap<>();
        unitTextures = new HashMap<>();
        fractionsTextures = new HashMap<>();
    }


    public void resizeTextures(ScreenManager scM){
        resizeTextureArray(mapTextures,scM);
        resizeTextureArray(unitTextures,scM);
        resizeTextureArray(fractionsTextures,scM);
        //fractionGround_test.resizeTexture(scM.cellWidth,scM.cellHeight);
        //fractionUnit_test.resizeTexture(scM.cellWidth,scM.cellHeight);
        moveOpportunityMarker.resizeTexture(scM.cellWidth, scM.cellHeight);
        attackOpportunityMarker.resizeTexture(scM.cellWidth, scM.cellHeight);

    }

    public void resizeTextureArray(Map <String,Texture> map, ScreenManager scM){
        for (Texture t:map.values()) {
            t.resizeTexture(scM.cellWidth,scM.cellHeight);
        }
    }

    public Texture getTextureByFraction(Player.Fraction f, TypeOfFractionTexture t){
        return fractionsTextures.get(f.toString().toLowerCase()+"_"+t.toString().toLowerCase());
    }

    public Texture getMapTextureByTerrainAndType(Cell.Terrain t, Cell.TypeOfCell type){
        String terrain = t.toString().toLowerCase();
        return mapTextures.get(terrain + "_"+ type.toString().toLowerCase());
    }



    public Texture getUnitTextureByType(Unit.TypeOfUnit t){
          return unitTextures.get(t.toString().toLowerCase());
    }

    public enum TypeOfFractionTexture{
        UNIT,CITY,TERRITORY
    }
}
