package com.szunwu.jumpnrun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szunwu.jumpnrun.screens.PlayScreen;

/**
 * Main game loop
 * Can have multiple Screens f.e Playscreen
 */
public class GameMain extends Game {
	public SpriteBatch batch;
	//draws Textures on the screen

	public static final int V_WIDTH = 240;
	public static final int V_HEIGHT = 120;
	//max width and height of game window

	public static final int MAP_WIDTH = 800;
	public static final int MAP_HEIGHT = 120;
	//size of map

	public static final float PPM = 100;
	//Pixels per Meter

	//called on start of game
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	//rendering
	@Override
	public void render () {
		super.render();
	}

	// runs when app is destroyed
	@Override
	public void dispose () {
	}
}
