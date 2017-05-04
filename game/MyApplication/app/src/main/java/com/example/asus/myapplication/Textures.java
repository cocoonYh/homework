package com.example.asus.myapplication;

import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;

import static com.example.asus.myapplication.R.id.time;

/**
 * Created by ASUS on 2017/5/1.
 */

public class Textures {     //地图 对象类
    public static TextureAtlas atlas,atalas2;
    public static Texture background,bee,sunflower,sun,pause,fire,quit,restart;
    public static void load()
    {
        atlas=new TextureAtlas(10);       //纹理地图
        atalas2=new TextureAtlas();
        background=new Texture("background.png");
        bee=new Texture("bee.png");
        sun=new Texture("sun.png");
        sunflower=new Texture("sunflower.png");
        pause=new Texture("pause.png");
        fire=new Texture("fire.png");
        quit=new Texture("quit.png");
        restart=new Texture("restart.png");
        atlas.insert(background);
        atlas.insert(bee);
        atlas.insert(sun);
        atlas.insert(sunflower);
        atalas2.insert(pause);
        atalas2.insert(fire);
        atalas2.insert(quit);
        atalas2.insert(restart);
        atlas.complete();
        atalas2.complete();
    }
}
