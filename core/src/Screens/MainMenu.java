package Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.myfirstgdx.gdgame.GDGame;

import Utilities.Assets;
import Utilities.Learn;
import Utilities.Learn1;

public class MainMenu extends Screens{

	ScrollPane scroll;
	
	public MainMenu(GDGame game) {
		super(game);
		
		Table menu = new Table();
		menu.setFillParent(true);
		menu.defaults().uniform().fillY();
		
		for (final Learn learn : Learn.values()) {
			TextButton bt = new TextButton(learn.name, Assets.txButtonStyle);
			bt.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					MainMenu.this.game.setScreen(getScreen(learn));
				}
			});
			
			menu.row().padTop(20).height(50);
			menu.add(bt).fillX();
		}
		
		scroll = new ScrollPane(menu, Assets.scrollPaneStyle);
		scroll.setSize(500, 150);
		scroll.setPosition(159, 0);
		stage.addActor(scroll);
	}


	private Screens getScreen(Learn learn) {
		switch(learn) {
		default:
			return new Learn1(game);
		}
	}
	
	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
