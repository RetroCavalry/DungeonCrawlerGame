package com.dungeoncrawler.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class CharacterController extends InputAdapter {
    private Character character;
    private OrthographicCamera camera;
    private Game game;
    private List<Slime> slimes;

    public CharacterController(Character character, OrthographicCamera camera, Game game, List<Slime> slimes) {
        this.character = character;
        this.camera = camera;
        this.game = game;
        this.slimes = slimes;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // Увеличение или уменьшение масштаба камеры
        camera.zoom += amountY * 0.1f;
        camera.update();
        return true;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.A) && !isColliding(character.getX() - 1, character.getY())) {
            character.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            character.moveRight();
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            character.moveUp();
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            character.moveDown();
        }
        if (Gdx.input.isKeyPressed(Keys.J)) {
            character.attack(character.getCurrentDirection());
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.A:
                if (!isColliding(character.getX() - 1, character.getY())) {
                    character.moveLeft();
                }
                break;
            case Keys.D:
                character.moveRight();
                break;
            case Keys.W:
                character.moveUp();
                break;
            case Keys.S:
                character.moveDown();
                break;
            case Keys.J:
                character.attack(character.getCurrentDirection());
                break;
            case Keys.ESCAPE:
                game.setScreen(new MainMenuScreen(game));
                break;
        }
        return true;
    }

    private boolean isColliding(float x, float y) {
        Rectangle futureBounds = new Rectangle(x, y, character.getWidth(), character.getHeight());
        for (Slime slime : slimes) {
            if (slime.getBounds().overlaps(futureBounds)) {
                return true;
            }
        }
        // Проверьте коллизии с другими препятствиями здесь...
        return false;
    }

}

