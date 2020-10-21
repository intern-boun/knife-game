package com.myknife.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.myknife.game.Constants.SPIN_FACTOR;
import static com.myknife.game.Constants.WOOD_HEIGHT;
import static com.myknife.game.Constants.WOOD_WIDTH;
import static com.myknife.game.Constants.WORLD_WIDTH;

public class Wood {
    private Texture texture;
    protected Sprite sprite;
    private float proportion;

    public Wood(){

        texture = new Texture("knife1/drawable-xxxhdpi/wood.png");
        sprite = new Sprite(texture);
        proportion = sprite.getHeight()/sprite.getWidth();
        sizeInit();
        sprite.setOrigin(WOOD_WIDTH/2,WOOD_WIDTH * proportion/2);
    }

    private void sizeInit() {
        sprite.setSize(WOOD_WIDTH, WOOD_WIDTH * proportion);
    }

    public void update(float delta){
        sprite.rotate(delta* SPIN_FACTOR);
    }

    public void render(SpriteBatch batch){
        sprite.setPosition(WORLD_WIDTH/2 - sprite.getWidth()/2, WOOD_HEIGHT);
        sprite.draw(batch);
    }
}
