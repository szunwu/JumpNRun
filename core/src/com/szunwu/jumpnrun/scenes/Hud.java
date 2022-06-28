package com.szunwu.jumpnrun.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szunwu.jumpnrun.GameMain;

/**
 * shows the hud on a Screen
 */

public class Hud {
    public Stage stage; //handles the viewport and distributes input
    //for hud: new separate camera and viewport -> game can move while hud always stays in one place
    private Viewport viewport;

    //all counters and stuff
    private int worldTimer;
    private  float timeCount;
    private  int score;
    private int lifeRemaining;

    //labels for display
    private Label scoreLabel;
    private Label lifeRemainingLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;

    private Label lifeLabel;

    public Hud(SpriteBatch batch){
        worldTimer = 300;
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

        //creating Labels
        scoreLabel = new Label(Integer.toString(score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeRemainingLabel = new Label(Integer.toString(lifeRemaining), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeLabel = new Label("LIFE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //adding Labels to 1st row
        table.add(lifeLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row(); // creating new row

        //2nd row
        table.add(lifeRemainingLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();

        stage.addActor(table); //adding table to stage
    }
}
