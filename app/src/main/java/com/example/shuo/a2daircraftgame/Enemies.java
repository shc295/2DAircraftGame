package com.example.shuo.a2daircraftgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by shuo on 7/13/2018.
 */

public class Enemies {
    private Bitmap bitmap;
    //coordinates
    private int x;
    private int y;

    private int maxX;
    private int minX;
    private int maxY;
    private int minY;

    private int speed = 1;

    private Rect detectCollision;

    public Enemies(Context context, int screenX, int screenY){
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        x = screenX;
        Random generator = new Random();
        y = generator.nextInt(maxY)-bitmap.getHeight();
        speed = generator.nextInt(6)+10;

        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());
    }

    public void update(int playerSpeed){
        //x-=playerSpeed;
        x-=speed;

        if(x<minX - bitmap.getWidth()){
            Random generator = new Random();
            speed = generator.nextInt(10)+10;
            x = maxX;
            y = generator.nextInt(maxY)-bitmap.getHeight();
            if(y<bitmap.getHeight()){
                y = bitmap.getHeight();
            }
        }

        //Adding the top l b and r to the rect object
        detectCollision.left = x;
        detectCollision.top =y;
        detectCollision.right =x+bitmap.getWidth();
        detectCollision.bottom = y+bitmap.getHeight();
    }

    public void setX(int x){
        this.x = x;
    }

    public Rect getDetectCollision(){
        return detectCollision;
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

    public int getSpeed() {
        return speed;
    }
}
