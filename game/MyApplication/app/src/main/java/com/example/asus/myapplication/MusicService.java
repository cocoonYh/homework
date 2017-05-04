package com.example.asus.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ASUS on 2017/5/3.
 */

public class MusicService extends Service {
    MediaPlayer mMediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mMediaPlayer.isPlaying())
        {
            mMediaPlayer.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer=MediaPlayer.create(this,R.raw.bgm);        //生成播放器对象
        mMediaPlayer.setLooping(true);      //设置可重复播放
    }
}
