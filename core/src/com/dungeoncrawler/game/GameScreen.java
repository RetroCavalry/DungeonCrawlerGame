package com.dungeoncrawler.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private DungeonCrawlerGame game;
    private SpriteBatch batch;
    private Texture background;

    public GameScreen(DungeonCrawlerGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("background.png"));
    }


    @Override
    public void show() {
        // Инициализация ресурсов игры
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Обработка изменения размера экрана
    }

    @Override
    public void pause() {
        // Обработка паузы игры
    }

    @Override
    public void resume() {
        // Обработка возобновления игры
    }

    @Override
    public void hide() {
        // Обработка переключения с этого экрана на другой
    }

    @Override
    public void dispose() {
        // Освобождение ресурсов
    }
}
