package com.dungeoncrawler.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private Label label;
    private Texture backgroundTexture;
    private TextButton startGameButton, continueGameButton, settingsButton, exitButton;

    public MainMenuScreen(Game game) {
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/UI/Interface/kenney-pixel/skin/skin.json"));

        backgroundTexture = new Texture(Gdx.files.absolute("assets/Tiled/Tilemaps/BeginningFields.png"));

        label = new Label("Название игры", skin);
        label.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 50);
        stage.addActor(label);

        startGameButton = new TextButton("Начать игру", skin);
        startGameButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 100);
        stage.addActor(startGameButton);

        continueGameButton = new TextButton("Продолжить игру", skin);
        continueGameButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 50);
        stage.addActor(continueGameButton);

        settingsButton = new TextButton("Настройки", skin);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(settingsButton);

        exitButton = new TextButton("Выход", skin);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);
        stage.addActor(exitButton);


        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new GameScreen(game));
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        stage.dispose();
        backgroundTexture.dispose();
    }
}

