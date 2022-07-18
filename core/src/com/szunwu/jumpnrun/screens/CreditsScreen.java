package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.szunwu.jumpnrun.GameMain;

public class CreditsScreen implements Screen{

    private GameMain game;

    private Stage stage;


    public CreditsScreen(GameMain gameMain){
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
        table.setDebug(GameMain.DEBUG_MODE);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));

        Label Headline = new Label("Credits", skin);
        Label Wojtec = new Label("Wojtek", skin);
        Label Francis = new Label("Francis", skin);
        Label Ramah = new Label("Ramah",skin);
        Label Hannah = new Label("Hannah", skin);
        Label Svenja = new Label("Svenja", skin);
        TextButton Exit = new TextButton("Exit", skin);

        table.add(Headline);
        table.row();
        table.add(Wojtec);
        table.row();
        table.add(Francis);
        table.row();
        table.add(Ramah);
        table.row();
        table.add(Hannah);
        table.row();
        table.add(Svenja);
        table.row();
        table.add(Exit);

        stage.draw();

        Exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(GameMain.MainMenue);
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
