package com.example.asus.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;

import com.stickycoding.rokon.Font;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;
import com.stickycoding.rokon.background.FixedBackground;

import java.util.Random;

import static java.lang.StrictMath.abs;

/**
 * Created by ASUS on 2017/5/1.
 */

public class GameScene extends Scene {
    //记录背景
    private FixedBackground background;
    //闪出蜜蜂、死亡时的对话框和实时显示分数
    private Sprite bee,dialog,score;
    //闪出的太阳、太阳花和火球
    private Sprite[] sun,sunflower,fire;
    //判断是否暂停、是否退出、是否出现火球
    private boolean isPause =false, isStopped =false, hasFire =false;
    //对话框中的按钮
    private Sprite restart,quit;
    private Random randomInt;
    private long lCur, lTime;
    private int iScore =0;
    private int i1=0,i2=0;
    private MainActivity mainActivity;
    private Font font;

    //实时更新分数
    public void setScore()
    {
        Texture textTexture= font.createTexture("SCORE:"+ iScore);
        TextureAtlas textureAtlas=new TextureAtlas();
        textureAtlas.insert(textTexture);
        textureAtlas.complete();
        score.setTexture(textTexture);
    }
    public GameScene(MainActivity mActivity)
    {
        super(4,10);
        //获取随机数
        randomInt =new Random();
        //设置背景
        background=new FixedBackground(Textures.background);
        setBackground(background);
        //设置蜜蜂属性
        bee=new Sprite(200,160,40,40);
        bee.setTexture(Textures.bee);
        add(0,bee);
        bee.accelerateY(180);
        //设置太阳和太阳花属性
        sun=new Sprite[10];
        sunflower=new Sprite[10];
        for(int i=0;i<10;++i) {
            sun[i] = new Sprite(480+i*150,abs(randomInt.nextInt()%250)+70, 40, 40);
            sunflower[i] = new Sprite(480+i*190, abs(randomInt.nextInt()%250)+70, 40, 40);
            sun[i].setTexture(Textures.sun);
            sunflower[i].setTexture(Textures.sunflower);
            //太阳和太阳花向左移动，蜜蜂上下移动
            sun[i].setSpeedX(-100);
            sunflower[i].setSpeedX(-100);
            add(2,sun[i]);
            add(1,sunflower[i]);
        }
        //设置散出火球的属性
        fire=new Sprite[3];
        for(int i=0;i<3;++i)
        {
            fire[i]=new Sprite(500,500,30,30);
            fire[i].setTexture(Textures.fire);
            add(3,fire[i]);
        }
        //获取当前时间并记录
        lCur =System.currentTimeMillis();
        lTime = lCur;
        mainActivity =mActivity;
        //设置显示分数的字体属性
        font =new Font("fonts/font.ttf");
        font.setFontSize(40);
        Texture textTexture= font.createTexture("SCORE:"+ iScore);
        TextureAtlas textureAtlas=new TextureAtlas();
        textureAtlas.insert(textTexture);
        textureAtlas.complete();
        score=new Sprite(240,30,textTexture.getWidth(),textTexture.getHeight());
        score.setTexture(textTexture);
        add(0,score);
    }

    //游戏中单击屏幕事件
    @Override
    public void onTouchDownReal(float x, float y, MotionEvent event, int pointerCount, int pointerId) {
        super.onTouchDownReal(x, y, event, pointerCount, pointerId);
        //如果已经死亡
        if(isStopped)
        {
            if (x <= 260 && x >= 200)  //restart
            {
                Intent ii = new Intent();
                mainActivity.setResult(Activity.RESULT_OK, ii);
                mainActivity.finish();
            }
            if (x <= 340 && x >= 300)  //quit
            {
                Intent ii = new Intent();
                mainActivity.setResult(Activity.RESULT_CANCELED, ii);
                mainActivity.finish();
            }
        }
        //如果还没死亡，单击屏幕的话蜜蜂上移
        else
        {
            bee.setSpeedY(-100);
        }
    }

    @Override
    public void onReady() {
    }
    @Override
    public void onPause() {

    }
    @Override
    public void onResume() {

    }

    //停止事件
    public void stop()
    {
        //蜜蜂停止
        bee.accelerateY(0);
        bee.setSpeed(0, 0);
        //太阳、太阳花和火球停止
        for(int i=0;i<10;++i) {
            sun[i].setSpeed(0, 0);
            sunflower[i].setSpeed(0, 0);
        }
        for(int i=0;i<3;++i)
        {
            fire[i].setSpeed(0,0);
        }
        //确定已经暂停
        isPause =true;
        //弹出暂停对话框
        dialog=new Sprite(200,-100,150,150);
        dialog.setTexture(Textures.pause);
        dialog.setSpeedY(200);
        //弹出返回主界面按钮
        restart=new Sprite(220,0,40,40);
        restart.setTexture(Textures.restart);
        restart.setSpeedY(200);
        //弹出退出游戏按钮
        quit=new Sprite(300,0,40,40);
        quit.setTexture(Textures.quit);
        quit.setSpeedY(200);
        //显示到屏幕上
        add(0,dialog);
        add(0,restart);
        add(0,quit);
    }

    @Override
    protected void onEndScene() {
        super.onEndScene();
    }

    @Override
    public void onGameLoop() {
        //每过一秒更新一次分数
        if((System.currentTimeMillis()- lTime)>=1000)
        {
            iScore +=1;
            setScore();
            lTime =System.currentTimeMillis();
        }
        //每过一秒太阳和太阳花的速度都增加
        if(sun[0].getSpeedX()>=-200&&(System.currentTimeMillis()- lCur)>1000)
        {
            float fSpeed=sun[0].getSpeedX();
            for(int i=0;i<10;++i)
            {
                sun[i].setSpeedX(fSpeed-1);
                sunflower[i].setSpeedX(fSpeed-1);
            }
            lCur =System.currentTimeMillis();
        }
        //设置蜜蜂位置商界
        if(!isPause &&bee.y>=280)
        {
            bee.setY(280);
        }
        //设置蜜蜂下界
        if(!isPause &&bee.y<=100)
        {
            bee.setY(100);
        }
        //如果和火球触碰就游戏结束
        for(int i=0;i<3;++i)
        {
            if(fire[i].x>=bee.x&&fire[i].x<=bee.x+bee.getWidth()&&fire[i].y>=bee.y&&fire[i].y<=bee.y+bee.getHeight()&&!isPause)
            {
                stop();
            }
        }
        if(hasFire)
        {
            if(fire[0].x<=0&&fire[1].x<=0&&fire[2].x<=0)
                hasFire =false;
        }
        //第五朵太阳花处开始发火球
        if(sunflower[4].x<=300&&!hasFire)
        {
            fire[0].set(sunflower[4].x,sunflower[4].y);
            fire[1].set(sunflower[4].x,sunflower[4].y);
            fire[2].set(sunflower[4].x,sunflower[4].y);
            fire[0].setSpeedX(-200);
            fire[1].setSpeed(-120,-120);
            fire[2].setSpeed(-120,120);
            hasFire =true;
        }
        //随机获取太阳位置
        if(sun[i1].x<=0)
        {
            sun[i1].set(1500,abs(randomInt.nextInt()%250)+70);
            i1=(i1+1)%10;
        }
        //随机获取太阳花位置
        if(sunflower[i2].x<=0)
        {
            sunflower[i2].set(1900,abs(randomInt.nextInt()%250)+70);
            i2=(i2+1)%10;
        }
        //如果碰到太阳或者太阳花就暂停游戏
        for (int i = 0; i < 10; ++i) {
            if ((((sun[i].x >= bee.x && sun[i].x <= bee.x + bee.getWidth()) && (sun[i].y >= bee.y && sun[i].y <= bee.y + bee.getHeight())) || ((sunflower[i].x >= bee.x && sunflower[i].x <= bee.x + bee.getWidth()) && (sunflower[i].y >= bee.y && sunflower[i].y <= bee.y + bee.getHeight())))&&!isPause) {
                stop();
            }
        }
        //如果游戏结束但并未退出
        if(isPause &&!isStopped)
        {
            if(dialog.y+dialog.getHeight()>=160)
            {
                isStopped =true;
                dialog.setSpeedY(0);
                quit.setSpeedY(0);
                restart.setSpeedY(0);
            }
        }
        //如果都判定蜜蜂静止
        if(isPause && isStopped)
        {
            bee.setSpeed(0,0);
        }
    }
}
