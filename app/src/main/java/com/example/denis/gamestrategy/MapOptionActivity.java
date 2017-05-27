package com.example.denis.gamestrategy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.example.denis.gamestrategy.Gameplay.GameplayActivity;

public class MapOptionActivity extends AppCompatActivity {
    boolean isSizeChecked;
    boolean isPatternChecked;
    int checkedPattern;
    int checkedSize;
    int choosenTribe;
    boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_option);

        Intent i = getIntent();
        isNewGame = i.getBooleanExtra("isNewGame",false);
        choosenTribe =  i.getIntExtra("choosenTribe",0);
        final ImageButton startGame  = (ImageButton)findViewById(R.id.button_continue2);

        RadioGroup sizeRadioGroup = (RadioGroup) findViewById(R.id.sizeRadioGroup);

        sizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        break;
                    case R.id.button32:
                        checkedSize = 32;
                        isSizeChecked = true;
                        if (isSizeChecked && isPatternChecked)
                            startGame.setVisibility(View.VISIBLE);
                        break;
                    case R.id.button64:
                        checkedSize = 64;
                        isSizeChecked = true;
                        if (isSizeChecked && isPatternChecked)
                            startGame.setVisibility(View.VISIBLE);
                        break;
                    case R.id.button128:
                        checkedSize = 128;
                        isSizeChecked= true;
                        if (isSizeChecked && isPatternChecked)
                            startGame.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        RadioGroup patternRadioGroup = (RadioGroup) findViewById(R.id.patternRadioGroup);

        patternRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        break;
                    case R.id.buttonAfricanZones:
                        checkedPattern = 1;
                        isPatternChecked = true;
                        if (isSizeChecked && isPatternChecked)
                            startGame.setVisibility(View.VISIBLE);
                        break;
                    case R.id.buttonNucleus:
                        checkedPattern = 2;
                        isPatternChecked = true;
                        if (isSizeChecked && isPatternChecked)
                            startGame.setVisibility(View.VISIBLE);
                        break;
                    case R.id.buttonIslands:
                        checkedPattern = 3;
                        isPatternChecked = true;
                        if (isSizeChecked && isPatternChecked)
                            startGame.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    public void startNewGame(View view){
        Intent gameplayActivity = new Intent(this, GameplayActivity.class);
        gameplayActivity.putExtra("choosenTribe",choosenTribe);
        gameplayActivity.putExtra("isNewGame",true);
        gameplayActivity.putExtra("choosenSize",checkedSize);
        gameplayActivity.putExtra("choosenPattern",checkedPattern);
        startActivity(gameplayActivity);
        finish();
    }
    public void toChooseActivity(View view){
        Intent chooseActivity = new Intent(this,ChooseActivity.class);
        startActivity(chooseActivity);
        finish();
    }
}
