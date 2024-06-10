package com.dungeoncrawler.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    protected Vector2 position;

    public Enemy() {
        position = new Vector2();
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);
}
