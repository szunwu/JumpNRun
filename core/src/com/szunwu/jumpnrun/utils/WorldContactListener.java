package com.szunwu.jumpnrun.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.szunwu.jumpnrun.GameMain;
import com.szunwu.jumpnrun.entities.creatures.Enemy;
import com.szunwu.jumpnrun.entities.creatures.Player;

/**
 * gets Called when 2 fixtures colide in B2World
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //collision detection get fixtures
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //get bits from collision and ors them
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch(cDef){
            //check what collided
            case GameMain.ENEMY_HEAD_BIT | GameMain.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == GameMain.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).die();
                    ((Enemy) fixA.getUserData()).addScore(200);
                }
                else {
                    ((Enemy) fixB.getUserData()).die();
                    ((Enemy) fixB.getUserData()).addScore(200);
                }
                break;
            case GameMain.PLAYER_BIT | GameMain.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == GameMain.PLAYER_BIT) {
                    ((Enemy) fixB.getUserData()).die();
                    //((Enemy) fixB.getUserData()).addScore(-200);
                    ((Player) fixA.getUserData()).hit("enemy");
                } else {
                    ((Enemy) fixA.getUserData()).die();
                    //((Enemy) fixA.getUserData()).addScore(-200);
                    ((Player) fixB.getUserData()).hit("enemy");
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
