package com.szunwu.jumpnrun.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.CustomLabel;
import com.szunwu.jumpnrun.utils.FontGenerator;


/**
 * shows the hud on a Screen
 */

public class Hud implements Disposable {
    public Stage stage; //handles the viewport and distributes input
    //for hud: new separate camera and viewport -> game can move while hud always stays in one place
    private Viewport viewport;

    //all counters and stuff
    private int worldTimer;
    private  float timeCount;
    private  int score;
    public int lifeRemaining;

    //labels for display
    public CustomLabel scoreLabel;
    public CustomLabel lifeRemainingLabel;
    private CustomLabel timeLabel;
    private CustomLabel countTimeLabel;
    private CustomLabel worldLabel;

    private CustomLabel lifeLabel;

    private BitmapFont font;
    private boolean pause = false;
    private Table tablePause;

    public Hud(SpriteBatch batch){
        worldTimer = 0;
        timeCount = 0;
        score = 0;
        lifeRemaining = 3;

        viewport = new FitViewport(GameMain.V_WIDTH, GameMain.V_HEIGHT, new OrthographicCamera()); //creating new Viewport
        stage = new Stage(viewport, batch); //stage == "empty box"
                                            //if we put something in it everything would "fall off"
                                            //to put things in stage -> table

        Table table = new Table(); //widgets can be displayed using table
        table.top(); //default table is placed in centre we need it on the top
        table.setFillParent(true); //table is size of the stage

        //generating font from FontGenerator
        font = FontGenerator.getBitmapFromTrueFont(30, 1, 3, 3, Color.WHITE, "fontHud.ttf");

        //creating Labels
        scoreLabel = new CustomLabel(Integer.toString(score), new Label.LabelStyle(font, Color.WHITE));
        lifeRemainingLabel = new CustomLabel(Integer.toString(lifeRemaining), new Label.LabelStyle(font, Color.WHITE));
        timeLabel = new CustomLabel("SCORE", new Label.LabelStyle(font, Color.WHITE));
        countTimeLabel = new CustomLabel(Integer.toString(worldTimer), new Label.LabelStyle(font, Color.WHITE));
        worldLabel = new CustomLabel("TIME", new Label.LabelStyle(font, Color.WHITE));
        lifeLabel = new CustomLabel("LIFE", new Label.LabelStyle(font, Color.WHITE));

        Label.LabelStyle fontStyle = new Label.LabelStyle(font, Color.WHITE);
        Label pauseText = new Label("PAUSE", fontStyle);
        Label pauseInfo = new Label("To resume click ESC", fontStyle);


        float ptToPx = 1.3189124498483775f*30+0.2510259653764754f;
        float scale = ptToPx / GameMain.V_HEIGHT;
        //scale -= 0.15f;

        scoreLabel.setFontScale(scale);
        lifeRemainingLabel.setFontScale(scale);
        timeLabel.setFontScale(scale);
        countTimeLabel.setFontScale(scale);
        worldLabel.setFontScale(scale);
        lifeLabel.setFontScale(scale);

        System.out.println(scale);

        //adding Labels to 1st row
        table.add(lifeLabel).expandX().padTop(1);
        table.add(worldLabel).expandX().padTop(1);
        table.add(timeLabel).expandX().padTop(1);

        table.row(); // creating new row

        //2nd row
        table.add(lifeRemainingLabel).expandX();
        table.add(countTimeLabel).expandX();
        table.add(scoreLabel).expandX();

        tablePause = new Table();
        tablePause.setFillParent(true);
        tablePause.row();
        tablePause.add(pauseText).expandX();
        tablePause.row();
        pauseInfo.setFontScale(scale + 0.2f);
        tablePause.add(pauseInfo).expandX();

        stage.addActor(table); //adding table to stage
    }

    public void update(float dt){
        if(pause){
            stage.addActor(tablePause);
        } else {
            tablePause.remove();
            timeCount += dt;
            if (timeCount >= 1) {
                worldTimer++;
                countTimeLabel.setText(worldTimer);
                timeCount = 0;
            }
        }
    }

    public void addScore(int value){
        score += value;
        scoreLabel.setText(Integer.toString(score));
    }

    public void updateLife(int nLife){
        lifeRemaining = nLife;
        lifeRemainingLabel.setText(Integer.toString(lifeRemaining));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setPause(boolean pause){
        this.pause = pause;
    }

}
