package com.example.denis.gamestrategy;

/**
 * Created by denis on 04.04.17.
 */

public class ResourceBar {
    private Texture resourceBarTexture;


    private Texture eatScoreIcon;
    private Texture populationScoreIcon;
    private Texture powerScoreIcon;
    private Texture happinessScoreIcon;

    public int eatScore = 9999;
    public int populationScore = 9999;
    public int powerScore = 9999;
    public int happinessScore = 9999;

    public int width,height;
    public int padding;
    int iconSize;
    public int x = 0,y = 0;
    public  int textSize;

    public ResourceBar(int screenWidth,  ScreenManager scM, Texture rb,Texture esi,Texture psi,Texture posi,Texture hsi){
        width = screenWidth;
        height = scM.cellHeight;
        textSize = height / 4 * 3;
        padding = width/30;
        resourceBarTexture = rb;
        resourceBarTexture.resizeTexture(width,height);
        iconSize = (int)(4.0/6*height);
        eatScoreIcon = esi;
        eatScoreIcon.resizeTexture(iconSize,iconSize);
        populationScoreIcon = psi;
        populationScoreIcon.resizeTexture(iconSize,iconSize);
        powerScoreIcon = posi;
        powerScoreIcon.resizeTexture(iconSize,iconSize);
        happinessScoreIcon = hsi;
        happinessScoreIcon.resizeTexture(iconSize,iconSize);

    }

    public Texture getResourceBarTexture(){

        return resourceBarTexture;}

    public Texture getEatScoreIcon() {
        return eatScoreIcon;
    }

    public Texture getHappinessScoreIcon() {
        return happinessScoreIcon;
    }

    public Texture getPopulationScoreIcon() {
        return populationScoreIcon;
    }

    public Texture getPowerScoreIcon() {
        return powerScoreIcon;
    }

}

