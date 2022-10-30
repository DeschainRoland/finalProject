package com.example.gunslinger;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;

public class LevelListActivity extends Activity {

    ImageButton btnToMainMenu, btnLevelFirst, btnLevelSecond, btnToComics;
    AudioPlayer audioPlayer;
    Global global;

    int putPath;
    Intent toMMIntent, chooseLevelIntent, toComicsIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnToComics = findViewById(R.id.b_level_zero);
        btnToMainMenu = findViewById(R.id.buttonToMainMenu);
        btnLevelFirst = findViewById(R.id.b_level_one);
        btnLevelSecond = findViewById(R.id.b_level_two);

        global = new Global();

        toMMIntent = new Intent(LevelListActivity.this, MainMenu.class);
        chooseLevelIntent = new Intent(LevelListActivity.this, MainActivity.class);
        toComicsIntent = new Intent(LevelListActivity.this, Comics.class);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.buttonToMainMenu:
                        startActivity(toMMIntent);
                        audioPlayer.stop();
                        break;

                    case R.id.b_level_zero:
                        startActivity(toComicsIntent);
                        finish();
                        audioPlayer.stop();
                        break;

                    case R.id.b_level_one:
                        global.setLevelNumber(1);
                        putPath = R.raw.level_one;
                        chooseLevelIntent.putExtra("LEVEL_NUM",putPath); //ща ошибка будет
                        startActivity(chooseLevelIntent); //finish();
                        audioPlayer.stop();
                        break;

                    case R.id.b_level_two:
                        global.setLevelNumber(2);
                        putPath = R.raw.level_two;
                        chooseLevelIntent.putExtra("LEVEL_NUM",putPath);
                        startActivity(chooseLevelIntent); //finish();
                        audioPlayer.stop();
                        break;
                }

            }

        };

        btnToComics.setOnClickListener(onClickListener);
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