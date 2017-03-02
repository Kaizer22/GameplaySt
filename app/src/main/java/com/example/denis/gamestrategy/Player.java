package com.example.denis.gamestrategy;

import java.util.ArrayList;

/**
 * Created by denis on 02.03.17.
 */

public class Player {
    ArrayList<Unit> units = new ArrayList<Unit>();
    Texture fraction;

    public Player(Texture f){
        fraction = f;
    }
}
