package com.dungeoncrawler.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.dungeoncrawler.game.DungeonCrawlerGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.setForegroundFPS(60);
		config.setTitle("DungeonCrawlerGame");
		new Lwjgl3Application(new DungeonCrawlerGame(), config);
	}
}
