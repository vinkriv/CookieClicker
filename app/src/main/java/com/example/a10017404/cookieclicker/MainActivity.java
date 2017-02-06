package com.example.a10017404.cookieclicker;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    RelativeLayout relativeLayout;
    TextView plusone;
    AtomicInteger grandpas;
    volatile int total;
    TextView cookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.cookie);
        img.setImageResource(R.drawable.cookie);
        cookies = (TextView)findViewById(R.id.cookies);
        relativeLayout = (RelativeLayout)findViewById(R.id.relative_layout);
        final ScaleAnimation scaleAnimation =  new ScaleAnimation(1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(50);
        final Thread OG = new Thread() {
            public void run() {
                //int num1 = grandpas.get();
                total += 50;
                cookies.setText(String.valueOf(total));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        OG.start();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.startAnimation(scaleAnimation);
                final TextView plusone = new TextView(MainActivity.this);
                total++;
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
