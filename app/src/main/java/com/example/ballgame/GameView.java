package com.example.ballgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {

    private Bitmap ball;
    private float view_width,view_height,ballw,ballh,dx,dy;
    private LinkedList<BallTask> balls=new LinkedList<>();
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLACK);
        ball= BitmapFactory.decodeResource(context.getResources(),R.drawable.ball);
        timer.schedule(new RefreshTask(),0,17);
        //timer.schedule(new BallTask(),1000,30);
    }
    private boolean isInit;
    private void init(){
        view_width=getWidth();
        view_height=getHeight();
        ballw=view_width / 10;
        dx=dy=8;
        ballh=ballw;
        Matrix matrix=new Matrix();
        matrix.postScale(ballw/ball.getWidth(),ballh/ball.getHeight());
        ball=Bitmap.createBitmap(ball,0,0,ball.getWidth(),ball.getHeight(),matrix,false);
        isInit=true;


    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInit){
            init();
        }
        for(BallTask ballTask:balls){
            canvas.drawBitmap(ball,ballTask.x,ballTask.y,null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ballx=event.getX()-ballw/2;
        float bally=event.getY()-ballh/2;
        BallTask ballTask=new BallTask(ballx,bally);
        balls.add(ballTask);
        timer.schedule(ballTask,500,30);
        return false;
    }
    private Timer timer=new Timer();
    private class RefreshTask extends TimerTask{
        @Override
        public void run() {
            invalidate();
        }
    }
    private class BallTask extends TimerTask{
        float x,y,dx,dy;
        BallTask(float x,float y){
            dx=dy=8;
            this.x=x;
            this.y=y;
        }
        @Override
        public void run() {
            if(x<0 || x+ballw>view_width){
                dx*=-1;
            }
            if(y<0 || y+ballh>view_height){
                dy*=-1;
            }
            x+=dx;
            y+=dy;
        }
    }
    private class Ball{
        float x,y;
    }
}
