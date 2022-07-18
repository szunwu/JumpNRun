package com.szunwu.jumpnrun.entities.creatures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.xerces.internal.impl.dv.xs.AnyURIDV;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;
import com.szunwu.jumpnrun.scenes.Hud;

import javax.lang.model.element.AnnotationMirror;
import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.SortedMap;

// TODO comment this class
public class Player extends Entity {

    public Body body;
    private TextureRegion playerStand;
    private TextureAtlas atlas;

    private int lifeLeft;
    private boolean isDead;
    private Hud hud;

    public State currState;
    private State prevState;

    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private boolean runningRight;
    private float stateTimer;

    public Player(World world, int spawn_x, int spawn_y, int life, Hud hud) {
        super(world, spawn_x, spawn_y, new TextureAtlas("playerTextures/player1/player1.txt"));
        atlas = new TextureAtlas("playerTextures/player1/player1.txt");
        playerStand = new TextureRegion(atlas.findRegion("player1_laufen1"), 0, 0, 128, 128);
        setBounds(0, 0, 20 / GameMain.PPM, 20 / GameMain.PPM);
        setRegion(playerStand);
        this.lifeLeft = life;
        this.hud = hud;
        this.currState = State.STANDING;
        this.prevState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i <= 1; i++){
            frames.add(new TextureRegion(getTexture(), i * 128, 0, 128, 128));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(getTexture(), 256, 0, 128, 128));
        playerJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    @Override
    public void defineEntity(int spawn_x , int spawn_y) {
        //new Body for player, set position and type of it, then add it to world
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawn_x / GameMain.PPM, spawn_y / GameMain.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        //create new fixture for body and create the shape to be shown on screen
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / GameMain.PPM);
        fdef.filter.categoryBits = GameMain.PLAYER_BIT;
        fdef.filter.maskBits = GameMain.DEFAULT_BIT |
                GameMain.ENEMY_BIT |
                GameMain.ENEMY_HEAD_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GameMain.PPM, 7 / GameMain.PPM), new Vector2(2 / GameMain.PPM, 7 / GameMain.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        body.createFixture(fdef).setUserData("head");
    }

    @Override
    public void handleInput(float dt, OrthographicCamera gamecam) {
        //System.out.println((int)(body.getPosition().x * GameMain.PPM));
        //move right
        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= DEFAULT_SPEED)
            body.applyLinearImpulse(new Vector2(0.2f, 0), body.getWorldCenter(), true);

        //move left
        if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -DEFAULT_SPEED)
            body.applyLinearImpulse(new Vector2(-0.2f, 0), body.getWorldCenter(), true);

        //jump
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && getState() != State.JUMPING){
            this.body.applyLinearImpulse(new Vector2(0, 3.5f), this.body.getWorldCenter(), true);
        }
    }

    public void hit(String reason){
        switch (reason){
            case "fall":
                if(lifeLeft <= 0){
                    isDead = true;
                    die();
                } else{
                    body.applyLinearImpulse(new Vector2(0f, 8), this.body.getWorldCenter(), true);
                    lifeLeft--;
                    hud.updateLife(lifeLeft);
                }
                break;
            case "enemy":
                if(lifeLeft <= 0){
                    isDead = true;
                    die();
                } else{
                    body.applyLinearImpulse(new Vector2(0f, 4), this.body.getWorldCenter(), true);
                    lifeLeft--;
                    hud.updateLife(lifeLeft);
                }
                break;
        }

    }

    @Override
    public void die() {
    }

    @Override
    public State getState(){
        if(body.getLinearVelocity().y > 0 || body.getLinearVelocity().y < 0 && body.getPosition().y > 0){
            return State.JUMPING;
        } else if (isDead) {
            return State.DEAD;
        } else if (body.getPosition().y < 0){
            return State.FALLING;
        } else if (body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 0.04f);
        if(getState() == State.FALLING){
            hit("fall");
        }
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt){
        currState = getState();

        TextureRegion region;
        switch (currState){
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            default:
                region = playerStand;
        }
        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currState == prevState ? stateTimer + dt : 0;
        prevState = currState;
        return region;
    }

    public boolean isDead(){
        return isDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }
}
