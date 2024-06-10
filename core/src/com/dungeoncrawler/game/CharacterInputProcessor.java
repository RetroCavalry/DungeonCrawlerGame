package com.dungeoncrawler.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class CharacterInputProcessor extends InputAdapter {
    private Character character;

    public CharacterInputProcessor(Character character) {
        this.character = character;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                character.moveUp();
                break;
            case Input.Keys.S:
                character.moveDown();
                break;
            case Input.Keys.A:
                character.moveLeft();
                break;
            case Input.Keys.D:
                character.moveRight();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:
                character.stopVerticalMovement();
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                character.stopHorizontalMovement();
                break;
        }
        return true;
    }
}

