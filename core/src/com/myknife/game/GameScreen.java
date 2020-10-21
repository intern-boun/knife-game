package com.myknife.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.myknife.game.Constants.APPLE_HIT;
import static com.myknife.game.Constants.BACKGROUND_COLOR;
import static com.myknife.game.Constants.ORANGE_HIT;
import static com.myknife.game.Constants.PEAR_HIT;
import static com.myknife.game.Constants.WOOD_WIDTH;
import static com.myknife.game.Constants.WORLD_HEIGHT;
import static com.myknife.game.Constants.WORLD_WIDTH;

public class GameScreen implements Screen {

    SpriteBatch batch;
    //SpriteBatch hudBatch;
    Texture background;

    StretchViewport gameViewport;
    //ScreenViewport hudVPort;

    MainAction mainAction;
    Hud hud;



    @Override
    public void show() {
        background = new Texture("knife1/drawable-hdpi/knife_bg.png");

        batch = new SpriteBatch();

        //hudVPort = new ScreenViewport();
        //hudBatch = new SpriteBatch();

        mainAction = new MainAction(gameViewport);
        Gdx.input.setInputProcessor(mainAction);

        hud = new Hud();
        Texture background = new Texture("knife3/drawable-mdpi/knife_bg.png");

        gameViewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void render(float delta) {
        mainAction.update(delta);

        if(mainAction.redKnife<5&&mainAction.redKnife>=0){
            hud.redAlpha[mainAction.redKnife]=0.2f;
        }
        if (mainAction.blueKnife<5&&mainAction.blueKnife>=0){
            hud.blueAlpha[mainAction.blueKnife]=0.2f;
        }

        gameViewport.apply();
        Gdx.gl.glClearColor(BACKGROUND_COLOR.r,BACKGROUND_COLOR.g, BACKGROUND_COLOR.b,BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       // hudVPort.apply();

        batch.setProjectionMatrix(gameViewport.getCamera().combined);
        //hudBatch.setProjectionMatrix(hudVPort.getCamera().combined);

        batch.begin();
        batch.draw(background,0, 0, gameViewport.getScreenWidth(), gameViewport.getWorldHeight());
        mainAction.render(batch);
        hud.render(batch);
        batch.end();

        //hudBatch.begin();
        //drawKnives(hudBatch);
        //hudBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width,height,true);
        //hudVPort.update(width,height,true);
        mainAction.init();
        hud.init();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        //hudBatch.dispose();
    }

    @Override
    public void dispose() {

    }
}
