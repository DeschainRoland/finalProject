package com.example.gunslinger;
import android.app.Dialog;
import android.content.Context;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

import android.util.Log;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.widget.Button;

import android.widget.Toast;

import java.util.ArrayList;

public class GameMap extends SurfaceView implements SurfaceHolder.Callback, View.OnLongClickListener{
    Global global;
    //Переменные для рисования
    float x1, y1, //текущее положение картинки
    // x2, y2, //смещение координат
    touchX, touchY; //точки касания
    int levelPath;
    Resources res;
    DrawMap drawMap;
    DrawThread drawThread;
    Roland roland;
    Gates gates;
    Dialog dialog;
    Button restart,goMainM;
    ArrayList<Spike> listOfSpikes = new ArrayList<>();
    ArrayList<Crate> listOfCrates = new ArrayList<>();
    ArrayList<Lever> listOfLevers = new ArrayList<>();
    ArrayList<Key> listOfKeys = new ArrayList<>();


    public GameMap(Context context, int levelPath) {
        super(context);
        global = new Global();
        getHolder().addCallback(this);
        this.levelPath = levelPath;
        res = getResources();
        drawMap = new DrawMap(res,levelPath);
        createObjects();
        setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (touchX<roland.x) roland.currentImage = roland.imageMap.get("Right");
                else roland.currentImage = roland.imageMap.get("Left");
                if (!roland.isJumping)
                    roland.setTargetX(touchX);

                for (Lever lever:listOfLevers) {
                    if (lever.hitbox.contains((int)touchX, (int)touchY)&&lever.isClickable) {
                        lever.changeOnOff();
                        if (lever.isOn) {
                            gates.isOpened = true;
                        }
                        else gates.isOpened = false;
                    }
                }
            }

            @Override
            public void onDoubleClick(View view) {
                Log.d("DJUMP", "D JUMP");
                if (!roland.isFalling()){
                    roland.isJumping = true;
                    roland.jumpingCounter = 0;
                }
            }

        }));
    }
    public void createObjects(){
        for (CoordClass coordItem: drawMap.coordList) {
            switch (coordItem.className){
                case "Spike": listOfSpikes.add(new Spike(this,res,coordItem.xyCoords.first*48,coordItem.xyCoords.second*48));break;
                case "Crate":listOfCrates.add(new Crate(this,res,coordItem.xyCoords.first*48,coordItem.xyCoords.second*48)); break;
                case "Lever":listOfLevers.add(new Lever(this,res,coordItem.xyCoords.first*48,coordItem.xyCoords.second*48));break;
                case "Key":listOfKeys.add(new Key(this,res,coordItem.xyCoords.first*48,coordItem.xyCoords.second*48));break;
                case"Gates": gates = new Gates(this,res,coordItem.xyCoords.first*48,coordItem.xyCoords.second*48);break;
                case"Roland": roland = new Roland(this,res,coordItem.xyCoords.first*48,coordItem.xyCoords.second*48+6);break;
            }
        }
    }
   @Override
    public boolean onTouchEvent(MotionEvent event){

        //todo add gestureListener, longClickListener
        touchX = event.getX();
        touchY = event.getY();
       switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                /*if (touchX<roland.x) roland.currentImage = roland.imageMap.get("Right");
              else roland.currentImage = roland.imageMap.get("Left");
                if (!roland.isJumping)
                    roland.setTargetX(touchX);

         for (Lever lever:listOfLevers) {
             if (lever.hitbox.contains((int)touchX, (int)touchY)&&lever.isClickable) {
                 lever.changeOnOff();
                 if (lever.isOn) {
                     gates.isOpened = true;
                 }
                 else gates.isOpened = false;
             }
         }*/
            break;

           // TODO: 27.05.2022 переделать прыжок
            //case MotionEvent.ACTION_UP:
            //    if (!roland.isFalling()){
            //        roland.isJumping = true;
            //        roland.jumpingCounter = 0;
            //    }
            //    break;
        }

        return super.onTouchEvent(event);
    }




    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        drawMap.draw(canvas);

        for (Spike spike: listOfSpikes) {
            spike.draw(canvas);
            if (Rect.intersects(spike.hitbox,roland.hBForSpikes)){
                roland.currentImage = roland.imageMap.get("Dead");
                roland.isDead = true;
                roland.movingVelocity = 0;
            }

        }

        for (Lever lever:listOfLevers) {
            lever.draw(canvas);
        }

        for (Key key:listOfKeys) {
            if (roland.isCollision(key.x,key.x+key.width,key.y+key.height))
                key.hasTaken = true;

            key.draw(canvas);
        }

        for (Crate crate:listOfCrates){
            if (crate.isCollision(roland.x,roland.x+roland.width,roland.y+roland.height/2)){
                roland.movingVelocity = crate.movingVelocity;
            }
            else roland.movingVelocity = 16;
            roland.checkCrateVerCol(crate);
            crate.draw(canvas);
        }

        for (Key k :listOfKeys) {
            if (!listOfKeys.isEmpty()){
            if  (k.hasTaken)
                gates.isOpened = true;
            }
            else gates.isOpened = false;
            gates.draw(canvas);
        }

        if (!gates.isOpened && Rect.intersects(roland.hitbox, gates.hitbox)){

            Log.d(VIEW_LOG_TAG, "!gates.isOpened");
            touchX = roland.x-6;
            roland.targetX = (int)touchX;
            roland.moveX();

        }

        roland.draw(canvas);

        if (roland.isDead){

            Log.d(VIEW_LOG_TAG, "Dead");
            listOfCrates.clear();
            listOfKeys.clear();
            listOfLevers.clear();
            listOfSpikes.clear();
            drawMap = new DrawMap(res,levelPath);
            createObjects();
        }

        if (gates.isOpened && Rect.intersects(roland.hitbox, gates.hitbox)){
            //Log.d(VIEW_LOG_TAG, "Near gates");
            switch (global.getLevelNumber()){
                case 1:
                    listOfCrates.clear();
                    listOfKeys.clear();
                    listOfLevers.clear();
                    listOfSpikes.clear();
                    drawMap = new DrawMap(res, R.raw.level_two);
                    createObjects();
                break;
            }
            global.setLevelNumber(global.getLevelNumber() + 1);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(this, getHolder());
        drawThread.setRun(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean stop = true;
        drawThread.setRun(false);
        while(stop) {
            try {
                drawThread.join();
                stop = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getContext(),"long", Toast.LENGTH_SHORT).show();
        return true;
    }


}