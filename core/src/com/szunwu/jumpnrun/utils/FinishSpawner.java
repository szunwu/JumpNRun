package com.szunwu.jumpnrun.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.szunwu.jumpnrun.GameMain;

public class FinishSpawner {

    public static Rectangle createSpawners(TiledMap map){
        //creating ground bodies/fixtures for tiles from map
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            //creating Rectangles for every object group from map
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            rectangle.setPosition(rectangle.x * GameMain.SCALE, rectangle.y * GameMain.SCALE);
            rectangle.setSize(rectangle.getWidth() * GameMain.SCALE, rectangle.getHeight() * GameMain.SCALE);
            return rectangle;
        }
        return null;
    }

}
