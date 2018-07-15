package com.example.shuo.a2daircraftgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by shuo on 7/13/2018.
 */

public class Boom {
    private Bitmap bitmap;

    private int x;
    private int y;

    public Boom(Context context){
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.boom);

        x=-250;
        y = -250;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public  void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
