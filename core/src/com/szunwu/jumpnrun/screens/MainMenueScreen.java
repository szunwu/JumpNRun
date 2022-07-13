package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.szunwu.jumpnrun.GameMain;

public class MainMenueScreen implements Screen {

    private GameMain game;
    private Stage stage;

    private Button PlayButton;
    private Button HigscoreButton;
    private Button CreditsButton;

    public MainMenueScreen(GameMain gameMain){
        game = gameMain;

        // add Stage with Viewport as argument -> controller for user input
        stage = new Stage(new ScreenViewport());

        // tell Stage to act and draw -> without no actions after user input/no content
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        //create table that fills the screen -> everything later is added to the table
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

    }




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);  //renders solid color background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
