package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

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

        MediaPlayer song = MediaPlayer.create(this,R.raw.anya_sound);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                song.start();
            }
        }, 1000);

        splashImg.animate().translationY(-3000).setDuration(2000).setStartDelay(1800);
        logo.animate().translationY(2300).setDuration(2000).setStartDelay(1800);
        appName.animate().translationY(2300).setDuration(2000).setStartDelay(1800);
        lottieAnimationView.animate().translationY(2300).setDuration(2000).setStartDelay(1800);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(IntroActivity.this,Store_dashboardActivity.class));
            }
        }, 2800);
    }
}