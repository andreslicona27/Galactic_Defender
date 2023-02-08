package com.myfirstgdx.gdgame;

import com.badlogic.gdx.Game;

import Screens.MainMenu;
import Utilities.Assets;

public class GDGame extends Game{

	// It runs ones, when it create the project
	@Override
	public void create() {
		// Screen code
		Assets.load();
		setScreen(new MainMenu(this));

	}
}
