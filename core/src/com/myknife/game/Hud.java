package com.myknife.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.myknife.game.Constants.APPLE_HIT;
import static com.myknife.game.Constants.ORANGE_HIT;
import static com.myknife.game.Constants.PEAR_HIT;
import static com.myknife.game.Constants.WORLD_HEIGHT;
import static com.myknife.game.Constants.WORLD_WIDTH;
import static com.myknife.game.MainAction.*;

public class Hud {
    private Texture red;
    private Sprite redS;

    private Texture blue;
    private Sprite blueS;

    private Texture icon;
    private BitmapFont font;

    private Texture iconAppleNotSliced;
    private Texture iconAppleSliced;

    private Texture iconOrangeNotSliced;
    private Texture iconOrangeSliced;

    private Texture iconPearNotSliced;
    private Texture iconPearSliced;

    private Texture iconDefault;

    private Sprite iconS;

    float[] redAlpha;
    float[] blueAlpha;

    Hud(){
        init();
    }

    public void init(){
        red = new Texture("knife1/drawable-xxxhdpi/red_knife_count_indicator.png");
        redS = new Sprite(red);

        blue = new Texture("knife1/drawable-xxxhdpi/blue_knife_count_indicator.png");
        blueS = new Sprite(blue);

        iconAppleNotSliced = new Texture("knife1/drawable-xxxhdpi/speed_2_x.png");
        iconAppleSliced = new Texture("knife2/drawable-xxxhdpi/speed_2_x.png");

        iconOrangeNotSliced = new Texture("knife1/drawable-xxxhdpi/speed_4_x.png");
        iconOrangeSliced = new Texture("knife2/drawable-xxxhdpi/speed_4_x.png");

        iconPearNotSliced = new Texture("knife1/drawable-xxxhdpi/reverse.png");
        iconPearSliced = new Texture("knife2/drawable-xxxhdpi/reverse.png");

        iconDefault = new Texture("knife2/drawable-xxxhdpi/speed_2_x.png");

        redAlpha = new float[]{1, 1, 1, 1, 1};
        blueAlpha = new float[]{1, 1, 1, 1, 1};

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(7);
    }

    public void render(SpriteBatch batch){

        for(int i = 0; i<5; i++){
            float scale = redS.getHeight()/redS.getWidth();
            redS.setAlpha(redAlpha[i]);
            redS.setSize(WORLD_WIDTH/10,WORLD_WIDTH/10*scale);
            redS.setPosition(WORLD_WIDTH/10,WORLD_HEIGHT/10+i*redS.getHeight()*1.1f);
            redS.draw(batch);
        }
        for (int i=0; i<5; i++){
            float scale = blueS.getHeight()/blueS.getWidth();
            blueS.setAlpha(blueAlpha[i]);
            blueS.setSize(WORLD_WIDTH/10,WORLD_WIDTH/10*scale);
            blueS.setPosition(WORLD_WIDTH*8/10,WORLD_HEIGHT/10+i*blueS.getHeight()*1.1f);
            blueS.draw(batch);
        }
        for (int i=0;i<3;i++){
            switch (i){
                case 0 : {
                    if (!APPLE_HIT)
                        icon = iconAppleNotSliced;
                    else
                        icon = iconAppleSliced;
                    break;
                }
                case 1 : {
                    if (!ORANGE_HIT)
                        icon = iconOrangeNotSliced;
                    else
                        icon = iconOrangeSliced;
                    break;
                }
                case 2 : {
                    if (!PEAR_HIT)
                        icon = iconPearNotSliced;
                    else
                        icon = iconPearSliced;
                    break;
                }
                default:icon = iconDefault;
                    break;
            }
            iconS = new Sprite(icon);
            float scale = iconS.getHeight()/iconS.getWidth();
            iconS.setSize(WORLD_WIDTH/15,WORLD_WIDTH/15*scale);
            iconS.setPosition(WORLD_WIDTH*10/15f+i*iconS.getWidth()*1.3f,WORLD_HEIGHT*9/10);
            iconS.draw(batch);
        }



        font.setColor(Color.RED);
        font.draw(batch,
                scoreRed.toString(),
                WORLD_WIDTH/10,
                WORLD_HEIGHT/10+7*redS.getHeight()*1.1f);

        font.setColor(Color.BLUE);
        font.draw(batch,
                scoreBlue.toString(),
                WORLD_WIDTH*8/10,
                WORLD_HEIGHT/10+7*redS.getHeight()*1.1f);
    }

}
