package com.dungeoncrawler.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Character {
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> slashAnimation;
    private Animation<TextureRegion> currentAnimation;
    private float animationTime = 0;

    // Позиция и скорость персонажа
    private float x, y;
    private float speed = 200.0f;
    // Переменные для хранения направления движения персонажа
    private float dx = 0, dy = 0;

    public Character(TextureAtlas atlas) {
        // Загрузка анимаций из атласа текстур
        idleAnimation = new Animation<TextureRegion>(0.5f, atlas.findRegions("Character_Idle"), Animation.PlayMode.LOOP);
        walkAnimation = new Animation<TextureRegion>(0.1f, atlas.findRegions("Character_Walk"), Animation.PlayMode.LOOP);
        slashAnimation = new Animation<TextureRegion>(0.1f, atlas.findRegions("Character_Slash"), Animation.PlayMode.NORMAL);

        // Установка начальной анимации
        currentAnimation = idleAnimation;
    }

    public TextureRegion getFrame(float delta) {
        animationTime += delta;

        // Если текущая анимация закончилась, переключаемся обратно на idleAnimation
        if (currentAnimation.isAnimationFinished(animationTime)) {
            currentAnimation = idleAnimation;
        }

        return currentAnimation.getKeyFrame(animationTime);
    }

    // Методы для движения персонажа
    public void moveUp() {
        dy += speed;
        currentAnimation = walkAnimation;
    }

    public void moveDown() {
        dy -= speed;
        currentAnimation = walkAnimation;
    }

    public void moveLeft() {
        dx -= speed;
        currentAnimation = walkAnimation;
    }

    public void moveRight() {
        dx += speed;
        currentAnimation = walkAnimation;
    }

    // Метод для атаки персонажа
    public void attack() {
        currentAnimation = slashAnimation;
        animationTime = 0; // Сброс времени анимации, чтобы начать анимацию атаки с начала
    }

    // Методы для получения позиции персонажа
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


    // Метод для обновления позиции персонажа
    public void update(float delta) {
        // Обновление позиции персонажа
        x += dx * delta;
        y += dy * delta;

        // Сброс скорости
        dx = 0;
        dy = 0;
    }
}





