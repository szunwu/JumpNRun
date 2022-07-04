package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.scenes.Hud;

/**
 * PlayScreen class is responsible for loading Tiled Maps and displaying them
 * It also handles user input
 */
// TODO: 6/28/22 Create other class that handles user input (something more universal)

public class PlayScreen implements Screen {

    protected Screen screen; //Screen on which we draw

    private GameMain game; //used to specify where to draw

    private OrthographicCamera gamecam; //camera

    private Viewport gameport; //sets the resolution

    private Hud hud;

    private TmxMapLoader mapLoader;

    private TiledMap map;

    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(GameMain game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(GameMain.V_WIDTH, GameMain.V_HEIGHT, gamecam);
        //uses resolution from GameMain; if resized will create black bars to fill to aspect ratio
        hud = new Hud(game.batch);

        //map loading
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("testMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, (GameMain.V_HEIGHT/GameMain.MAP_HEIGHT));  //map rendering and set map scale
        gamecam.position.set(gameport.getScreenWidth() / 2, gameport.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() {

    }

    //runs all the time and renders (a loop)
    @Override
    public void render(float delta) {
        update(delta);  //update
        Gdx.gl.glClearColor(0, 0, 0, 1);  //renders solid color background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(gamecam);
        renderer.render(); //render map

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); //defines what will be shown by the camera
        hud.stage.draw();


    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();       //update cam for movement
        renderer.setView(gamecam);
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        if(screen != null){
            screen.hide();
        }
    }

    //first input handler
    private void handleInput(float dt){
        //when a key is pressed do this
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            //if d pressed move right
            gamecam.position.x += 200 * dt;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            //if d pressed move right
            gamecam.position.x -= 200 * dt;
        }
    }
}
