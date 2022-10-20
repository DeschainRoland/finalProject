package com.example.gunslinger;



import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;

public class LevelListActivity extends Activity {

    ImageButton btnToMainMenu, btnLevelFirst,btnLevelSecond;
    AudioPlayer audioPlayer;

    int putPath;
    Intent toMMIntent;
    Intent chooseLevelIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lever_list);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnToMainMenu = findViewById(R.id.buttonToMainMenu);
        btnLevelFirst = findViewById(R.id.b_level_one);
        btnLevelSecond = findViewById(R.id.b_level_two);
        toMMIntent = new Intent(LevelListActivity.this, MainMenu.class);
        chooseLevelIntent = new Intent(LevelListActivity.this, MainActivity.class);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (view.getId()){
                    case R.id.buttonToMainMenu:
                        startActivity(toMMIntent); audioPlayer.stop(); break;
                    case R.id.b_level_one:
                        putPath = R.raw.level_one;
                        chooseLevelIntent.putExtra("LEVEL_NUM",putPath);
                        startActivity(chooseLevelIntent); //finish();
                        audioPlayer.stop();break;
                    case R.id.b_level_two: putPath = R.raw.level_two;
                        chooseLevelIntent.putExtra("LEVEL_NUM",putPath);
                        startActivity(chooseLevelIntent); //finish();
                        audioPlayer.stop();
                    break;
                }

            }

        };

        btnLevelFirst.setOnClickListener(onClickListener);
        btnToMainMenu.setOnClickListener(onClickListener);
        btnLevelSecond.setOnClickListener(onClickListener);


        audioPlayer = new AudioPlayer(this, 1);
        audioPlayer.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        audioPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioPlayer.play();
    }


}