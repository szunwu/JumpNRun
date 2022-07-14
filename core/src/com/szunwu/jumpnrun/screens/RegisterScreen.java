package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.szunwu.jumpnrun.GameMain;

public class RegisterScreen implements Screen{

    private GameMain game;

    private Stage stage;

    public RegisterScreen(GameMain gameMain){
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

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        //set skin for buttons
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Label Name = new Label("Name", skin);
        Label Passwort = new Label("Passwort", skin);
        TextButton Register = new TextButton("Registrieren", skin);
        TextField EingabeName = new TextField("", skin);







    }

    @Override
    public void render(float delta) {

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
