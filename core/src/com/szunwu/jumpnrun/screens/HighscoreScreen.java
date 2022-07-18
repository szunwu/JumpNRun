package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.szunwu.jumpnrun.GameMain;

import java.util.Stack;

public class HighscoreScreen implements Screen{

    private GameMain game;
    private Stage stage;

    public HighscoreScreen(GameMain gameMain){
        game = gameMain;

        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //tell stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

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
        stage.dispose();


    }
}
