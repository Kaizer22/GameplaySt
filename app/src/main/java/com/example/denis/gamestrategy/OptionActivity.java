package com.example.denis.gamestrategy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OptionActivity extends AppCompatActivity {
    public boolean currentMusic = true;
    public boolean currentSounds = true;
    public boolean currentTips = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);


    }

    public void music(View view){
        ImageButton musicButton = (ImageButton) findViewById(R.id.button_music);
        if(currentMusic) {
            musicButton.setImageResource(R.drawable.music_off);
            currentMusic = false;
        }
        else {
            musicButton.setImageResource(R.drawable.music_on);
            currentMusic = true;
        }

    }
    public void sounds(View view){
        ImageButton soundsButton = (ImageButton) findViewById(R.id.button_sounds);
        if(currentSounds) {
            soundsButton.setImageResource(R.drawable.sounds_off);
            currentSounds = false;
        }
        else {
            soundsButton.setImageResource(R.drawable.sounds_on);
            currentSounds = true;
        }

    }
    public void tips(View view){
        ImageButton tipsButton = (ImageButton) findViewById(R.id.button_tips);
        if(currentTips) {
            tipsButton.setImageResource(R.drawable.tips_off);
            currentTips = false;
        }
        else {
            tipsButton.setImageResource(R.drawable.tips_on);
            currentTips = true;
        }
    }

    public void backfromOptions(View view){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
        finish();

    }

}
