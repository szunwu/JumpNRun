package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
        TextField enterName = new TextField("", skin);
        TextField enterPwort = new TextField("", skin);
        TextButton Login = new TextButton("Login", skin);

        table.add(Name).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(enterName).fillX().uniformX();
        table.row();
        table.add(Passwort).fillX().uniformX();
        table.row();
        table.add(enterPwort).fillX().uniformX();
        table.row();
        table.add(Register).fillX().uniformX();
        table.row();
        table.add(Login);

        stage.draw();

        Login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GameMain.Login);
                dispose();
            }
        });

        Register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GameMain.Character);
                dispose();
            }
        });








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
