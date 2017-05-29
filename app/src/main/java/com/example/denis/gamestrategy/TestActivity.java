package com.example.denis.gamestrategy;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {
    private Button buttonClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        buttonClick = (Button) findViewById(R.id.buttonClick);

        // add listener to button
        buttonClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(TestActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.city_dialog);
                // Set dialog title
                dialog.setTitle("Tiutiuakan");


                dialog.show();




            }

        });

    }

}


