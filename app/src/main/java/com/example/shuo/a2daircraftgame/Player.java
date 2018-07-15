package com.example.shuo.a2daircraftgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by shuo on 7/12/2018.
 */

public class Player {
    private Bitmap bitmap;

    private int x;
    private int y;

    private int maxY;
    private int minY;

    private  boolean boosting;
    private final int GRAVITY = -10;

    private int MIN_SPEED = 1;
    private int MAX_SPEED = 20;
    private int speed = 0;

    private Rect detectCollision;

    public Player(Context context,int screenX,int screenY){
        x=75;
        y=50;
        speed = 1;
        minY =0;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.player);
        maxY = screenY-bitmap.getHeight();
        boosting = false;

        //initializing rect object
        detectCollision = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());

    }

    public void update(){
        if(boosting){
            speed+=2;
        }else{
            speed-=5;
        }
        if(speed>MAX_SPEED){
            speed=MAX_SPEED;
        }
        if(speed<MIN_SPEED){
            speed=MIN_SPEED;
        }
        y-=speed+GRAVITY;
        if(y<minY) {
            y=minY;
        }
        if(y>maxY) {
            y=maxY;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x+bitmap.getWidth();
        detectCollision.bottom = y+bitmap.getHeight();
    }

    public Rect getDetectCollision(){
        return detectCollision;
    }

    public void setBoosting(){
        boosting = true;
    }
    public void stopBoosting(){
        boosting = false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }
}
