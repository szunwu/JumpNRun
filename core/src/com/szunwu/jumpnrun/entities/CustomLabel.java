package com.szunwu.jumpnrun.entities;

/**
 * class to creating Labels
 * can be customised
 */

public class CustomLabel extends com.badlogic.gdx.scenes.scene2d.ui.Label{
    private String text;

    public CustomLabel(final CharSequence text, final LabelStyle style){
        super(text, style);
        this.text = text.toString();
    }

    public void updateText(final CharSequence text){
        this.setText(text);
    }
}
