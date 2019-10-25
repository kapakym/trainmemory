package com.pedan.trainermemory;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button ButtonStart = findViewById(R.id.button3);
        Button ButtonExit = findViewById(R.id.button2);
       ButtonStart.setOnClickListener(this);
       ButtonExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3 : {
                //setContentView(R.layout.myscreen);
                Intent intent = new Intent(this, Game_screen.class);
                startActivity(intent);
                final MediaPlayer mp1 = MediaPlayer.create(this, R.raw.click);
                mp1.start();




                break;}
            case R.id.button2 : {
                final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.click);
                mp2.start();

                finish(); System.exit(0); break;}

        }
    }
}
