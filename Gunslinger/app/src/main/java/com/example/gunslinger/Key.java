package com.example.gunslinger;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Key extends GameObject {
    boolean hasTaken = false;
    Rect emtyRect;
    public Key(GameMap gameMap, Resources res, int x, int y) {
        super(gameMap, res, x, y);
        image = BitmapFactory.decodeResource(res,R.drawable.key);
        emtyRect  = new Rect(x,y,x+width,y+height);
        hitbox = new Rect(x,y,x+width,y+height);
    }


    @Override
    public void draw(Canvas canvas) {
        if (!hasTaken) canvas.drawBitmap(image,x,y,paint);
        canvas.drawRect(emtyRect, paint);
    }
}
