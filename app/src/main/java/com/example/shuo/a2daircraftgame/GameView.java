package com.example.shuo.a2daircraftgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by shuo on 7/12/2018.
 */

public class GameView extends SurfaceView implements Runnable {
    volatile boolean playing;

    private Thread gameThread = null;

    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;



    //background star
    private ArrayList<Star> stars = new ArrayList<Star>();
    //enemies
    //private ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    private Enemies enemies;
    private int enemyCount;

    private Boom boom;

    int screenX;
    int countMisses;
    boolean flag;
    private boolean isGameOver;
    private Friend friend;

    //score part
    int score;
    int highScore[] =  new int[4];

    SharedPreferences sharedPreferences;

    public GameView(Context context,int x, int y){
        super(context);
        player = new Player(context,x,y);
        int starNumber =100;
        for(int i=0;i<starNumber;i++){
            Star s = new Star(x,y);
            stars.add(s);
        }
        //enemyCount = 3;
        /*for(int i=0;i<enemyCount;i++){
            enemies.add(new Enemies(context,x,y));
        }*/
        enemies = new Enemies(context,x,y);
        friend = new Friend(context,x,y);

        surfaceHolder = getHolder();

        paint = new Paint();

        boom = new Boom(context);

        //making game over
        this.screenX = x;
        countMisses =0;
        isGameOver =false;

        score = 0;
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);
    }

    @Override
    public void run(){
        while(playing){
            update();

            draw();

            control();
        }
    }

    public void update(){

        player.update();
        boom.setX(-250);
        boom.setY(-250);
        for(Star s:stars){
            s.update(player.getSpeed());
        }


        /*for(Enemies e:enemies){
            e.update(player.getSpeed());
            if(Rect.intersects(player.getDetectCollision(),e.getDetectCollision())){
                boom.setX(e.getX());
                boom.setY(e.getY());
                e.setX(-200);
            }
        }*/
        if(enemies.getX()==screenX){
            flag = true;
        }
        enemies.update(player.getSpeed());

        if(Rect.intersects(player.getDetectCollision(),enemies.getDetectCollision())){
            score++;
            boom.setX(enemies.getX());
            boom.setY(enemies.getY());
            enemies.setX(-200);
        }
        else{
            if(flag){
                if(player.getDetectCollision().exactCenterX()>=enemies.getDetectCollision().exactCenterX()){
                    countMisses++;
                    flag = false;
                    if(countMisses==3){
                        playing = false;
                        isGameOver = true;
                        for(int i=0;i<4;i++){
                            if(highScore[i]<score){
                                final int finalI = i;
                                highScore[i] = score;
                                break;
                            }
                        }
                        SharedPreferences.Editor e = sharedPreferences.edit();
                        for(int i=0;i<4;i++){
                            int j=i+1;
                            e.putInt("score"+j,highScore[i]);
                        }
                        e.apply();
                    }
                }
            }
        }
        friend.update(player.getSpeed());

        if(Rect.intersects(player.getDetectCollision(),friend.getDetectCollision())){
            boom.setX(friend.getX());
            boom.setY(friend.getY());
            playing = false;
            isGameOver = true;
            for(int i=0;i<4;i++){
                if(highScore[i]<score){
                    final int finalI = i;
                    highScore[i] = score;
                    break;
                }
            }
            SharedPreferences.Editor e = sharedPreferences.edit();
            for(int i=0;i<4;i++){
                int j=i+1;
                e.putInt("score"+j,highScore[i]);
            }
            e.apply();

        }

    }

    public void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            //background star
            paint.setColor(Color.WHITE);
            paint.setTextSize(20);
            for(Star s:stars){
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(),s.getY(),paint);
            }

            //drawing the score on the game screen
            paint.setTextSize(30);
            canvas.drawText("Score:"+score,100,50,paint);

            /*for(Enemies e: enemies){
                canvas.drawBitmap(e.getBitmap(),
                        e.getX(),e.getY(),paint);
            }*/

            canvas.drawBitmap(enemies.getBitmap(),enemies.getX(),enemies.getY(),paint);

            canvas.drawBitmap(player.getBitmap(),player.getX(),player.getY(),paint);
            canvas.drawBitmap(boom.getBitmap(),boom.getX(),boom.getY(),paint);
            canvas.drawBitmap(friend.getBitmap(),friend.getX(),friend.getY(),paint);
            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(17);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause(){
        playing =false;
        try{
            gameThread.join();
        }catch (InterruptedException e){

        }
    }

    public void resume(){
        playing =true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                //when the user presses on the screen
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                //when user release the screen
                player.setBoosting();
                break;
        }
        return true;
    }
}
