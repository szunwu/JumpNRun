package com.szunwu.jumpnrun.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.statics.Entity;

public class Player extends Entity {

    public Body body;


    public Player(World world) {
        super(world);
    }

    @Override
    public void defineEntity() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100 / GameMain.PPM, 100 / GameMain.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / GameMain.PPM);

        fdef.shape = shape;
        body.createFixture(fdef);
    }

    @Override
    public void handleInput(float dt, OrthographicCamera gamecam) {
        if(Gdx.input.isKeyPressed(Input.Keys.D) && this.body.getLinearVelocity().x <= DEFAULT_SPEED){
            //if d pressed move right
            this.body.applyLinearImpulse(new Vector2(0.05f, 0), this.body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)&& this.body.getLinearVelocity().x <= DEFAULT_SPEED){
            //if d pressed move right
            this.body.applyLinearImpulse(new Vector2(-0.05f, 0), this.body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            this.body.applyLinearImpulse(new Vector2(0, 2f), this.body.getWorldCenter(), true);
        }
    }
}
