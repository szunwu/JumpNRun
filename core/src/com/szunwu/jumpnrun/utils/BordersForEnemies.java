package com.szunwu.jumpnrun.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.szunwu.jumpnrun.GameMain;

import java.util.ArrayList;

public class BordersForEnemies {

    private static ArrayList<Rectangle> rectangles;

    public BordersForEnemies(World world, TiledMap map){
        //defines bodies and fixtures for tiles from map
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        rectangles = new ArrayList<>();

        //creating ground bodies/fixtures for tiles from map
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            //creating Rectangles for every object group from map
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            rectangle.setPosition(rectangle.x * GameMain.SCALE, rectangle.y * GameMain.SCALE);
            rectangle.setSize(rectangle.getWidth() * GameMain.SCALE, rectangle.getHeight() * GameMain.SCALE);

            //not movable body
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2) / GameMain.PPM, (rectangle.getY() + rectangle.getHeight()/2) / GameMain.PPM);

            //create
            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth()/2 / GameMain.PPM, rectangle.getHeight()/2 / GameMain.PPM);
            fDef.shape = shape;
            body.createFixture(fDef);
            rectangles.add(((RectangleMapObject) object).getRectangle());

        }
    }

    public static ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }
}
