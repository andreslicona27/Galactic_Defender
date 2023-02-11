package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.myfirstgdx.gdgame.GDGame;

import Utilities.Assets;
import Utilities.Display;

public class MainMenu extends Screens {

	// FONT PROPERTIES
	BitmapFont font;
	ScrollPane scroll;

	// FRONTPAGE PROPERTIES
	Texture image;
	Sprite sImage;

	// MUSIC PROPERTIES
	Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/BackGround.mp3"));
	Boolean musicOn;

	public MainMenu(GDGame game) {
		super(game);

		Table menu = new Table();
		menu.setFillParent(true);
		menu.defaults().uniform().fillY();

		// Creation of buttons for each value in displays, this would send you to the
		// different screens in the game
		for (final Display display : Display.values()) {
			TextButton textButton = new TextButton(display.name, Assets.txButtonStyle);
			textButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					MainMenu.this.game.setScreen(getScreen(display));
				}
			});

			menu.row().padTop(10).height(40);
			menu.row().padRight(0).width(200);
			menu.add(textButton).fillX();
		}

		scroll = new ScrollPane(menu, Assets.scrollPaneStyle);
		scroll.setSize(500, SCREEN_HEIGHT);
		scroll.setPosition(350, 0);
		stage.addActor(scroll);

		// Addition of the music
		bgMusic.play();
		bgMusic.setLooping(true);
	}

	private Screens getScreen(Display display) {
		switch (display) {
		case DISPLAY_1:
			return new Game(game);
		case DISPLAY_2:
			return new HowToPlay(game);
		case DISPLAY_3:
			return new Awards(game);
		case DISPLAY_4:
			return new Credits(game);
		case DISPLAY_5:
			return new Settings(game);
		default:
			return new Game(game);
		}
	}

	@Override
	public void draw(float delta) {
		image = new Texture(Gdx.files.internal("Backgrounds/frontPage.png"));
		sImage = new Sprite(image);
		sImage.setPosition(0, 0);
		sImage.setSize((SCREEN_WIDTH / 2) + 25, SCREEN_HEIGHT);

		spriteBatch.begin();
		sImage.draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void update(float delta) {

	}

}