package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.myfirstgdx.gdgame.GDGame;

public class Settings extends Screens{
	// FONT PROPERTIES 
		SpriteBatch batch;
		BitmapFont font;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Russo_One.ttf"));
		
	public Settings(final GDGame game) {
		super(game);
		GoBackButton();
		controls();
	}

	@Override
	public void draw(float delta) {
		batch = new SpriteBatch();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 48;
		font = generator.generateFont(parameter);
		
		batch.begin();
		font.draw(batch, "Settings", (float) (SCREEN_WIDTH * 0.1), (float) (SCREEN_HEIGHT - (SCREEN_HEIGHT * 0.02)));
		batch.end();
	}

	public void controls() {
//		Table menu = new Table();
//		menu.setFillParent(true);
//		menu.defaults().uniform().fillY();
//		
//		
//		
//		menu.row().padTop(10).height(40);
//		menu.row().padRight(0).width(200);
//		menu.add(imageButton).fillX();
		
		TextureRegionDrawable musicImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/music.png")));
		ImageButton music = new ImageButton(musicImage);
		
		music.setPosition((float) (SCREEN_WIDTH / 2)  - (musicImage.getMinWidth()/2), (float) (SCREEN_HEIGHT / 2 + 20));
		music.setSize(50, 50);
		music.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		stage.addActor(music);
		
		TextureRegionDrawable soundImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/sounds.png")));
		ImageButton sound = new ImageButton(soundImage);
		
		sound.setPosition((float) (SCREEN_WIDTH / 2)  - (soundImage.getMinWidth()/2), (float) (SCREEN_HEIGHT / 2));
		sound.setSize(50, 50);
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenu(game));
			}
		});
		stage.addActor(music);
		
		TextureRegionDrawable languageImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/BackArrow.png")));
		ImageButton language = new ImageButton(languageImage);
		
		language.setPosition((float) (SCREEN_WIDTH / 2) - (languageImage.getMinWidth()/2), (float) (SCREEN_HEIGHT / 2 - 20));
		language.setSize(50, 50);
		language.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenu(game));
			}
		});
		stage.addActor(music);
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * Adds all the properties and information of the components
	 * */
}
