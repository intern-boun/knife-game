package com.myknife.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.myknife.game.Constants.WOOD_HEIGHT;
import static com.myknife.game.Constants.WOOD_WIDTH;
import static com.myknife.game.Constants.WORLD_WIDTH;

public class WoodFinishedAnimation {
    private TextureRegion texture1 = new TextureRegion(new Texture("knife3/drawable-xxxhdpi/1_st_piece.png"));
    private TextureRegion texture2 = new TextureRegion(new Texture("knife3/drawable-xxxhdpi/2_nd_piece.png"));
    private TextureRegion texture3 = new TextureRegion(new Texture("knife3/drawable-xxxhdpi/3_rd_piece.png"));
    private TextureRegion texture4 = new TextureRegion(new Texture("knife3/drawable-xxxhdpi/4_th_piece.png"));
    private Vector2 position1, position2, position3, position4;
    private Sprite sprite1, sprite2, sprite3, sprite4;
    private float vx1,vx2,vx3,vx4,vy1,vy2,vy3,vy4;

    private Viewport viewport;

    public  WoodFinishedAnimation(Viewport viewport){
        this.viewport = viewport;

        sprite1 = new Sprite(texture1);
        sprite2 = new Sprite(texture2);
        sprite3 = new Sprite(texture3);
        sprite4 = new Sprite(texture4);
        init();
    }
    public void init(){
        sizeInit();


        position1 = new Vector2(WORLD_WIDTH/2 - sprite1.getWidth(), WOOD_HEIGHT + sprite1.getHeight());
        position2 = new Vector2(WORLD_WIDTH/2 - sprite2.getWidth(), WOOD_HEIGHT);
        position3 = new Vector2(WORLD_WIDTH/2, WOOD_HEIGHT);
        position4 = new Vector2(WORLD_WIDTH/2, WOOD_HEIGHT + sprite1.getHeight());
        vx1=-100.0f+ MathUtils.random()*200.0f;
        vx1*=2;
        vy1=250.0f+ MathUtils.random()*30.0f;
        vy1*=3;
        vx2=-100.0f+ MathUtils.random()*200.0f;
        vx2*=2;
        vy2=250.0f+ MathUtils.random()*30.0f;
        vy2*=3;
        vx3=-100.0f+ MathUtils.random()*200.0f;
        vx3*=2;
        vy3=250.0f+ MathUtils.random()*30.0f;
        vy3*=3;
        vx4=-100.0f+ MathUtils.random()*200.0f;
        vx4*=2;
        vy4=250.0f+ MathUtils.random()*30.0f;
        vy4*=3;
    }

    private void sizeInit() {
        sprite1.setSize(WOOD_WIDTH / 2, WOOD_WIDTH / 2);
        sprite2.setSize(WOOD_WIDTH / 2, WOOD_WIDTH / 2);
        sprite3.setSize(WOOD_WIDTH / 2, WOOD_WIDTH / 2);
        sprite4.setSize(WOOD_WIDTH / 2, WOOD_WIDTH / 2);
    }
    public void update(float delta){
        vy1-=36.0f;
        vy2-=36.0f;
        vy3-=36.0f;
        vy4-=36.0f;
        position1.x += delta* vx1;
        position1.y += delta* vy1;
        position2.x += delta* vx2;
        position2.y += delta* vy2;
        position3.x += delta* vx3;
        position3.y += delta* vy3;
        position4.x += delta* vx4;
        position4.y += delta* vy4;
    }
    public void render(SpriteBatch batch){
        sprite1.setPosition(position1.x, position1.y);
        sprite1.draw(batch);
        sprite2.setPosition(position2.x, position2.y);
        sprite2.draw(batch);
        sprite3.setPosition(position3.x, position3.y);
        sprite3.draw(batch);
        sprite4.setPosition(position4.x, position4.y);
        sprite4.draw(batch);
    }
}