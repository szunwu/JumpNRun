package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.szunwu.jumpnrun.GameMain;

import java.awt.*;

public class MainMenueScreen implements Screen {

    private GameMain game;
    private Stage stage;



    public MainMenueScreen(GameMain gameMain){
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
        table.setDebug(true);
        stage.addActor(table);


        //set skin for buttons
        Skin skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));

        //create buttons
        TextButton play = new TextButton("Play", skin);
        TextButton highscore = new TextButton("Highscore", skin);
        TextButton credits = new TextButton("Credits", skin);
        TextButton exit = new TextButton("Exit", skin);

        //add buttons to table + fill out x axis + uniform with other cells
        table.add(play).fillX().uniformX();
        //add space between buttons
        table.row().pad(10, 0, 10, 0);
        table.add(highscore).fillX().uniformX();
        table.row();
        table.add(credits).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exit).fillX().uniformX();

        stage.draw();

        //action exit button
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        //action play button
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GameMain.Login);
                dispose();
            }
        });

        //action highscore button
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GameMain.Highscore);
                //dispose();
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
