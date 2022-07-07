package com.szunwu.jumpnrun.entities.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;

import java.util.SortedMap;

// TODO comment this class
public class Player extends Entity {

    public Body body;

    public Player(World world) {
        super(world);
    }

    @Override
    public void defineEntity() {
        //new Body for player, set position and type of it, then add it to world
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100 / GameMain.PPM, 100 / GameMain.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        //create new fixture for body and create the shape to be shown on screen
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / GameMain.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }

    @Override
    public void handleInput(float dt, OrthographicCamera gamecam) {
        System.out.println((int)(body.getPosition().x * GameMain.PPM));
        //move right
        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= DEFAULT_SPEED)
            body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);

        //move left
        if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -DEFAULT_SPEED)
            body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);

        //jump
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && getState() != State.JUMPING){
            this.body.applyLinearImpulse(new Vector2(0, 2f), this.body.getWorldCenter(), true);
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
}
