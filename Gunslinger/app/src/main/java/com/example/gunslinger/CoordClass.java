package com.example.gunslinger;

import android.graphics.Canvas;
import android.util.Pair;

public class CoordClass {
    String className;
    Pair<Integer,Integer> xyCoords;
    public CoordClass(String className, Pair xyCoords){
        this.className = className;
        this.xyCoords = xyCoords;
    }
}
