package com.szunwu.jumpnrun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.szunwu.jumpnrun.screens.*;

/**
 * Main game loop
 * Can have multiple Screens f.e Playscreen
 */
public class GameMain extends Game {
	public SpriteBatch batch;
	//draws Textures on the screen

	public static final int V_WIDTH = 369;
	public static final int V_HEIGHT = 13*16;
	//max width and height of game window

	public static final int MAP_WIDTH = 500*16;
	public static final int MAP_HEIGHT = 13*16;
	public static final float SCALE = 0.25f;
	//size of map

	public static final float PPM = 100;
	//Pixels per Meter

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short ENEMY_HEAD_BIT = 8;


	private MainMenueScreen mainMenueScreen;
	private PlayScreen playScreen;
	private CharacterScreen characterScreen;
	private CreditsScreen creditsScreen;
	private LoginScreen loginScreen;
	private RegisterScreen registerScreen;
	private HighscoreScreen highscoreScreen;
	private GameOverScreen gameOverScreen;

	public final static int MainMenue = 0;
	public final static int Play = 1;
	public final static int Character = 2;
	public final static int Credits = 3;
	public final static int Login = 4;
	public final static int Register = 5;
	public final static int Highscore = 6;
	public final static int GameOverScreen = 7;

	public final static boolean DEBUG_MODE = true;

	public enum Screens  {
		MainMenu,
		Play,
		Character,
		Login,
		Register,
		Highscore,
		GameOverScreen,
		FinishScreen
	}


	//called on start of game
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenueScreen(this));
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


	public void changeScreen(int screen){
		//method to switch between screens
		switch (screen){
			case MainMenue:
				if (mainMenueScreen == null) mainMenueScreen = new MainMenueScreen(this);
				this.setScreen(mainMenueScreen);
				break;
			case Play:
				if (playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case Character:
				if (characterScreen == null) characterScreen = new CharacterScreen(this);
				this.setScreen(characterScreen);
				break;
			case Credits:
				if (creditsScreen == null) creditsScreen = new CreditsScreen(this);
				this.setScreen(creditsScreen);
				break;
			case Login:
				if (loginScreen == null) loginScreen = new LoginScreen(this);
				this.setScreen(loginScreen);
				break;
			case Register:
				if (registerScreen == null) registerScreen = new RegisterScreen(this);
				this.setScreen(registerScreen);
				break;
			case  Highscore:
				if (highscoreScreen == null) highscoreScreen = new HighscoreScreen(this);
				break;
			case GameOverScreen:
				if (gameOverScreen == null) gameOverScreen = new GameOverScreen(this);
				break;

		}
	}

	public void changeScreen(Screens screens){
		switch (screens){
			case GameOverScreen:
				if (gameOverScreen == null) setScreen(new GameOverScreen(this));
				break;
		}
	}

	public void finish(int time, int life, int score){
		setScreen(new FinishScreen(this, score, time, life));
	}

}
