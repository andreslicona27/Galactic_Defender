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
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		// MUSIC ICON PROPERTIES 
		TextureRegionDrawable musicImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/music.png")));
		ImageButton music = new ImageButton(musicImage);
		
		// SOUNDS ICON PROPERTIES
		TextureRegionDrawable soundImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/sound.png")));
		ImageButton sound = new ImageButton(soundImage);
		
		// LANGUAGE ICON PROPERTIES 
		TextureRegionDrawable languageImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/en.png")));
		ImageButton language = new ImageButton(languageImage);
		
		
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
		
		parameter.size = 24;
		font = generator.generateFont(parameter);
		batch.begin();
		font.draw(batch, "music", (float) (SCREEN_WIDTH / 4), (float) (SCREEN_HEIGHT / 2));
		batch.end();
		batch.begin();
		font.draw(batch, "sound", (float) (SCREEN_WIDTH / 4), (float) (SCREEN_HEIGHT / 2));
		batch.end();
		batch.begin();
		font.draw(batch, "language", (float) (SCREEN_WIDTH / 4), (float) (SCREEN_HEIGHT / 2));
		batch.end();
	}


	public void controls() {
		
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
				if (english = false) {
					english = true;
					language.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/en.png")));
				} else {
					english = false;
					language.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/es.png")));
				}
			}
		});
		
		stage.addActor(music);
		stage.addActor(sound);
		stage.addActor(language);
	}
	@Override
	public void update(float delta) {
		
	}

}
