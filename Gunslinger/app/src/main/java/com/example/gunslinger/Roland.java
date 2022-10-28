package com.example.gunslinger;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.HashMap;

public class Roland extends GameObject  {

    //переменные для спрайтовой анимации
    final int IMAGE_ROWS = 1;
    final int IMAGE_COLUMNS = 8;
    int currentFrame = 0;
    int direction = 4;

    //переменные для передвижения и взаимодействия
    boolean isJumping = false,
    crateFalling = false,
    isDead = false,
    isGates = false;
    int targetX,
    jumpingVelocity = 12,
    jumpingBorder = 96, // высота прыжка (потолок прыжка)
    jumpingCounter = 0; //счетчик текущей высоты прыжка
    Bitmap currentImage;
    Rect hBForSpikes;
    HashMap<String ,Bitmap> imageMap = new HashMap<>();

    public Roland(GameMap gameMap, Resources res, int x, int y){
        super(gameMap,res,x,y);
        imageMap.put("Left",BitmapFactory.decodeResource(res,R.drawable.roland_single_l_32));
        imageMap.put("Right",BitmapFactory.decodeResource(res,R.drawable.roland_single_r_32));
        imageMap.put("Dead",BitmapFactory.decodeResource(res,R.drawable.roland_dead));
        currentImage = imageMap.get("Left");
        width = currentImage.getWidth();
        height = currentImage.getHeight();
        hitbox = new Rect(x,y,x+width,y +height);
        hBForSpikes = new Rect(x+24,y,x+width-15,y+height);
        fallRow = hitbox.bottom/48;
        fallColumn = (hitbox.left+(hitbox.right-hitbox.left)/2)/48;
        horColColumn = hitbox.left/48;
        horColRow = hitbox.bottom/48;
        targetX = x;
        //ещё один кусок кода для спрайтовой анимации
        //width = this.image.getWidth() / IMAGE_COLUMNS;
        //height = this.image.getHeight() / IMAGE_ROWS;
    }

    public void jump(){
        if (isJumping && jumpingCounter < jumpingBorder) {
            y -= jumpingVelocity;
            jumpingCounter += jumpingVelocity;
        }
        else {
            isJumping = false;
        }
    }


    @Override
    public void fall(){
        if (isFalling() && !isJumping && crateFalling) {
            y += fallingVelocity;
        }
    }

    public void checkCrateVerCol(Crate crate){
        if (((hitbox.left >= crate.hitbox.left && hitbox.left<=crate.hitbox.right)
                ||(hitbox.right>=crate.hitbox.left && hitbox.right<=crate.hitbox.right)) && (Math.abs(hitbox.bottom-crate.hitbox.top) <= 3)){
           crateFalling = false;
        }
        else crateFalling = true;
    }

    public void setTargetX(float touchX) {
        targetX = (int) touchX;
        movingVelocity = 16;
    }

    @Override
    public void moveX(){
        if (targetX >= x) {

            if (Math.abs(x - targetX)-width > 5)
                x += movingVelocity;
        }

        else if (targetX <=x){

            if (Math.abs(x - targetX)> 5)
                x -= movingVelocity;
        }

        else movingVelocity =0;
        //кусок кода для спрайтовой анимации
        //currentFrame = ++currentFrame%IMAGE_COLUMNS;
    }

    @Override
    public void draw(Canvas canvas) {
        //заготовка спрайтовой анимации
        //Rect src = new Rect(currentFrame*width, direction*height,
        // currentFrame*width+width, direction*height+height);
        //Rect dst = new Rect(x, y, x+width, y+width);
        //canvas.drawBitmap(image, src, dst, paint);
        //currentFrame = ++currentFrame%IMAGE_COLUMNS;
        if (currentImage.equals(imageMap.get("Dead")))
            canvas.drawBitmap(currentImage,x,y+48,paint);
        else
        canvas.drawBitmap(currentImage,x,y,paint);
        moveX();
        //todo сломался прыжок
        jump();
        fall();
        hitbox.set(x,y,x+width,y+height);
        hBForSpikes.set(x+24,y,x+width-15,y+height);
        fallRow = hitbox.bottom/48;
        fallColumn = (hitbox.left+(hitbox.right-hitbox.left)/2)/48;
        //Не готово
        //horColColumn = hitbox.left/48;
        //horColRow = hitbox.bottom/48;
    }

    public void checkDeath(Spike spike){
        if (isCollision(spike.hitbox)) {
            isDead = true;
        }
    }

    public void checkGates(Gates gates){
        if (isCollision(gates.hitbox)){
            isGates = true;
        }
    }
}
//TODO спрайтовая анимация или две картинки (если нет времени)
