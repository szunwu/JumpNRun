package com.szunwu.jumpnrun.entities.statics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity extends Sprite {

    public World world;
    public Body body;
    private int x, y;
    private float speed, damage, health;
    public final float DEFAULT_SPEED = 2f;

    public Entity(World world){
        this.world = world;
        defineEntity();
    }

    public abstract void defineEntity();

    public abstract void handleInput(float dt, OrthographicCamera gamecam);

}