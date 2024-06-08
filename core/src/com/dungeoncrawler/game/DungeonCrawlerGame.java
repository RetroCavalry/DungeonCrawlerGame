package com.dungeoncrawler.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class DungeonCrawlerGame extends Game {
	SpriteBatch batch;
	TiledMap map;
	Character character;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer mapRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();

		// Загрузка текстур персонажа
		TextureAtlas atlas = new TextureAtlas("assets/Characters/Character.atlas");
		character = new Character(atlas);

		// Создание камеры
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 60, 60);
		// Установка начальной позиции камеры, чтобы она была сфокусирована на персонаже
		camera.position.set(character.getX(), character.getY(), 0);
		camera.update();

		// Загрузка тайловой карты
		map = new TmxMapLoader().load("assets/Tiled/Tilemaps/BeginningFields.tmx");

		for (MapLayer layer : map.getLayers()) {
			for (MapObject object : layer.getObjects()) {
				if (object instanceof RectangleMapObject) {
					Rectangle rect = ((RectangleMapObject) object).getRectangle();
					// Проверка наличия свойства "StartPosition"
					if (object.getProperties().containsKey("StartPosition")) {
						// Установка стартовой позиции игрока
						character.setPosition(rect.x, rect.y);
					}
				}
			}
		}


		// Создание рендерера карты
		mapRenderer = new OrthogonalTiledMapRenderer(map, batch);

		// Создание контроллера и привязка его к персонажу
		CharacterController controller = new CharacterController(character, camera);
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();

		// Обновление позиции персонажа
		character.update(delta);

		// Обновление позиции камеры, чтобы она следовала за персонажем
		camera.position.set(character.getX(), character.getY(), 0);
		camera.update();

		// Отрисовка всех слоев карты
		mapRenderer.setView(camera);
		mapRenderer.render();

		for (MapLayer layer : map.getLayers()) {
			// Если это слой RockSlopes, пропустить его
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
					if (object instanceof RectangleMapObject) {
						Rectangle rect = ((RectangleMapObject) object).getRectangle();
						if (object.getProperties().containsKey("StartPosition")) {
							// Установка стартовой позиции игрока
							character.setPosition(rect.x, rect.y);
						} else if (object.getName().equals("DungeonEntrance")) {
							// Отображение входа в данж
							// Здесь вы можете добавить свой код для отображения входа в данж
						}
					} else if (object instanceof TextureMapObject) {
						TextureMapObject textureObj = (TextureMapObject) object;
						// Отображение объекта на карте
						batch.draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY());
					}
				}
				mapRenderer.getBatch().end();
			}
		}


		batch.begin();
		// Отрисовка персонажа
		batch.draw(character.getFrame(delta), character.getX(), character.getY());
		batch.end();
	}



	@Override
	public void dispose () {
		batch.dispose();
		map.dispose();
	}
}
