package com.example.asus.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    ImageButton mContinue;
    ImageButton mQuit;
    ImageButton mmusic;
    private boolean ison=true,f=false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED)
            finish();
    }

    @Override
    protected void onPostResume() {
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //设置为横屏
        }
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Intent intent=new Intent(MenuActivity.this,MusicService.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题    需要在未设置布局前调用
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏
        setContentView(R.layout.activity_main);
        Intent ii=new Intent(MenuActivity.this,MusicService.class);
        startService(ii);
        mContinue=(ImageButton)findViewById(R.id.menu_play);
        mContinue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton i=(ImageButton)v;
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    i.setImageResource(R.drawable.play2);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    i.setImageResource(R.drawable.menu_play);
                }
                return false;
            }
        });
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this,MainActivity.class);
                startActivityForResult(i,0);
            }
        });
        mmusic=(ImageButton)findViewById(R.id.menu_music);
        mmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton i=(ImageButton)v;
                if(ison)
                {
                    i.setImageResource(R.drawable.mute);
                    Intent ii=new Intent(MenuActivity.this,MusicService.class);
                    stopService(ii);
                    ison=false;
                }
                else
                {
                    ison=true;
                    i.setImageResource(R.drawable.music);
                    Intent ii=new Intent(MenuActivity.this,MusicService.class);
                    startService(ii);
                }
            }
        });
        mQuit=(ImageButton)findViewById(R.id.quit);
        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
