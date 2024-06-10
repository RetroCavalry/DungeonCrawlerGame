package com.dungeoncrawler.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {
    private Texture texture;
    private TextureRegion[][] frames;

    public SpriteSheet(String path, int frameWidth, int frameHeight) {
        texture = new Texture(path);
        frames = TextureRegion.split(texture, frameWidth, frameHeight);
    }

    public TextureRegion[] getFrames() {
        int rows = texture.getHeight() / frames[0][0].getRegionHeight();
        int cols = texture.getWidth() / frames[0][0].getRegionWidth();
        TextureRegion[] framesLinear = new TextureRegion[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                framesLinear[i * cols + j] = frames[i][j];
            }
        }
        return framesLinear;
    }
}

