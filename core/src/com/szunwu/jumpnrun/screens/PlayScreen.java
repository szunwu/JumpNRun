package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.creatures.Enemy;
import com.szunwu.jumpnrun.entities.creatures.Player;
import com.szunwu.jumpnrun.scenes.Hud;
import com.szunwu.jumpnrun.utils.B2WorldCreator;
import com.szunwu.jumpnrun.utils.BordersForEnemies;

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

    //TiledMap var
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d var
    private World world;

    private Box2DDebugRenderer b2dr;

    //player
    private Player player;

    //enemies
    private Enemy enemy;

    public PlayScreen(GameMain game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(GameMain.V_WIDTH / GameMain.PPM, GameMain.V_HEIGHT / GameMain.PPM, gamecam);
        //uses resolution from GameMain; if resized will create black bars to fill to aspect ratio
        hud = new Hud(game.batch);

        //map loading
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("newTestMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameMain.PPM);  //map rendering and set map scale
        gamecam.position.set(gameport.getScreenWidth() / 2f, gameport.getWorldHeight() / 2f, 0);

        world = new World(new Vector2(0, -9.81f), true);  //new 2d World, 1nd parm=gravity
        b2dr = new Box2DDebugRenderer(); //renderer for Box2d in debug mode

        player = new Player(world);
        enemy = new Enemy(world);

        new B2WorldCreator(world, map);
        new BordersForEnemies(world, map);
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

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); //defines what will be shown by the camera
        hud.stage.draw();


    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);    //def of how the physics sim is running
        gamecam.position.x = player.body.getPosition().x;   //center camera on player

        gamecam.update();       //update cam for movement
        renderer.setView(gamecam);  //draw only what the camera can see
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
        //delete unused
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    //first input handler
    private void handleInput(float dt){
        //when a key is pressed do this
        player.handleInput(dt, this.gamecam);
    }
}
