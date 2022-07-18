package com.szunwu.jumpnrun.screens;

import com.badlogic.gdx.Screen;
import com.szunwu.jumpnrun.GameMain;

import static com.szunwu.jumpnrun.GameMain.Play;

public class LoginScreen implements Screen{

    private GameMain game;
    public LoginScreen(GameMain gameMain){
        game = gameMain;
        gameMain.changeScreen(Play);
    }

    @Override
    public void show() {

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
