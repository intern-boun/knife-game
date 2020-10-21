package com.myknife.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.myknife.game.Constants.APPLE_HIT;
import static com.myknife.game.Constants.FRUIT_WIDTH;
import static com.myknife.game.Constants.ORANGE_HIT;
import static com.myknife.game.Constants.PEAR_HIT;
import static com.myknife.game.Constants.SPIN_DIRECTION;
import static com.myknife.game.Constants.SPIN_FACTOR;
import static com.myknife.game.Constants.SPIN_FACTOR_APPLE;
import static com.myknife.game.Constants.SPIN_FACTOR_ORANGE;
import static com.myknife.game.Constants.WOOD_HEIGHT;

import static com.myknife.game.Constants.ORANGE_TYPE;
import static com.myknife.game.Constants.APPLE_TYPE;
import static com.myknife.game.Constants.PEAR_TYPE;
import static com.myknife.game.Constants.WORLD_WIDTH;


public class Fruit {

    private TextureRegion texture1,texture2,texture3;
    protected Sprite sprite1,sprite2,sprite3;
    private Vector2 position1 = new Vector2(0,0),position2 = new Vector2(0,0),position3 = new Vector2(0,0);
    private Boolean isSliced;

    private int type;
    private float random;

    private float vx1,vx2,vx3,vy1,vy2,vy3;




    public Fruit(String pngPath1, String pngPath2, String pngPath3, float woodHeight, float random, int type) {

        texture1 = new TextureRegion(new Texture(pngPath1));
        texture2 = new TextureRegion(new Texture(pngPath2));
        texture3 = new TextureRegion(new Texture(pngPath3));
        sprite1 = new Sprite(texture1);
        sprite2 = new Sprite(texture2);
        sprite3 = new Sprite(texture3);
        sizeInit();

        this.type = type;


        isSliced = false;

        this.random = random;

        sprite1.flip(false,true);
        sprite1.setOrigin(sprite1.getWidth()/2 - sprite1.getWidth()/9,
                woodHeight + sprite1.getHeight());

        sprite2.flip(false,true);
        sprite2.setOrigin(-sprite2.getWidth()/2 + sprite2.getWidth()/9,
                woodHeight + sprite2.getHeight());

        sprite3.flip(false, true);
        sprite3.setOrigin(0 ,
                woodHeight + sprite1.getHeight() + sprite3.getHeight());

        init();

    }

    private void sizeInit() {
        float proportion , proportion2, proportion3;
        proportion2 = sprite2.getHeight()/sprite1.getHeight();
        proportion3 = sprite1.getHeight()/sprite3.getWidth();
        proportion = sprite1.getHeight()/sprite1.getWidth();

        //to change fruit size change FRUIT_WIDTH
        sprite1.setSize(FRUIT_WIDTH, FRUIT_WIDTH * proportion);

        proportion = sprite2.getHeight()/sprite2.getWidth();
        sprite2.setSize(sprite1.getHeight() * proportion2 / proportion, sprite1.getHeight() * proportion2);
        proportion = sprite3.getHeight()/sprite3.getWidth();
        sprite3.setSize(sprite1.getHeight()/proportion3, sprite1.getHeight() * proportion/proportion3);

    }

    public void init(){

        sprite1.rotate(random);
        sprite2.rotate(random);
        sprite3.rotate(random);

        //velocities of fruit parts
        vx1= 25.0f+ MathUtils.random()*50;
        vx2= 25.0f+ MathUtils.random()*50;
        vx3= -25.0f+ MathUtils.random()*75;
        vy1= 250.0f+ MathUtils.random()*20;
        vy2= 250.0f+ MathUtils.random()*20;
        vy3= 250.0f+ MathUtils.random()*20;
        vx1*=3;
        vx2*=3;
        vx3*=3;
        vy1*=3;
        vy2*=3;
        vy3*=3;
    }

    public void update(float delta){
        sprite1.rotate(delta * SPIN_FACTOR);
        sprite2.rotate(delta * SPIN_FACTOR);
        sprite3.rotate(delta * SPIN_FACTOR);
    }


    public void render(SpriteBatch batch){
        sprite1.setPosition(WORLD_WIDTH/2 - sprite1.getWidth()/2 + sprite1.getWidth()/9,
                WOOD_HEIGHT - sprite1.getHeight());
        sprite1.draw(batch);

        sprite2.setPosition(WORLD_WIDTH/2 + sprite2.getWidth()/2 - sprite2.getWidth()/9,
                WOOD_HEIGHT - sprite1.getHeight());
        sprite2.draw(batch);

        sprite3.setPosition(WORLD_WIDTH/2,
                WOOD_HEIGHT - sprite1.getHeight() - sprite3.getHeight());
        sprite3.draw(batch);

        position1.x = sprite1.getX();
        position1.y = sprite1.getY();

        position2.x = sprite2.getX();
        position2.y = sprite2.getY();

        position3.x = sprite3.getX();
        position3.y = sprite3.getY();

    }

    public void updateSlice(float delta){
        vy1-=36.0f;
        vy3-=36.0f;
        vy2-=36.0f;
        position1.y += delta * vy1;
        position1.x += -delta * vx1;
        sprite1.rotate(delta*60);
        position2.x += delta * vx2;
        position2.y += delta * vy2;
        sprite2.rotate(delta*60);
        position3.x += -delta * vx3;
        position3.y += delta * vy3;
        sprite3.rotate(delta*60);
    }

    public void addBuff(){
        switch (this.type){
            case APPLE_TYPE : {
                SPIN_FACTOR = SPIN_FACTOR_APPLE;
                SPIN_FACTOR *= SPIN_DIRECTION;
                APPLE_HIT = true;
                break;
            }
            case ORANGE_TYPE : {
                SPIN_FACTOR = SPIN_FACTOR_ORANGE;
                SPIN_FACTOR *= SPIN_DIRECTION;
                ORANGE_HIT = true;
                break;
            }
            case PEAR_TYPE : {
                SPIN_DIRECTION = -1;
                SPIN_FACTOR *= SPIN_DIRECTION;
                PEAR_HIT = true;
                break;
            }
            default:break;
        }

    }

    public void renderSlice(SpriteBatch batch){
        sprite1.setPosition(position1.x,
                position1.y);
        sprite1.draw(batch);

        sprite2.setPosition(position2.x,
                position2.y);
        sprite2.draw(batch);

        sprite3.setPosition(position3.x,
                position3.y);
        sprite3.draw(batch);

        position1.x = sprite1.getX();
        position1.y = sprite1.getY();

        position2.x = sprite2.getX();
        position2.y = sprite2.getY();

        position3.x = sprite3.getX();
        position3.y = sprite3.getY();
    }

    public void updateFinished(float delta){
        vy1-=36.0f;
        vy3-=36.0f;
        vy2-=36.0f;
        position1.y += delta * vy1;
        position1.x += delta * vx1;
        sprite1.rotate(delta*60);
        position2.x += delta * vx1;
        position2.y += delta * vy1;
        sprite2.rotate(delta*60);
        position3.x += delta * vx1;
        position3.y += delta * vy1;
        sprite3.rotate(delta*60);
    }

    public void renderFinished(SpriteBatch batch){
        sprite1.setPosition(position1.x,
                position1.y);
        sprite1.draw(batch);

        sprite2.setPosition(position2.x,
                position2.y);
        sprite2.draw(batch);

        sprite3.setPosition(position3.x,
                position3.y);
        sprite3.draw(batch);

        position1.x = sprite1.getX();
        position1.y = sprite1.getY();

        position2.x = sprite2.getX();
        position2.y = sprite2.getY();

        position3.x = sprite3.getX();
        position3.y = sprite3.getY();
    }


    public Boolean getSliced() {
        return isSliced;
    }

    public void setSliced(Boolean sliced) {
        isSliced = sliced;
    }


}
