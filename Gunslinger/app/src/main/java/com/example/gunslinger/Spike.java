package com.example.gunslinger;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Spike extends GameObject   {

    public Spike(GameMap gameMap, Resources res, int x, int y){
        super(gameMap,res,x,y);
        image = BitmapFactory.decodeResource(res, R.drawable.spike_16);
        width = image.getWidth();
        height = image.getHeight();
        hitbox = new Rect(x+15,y+12,x+width-15,y+height-9);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,x,y, paint);
    }
    //TODO как реализовывать смерть

}