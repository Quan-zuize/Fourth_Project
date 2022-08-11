package com.example.project_4_admin.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.project_4_admin.R;

public class SoundService extends Service {
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.anya_sound); //select music file
        player.setLooping(false); //set looping
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }

}