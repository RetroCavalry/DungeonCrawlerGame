package com.dungeoncrawler.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DungeonCrawlerGame extends Game {
	SpriteBatch batch;
	MainMenuScreen MainMenuScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();

		MainMenuScreen = new MainMenuScreen(this);
		setScreen(MainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		MainMenuScreen.dispose();
	}
}



