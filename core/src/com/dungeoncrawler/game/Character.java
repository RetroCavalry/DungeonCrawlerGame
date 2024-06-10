package com.dungeoncrawler.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Character {
    private Animation<TextureRegion> idleAnimationUp, idleAnimationDown, idleAnimationLeft, idleAnimationRight;
    private Animation<TextureRegion> walkAnimationUp, walkAnimationDown, walkAnimationLeft, walkAnimationRight;
    private Animation<TextureRegion> slashAnimationUp, slashAnimationDown, slashAnimationLeft, slashAnimationRight;
    private Animation<TextureRegion> currentAnimation;
    private float animationTime = 0;
    private float x, y;
    private float width,height;
    private int hp;
    private int damage;
    private int xp;
    private float speed;
    private int level;
    private int xpToNextLevel;
    private float dx = 0, dy = 0;
    private Rectangle bounds;

    private String currentDirection = "down";
    public String getCurrentDirection() {
        return currentDirection;
    }

    public Character(float startX, float startY) {
        hp = 100;
        damage = 10;
        xp = 0;
        speed = 50.0f;
        level = 1;
        xpToNextLevel = 100;
        this.x = startX;
        this.y = startY;
        this.width = 16;
        this.height = 16;
        this.bounds = new Rectangle(x, y, width, height);
        Texture idleSheetDown = new Texture("assets/Characters/Character/Characters/Character_Idle_Down.png");
        Texture idleSheetUp = new Texture("assets/Characters/Character/Characters/Character_Idle_Up.png");
        Texture idleSheetLeft = new Texture("assets/Characters/Character/Characters/Character_Idle_Left.png");
        Texture idleSheetRight = new Texture("assets/Characters/Character/Characters/Character_Idle_Right.png");

        Texture walkSheetDown = new Texture("assets/Characters/Character/Characters/Character_Walk_Down.png");
        Texture walkSheetUp = new Texture("assets/Characters/Character/Characters/Character_Walk_Up.png");
        Texture walkSheetLeft = new Texture("assets/Characters/Character/Characters/Character_Walk_Left.png");
        Texture walkSheetRight = new Texture("assets/Characters/Character/Characters/Character_Walk_Right.png");

        Texture slashSheetDown = new Texture("assets/Characters/Character/Characters/Character_Slash_Down.png");
        Texture slashSheetUp = new Texture("assets/Characters/Character/Characters/Character_Slash_Up.png");
        Texture slashSheetLeft = new Texture("assets/Characters/Character/Characters/Character_Slash_Left.png");
        Texture slashSheetRight = new Texture("assets/Characters/Character/Characters/Character_Slash_Right.png");

        TextureRegion[] idleFramesDown = TextureRegion.split(idleSheetDown, idleSheetDown.getWidth() / 2, idleSheetDown.getHeight())[0];
        TextureRegion[] idleFramesUp = TextureRegion.split(idleSheetUp, idleSheetUp.getWidth() / 2, idleSheetUp.getHeight())[0];
        TextureRegion[] idleFramesLeft = TextureRegion.split(idleSheetLeft, idleSheetLeft.getWidth() / 2, idleSheetLeft.getHeight())[0];
        TextureRegion[] idleFramesRight = TextureRegion.split(idleSheetRight, idleSheetRight.getWidth() / 2, idleSheetRight.getHeight())[0];

        TextureRegion[] walkFramesDown = TextureRegion.split(walkSheetDown, walkSheetDown.getWidth() / 4, walkSheetDown.getHeight())[0];
        TextureRegion[] walkFramesUp = TextureRegion.split(walkSheetUp, walkSheetUp.getWidth() / 4, walkSheetUp.getHeight())[0];
        TextureRegion[] walkFramesLeft = TextureRegion.split(walkSheetLeft, walkSheetLeft.getWidth() / 4, walkSheetLeft.getHeight())[0];
        TextureRegion[] walkFramesRight = TextureRegion.split(walkSheetRight, walkSheetRight.getWidth() / 4, walkSheetRight.getHeight())[0];

        TextureRegion[] slashFramesDown = TextureRegion.split(slashSheetDown, slashSheetDown.getWidth() / 6, slashSheetDown.getHeight())[0];
        TextureRegion[] slashFramesUp = TextureRegion.split(slashSheetUp, slashSheetUp.getWidth() / 6, slashSheetUp.getHeight())[0];
        TextureRegion[] slashFramesLeft = TextureRegion.split(slashSheetLeft, slashSheetLeft.getWidth() / 6, slashSheetLeft.getHeight())[0];
        TextureRegion[] slashFramesRight = TextureRegion.split(slashSheetRight, slashSheetRight.getWidth() / 6, slashSheetRight.getHeight())[0];

        idleAnimationDown = new Animation<TextureRegion>(0.5f, idleFramesDown);
        idleAnimationUp = new Animation<TextureRegion>(0.5f, idleFramesUp);
        idleAnimationLeft = new Animation<TextureRegion>(0.5f, idleFramesLeft);
        idleAnimationRight = new Animation<TextureRegion>(0.5f, idleFramesRight);

        walkAnimationDown = new Animation<TextureRegion>(0.1f, walkFramesDown);
        walkAnimationUp = new Animation<TextureRegion>(0.1f, walkFramesUp);
        walkAnimationLeft = new Animation<TextureRegion>(0.1f, walkFramesLeft);
        walkAnimationRight = new Animation<TextureRegion>(0.1f, walkFramesRight);

        slashAnimationDown = new Animation<TextureRegion>(0.1f, slashFramesDown);
        slashAnimationUp = new Animation<TextureRegion>(0.1f, slashFramesUp);
        slashAnimationLeft = new Animation<TextureRegion>(0.1f, slashFramesLeft);
        slashAnimationRight = new Animation<TextureRegion>(0.1f, slashFramesRight);

        currentAnimation = idleAnimationDown;
    }

    public TextureRegion getFrame(float delta) {
        animationTime += delta;
        if (currentAnimation.isAnimationFinished(animationTime)) {
            switch (currentDirection) {
                case "up":
                    currentAnimation = idleAnimationUp;
                    break;
                case "down":
                    currentAnimation = idleAnimationDown;
                    break;
                case "left":
                    currentAnimation = idleAnimationLeft;
                    break;
                case "right":
                    currentAnimation = idleAnimationRight;
                    break;
            }
        }

        return currentAnimation.getKeyFrame(animationTime);
    }

    public void moveUp() {
        dy += speed;
        currentAnimation = walkAnimationUp;
        currentDirection = "up";
    }

    public void moveDown() {
        dy -= speed;
        currentAnimation = walkAnimationDown;
        currentDirection = "down";
    }

    public void moveLeft() {
        dx -= speed;
        currentAnimation = walkAnimationLeft;
        currentDirection = "left";
    }

    public void moveRight() {
        dx += speed;
        currentAnimation = walkAnimationRight;
        currentDirection = "right";
    }

    public void attack(String direction) {
        animationTime = 0;
        switch (direction) {
            case "up":
                currentAnimation = slashAnimationUp;
                break;
            case "down":
                currentAnimation = slashAnimationDown;
                break;
            case "left":
                currentAnimation = slashAnimationLeft;
                break;
            case "right":
                currentAnimation = slashAnimationRight;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        currentDirection = direction;
    }

    public void gainXp(int xp) {
        this.xp += xp;
        if (this.xp >= xpToNextLevel) {
            levelUp();
        }
    }


    private void levelUp() {
        level++;
        xp = 0;
        xpToNextLevel *= 2;


        hp += 10;
        damage += 5;
        speed += 10.0f;
    }


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

    public boolean isMoving() {
        return dx != 0 || dy != 0;
    }

    public void update(float delta) {
        x += dx * delta;
        y += dy * delta;
        dx = 0;
        dy = 0;

        bounds.setPosition(x, y);
    }

    public void stopVerticalMovement() {
        dy = 0;
        switch (currentDirection) {
            case "up":
                currentAnimation = idleAnimationUp;
                break;
            case "down":
                currentAnimation = idleAnimationDown;
                break;
        }
    }

    public void stopHorizontalMovement() {
        dx = 0;
        switch (currentDirection) {
            case "left":
                currentAnimation = idleAnimationLeft;
                break;
            case "right":
                currentAnimation = idleAnimationRight;
                break;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean attack(Slime slime) {
        if (slime.getBounds().overlaps(bounds)) {
            slime.setHp(slime.getHp() - damage);
        }
        return false;
    }

    public void setDead(boolean b) {
    }
}






