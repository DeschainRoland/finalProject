package com.example.gunslinger;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.HashMap;

public class Lever extends GameObject {

    HashMap<String,Bitmap> imageMap = new HashMap<>();
    Bitmap currentImage;
    boolean isOn = false,
    isClickable = false;

    public Lever(GameMap gameMap, Resources res,int x, int y) {
        super(gameMap,res,x,y);
        imageMap.put("L0",BitmapFactory.decodeResource(res,R.drawable.lever_l0_32) );
        imageMap.put("L1",BitmapFactory.decodeResource(res,R.drawable.lever_l1_32) );
        imageMap.put("R1",BitmapFactory.decodeResource(res, R.drawable.lever_r1_32) );
        imageMap.put("R0",BitmapFactory.decodeResource(res, R.drawable.lever_r0_32) );
        currentImage = imageMap.get("L0");
        width = currentImage.getWidth();
        height = currentImage.getHeight();
        hitbox = new Rect(x,y,x+width,y+height);
    }

    void changeOnOff(){
        isOn = !isOn;
    }
        //    public void changeActivity(){
        //        if (isCollision(roland.hitbox)) {
        //            isClickable = true;
        //            if (isOn) currentImage = imageMap.get("R1");
        //            else currentImage = imageMap.get("L1");
        //        }
        //        else{
        //            isClickable = false;
        //            if (isOn) currentImage = imageMap.get("R0");
        //            else currentImage = imageMap.get("L0");
        //
        //        }
        //    }

    public void changeMode(){
        if (isCollision(gameMap.roland.x,gameMap.roland.x+width-16,gameMap.roland.y+height/2)){
            if (isOn) currentImage = imageMap.get("R1");
            else currentImage = imageMap.get("L1");
            isClickable = true;
        }
        else{
            if (isOn) currentImage = imageMap.get("R0");
            else currentImage = imageMap.get("L0");
            isClickable = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(currentImage,x,y,paint);
        changeMode();
    }
}
