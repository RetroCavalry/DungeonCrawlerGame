package com.dungeoncrawler.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
    public static final int SIZE = 16;

    private TextureRegion texture;

    public Tile(TextureRegion texture) {
        this.texture = texture;
    }

    public TextureRegion getTexture() {
        return texture;
    }
}

