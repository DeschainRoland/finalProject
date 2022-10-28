package com.example.gunslinger;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DrawMap {

    Resources res;
    Bitmap innerBrick, upBrick, downBrick;
    String[][] mapArray;
    int textureWidth, textureHeight;
    boolean generatedFirst = true;
    Paint paint = new Paint();
    //переменные для работы с файлами
    int path;
    InputStream openPath;
    InputStreamReader isr;
    BufferedReader br;
    String info;
    ArrayList<CoordClass> coordList = new ArrayList();
    //HashMap<String, Pair<Integer,Integer>> coordInform =new HashMap<>();

    public DrawMap(Resources res,int path) {
        this.res = res;
        this.path = path;
        innerBrick = BitmapFactory.decodeResource(res, R.drawable.floor_block_down);
        upBrick = BitmapFactory.decodeResource(res, R.drawable.floor_block_up);
        downBrick = BitmapFactory.decodeResource(res, R.drawable.roof_block_up);
        textureHeight = innerBrick.getHeight();
        textureWidth = innerBrick.getWidth();
        openPath = res.openRawResource(path);
        isr = new InputStreamReader(openPath);
        br = new BufferedReader(isr);
        if (generatedFirst) {
            try {
                generateMapArray();
                getObjectSpawnCoords();
            } catch (IOException e) {
                e.printStackTrace();
            }
            generatedFirst = false;
        }
    }

    //рассчитывает длину и высоту массива перед выделением памяти
    private void checkLength() throws IOException {
        int rowCounter = 0;
        int columnCounter = 0;
        boolean hasTaken = false;
        while ((info = br.readLine()) != null) {
        //чтобы забрать кол-во колонок лишь один раз
            if (!hasTaken) {
                columnCounter = info.length();
                hasTaken = true;
            }
            rowCounter++;
        }
        br.close();
        mapArray = new String[rowCounter][columnCounter];
    }

    //генерирует строковый массив из файла
    public void generateMapArray() throws IOException {
        checkLength();
        openPath = res.openRawResource(path);
        isr = new InputStreamReader(openPath);
        br = new BufferedReader(isr);
        int i = 0;
        while ((info = br.readLine()) != null) {
            for (int j = 0; j < info.length(); j++) {
                mapArray[i][j] = String.valueOf(info.charAt(j));
            }
            i++;
        }
        br.close();
    }

    public void getObjectSpawnCoords(){
        for (int y = 0; y < mapArray.length; y++) {
            for (int x = 0; x < mapArray[y].length; x++) {
                switch (mapArray[y][x]){
                    //case "s" : coordInform.put("Spike",new Pair<>(x,y-1));break;
                    //case "l" : coordInform.put("Lever",new Pair<>(x,y-1));break;
                    //case "c" : coordInform.put("Crate",new Pair<>(x,y-1));break;
                    //case "|" : coordInform.put("Roland",new Pair<>(x,y-1));break;
                    case "s" : coordList.add(new CoordClass("Spike",new Pair(x,y)));break;
                    case "l" :  coordList.add(new CoordClass("Lever",new Pair(x,y-1)));break;
                    case "c" :  coordList.add(new CoordClass("Crate",new Pair(x-1,y-1)));break;
                    case "k" :  coordList.add(new CoordClass("Key",new Pair(x,y)));break;
                    case "g" :  coordList.add(new CoordClass("Gates",new Pair(x,y-1)));break;
                    case "|" :  coordList.add(new CoordClass("Roland",new Pair(x,y-1)));break;

                }
            }
        }
    }

    public void draw(Canvas canvas) {
        //рисуем задний фон
        canvas.drawBitmap(BitmapFactory.decodeResource(res,
                R.drawable.level_bg),0,0,paint);
        //рисуем текстурки
        drawTextures(canvas);
    }

    public void drawTextures(Canvas canvas) {
        for (int y = 0; y < mapArray.length; y++) {
            for (int x = 0; x < mapArray[y].length; x++) {
                switch (mapArray[y][x]){
                    case "i" : canvas.drawBitmap(innerBrick,x*textureWidth,y*textureHeight, paint);break;
                    case "u" : canvas.drawBitmap(upBrick,x*textureWidth,y*textureHeight, paint);break;
                    case "d" : canvas.drawBitmap(downBrick,x*textureWidth,y*textureHeight, paint);break;

                }
            }
        }
    }
}
