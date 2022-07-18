package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.szunwu.jumpnrun.GameMain;

public class CharacterScreen implements Screen {

    private GameMain game;

    private Stage stage;

    private Label b;
    private Label g;
    private CheckBox boy;
    private CheckBox girl;

    public CharacterScreen(GameMain gameMain){
        game = gameMain;

        // add Stage with Viewport as argument -> controller for user input
        stage = new Stage(new ScreenViewport());

        // tell Stage to act and draw -> without no actions after user input/no content
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {

        //create table that fills the screen -> everything later is added to the table
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(GameMain.DEBUG_MODE);
        stage.addActor(table);


        //set skin for buttons
        Skin skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));

        b = new Label("männlich",skin);
        g = new Label("weiblich", skin);
        Label headline = new Label("Charakterauswahl", skin);
        boy = new CheckBox("", skin);
        girl = new CheckBox("", skin);
        TextButton play = new TextButton("Play", skin);

        table.add(headline);
        table.row();
        table.add(b);
        table.add(g);
        table.row();
        table.add(boy);
        table.add(girl);

        stage.draw();


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
