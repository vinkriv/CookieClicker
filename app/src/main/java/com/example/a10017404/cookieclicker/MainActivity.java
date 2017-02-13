package com.example.a10017404.cookieclicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends Activity {
    ImageView img;
    RelativeLayout relativeLayout;
    TextView plusone;
    Integer grandpas =0;
    AtomicInteger total = new AtomicInteger(0);
    TextView cookies;
    boolean grandpaonscr=false;
    ImageView gcount;
    int income=0;

    public class backgroundThread extends Thread{
        public void run(){
            while(grandpas>0){
                total.getAndAdd(income);
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cookies.setText(String.valueOf(total.intValue()));
                        if (grandpas>9){
                            if (!grandpaonscr) {
                                grandpaonscr = true;
                                createGrandpa();
                            }
                        }
                    }
                });
            }
        }
    }

    public void createGrandpa(){
        final ImageView grandpa = new ImageView(MainActivity.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE,R.id.cookie);
        params.addRule(RelativeLayout.ALIGN_LEFT,RelativeLayout.TRUE);
        grandpa.setImageResource(R.drawable.grandpa);
        relativeLayout.addView(grandpa,params);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(100);

        grandpa.setAnimation(fadeIn);
        grandpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundThread background = new backgroundThread();
                background.start();
                grandpaonscr=false;
                total.getAndSet(total.intValue()-10);
                grandpas++;
                income+=2;
                findViewById(R.id.gcount).setVisibility(View.VISIBLE);
                TextView count = (TextView)findViewById(R.id.textView);
                count.setVisibility(View.VISIBLE);
                count.setText(String.valueOf(grandpas.intValue()));
                cookies.setText(String.valueOf(total.intValue()));
                if (total.intValue()<10){
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
                    fadeOut.setStartOffset(1000);
                    fadeOut.setDuration(100);
                    grandpa.setAnimation(fadeOut);
                    relativeLayout.removeView(grandpa);
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.cookie);
        img.setImageResource(R.drawable.cookie);
        cookies = (TextView)findViewById(R.id.cookies);
        relativeLayout = (RelativeLayout)findViewById(R.id.relative_layout);
        final ScaleAnimation scaleAnimation =  new ScaleAnimation(1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        gcount = (ImageView)findViewById(R.id.gcount);
        gcount.setAnimation(rotate);
        scaleAnimation.setDuration(50);
        cookies.setText(String.valueOf(total));

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total.intValue()>=9){
                    if (!grandpaonscr){
                        grandpaonscr=true;
                        createGrandpa();
                    }
                }
                img.startAnimation(scaleAnimation);
                final TextView plusone = new TextView(MainActivity.this);
                total.getAndAdd(1);
                cookies.setText(String.valueOf(total));
                plusone.setText("+1");
                plusone.setTextColor(Color.BLACK);
                plusone.setTextSize(30.0f);
                int ltpad = (int)(Math.random()*30);
                int rtpad = (int)(Math.random()*301);
                plusone.setPadding(ltpad,0,rtpad,50);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ABOVE,R.id.cookie);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);

                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-10);
                translateAnimation.setDuration(1500);

                relativeLayout.addView(plusone,params);
                plusone.startAnimation(translateAnimation);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        relativeLayout.removeView(plusone);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }

}
