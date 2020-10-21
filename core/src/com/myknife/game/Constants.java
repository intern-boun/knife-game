package com.myknife.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Constants {
    public static final Color BACKGROUND_COLOR = new Color(0,0,139/255f,1);

    public static final float WOOD_HEIGHT = Gdx.graphics.getHeight()*6.0f/10;

    public static float SPIN_FACTOR = 120.f;
    public static final float SPIN_FACTOR_APPLE = 140.0f;
    public static final float SPIN_FACTOR_ORANGE = 180.0f;
    public static float SPIN_DIRECTION = 1;

    public static final int ORANGE_TYPE = 1;
    public static boolean ORANGE_HIT = false;
    public static final int APPLE_TYPE = 2;
    public static boolean APPLE_HIT = false;
    public static final int PEAR_TYPE = 3;
    public static boolean PEAR_HIT = false;

    //Scores
    public static final int FRUIT_HIT_SCORE = 5;
    public static final int SCORE = 1;
    public static final int x2_SCORE = 2;
    public static final int x4_SCORE = 4;

    /*
    Size of game objects and knife velocity are adjusted according to world dimensions.
    Therefore the ratio of the dimensions of the game world (width and height) to each other must be constant.
     */
    public static float WORLD_WIDTH = Gdx.graphics.getWidth();
    public static float WORLD_HEIGHT = Gdx.graphics.getHeight();

    public static float WOOD_WIDTH = WORLD_WIDTH/2.5f;
    public static float KNIFE_WIDTH = WORLD_WIDTH/25;
    public static float FRUIT_WIDTH = WORLD_WIDTH/40.6f;

    public static final float KNIFE_VELOCITY = WORLD_HEIGHT*8.0f/3.0f;
}
