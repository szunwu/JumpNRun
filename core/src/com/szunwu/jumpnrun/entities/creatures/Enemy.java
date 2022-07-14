package com.szunwu.jumpnrun.entities.creatures;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;
import com.szunwu.jumpnrun.utils.BordersForEnemies;

import java.util.ArrayList;

public class Enemy extends Entity {

    public Vector2 velocoty;
    private int positive = 1;
    private ArrayList<Rectangle> rectangles;
    int spawn_x, spawn_y;

    public Enemy(World world, int spawn_x, int spawn_y) {
        super(world, spawn_x, spawn_y, null);
        this.spawn_x = spawn_x;
        this.spawn_y = spawn_y;
    }

    @Override
    public void defineEntity(int spawn_x, int spawn_y) {
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
        velocoty = new Vector2(1, 0);

        rectangles = BordersForEnemies.getRectangles();
    }

    @Override
    public void handleInput(float dt, OrthographicCamera gamecam) {
        //get all borders for enemies
        for (Rectangle rectangle : rectangles) {
            //if enemy is on tile
            if ((int) rectangle.getX() <= (int) (body.getPosition().x * GameMain.PPM) &&(int) (rectangle.getX() + 5) >= (int) (body.getPosition().x * GameMain.PPM) ) {
                //reverse its velocity
                reverseVelocity(true, false);
            }
        }

        //add velocity but not faster than speed
        if(body.getLinearVelocity().x <= DEFAULT_SPEED && positive == 1)
            body.setLinearVelocity(velocoty);
        else if (body.getLinearVelocity().x >= -DEFAULT_SPEED && positive == -1)
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
        positive = -positive;
    }
}
