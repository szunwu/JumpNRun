package com.szunwu.jumpnrun.entities.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;

import java.util.SortedMap;

// TODO comment this class
public class Player extends Entity {

    public Body body;
    private TextureRegion playerStand;
    private TextureAtlas atlas;

    public Player(World world, int spawn_x, int spawn_y) {
        super(world, spawn_x, spawn_y, new TextureAtlas("playerTextures/player1.txt"));
        atlas = new TextureAtlas("playerTextures/player1.txt");
        playerStand = new TextureRegion(atlas.findRegion("player128"), 0, 0, 128, 128);
        setBounds(0, 0, 20 / GameMain.PPM, 20 / GameMain.PPM);
        setRegion(playerStand);
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
        fdef.shape = shape;
        body.createFixture(fdef);

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

    @Override
    public void die() {
    }

    @Override
    public State getState(){
        if(body.getLinearVelocity().y > 0 || body.getLinearVelocity().y < 0){
            return State.JUMPING;
        }
        return null;
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 0.03f);
    }
}
