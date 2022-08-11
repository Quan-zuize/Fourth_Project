package com.example.project_4_admin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project_4_admin.service.SoundService;

public class IntroActivity extends AppCompatActivity {
    private ImageView logo, appName, splashImg;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.brand);
        splashImg = findViewById(R.id.imageView);
        lottieAnimationView = findViewById(R.id.animation);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(IntroActivity.this, SoundService.class));
            }
        }, 1000);

        splashImg.animate().translationY(-3000).setDuration(2000).setStartDelay(3500);
        logo.animate().translationY(2300).setDuration(2000).setStartDelay(3500);
        appName.animate().translationY(2300).setDuration(2000).setStartDelay(3500);
        lottieAnimationView.animate().translationY(2300).setDuration(2000).setStartDelay(3500);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(IntroActivity.this, Store_dashboardActivity.class));
            }
        }, 3500);
    }
}