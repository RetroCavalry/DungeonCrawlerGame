package com.dungeoncrawler.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Slime extends Enemy {
    public Animation<TextureRegion> deathAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> idleAnimation;
    private List<Slime> slimes;
    private Character character;
    private Animation<TextureRegion> currentAnimation;
    private Rectangle bounds;
    private boolean isDead;
    private boolean isAttacking;
    private boolean isMoving;
    private Vector2 velocity;
    private Vector2 position;
    private TextureRegion currentFrame;
    private int hp;
    private int damage;
    private float speed;

    public Slime(List<Slime> slimes, Character character) {
        this.slimes = slimes;
        this.character = character;
        hp = 10;
        damage = 10;
        speed = 1.0f;
        float x = 0;
        float y = 0;
        float width = 16;
        float height = 16;
        velocity = new Vector2(0, 0);
        position = new Vector2(0, 0);
        bounds = new Rectangle(x, y, width, height);
        Texture idleSheet = new Texture("assets/Characters/Slime/Slime_Idle.png");
        Texture walkSheet = new Texture("assets/Characters/Slime/Slime_Walk.png");
        Texture attackSheet = new Texture("assets/Characters/Slime/Slime_Attack.png");
        Texture deathSheet = new Texture("assets/Characters/Slime/Slime_Death.png");
        deathAnimation = new Animation<TextureRegion>(0.1f, getFrames(deathSheet, 7));
        walkAnimation = new Animation<TextureRegion>(0.1f, getFrames(walkSheet, 4));
        attackAnimation = new Animation<TextureRegion>(0.1f, getFrames(attackSheet, 6));
        idleAnimation = new Animation<TextureRegion>(0.1f, getFrames(idleSheet, 2));
        currentFrame = idleAnimation.getKeyFrame(0);
        isDead = false;
        isAttacking = false;
        isMoving = false;
    }

    private TextureRegion[] getFrames(Texture sheet, int frameCount) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth() / frameCount, sheet.getHeight());
        for (int i = 0; i < frameCount; i++) {
            frames[i] = temp[0][i];
        }
        return frames;
    }

    public TextureRegion getFrame(float delta) {
        if (isDead()) {
            currentFrame = deathAnimation.getKeyFrame(delta);
        } else if (isAttacking()) {
            currentFrame = attackAnimation.getKeyFrame(delta);
        } else if (isMoving()) {
            currentFrame = walkAnimation.getKeyFrame(delta);
        } else {
            currentFrame = idleAnimation.getKeyFrame(delta);
        }
        return currentFrame;
    }
    @Override
    public void update(float delta) {
        if (isDead()) {
            deathAnimation.getKeyFrame(delta);
        } else if (isAttacking()) {
            attackAnimation.getKeyFrame(delta);
        } else if (isMoving()) {
            walkAnimation.getKeyFrame(delta);
        } else {
            idleAnimation.getKeyFrame(delta);
        }
        bounds.x += velocity.x * delta;
        bounds.y += velocity.y * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        float delta = 0.1f;
        if (isDead()) {
            batch.draw(deathAnimation.getKeyFrame(delta), bounds.x, bounds.y);
        } else if (isAttacking()) {
            batch.draw(attackAnimation.getKeyFrame(delta), bounds.x, bounds.y);
        } else if (isMoving()) {
            batch.draw(walkAnimation.getKeyFrame(delta), bounds.x, bounds.y);
        } else {
            batch.draw(idleAnimation.getKeyFrame(delta), bounds.x, bounds.y);
        }
        for (Slime slime : slimes) {
            slime.update(delta);
            slime.moveTowards(character);
            slime.attack(character);
            if (slime.getHp() <= 0 && !slime.isDead()) {
                slime.setDead(true);

                slime.currentAnimation = slime.deathAnimation;
            }
            batch.draw(slime.getFrame(delta), slime.getX(), slime.getY());
        }
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void moveTowards(Character character) {
        if (this.character.getX() > position.x) {
            position.x += speed;
        } else if (this.character.getX() < position.x) {
            position.x -= speed;
        }

        if (this.character.getY() > position.y) {
            position.y += speed;
        } else if (this.character.getY() < position.y) {
            position.y -= speed;
        }
    }

    public boolean attack(Character character) {
        if (this.character.getBounds().overlaps(bounds)) {
            this.character.setHp(this.character.getHp() - damage);
        }
        return false;
    }
    public void setCurrentAnimation(Animation<TextureRegion> animation) {
        this.currentAnimation = animation;
    }


}



