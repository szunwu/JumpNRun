package com.szunwu.jumpnrun.entities.creatures;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;

public class Enemy extends Entity {

    public Vector2 velocoty;

    public Enemy(World world) {super(world);}

    @Override
    public void defineEntity() {
        //new Body for player, set position and type of it, then add it to world
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100 / GameMain.PPM, 50 / GameMain.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        //create new fixture for body and create the shape to be shown on screen
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / GameMain.PPM);
        fdef.shape = shape;

        body.createFixture(fdef);
        //velocoty = new Vector2(1, 0);
    }

    @Override
    public void handleInput(float dt, OrthographicCamera gamecam) {

    }

    public void update(float dt){
        body.setLinearVelocity(velocoty);
    }

    @Override
    public void die() {
    }

    @Override
    public State getState() {
        return null;
    }

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocoty.x = -velocoty.x;
        if(y)
            velocoty.y = -velocoty.y;
    }
}
