package com.printto.printmov.terriermonidle;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final int AMOUNT_OF_MODES = 10;

    AnimationDrawable walkerAnimation;
    ImageView walker;

    Random random = new Random();
    ObjectAnimator animatorX;
    ObjectAnimator animatorY;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = getWindowManager().getDefaultDisplay();

        walker = findViewById(R.id.walker);
        startWalking();
    }

    private void startWalking(){
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                generateWalk();
            }
        },0,5000);
    }

    private void generateWalk(){
        int randomedMode = random.nextInt(10);
        renderAnimation(randomedMode);
        walkWalker(randomedMode);
    }

    private void walkWalker(int mode){
        int min = 0;
        int max = 300;
        float randomX = min + random.nextFloat() * (max - min);
        float randomY = min + random.nextFloat() * (max - min);
        float currentX = walker.getTranslationX();
        float currentY = walker.getTranslationY();
        float toWalkX = 0;
        float toWalkY = 0;
        Log.d("walkWalker()", "randomX: "+randomX
                +"\nrandomY: "+randomY
                +"\ncurrentX: "+currentX
                +"\ncurrentY: "+currentY);
        switch(mode){
            case 0:
                //Downright
                toWalkX = currentX - randomX;
                toWalkY = currentY + randomY;
                break;
            case 1:
                //Downleft
                toWalkX = currentX + randomX;
                toWalkY = currentY + randomY;
                break;
            case 2:
                //Upright
                toWalkX = currentX - randomX;
                toWalkY = currentY - randomY;
                break;
            case 3:
                //Upleft
                toWalkX = currentX + randomX;
                toWalkY = currentY - randomY;
                break;
            default:
                toWalkX = currentX;
                toWalkY = currentY;
                break;
        }
        animatorX = ObjectAnimator.ofFloat(walker, "translationX", toWalkX);
        animatorY = ObjectAnimator.ofFloat(walker, "translationY", toWalkY);
        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(animatorX, animatorY);
        animSet.setDuration(4950);
        Log.d("Walker","Initialized walk");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                Log.d("Walker","Walk started");
                animSet.start();
            }
        });
    }

    private void renderAnimation(int mode){
        switch(mode){
            case 0:
                walker.setBackgroundResource(R.drawable.ani_downleft);
                break;
            case 1:
                walker.setBackgroundResource(R.drawable.ani_downright);
                break;
            case 2:
                walker.setBackgroundResource(R.drawable.ani_upleft);
                break;
            case 3:
                walker.setBackgroundResource(R.drawable.ani_upright);
                break;
            case 4:
                walker.setBackgroundResource(R.drawable.ani_downleft_idle);
                break;
            case 5:
                walker.setBackgroundResource(R.drawable.ani_downright_idle);
                break;
            case 6:
                walker.setBackgroundResource(R.drawable.ani_upleft_idle);
                break;
            case 7:
                walker.setBackgroundResource(R.drawable.ani_upright_idle);
                break;
            case 8:
                walker.setBackgroundResource(R.drawable.ani_sit);
                break;
            case 9:
                walker.setBackgroundResource(R.drawable.ani_jump);
                break;
        }
        walkerAnimation = (AnimationDrawable) walker.getBackground();
        walkerAnimation.start();
    }

}
