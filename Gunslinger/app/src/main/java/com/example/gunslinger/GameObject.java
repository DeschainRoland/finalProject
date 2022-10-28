package com.example.gunslinger;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
public abstract class GameObject {

    //переменные для рисования
    Bitmap image;
    Paint paint = new Paint();
    GameMap gameMap;
    Resources res;

    //переменные для передвижения и взаимодействия
    Rect hitbox;
    int fallingVelocity = 9, //12
    movingVelocity,
    x, y, //координаты кадра
    height, width, //ширина и высота объекта
     fallRow, fallColumn,horColRow,horColColumn;


    public GameObject(GameMap gameMap, Resources res, int x, int y){
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.res = res;

    }

    //не готовая коллизия с текстурами

    public boolean isHorizontalCollision(){
        return gameMap.drawMap.mapArray[horColRow][horColColumn].equals("i")||
             gameMap.drawMap.mapArray[horColRow][horColColumn].equals("d")||
             gameMap.drawMap.mapArray[horColRow][horColColumn].equals("u")||
             gameMap.drawMap.mapArray[horColRow][horColColumn +1].equals("i")||
             gameMap.drawMap.mapArray[horColRow][horColColumn +1].equals("d")||
             gameMap.drawMap.mapArray[horColRow][horColColumn +1].equals("u");
    }

    public boolean isFalling(){
        return gameMap.drawMap.mapArray[fallRow][fallColumn].equals("e") ||
                gameMap.drawMap.mapArray[fallRow][fallColumn].equals("|") ||
                gameMap.drawMap.mapArray[fallRow][fallColumn].equals("l") ||
                gameMap.drawMap.mapArray[fallRow][fallColumn].equals("k") ||
                gameMap.drawMap.mapArray[fallRow][fallColumn].equals("s") ||
                gameMap.drawMap.mapArray[fallRow][fallColumn].equals("c");
    }

    public boolean isCollision(Rect foreignHitbox){
        return hitbox.contains(foreignHitbox.left,foreignHitbox.bottom-5)||
                    hitbox.contains(foreignHitbox.right,foreignHitbox.bottom+5);}

    public boolean isCollision(int left, int right, int bottom){
        return hitbox.contains(left,bottom)||hitbox.contains(right,bottom);
    }

    //todo горизонтальная коллизия с коробкой, шипами, вертикальная коллизия с текстурами
    //не лезь сюда, тут всё ок
        public abstract void draw(Canvas canvas);
        public void fall(){}
        public void moveX(){}


    }
