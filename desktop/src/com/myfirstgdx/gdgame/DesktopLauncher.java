package com.myfirstgdx.gdgame;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Galactic-Defender");
		config.setResizable(false);
		config.setWindowedMode(1000, 700);
//		config.setWindowedMode(600, 300);
		new Lwjgl3Application(new GDGame(), config);
	}
}
