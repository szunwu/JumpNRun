package com.szunwu.jumpnrun.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * class used to create Bitmap fonts form FreeTypeFont
 * uses {@param int size} for size
 * {@param int borderWidth} for border size between letters
 * {@param int shadowOffsetX} for shadow offset X
 * {@param int shadowOffsetY} for shadow offset Y
 * {@param Color color} for color
 * {@param String name} for name of the font in folder "/assets"
 */
public class FontGenerator {

    public static BitmapFont getBitmapFromTrueFont(int size, int borderWidth, int shadowOffsetX, int shadowOffsetY, Color color, String fontName) {
        //class used to convert ttf to Bitmap
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        //parameters = specs of font f.e size etc.
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = borderWidth;
        parameter.color = color;
        parameter.shadowOffsetX = shadowOffsetX;
        parameter.shadowOffsetY = shadowOffsetY;
        parameter.shadowColor = new Color(0, 0, 0, 0.75f);
        //creating new font
        BitmapFont font24 = generator.generateFont(parameter);
        //closing the generator
        generator.dispose();

        return font24;
    }

}
