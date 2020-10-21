package com.myknife.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.myknife.game.Constants.SPIN_FACTOR;
import static com.myknife.game.Constants.WOOD_HEIGHT;
import static com.myknife.game.Constants.WORLD_WIDTH;

public class StuckKnife extends Knife {
    private float vx;
    private float vy;

    public StuckKnife(String pngPath, float woodHeight, float defaultKnifeRotationStart) {
        super(pngPath);
        sprite.setOrigin(sprite.getWidth()/2, woodHeight/2 + sprite.getHeight()/2);
        sprite.rotate(defaultKnifeRotationStart);
        position.x= WORLD_WIDTH/2 - sprite.getWidth()/2;
        position.y= WOOD_HEIGHT - sprite.getHeight()/2;
        vx=-250.0f+ MathUtils.random()*500.0f;
        vx*=2;
        vy=-250.0f+ MathUtils.random()*500.0f;
        vy*=2;
    }

    public StuckKnife(String pngPath, float woodHeight) {
        super(pngPath);
        sprite.setOrigin(sprite.getWidth()/2, woodHeight/2 + sprite.getHeight()/2);
        position.x= WORLD_WIDTH/2 - sprite.getWidth()/2;
        position.y= WOOD_HEIGHT - sprite.getHeight()/2;
        vx=-250.0f+ MathUtils.random()*500.0f;
        vy=-250.0f+ MathUtils.random()*500.0f;
        vx*=2;
        vy*=2;
    }

    public void updateFinished(float delta){
        vy-=36.0f;
        position.x+=delta*vx;
        position.y+=delta*vy;
        sprite.rotate(delta * 150.0f);
    }
    public void renderFinished(SpriteBatch batch){
        sprite.setPosition(position.x,
                position.y);
        sprite.draw(batch);
        position.x = sprite.getX();
        position.y = sprite.getY();
    }


    @Override
    public void update(float delta){
        sprite.rotate(delta * SPIN_FACTOR);
    }

    @Override
    public void render(SpriteBatch batch){
        sprite.setPosition(WORLD_WIDTH/2 - sprite.getWidth()/2, WOOD_HEIGHT - sprite.getHeight()/2 );
        sprite.draw(batch);
        position.x=sprite.getX();
        position.y=sprite.getY();
    }
}
