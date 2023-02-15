package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.myfirstgdx.gdgame.GDGame;

import Utilities.Assets;

import java.util.Locale;

public class Settings extends Screens{
		Locale locale = Locale.getDefault();
		// FONT PROPERTIES
		SpriteBatch batch;
		BitmapFont font;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Russo_One.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		// ICONS PROPERTIES 
		TextureRegionDrawable musicImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/music.png")));
		ImageButton music = new ImageButton(musicImage);
		
		TextureRegionDrawable soundImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/sound.png")));
		ImageButton sound = new ImageButton(soundImage);
		
		TextureRegionDrawable languageImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/en.png")));
		ImageButton language = new ImageButton(languageImage);
		
		ScrollPane scroll;

		
	public Settings(final GDGame game) {
		super(game);
		GoBackButton();
		controls();
	}

	@Override
	public void draw(float delta) {
		batch = new SpriteBatch();
		parameter.size = 48;
		font = generator.generateFont(parameter);
		
		batch.begin();
		font.draw(batch, "Settings", (float) (SCREEN_WIDTH * 0.1), (float) (SCREEN_HEIGHT - (SCREEN_HEIGHT * 0.02)));
		batch.end();	
	}


	public void controls() {
		Table table = new Table();
		table.setFillParent(true);
		table.defaults().uniform().fillY();
		
		music.setPosition((float) (SCREEN_WIDTH / 2) - (musicImage.getMinWidth() / 2), (float) (SCREEN_HEIGHT / 2 + 80));
		music.setSize(50, 50);
		music.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (bgMusic.isPlaying()) {
					bgMusic.stop();
					music.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/no_music.png")));
				} else {
					bgMusic.play();
					music.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/music.png")));
				}
			}
		});
		
		sound.setPosition((float) (SCREEN_WIDTH / 2) - (soundImage.getMinWidth() / 2), (float) (SCREEN_HEIGHT / 2));
		sound.setSize(50, 50);
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (soundOn == false) {
					soundOn = true;
					sound.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/sound.png")));
				} else {
					soundOn = false;
					sound.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/no_sound.png")));
				}
			}
		});
		
		language.setPosition((float) (SCREEN_WIDTH / 2) - (languageImage.getMinWidth() / 2), (float) (SCREEN_HEIGHT / 2 - 80));
		language.setSize(50, 50);
		language.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (locale.getLanguage().equals(("es"))) {
					Locale.setDefault(Locale.ENGLISH);
					language.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/en.png")));
				} else {
					//Locale.setDefault(Locale.);
					language.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/es.png")));
				}
			}
		});
		
		table.row().padTop(10).height(40);
		table.row().padRight(0).width(200);
		
		table.add(music).fillX();
		table.add(sound).fillX();
		table.add(language).fillX();
		
		scroll = new ScrollPane(table, Assets.scrollPaneStyle);
		scroll.setSize(500, SCREEN_HEIGHT);
		scroll.setPosition(SCREEN_WIDTH / 6, 0);
		stage.addActor(scroll);
	}
	@Override
	public void update(float delta) {
		
	}

}
