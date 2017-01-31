package com.example.a10017404.cookieclicker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    RelativeLayout relativeLayout;
    TextView plusone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.cookie);
        img.setImageResource(R.drawable.cookie);
        relativeLayout = (RelativeLayout)findViewById(R.id.relative_layout);
        plusone = (TextView)findViewById(R.id.plusone);
        final ScaleAnimation scaleAnimation =  new ScaleAnimation(1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(50);
        final TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-10);
        translateAnimation.setDuration(500);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.startAnimation(scaleAnimation);
                TextView plusone = new TextView(MainActivity.this);
                plusone.startAnimation(translateAnimation);
            }
        });

    }
}
