package com.szunwu.jumpnrun.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity extends Sprite {

    public World world;
    public Body body;
    public final float DEFAULT_SPEED = 1f;
    public float spawn_x;
    public float spawn_y;

    public TextureAtlas atlas;

    public enum State { //state to see what is the state of Entity
        JUMPING,
        FALLING,
        DEAD,
        STANDING,
        RUNNING,
    }

    public Entity(World world, float spawn_x, float spawn_y, TextureAtlas atlas){
        //create new Entity
        this.world = world;
        this.spawn_x = spawn_x;
        this.spawn_y = spawn_y;
        this.atlas = atlas;
        defineEntity(spawn_x, spawn_y);
    }

    public abstract void defineEntity(float spawn_x, float spawn_y);

    public abstract void handleInput(float dt, OrthographicCamera gamecam);

    public abstract void die();

    public abstract State getState();
}