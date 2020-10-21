package com.myknife.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.myknife.game.Constants.KNIFE_VELOCITY;
import static com.myknife.game.Constants.KNIFE_WIDTH;
import static com.myknife.game.Constants.WORLD_WIDTH;

public class Knife {
    private Texture texture;
    protected Sprite sprite;
    protected Vector2 position;
    private float vx, vy;
    private Boolean isHit;

    public Knife(String pngPath){

        texture = new Texture(pngPath);
        sprite = new Sprite(texture);
        sizeInit();
        position = new Vector2(WORLD_WIDTH/2 - sprite.getWidth()/2, 0 );
        isHit = false;

        init();
    }

    private void sizeInit() {
        float proportion = sprite.getHeight()/sprite.getWidth();
        sprite.setSize(KNIFE_WIDTH,KNIFE_WIDTH * proportion);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
    }

    public void init(){
        sprite.setPosition(WORLD_WIDTH/2 - sprite.getWidth()/2, 0 );

        vx = MathUtils.random()*2000 - 500.0f;
        vy = -(KNIFE_VELOCITY/2f+ MathUtils.random()*200);

    }

    public void update(float delta){
        position.y = position.y + delta * (KNIFE_VELOCITY - delta* 9.8f);
        sprite.setPosition(position.x, position.y);
    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);

        position.x = sprite.getX();
        position.y = sprite.getY();
    }

    public void updateHit(float delta){

        position.x += delta * vx;
        position.y += delta * (vy);
        sprite.rotate(delta*1200.0f);
    }

    public void renderHit(SpriteBatch batch){
        sprite.setPosition(position.x,
                position.y);
        sprite.draw(batch);

        position.x = sprite.getX();
        position.y = sprite.getY();

    }


    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Boolean getHit() {
        return isHit;
    }

    public void setHit(Boolean hit) {
        isHit = hit;
    }
}
