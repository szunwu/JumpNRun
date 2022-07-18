package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.Entity;
import com.szunwu.jumpnrun.utils.FontGenerator;

public class FinishScreen implements Screen {

    private Game game;
    private Viewport viewport;
    private Stage stage;
    private BitmapFont font;

    private int score, time, life, total;

    public FinishScreen(Game game, int score, int time, int life) {
        this.score = score;
        this.time = time;
        this.life = life;
        this.game = game;
        viewport = new FitViewport(GameMain.V_WIDTH, GameMain.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((GameMain) game).batch);

        font = FontGenerator.getBitmapFromTrueFont(20, 1, 3, 3, Color.WHITE, "fontHud.ttf");
        Label.LabelStyle fontStyle = new Label.LabelStyle(font, Color.WHITE);

        total = (int)(score - 0.25f * time + 1000 * life);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label finishLabel = new Label("Game Finished", fontStyle);
        Label scoreLabel = new Label("Your Score: " + score, fontStyle);
        Label playAgain = new Label("Click P to Play Again", fontStyle);
        Label exit = new Label("Click ESC to Escape", fontStyle);

        table.add(finishLabel).expandX();
        table.row();
        table.add(scoreLabel).expandX();
        table.row();
        playAgain.setFontScale(0.5f);
        table.add(playAgain).padTop(10f);
        table.row();
        exit.setFontScale(0.5f);
        table.add(exit).padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            System.out.println("play again");
            game.setScreen(new PlayScreen((GameMain) game));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            System.out.println("esc");
            Gdx.app.exit();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
