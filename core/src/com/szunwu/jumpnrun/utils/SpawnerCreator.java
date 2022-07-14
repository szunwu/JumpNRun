package com.szunwu.jumpnrun.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.szunwu.jumpnrun.GameMain;

import java.util.ArrayList;

public class SpawnerCreator {

    public static ArrayList<Rectangle> createSpawners(TiledMap map){
        //creating ground bodies/fixtures for tiles from map
        ArrayList<Rectangle> spawners = new ArrayList<>();
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            //creating Rectangles for every object group from map
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            rectangle.setPosition(rectangle.x * GameMain.SCALE, rectangle.y * GameMain.SCALE);
            rectangle.setSize(rectangle.getWidth() * GameMain.SCALE, rectangle.getHeight() * GameMain.SCALE);
            spawners.add(rectangle);
        }
        return spawners;
    }
}
