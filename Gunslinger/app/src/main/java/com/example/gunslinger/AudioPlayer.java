package com.example.gunslinger;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer {

    ////////Deadman = для выбора уровней, Searching the answer = для геймплея, The Creed = для менюшки

    private MediaPlayer mediaPlayer;

    public AudioPlayer(Context context, Integer integer){
        switch (integer) {
            case 0: mediaPlayer = MediaPlayer.create(context, R.raw.music_for_mainmenu); break;
            case 1: mediaPlayer = MediaPlayer.create(context, R.raw.music_for_leverlist_light); break;
            case 2: mediaPlayer = MediaPlayer.create(context, R.raw.music_for_gameplay); break;
        }
    }

    public void play(){
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop();
                }
            });
        }
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    public void stop(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void pause(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

}
