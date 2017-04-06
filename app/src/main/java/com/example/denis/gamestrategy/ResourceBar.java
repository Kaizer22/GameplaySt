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

    public int eatScore = 0;
    public int populationScore = 0;
    public int powerScore = 0;
    public int happinessScore = 0;

    public int width,height;
    public int x = 0,y = 0;
    public  int textSize;

    public ResourceBar(int screenWidth,  ScreenManager scM, Texture rb,Texture esi,Texture psi,Texture posi,Texture hsi){
        width = screenWidth;
        height = scM.cellHeight;
        textSize = height / 4 * 3;
        resourceBarTexture = rb;
        resourceBarTexture.resizeTexture(width,height);
        eatScoreIcon = esi;
        eatScoreIcon.resizeTexture(height,height);
        populationScoreIcon = psi;
        populationScoreIcon.resizeTexture(height,height);
        powerScoreIcon = posi;
        powerScoreIcon.resizeTexture(height,height);
        happinessScoreIcon = hsi;
        happinessScoreIcon.resizeTexture(height,height);

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

