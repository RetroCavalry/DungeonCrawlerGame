package com.dungeoncrawler.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private TiledMap map;
    private Character character;
    private Slime slime;
    private Dungeon dungeon;
    private CharacterController controller;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private List<Slime> slimes;

    public GameScreen(Game game) {
        this.game = game;
        slimes = new ArrayList<Slime>();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/UI/Interface/kenney-pixel/skin/skin.json"));
        batch = new SpriteBatch();

        character = new Character(711,380);
        dungeon = new Dungeon(100, 100, "assets/Art/Tiles/DungeonPrison/Assets/Tiles.png", "assets/Art/Tiles/DungeonPrison/Assets/Props.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 60, 60);

        camera.position.set(character.getX(), character.getY(), 0);
        camera.update();

        controller = new CharacterController(character, camera, game, slimes);

        map = new TmxMapLoader().load("assets/Tiled/Tilemaps/BeginningFields.tmx");

        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    if (object.getProperties().containsKey("StartPosition")) {
                        character.setPosition(rect.x, rect.y);
                    }
                }
            }
        }

        int numberOfSlimes = MathUtils.random(5, 10);
        for (int i = 0; i < numberOfSlimes; i++) {
            Slime slime = new Slime(slimes, character);
            float x = MathUtils.random(0,60 * Tile.SIZE);
            float y = MathUtils.random(0, 60 * Tile.SIZE);
            slime.setPosition(x, y);
            slimes.add(slime);
        }

        mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        slime.update(delta);
        character.update(delta);
        controller.update(delta);

        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    if (object.getName().equals("DungeonEntrance")) {
                        if (character.getBounds().overlaps(rect)) {
                            int newDungeonX = MathUtils.random(dungeon.getWidth() * Tile.SIZE);
                            int newDungeonY = MathUtils.random(dungeon.getHeight() * Tile.SIZE);
                            character.setPosition(newDungeonX, newDungeonY);

                        }
                    }
                }
            }
        }
        if (character.isMoving()) {
            camera.position.set(character.getX(), character.getY(), 0);
        }
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals("RockSlopes")) {
                continue;
            }

            if (layer instanceof TiledMapTileLayer) {
                mapRenderer.getBatch().begin();
                mapRenderer.renderTileLayer((TiledMapTileLayer) layer);
                mapRenderer.getBatch().end();
            } else {
                mapRenderer.getBatch().begin();
                for (MapObject object : layer.getObjects()) {
                    if (object instanceof TextureMapObject) {
                        TextureMapObject textureObj = (TextureMapObject) object;
                        batch.draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY());
                    }
                }
                mapRenderer.getBatch().end();
            }
        }

        if (character.getHp() <= 0) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        batch.begin();
        batch.draw(character.getFrame(delta), character.getX(), character.getY());
        for (Slime slime : slimes) {
            slime.update(delta);
            slime.moveTowards(character);
            if (slime.attack(character)) {

                character.setHp(character.getHp() - slime.getDamage());
                if (character.getHp() <= 0) {
                    character.setDead(true);
                }
            }
            if (character.attack(slime)) {
                slime.setHp(slime.getHp() - character.getDamage());
                if (slime.getHp() <= 0) {
                    slime.setDead(true);
                    character.gainXp(10);
                }
            }
            if (slime.getHp() <= 0) {
                slime.setDead(true);
                slime.setCurrentAnimation(slime.deathAnimation);
            }
            batch.draw(slime.getFrame(delta), slime.getX(), slime.getY());
        }
        batch.end();
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
        batch.dispose();
        map.dispose();
    }
}

