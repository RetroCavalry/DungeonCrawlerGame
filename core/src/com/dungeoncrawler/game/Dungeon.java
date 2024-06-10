package com.dungeoncrawler.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dungeon {
    private int width, height;
    private Tile[][] tiles;
    private TextureRegion[] tileTextures;
    private TextureRegion[] propTextures;

    public Dungeon(int width, int height, String tilesPath, String propsPath) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];

        SpriteSheet tileSheet = new SpriteSheet(tilesPath, 16, 16);
        SpriteSheet propSheet = new SpriteSheet(propsPath, 16, 16);
        tileTextures = tileSheet.getFrames();
        propTextures = propSheet.getFrames();

        generateDungeon();
    }

    private void generateDungeon() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    int wallTextureIndex = chooseWallTextureIndex(i, j);
                    tiles[i][j] = new Tile(tileTextures[wallTextureIndex]);
                } else {
                    tiles[i][j] = new Tile(tileTextures[30]);
                }
            }
        }
    }

    private int chooseWallTextureIndex(int i, int j) {
        if ((i == 0 && j == 0) || (i == 0 && j == height - 1) || (i == width - 1 && j == 0) || (i == width - 1 && j == height - 1)) {
            return 3;
        }
        else if (i == 0 || i == width - 1) {
            return 50;
        }
        else if (j == 0 || j == height - 1) {
            return 25;
        }
        else {
            return 30;
        }
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}



