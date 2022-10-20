package com.example.gunslinger;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;


public class MainActivity extends Activity {

    AudioPlayer audioPlayer;
    GameMap gameMap;
    int recievePath;
    private long buttonPressedTime;
    private Toast toastToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recievePath = getIntent().getIntExtra("LEVEL_NUM", -1);
        gameMap = new GameMap(this,recievePath);
        setContentView(gameMap);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        audioPlayer = new AudioPlayer(this, 2);
        audioPlayer.play();

    }

    @Override
    public void onBackPressed() {
        if (buttonPressedTime + 2000 > System.currentTimeMillis()){
            toastToExit.cancel();
            Intent intent = new Intent(MainActivity.this, LevelListActivity.class);
            startActivity(intent);

        } else {
            toastToExit = Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выбрать уровень.", Toast.LENGTH_SHORT);
            toastToExit.show();
        }
        buttonPressedTime = System.currentTimeMillis();
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