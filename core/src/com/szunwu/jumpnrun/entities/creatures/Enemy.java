package com.szunwu.jumpnrun.entities.creatures;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;
import com.szunwu.jumpnrun.scenes.Hud;
import com.szunwu.jumpnrun.utils.BordersForEnemies;

import java.util.ArrayList;

public class Enemy extends Entity {

    public Vector2 velocoty;
    private int positive = 1;
    private ArrayList<Rectangle> rectangles;
    int spawn_x, spawn_y;
    private TextureAtlas atlas;
    private TextureRegion enemyLeft, enemyRight, enemyDead;
    private boolean setToDestroy;
    private boolean destroyed;
    private int stateTime;

    private Hud hud;

    public Enemy(World world, int spawn_x, int spawn_y, Hud hud) {
        super(world, spawn_x, spawn_y, null);
        this.spawn_x = spawn_x;
        this.spawn_y = spawn_y;
        this.setToDestroy = false;
        this.destroyed = false;
        this.hud = hud;

        atlas = new TextureAtlas("enemySprites/enemySprites.txt");
        enemyLeft = new TextureRegion(atlas.findRegion("boss"), 0, 0, 128, 128);
        enemyRight = new TextureRegion(atlas.findRegion("boss"), 128, 0, 128, 128);
        enemyDead = new TextureRegion(atlas.findRegion("boss"), 256, 0, 128, 128);
        setBounds(0, 0, 20 / GameMain.PPM, 20 / GameMain.PPM);
        setRegion(enemyLeft);
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
        fdef.filter.categoryBits = GameMain.ENEMY_BIT;
        body.createFixture(fdef).setUserData(this);
        body.setActive(false);
        velocoty = new Vector2(1, 0);

        rectangles = BordersForEnemies.getRectangles();

        PolygonShape head = new PolygonShape();
        Vector2[] vector2s = new Vector2[4];
        vector2s[0] = new Vector2(-4, 8).scl(1 / GameMain.PPM);
        vector2s[1] = new Vector2(4, 8).scl(1 / GameMain.PPM);
        vector2s[2] = new Vector2(-2, 3).scl(1 / GameMain.PPM);
        vector2s[3] = new Vector2(2, 3).scl(1 / GameMain.PPM);
        head.set(vector2s);
        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = GameMain.ENEMY_HEAD_BIT;
        body.createFixture(fdef).setUserData(this);
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
    public void die() {setToDestroy = true;}

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

    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
            setRegion(enemyDead);
            TextureRegion empty = new TextureRegion(atlas.findRegion("boss"), 0, 0, 1, 1);
            setRegion(empty);
            //TODO: body disappears after 1s
        } else if (!destroyed) {
            if (velocoty.x <= 0) {
                setRegion(enemyLeft);
            } else {
                setRegion(enemyRight);
            }
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 0.04f);
        }
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
        else {
            TextureRegion empty = new TextureRegion(atlas.findRegion("boss"), 0, 0, 1, 1);
            this.setRegion(empty);
        }
    }

    public void addScore(int scoreAdded){
        this.hud.addScore(scoreAdded);
    }
}
