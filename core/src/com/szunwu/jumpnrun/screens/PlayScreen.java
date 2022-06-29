package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    public PlayScreen(GameMain game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(GameMain.V_WIDTH, GameMain.V_HEIGHT, gamecam);
        //uses resolution from GameMain; if resized will create black bars to fill to aspect ratio
        hud = new Hud(game.batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput(delta); //check for user inputs
        Gdx.gl.glClearColor(0, 0, 0, 1);  //renders solid color background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); //defines what will be shown by the camera
        hud.stage.draw();


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
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            //if a is pressed add one to lifeRemaining Label and update it
            hud.lifeRemainingLabel.updateText(Integer.toString(hud.lifeRemaining++));
        }
    }
}
