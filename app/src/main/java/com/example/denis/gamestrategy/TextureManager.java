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
                //if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("savannah");
               // return getTextureByType("savannah",type);

            case HILLS:
                //if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("hills");
                //return getTextureByType("hills",type);
            case PEAKS:
                //if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("peaks");
                //return getTextureByType("peaks",type);
            case DESERT:
                //if (type ==  Cell.TypeOfCell.DEFAULT)
                    return mapTextures.get("desert");
                //return getTextureByType("desert",type);
            default:
                {
                    //if (type ==  Cell.TypeOfCell.DEFAULT)
                        return mapTextures.get("jungle");
                    //return getTextureByType("jungle", type);
            }
        }
    }


    private Texture getTextureByType(String terrain , Cell.TypeOfCell type){

       switch (type) {
           case ISLAND:
               return mapTextures.get(terrain + "_island");

           case DIAG_L_D_C:
               return mapTextures.get(terrain + "_diag_l_d_c");
           case DIAG_L_T_C:
               return mapTextures.get(terrain + "_diag_l_t_c");
           case DIAG_R_D_C:
               return mapTextures.get(terrain + "_diag_r_d_c");
           case DIAG_R_T_C:
               return mapTextures.get(terrain + "_diag_r_t_c");

           case COAST_D:
               return mapTextures.get(terrain + "_coast_d");
           case COAST_T:
               return mapTextures.get(terrain + "_coast_t");
           case COAST_L:
               return mapTextures.get(terrain + "_coast_l");
           case COAST_R:
               return mapTextures.get(terrain + "_coast_r");

           case BAY_D:
               return mapTextures.get(terrain + "_bay_d");
           case BAY_T:
               return mapTextures.get(terrain + "_bay_t");
           case BAY_L:
               return mapTextures.get(terrain + "_bay_l");
           case BAY_R:
               return mapTextures.get(terrain + "_bay_r");

           case PENINSULA_D:
               return mapTextures.get(terrain + "_peninsula_d");
           case PENINSULA_T:
               return mapTextures.get(terrain + "_peninsula_t");
           case PENINSULA_L:
               return mapTextures.get(terrain + "_peninsula_l");
           case PENINSULA_R:
               return mapTextures.get(terrain + "_peninsula_r");


           case CORNER_L_D_C:
               return mapTextures.get(terrain + "_corner_l_d_c");
           case CORNER_L_T_C:
               return mapTextures.get(terrain + "_corner_l_t_c");
           case CORNER_R_D_C:
               return mapTextures.get(terrain + "_corner_r_d_c");
           case CORNER_R_T_C:
               return mapTextures.get(terrain + "_corner_r_t_c");

           case CORNER_REVERSE_L_D_C:
               return mapTextures.get(terrain + "_corner_reverse_l_d_c");
           case CORNER_REVERSE_L_T_C:
               return mapTextures.get(terrain + "_corner_reverse_l_t_c");
           case CORNER_REVERSE_R_D_C:
               return mapTextures.get(terrain + "_corner_reverse_r_d_c");
           case CORNER_REVERSE_R_T_C:
               return mapTextures.get(terrain + "_corner_reverse_r_t_c");


           case SMALL_DIAG_L_D_C:
               return mapTextures.get(terrain + "_small_diag_l_d_c");
           case SMALL_DIAG_L_D_C_Re:
               return mapTextures.get(terrain + "_smal_diag_l_d_c_re");
           case SMALL_DIAG_L_T_C:
               return mapTextures.get(terrain + "_small_diag_l_t_c");
           case SMALL_DIAG_L_T_C_Re:
               return mapTextures.get(terrain + "_small_diag_l_t_c_re");
           case SMALL_DIAG_R_D_C:
               return mapTextures.get(terrain + "_small_diag_r_d_c");
           case SMALL_DIAG_R_D_C_Re:
               return mapTextures.get(terrain + "_small_diag_r_d_c_re");
           case SMALL_DIAG_R_T_C:
               return mapTextures.get(terrain + "_small_diag_r_t_c");
           case SMALL_DIAG_R_T_C_Re:
               return mapTextures.get(terrain + "_small_diag_r_t_c_re");


           case SMALL_DIAG_REVERSE_L_D_C:
               return mapTextures.get(terrain + "_small_diag_re_l_d_c");
           case SMALL_DIAG_REVERSE_L_D_C_Re:
               return mapTextures.get(terrain + "_small_diag_re_l_d_c_re");
           case SMALL_DIAG_REVERSE_L_T_C:
               return mapTextures.get(terrain + "_small_diag_re_l_t_c");
           case SMALL_DIAG_REVERSE_L_T_C_Re:
               return mapTextures.get(terrain + "_small_diag_re_l_t_c_re");
           case SMALL_DIAG_REVERSE_R_D_C:
               return mapTextures.get(terrain + "_small_diag_re_r_d_c");
           case SMALL_DIAG_REVERSE_R_D_C_Re:
               return mapTextures.get(terrain + "_samll_diag_re_r_d_c_re");
           case SMALL_DIAG_REVERSE_R_T_C:
               return mapTextures.get(terrain + "_small_diag_re_r_t_c");
           case SMALL_DIAG_REVERSE_R_T_C_Re:
               return mapTextures.get(terrain + "_small_diag_re_r_t_c_re");
       }

      return mapTextures.get("island");
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
