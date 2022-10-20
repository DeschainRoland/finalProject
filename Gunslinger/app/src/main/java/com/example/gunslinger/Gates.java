package com.example.gunslinger;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Gates extends GameObject {
    boolean isOpened = false;
    Rect emtyRect;
    public Gates(GameMap gameMap, Resources res, int x, int y) {
        super(gameMap, res, x, y);
        image = BitmapFactory.decodeResource(res,R.drawable.gates);
        width = image.getWidth();
        height = image.getHeight();
        emtyRect = new Rect(x,y,x+width,y+height);
        hitbox = new Rect(x,y,x+width,y+height);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isOpened) canvas.drawBitmap(image,x,y,paint);
        else  canvas.drawRect(emtyRect,paint);
    }
}
