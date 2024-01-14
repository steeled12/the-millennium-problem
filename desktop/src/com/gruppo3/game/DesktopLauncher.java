package com.gruppo3.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("The Millennium Problem");
		config.setResizable(false);
		config.setWindowIcon("icon-128.png", "icon-32.png", "icon-16.png");
		config.setWindowSizeLimits(800, 600, -1, -1);

		new Lwjgl3Application(new MyGame(), config);
	}
}
