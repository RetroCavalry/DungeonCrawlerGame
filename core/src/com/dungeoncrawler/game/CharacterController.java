package com.dungeoncrawler.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CharacterController extends InputAdapter {
    private Character character;
    private OrthographicCamera camera;

    public CharacterController(Character character, OrthographicCamera camera) {
        this.character = character;
        this.camera = camera;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // Увеличение или уменьшение масштаба камеры
        camera.zoom += amountY * 0.1f;
        camera.update();
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.A:
                character.moveLeft();
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
                character.attack(); // Добавлено действие атаки на кнопку J
                break;
        }
        return true;
    }

}

