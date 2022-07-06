package com.szunwu.jumpnrun.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity extends Sprite {

    public World world;
    public Body body;
    public final float DEFAULT_SPEED = 1f;

    public enum State { //state to see what is the state of Entity
        JUMPING,
        FALLING
    }

    public Entity(World world){
        //create new Entity
        this.world = world;
        defineEntity();
    }

    public abstract void defineEntity();

    public abstract void handleInput(float dt, OrthographicCamera gamecam);

    public abstract void die();

    public abstract State getState();
}