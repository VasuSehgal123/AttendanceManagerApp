package com.example.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;

public class SplashScreen extends AppCompatActivity {
    ImageView ig;
    TextView t;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ig=findViewById(R.id.first);
        t=findViewById(R.id.title);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.fadein);
        ig.clearAnimation();
        ig.setAnimation(anim);
        ig.getAnimation().start();
        t.clearAnimation();
        t.setAnimation(anim);
        t.getAnimation().start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 Intent i = new Intent(SplashScreen.this, Login1.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }
}