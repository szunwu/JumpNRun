package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;
import com.szunwu.jumpnrun.entities.creatures.Enemy;
import com.szunwu.jumpnrun.entities.creatures.Player;
import com.szunwu.jumpnrun.scenes.Hud;
import com.szunwu.jumpnrun.utils.B2WorldCreator;
import com.szunwu.jumpnrun.utils.BordersForEnemies;
import com.szunwu.jumpnrun.utils.SpawnerCreator;
import com.szunwu.jumpnrun.utils.WorldContactListener;

import java.util.ArrayList;

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

    private ArrayList<Enemy> enemies;
    private float dt;
    private boolean paused = false;

    public PlayScreen(GameMain game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(GameMain.V_WIDTH / GameMain.PPM, GameMain.V_HEIGHT / GameMain.PPM, gamecam);
        //uses resolution from GameMain; if resized will create black bars to fill to aspect ratio
        hud = new Hud(game.batch);

        //map loading
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("untitled.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, GameMain.SCALE / GameMain.PPM);  //map rendering and set map scale
        gamecam.position.set(gameport.getScreenWidth() / 2f, gameport.getWorldHeight() / 2f, 0);

        world = new World(new Vector2(0, -9.81f), true);  //new 2d World, 1nd parm=gravity
        b2dr = new Box2DDebugRenderer(); //renderer for Box2d in debug mode

        player = new Player(world, 100, 100, 3, hud, game);

        new B2WorldCreator(world, map);
        new BordersForEnemies(world, map);
        enemies = new ArrayList<>();
        for(Rectangle r : SpawnerCreator.createSpawners(map)){
            enemy = new Enemy(world, (int) r.getX(), (int) r.getY(), this.hud);
            enemies.add(enemy);
        }

        world.setContactListener(new WorldContactListener());


    }

    @Override
    public void show() {

    }

    //runs all the time and renders (a loop)
    @Override
    public void render(float delta) {
        if(paused){
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                paused = false;
                System.out.println("unpaused");
                hud.setPause(false);
            }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                update(delta);  //update
            }
        Gdx.gl.glClearColor(0, 0, 0, 1);  //renders solid color background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(gamecam);
        renderer.render(); //render map

        b2dr.render(world, gamecam.combined);

        for(Enemy e : enemies) {
            e.handleInput(delta, gamecam);
        }

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for(Enemy e : enemies){
            e.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); //defines what will be shown by the camera
        hud.stage.draw();

        if(gameOver()){
            gameOverInit();
        }
    }

    public void gameOverInit(){
        //game.setScreen(new GameOverScreen(game));
        game.changeScreen(GameMain.Screens.GameOverScreen);
        dispose();
    }

    public void update(float dt){
        this.dt = dt;
        handleInput(dt);

        world.step(1/60f, 6, 2);    //def of how the physics sim is running

        player.update(dt);
        for(Enemy e : enemies){
            e.update(dt);
            e.body.setActive(e.getX() < player.getX() + 288 / GameMain.PPM);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            paused = true;
            hud.setPause(true);
            System.out.println("paused");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        hud.update(dt);

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
        //enemy.handleInput(dt, this.gamecam);
    }

    public boolean gameOver(){
        if(player.currState == Entity.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }
}
