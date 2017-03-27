package com.example.denis.gamestrategy;

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
        switch(t){
            case WATER:
                return mapTextures.get("water");
            case SAVANNAH:
                if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("savannah");
                return getTextureByType("savannah",type);

            case HILLS:
                if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("hills");
                return getTextureByType("hills",type);
            case PEAKS:
                if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("peaks");
                return getTextureByType("peaks",type);
            case DESERT:
                if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("desert");
                return getTextureByType("desert",type);
            default:
                {
                    if (type ==  Cell.TypeOfCell.DEFAULT)
                        return mapTextures.get("jungle");
                    return getTextureByType("jungle", type);
            }
        }
    }


    private Texture getTextureByType(String terrain , Cell.TypeOfCell type){

       switch (type){
           case ISLAND:
               return mapTextures.get(terrain + "_island");

                                                                                         //DIAG_L_T_C, DIAG_R_T_C, DIAG_R_D_C, DIAG_L_D_C,
                                                                                          // COAST_T, COAST_D, COAST_L, COAST_R,
                                                                                          //BAY_T, BAY_D, BAY_L, BAY_R,
                                                                                            //PENINSULA_T,PENINSULA_D,PENINSULA_L,PENINSULA_R,
                                                                                               // SMALL_DIAG_L_T_C,SMALL_DIAG_L_T_C_Re, SMALL_DIAG_R_T_C,SMALL_DIAG_R_T_C_Re, SMALL_DIAG_R_D_C,SMALL_DIAG_R_D_C_Re, SMALL_DIAG_L_D_C, SMALL_DIAG_L_D_C_Re,
                                                                                              //SMALL_DIAG_REVERSE_L_T_C,SMALL_DIAG_REVERSE_L_T_C_Re, SMALL_DIAG_REVERSE_R_T_C,SMALL_DIAG_REVERSE_R_T_C_Re, SMALL_DIAG_REVERSE_R_D_C, SMALL_DIAG_REVERSE_R_D_C_Re, SMALL_DIAG_REVERSE_L_D_C, SMALL_DIAG_REVERSE_L_D_C_Re,
                                                                                           //CORNER_L_T_C, CORNER_R_T_C, CORNER_R_D_C, CORNER_L_D_C,
                                                                                           //CORNER_REVERSE_L_T_C, CORNER_REVERSE_R_T_C, CORNER_REVERSE_R_D_C, CORNER_REVERSE_L_D_C,
                                                                                           // ISLAND, DEFAULT
       }
      return mapTextures.get("peaks");
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
