package com.example.gunslinger;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    GameMap gameMap;
    SurfaceHolder surfaceHolder;
    boolean isRun = false;
    long nowTime, prevTime, ellapsedTime;

    public DrawThread(GameMap gameMap, SurfaceHolder surfaceHolder) {
        this.gameMap = gameMap;
        this.surfaceHolder = surfaceHolder;
        prevTime = System.currentTimeMillis();

        Log.d("COORDS", "DRAW started!");
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (isRun){
            if (!surfaceHolder.getSurface().isValid()){
                continue;
            }
            canvas = null;
            nowTime = System.currentTimeMillis();
            ellapsedTime = nowTime - prevTime;
            if (ellapsedTime > 17){
                prevTime = nowTime;
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder){
                    gameMap.draw(canvas);
                }
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
