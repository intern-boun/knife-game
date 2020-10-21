package com.myknife.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


import static com.myknife.game.Constants.*;


public class MainAction implements InputProcessor {

    private Viewport viewport;
    private ArrayList<StuckKnife> stuckKnives = new ArrayList<>();
    private Boolean toss, isOverlappedKnife, isOverlappedFruit, turn , gameFinished;
    private DelayedRemovalArray<Knife> knifeRemoval;    //It is basically the knife which will be thrown.
    private DelayedRemovalArray<Fruit> fruitRemoval;
    private WoodFinishedAnimation woodFinishedAnimation;
    private Knife lastHitKnife;
    private Fruit lastHitFruit;
    private Wood wood;
    private Boolean knifeTurning;
    public static Integer scoreRed,scoreBlue;
    public int slicedFruitIndex,blueKnife,redKnife;

    public MainAction(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        slicedFruitIndex = -1;
        isOverlappedKnife = false;
        isOverlappedFruit = false;
        turn = true;    //true -> blue turn    false -> red turn
        toss = false;
        knifeTurning = false;   //if knife hit another knife it will be true
        redKnife = blueKnife = 5;
        gameFinished = false;
        wood = new Wood();
        woodFinishedAnimation = new WoodFinishedAnimation(viewport);
        lastHitKnife = null;
        lastHitFruit = null;
        scoreRed = scoreBlue = 0;

        knifeRemoval = new DelayedRemovalArray<>(false, 1);
        fruitRemoval = new DelayedRemovalArray<>(false, 4);

        woodPrepare();  //set the table -> fruits and default knives

        nextKnifeAdd(); //starter knife(blue)

    }

    private void woodPrepare() {
        //Default Knives are added to Wood
        SPIN_FACTOR = 120.0f;
        SPIN_DIRECTION = 1;
        stuckKnives.clear();
        PEAR_HIT = APPLE_HIT = ORANGE_HIT = false;

        for(int i = 0; i < 4; i++){
            stuckKnives.add(new StuckKnife("knife1/drawable-xxxhdpi/default_knife.png",
                    wood.sprite.getHeight(),
                    i * 90.0f));
        }

        //Fruits are added to Wood
        fruitRemoval.clear();

        for(int i = 0; i < 4; i++){
            float angle = 45 + i * 90;
            int random = MathUtils.random(150);
            if(random < 50) {
                fruitRemoval.add(orange(angle));
            }else if(random < 100 && random > 50){
                fruitRemoval.add(apple(angle));
            }else{
                fruitRemoval.add(pear(angle));
            }
        }
    }


    public void update(float delta) {
        if(!gameFinished){
            gameResumeUpdate(delta);
        }
        else {
            gameFinishUpdate(delta);
        }
    }

    private void gameFinishUpdate(float delta) {
        woodFinishedAnimation.update(delta);
        if(lastHitKnife != null){
            lastHitKnife.updateHit(delta);
        }
        if(lastHitFruit != null){
            lastHitFruit.updateSlice(delta);
        }
        deleteAll(delta);
    }

    private void gameResumeUpdate(float delta) {
        wood.update(delta);
        for(int i=0; i<stuckKnives.size();i++){
            stuckKnives.get(i).update(delta);
        }
        for(Fruit fruit: fruitRemoval){

            if(fruit.getSliced()){
                fruit.updateSlice(delta);
            }else{
                fruit.update(delta);
            }
        }
        ///
        tossKnife(delta);   //Almost everything about collision check is in tossKnife
        ///
        if(knifeTurning){
            updateKnifeHit(delta);
        }
    }

    private void deleteAll(float delta) {   //to save memory, if any piece is out of the screen, deleting it
        int highestKnife=-100;
        for(StuckKnife k: stuckKnives){
            k.updateFinished(delta);
            if(k.sprite.getY()>highestKnife)
                highestKnife=(int)k.sprite.getY();
        }
        if(highestKnife<0)
            stuckKnives.clear();

        int highestFruit = -100;
        for(Fruit fruit: fruitRemoval){
            fruit.updateFinished(delta);
            if (fruit.sprite1.getY()>highestFruit)
                highestFruit=(int)fruit.sprite1.getY();
        }
        if (highestFruit<0)
            fruitRemoval.clear();
    }

    private void tossKnife(float delta) {

        if((toss) && (knifeRemoval.size == 1)) {
            /*
            Changing the velocity of knife or wood rotation will cause some bugs for collision detection.
            Therefore when changing velocities, make sure that rectangles of objects are adjusted
            experimentally.

            Suggestions:
            -Wood rotation velocity does not have an effect on collision too much. But it must
             be lower than knife velocity.
            -Keep the knife velocity high.
             */
            /*
            Rectangle adjustments must be done here:
            thrown in both collision check functions is for thrown knife.
            stuck in knifeCollisionCheck is for stuck knives.
            fruit1 and fruit2 are for fruit parts except leaf.
            x and y are with respect to the lower left corner of objects.
             */
            knifeCollisionCheck();
            fruitCollisionCheck();

            if(isOverlappedKnife){
                ifHitKnife();

            }else if(isOverlappedFruit){
                ifHitFruit();

            }else if(knifeRemoval.get(0).getPosition().y > WOOD_HEIGHT - knifeRemoval.get(0).sprite.getHeight()/2){
                ifStuck();

            }else if(!knifeRemoval.get(0).getHit()){
                //if the knife continues its move
                knifeRemoval.get(0).update(delta);
            }
        }
    }

    private void ifStuck() {
        knifeRemoval.removeIndex(0);
        nextKnifeAdd();
        stuckKnifeAdd();
        knifeCount();
        if (turn){
            if (Math.abs(SPIN_FACTOR)>=SPIN_FACTOR_ORANGE)
                scoreRed+=x4_SCORE;
            else if (Math.abs(SPIN_FACTOR)>=SPIN_FACTOR_APPLE)
                scoreRed+=x2_SCORE;
            else
                scoreRed++;
        }
        else{
            if (Math.abs(SPIN_FACTOR)>=SPIN_FACTOR_ORANGE)
                scoreBlue+=x4_SCORE;
            else if (Math.abs(SPIN_FACTOR)>=SPIN_FACTOR_APPLE)
                scoreBlue+=x2_SCORE;
            else
                scoreBlue++;
        }
        toss = false;
    }

    private void ifHitFruit() {
        isOverlappedFruit = false;
        Fruit fruitTemp = fruitRemoval.get(slicedFruitIndex);
        fruitTemp.setSliced(true);
        fruitTemp.addBuff();
        fruitRemoval.set(slicedFruitIndex, fruitTemp);
        knifeRemoval.removeIndex(0);
        nextKnifeAdd();
        stuckKnifeAdd();
        knifeCount();
        if(gameFinished){
            lastHitFruit = fruitRemoval.get(slicedFruitIndex);
            fruitRemoval.removeIndex(slicedFruitIndex);
        }
        toss = false;
        if(turn)
            scoreRed+=FRUIT_HIT_SCORE;
        else
            scoreBlue+=FRUIT_HIT_SCORE;
    }

    private void ifHitKnife() {
        Knife knifeTemp = knifeRemoval.get(0);
        knifeRemoval.clear();
        knifeTemp.setHit(true);
        knifeRemoval.add(knifeTemp);
        knifeCount();
        System.out.println(1);
        if(gameFinished){
            lastHitKnife = knifeRemoval.get(0);
        }
        isOverlappedKnife = false;
        toss = false;
        knifeTurning = true;
    }

    private void fruitCollisionCheck() {
        Rectangle thrown;
        int index = 0;
        for(Fruit fruit: fruitRemoval){     //slice fruit check
            thrown = knifeRemoval.get(0).sprite.getBoundingRectangle();

            Rectangle fruit1 = fruit.sprite1.getBoundingRectangle();
            Rectangle fruit2 = fruit.sprite2.getBoundingRectangle();
            fruit1.setWidth(fruit1.getWidth()*5/7);
            fruit2.setWidth(fruit2.getWidth()*5/7);
            if((thrown.overlaps(fruit1) || thrown.overlaps(fruit2)) && !fruit.getSliced() && !knifeRemoval.get(0).getHit()){
                isOverlappedFruit = true;
                slicedFruitIndex = index;
            }
            index++;
        }
    }

    private void knifeCollisionCheck() {
        Rectangle thrown;
        if(SPIN_DIRECTION==1) {
            thrown =  knifeRemoval.get(0).sprite.getBoundingRectangle();
            thrown.setHeight(thrown.getHeight()/1.5f);  //these divisions are for current velocity of knife and wood.
            thrown.setWidth(thrown.getWidth()/6f);
        }else{
            thrown = knifeRemoval.get(0).sprite.getBoundingRectangle();
            thrown.setHeight(thrown.getHeight()/1.5f);
            thrown.setWidth(thrown.getWidth()/3f);
        }
        for(Knife knife : stuckKnives){ //
            Rectangle stuck = knife.sprite.getBoundingRectangle();
            if(SPIN_DIRECTION == 1){
                stuck.setWidth(stuck.getWidth()/1.5f);
                stuck.setHeight(stuck.getHeight()/1.5f);
            }else{
                stuck.setWidth(stuck.getWidth()/2f);
                stuck.setHeight(stuck.getHeight()/1.5f);
            }
        if(stuck.overlaps(thrown) && !knifeRemoval.get(0).getHit()){
            isOverlappedKnife = true;
        }
    }
    }

    private void updateKnifeHit(float delta) {
        if (knifeRemoval.get(0).getHit()) {
            knifeRemoval.get(0).updateHit(delta);
            if (knifeRemoval.get(0).getPosition().y < -knifeRemoval.get(0).sprite.getHeight()) {
                knifeRemoval.removeIndex(0);
                knifeTurning = false;
                nextKnifeAdd();
            }
        }
    }

    public void render(SpriteBatch batch) {
        if(!gameFinished) {
            gameResumeRender(batch);
        }else{
            gameFinishRender(batch);
        }
    }

    private void gameFinishRender(SpriteBatch batch) {
        if(lastHitKnife != null){
            lastHitKnife.renderHit(batch);
        }
        if(lastHitFruit != null){
            lastHitFruit.renderSlice(batch);
        }
        for(StuckKnife k : stuckKnives){
                k.renderFinished(batch);
            }
        woodFinishedAnimation.render(batch);
        for(Fruit fruit: fruitRemoval){
            fruit.renderFinished(batch);
        }
    }

    private void gameResumeRender(SpriteBatch batch) {
        for(int i=0; i<stuckKnives.size();i++){
            stuckKnives.get(i).render(batch);
        }
        if(knifeRemoval.get(0).getHit()){
            knifeRemoval.get(0).renderHit(batch);
        }else {
            knifeRemoval.get(0).render(batch);
        }

        wood.render(batch);

        for(Fruit fruit: fruitRemoval){
            if(fruit.getSliced()){
                fruit.renderSlice(batch);
            }else{
                fruit.render(batch);
            }
        }
    }

    public void nextKnifeAdd(){
        if(turn) {
            knifeRemoval.add(new Knife("knife1/drawable-xxxhdpi/blue_knife.png"));
        }else{
            knifeRemoval.add(new Knife("knife1/drawable-xxxhdpi/red_knife.png"));
        }
    }

    public void stuckKnifeAdd(){
        if(turn){
            stuckKnives.add(new StuckKnife("knife1/drawable-xxxhdpi/red_knife.png",
                    wood.sprite.getHeight()));
        }else {
            stuckKnives.add(new StuckKnife("knife1/drawable-xxxhdpi/blue_knife.png",
                    wood.sprite.getHeight()));
        }
    }

    public void knifeCount(){
        if(turn)
            redKnife-=1;
        else
            blueKnife-=1;

        if(redKnife <= 0 && blueKnife <= 0){
            gameFinished = true;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(!toss)
            {turn=!turn;}
        toss = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private Fruit orange(float random){
        return new Fruit("knife1/drawable-xxxhdpi/orange_half_1.png",
                "knife1/drawable-xxxhdpi/orange_half_2.png",
                "knife1/drawable-xxxhdpi/orange_leaf_2.png",
                wood.sprite.getHeight()/2,
                random, ORANGE_TYPE);
    }
    private Fruit apple(float random){
        return new Fruit("knife1/drawable-xxxhdpi/apple_half_2.png",
                "knife1/drawable-xxxhdpi/apple_half_1.png",
                "knife1/drawable-xxxhdpi/apple_leaf.png",
                wood.sprite.getHeight()/2,
                random, APPLE_TYPE);
    }
    private Fruit pear(float random){
        return new Fruit("knife1/drawable-xxxhdpi/apple_half_2.png",
                "knife1/drawable-xxxhdpi/orange_half_2.png",
                "knife1/drawable-xxxhdpi/apple_leaf.png",
                wood.sprite.getHeight()/2,
                random, PEAR_TYPE);
    }
}
