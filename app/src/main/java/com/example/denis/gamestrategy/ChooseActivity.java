package com.example.denis.gamestrategy;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denis.gamestrategy.Gameplay.GameplayActivity;
import com.example.denis.gamestrategy.Gameplay.Player;

public class ChooseActivity extends AppCompatActivity {

    public int choosenTribe = 1;
    public Player.Fraction choosenFraction;
    public final int maxTribes = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

    }

    public void backfromChoose(View view){
        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
        finish();

    }

    public void changeScreen(int choosenTribe){
        ImageView tribe = (ImageView) findViewById(R.id.tribe);
        TextView info = (TextView) findViewById(R.id.info);

        switch (choosenTribe){
            case 1:
                info.setText(getString(R.string.tribe_1));
                info.setTextColor(getResources().getColor(R.color.easy));
                choosenFraction = Player.Fraction.ZULU;
                tribe.setImageResource(R.drawable.zulu);
                break;
            case 2:
                info.setText(getString(R.string.tribe_2));
                choosenFraction = Player.Fraction.BUSHMAN;
                tribe.setImageResource(R.drawable.bushman);
                break;
            case 3:
                info.setText(getString(R.string.tribe_3));
                choosenFraction = Player.Fraction.LUBA;
                tribe.setImageResource(R.drawable.luba);
                break;
            case 4:
                info.setText(getString(R.string.tribe_4));
                choosenFraction = Player.Fraction.MASAI;
                tribe.setImageResource(R.drawable.masai);
                break;
            case 5:
                info.setText(getString(R.string.tribe_5));
                info.setTextColor(getResources().getColor(R.color.easy));
                choosenFraction = Player.Fraction.PYGMY;
                tribe.setImageResource(R.drawable.pygmy);
                break;
            case 6:
                info.setText(getString(R.string.tribe_6));
                info.setTextColor(getResources().getColor(R.color.normal));
                choosenFraction = Player.Fraction.NUER;
                tribe.setImageResource(R.drawable.nuer);
                break;
            case 7:
                info.setText(getString(R.string.tribe_7));
                choosenFraction = Player.Fraction.YORUBA;
                tribe.setImageResource(R.drawable.yoruba);
                break;
            case 8:
                info.setText(getString(R.string.tribe_8));
                choosenFraction = Player.Fraction.ASHANTI;
                tribe.setImageResource(R.drawable.ashanti);
                break;
            case 9:
                info.setText(getString(R.string.tribe_9));
                choosenFraction = Player.Fraction.NUBA;
                tribe.setImageResource(R.drawable.nuba);
                break;
            case 10:
                info.setText(getString(R.string.tribe_10));
                choosenFraction = Player.Fraction.HAUSA;
                tribe.setImageResource(R.drawable.hausa);
                break;
            case 11:
                info.setText(getString(R.string.tribe_11));
                info.setTextColor(getResources().getColor(R.color.normal));
                choosenFraction = Player.Fraction.DOGON;
                tribe.setImageResource(R.drawable.dogon);
                break;
            case 12:
                info.setText(getString(R.string.tribe_12));
                info.setTextColor(getResources().getColor(R.color.hard));
                choosenFraction = Player.Fraction.AMHARA;
                tribe.setImageResource(R.drawable.amhara);
                break;
            case 13:
                info.setText(getString(R.string.tribe_13));
                choosenFraction = Player.Fraction.FULBE;
                tribe.setImageResource(R.drawable.fulbe);
                break;
            case 14:
                info.setText(getString(R.string.tribe_14));
                choosenFraction = Player.Fraction.TUAREG;
                tribe.setImageResource(R.drawable.tuareg);
                break;
            case 15:
                info.setText(getString(R.string.tribe_15));
                info.setTextColor(getResources().getColor(R.color.hard));
                choosenFraction = Player.Fraction.BERBER;
                tribe.setImageResource(R.drawable.berber);
                break;
        }

    }

    public void nextTribe(View view){
        choosenTribe++;
        if (choosenTribe > maxTribes)
            choosenTribe = 1;
        changeScreen(choosenTribe);

    }

    public void previousTribe(View view){
        choosenTribe--;
        if (choosenTribe < 1)
            choosenTribe = maxTribes;
        changeScreen(choosenTribe);

    }

    public void toMapOptionsActivity(View view){
        Intent mapOptionsActivity = new Intent(this, MapOptionActivity.class);//GameplayActivity.class);
        mapOptionsActivity.putExtra("choosenTribe",choosenTribe-1);
        mapOptionsActivity.putExtra("isNewGame",true);
        startActivity(mapOptionsActivity);
        finish();
    }
}
