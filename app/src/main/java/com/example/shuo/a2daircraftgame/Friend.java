package com.example.shuo.a2daircraftgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by shuo on 7/13/2018.
 */

public class Friend {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private Rect detectCollision;

    public Friend(Context context, int screenX, int screenY){
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.friend);
        maxX = screenX;
        maxY =screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = generator.nextInt(6)+10;
        x=screenX;
        y=generator.nextInt(maxY)-bitmap.getHeight();

        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    public void update(int playerSpeed){
        x-=speed;
        if(x<minX-bitmap.getWidth()){
            x=maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY)-bitmap.getHeight();
            if(y<bitmap.getHeight()){
                y = bitmap.getHeight();
            }
        }

        detectCollision.left = x;
        detectCollision.right = x+bitmap.getWidth();
        detectCollision.top = y;
        detectCollision.bottom = y+bitmap.getHeight();
    }

    public Rect getDetectCollision(){
        return detectCollision;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
