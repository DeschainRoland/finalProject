package com.example.gunslinger;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
public class Crate extends GameObject {


    public Crate(GameMap gameMap, Resources res,int x, int y) {
        super(gameMap,res,x,y);
        image = BitmapFactory.decodeResource(res, R.drawable.crate_32);
        width = image.getWidth();
        height = image.getHeight();
        hitbox = new Rect(x,y,x+width,y+height);
        movingVelocity = 8;
        //todo нужно чтобы была 8, при этом еще и у роланда
        fallRow = hitbox.bottom/48;
        fallColumn = hitbox.left/48;

    }



   @Override
   public void fall() {
       if (isFalling())
       y += fallingVelocity;


   }
    @Override
    public void moveX() {
        if (isCollision(gameMap.roland.x,gameMap.roland.x+width-16,gameMap.roland.y+height/2)){

            if (gameMap.roland.x<x)
            x+= movingVelocity;
            else
            x-= movingVelocity;

        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,x,y,paint);
        moveX();
        fall();
        hitbox.set(x,y,x+width,y+height);
        fallRow = Math.round(hitbox.bottom/48);
        fallColumn = Math.round(hitbox.left/48);
    }

}
//todo вставание на ящик