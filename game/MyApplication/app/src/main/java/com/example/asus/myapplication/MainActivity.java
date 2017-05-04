package com.example.asus.myapplication;

import android.support.v7.app.AlertDialog;

import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;

/**
 * Created by ASUS on 2017/5/1.
 */

public class MainActivity extends RokonActivity {
    public static final float GAME_WIDTH = 480f;
    public static final float GAME_HEIGHT = 320f;

    @Override
    public void onLoadComplete() {
        //装载图片
        Textures.load();
        //设置游戏场景
        setScene(new GameScene(this));
    }

    @Override
    public void onCreate() {
        //设置为调试模式
        debugMode();
        //强制全屏
        forceFullscreen();
        //强制横屏
        forceLandscape();
        setGameSize(GAME_WIDTH,GAME_HEIGHT);
        //设置渲染方式 加快速度
        setDrawPriority(DrawPriority.PRIORITY_VBO);
        //设置图片目录
        setGraphicsPath("textures/");
        createEngine();
    }
}
