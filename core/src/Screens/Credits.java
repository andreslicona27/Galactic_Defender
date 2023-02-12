package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.myfirstgdx.gdgame.GDGame;

public class Credits extends Screens{
	// FONT PROPERTIES 
		SpriteBatch batch;
		BitmapFont font;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Russo_One.ttf"));
		
	public Credits(final GDGame game) {
		super(game);
		GoBackButton();
	}

	@Override
	public void draw(float delta) {
		batch = new SpriteBatch();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 48;
		font = generator.generateFont(parameter);
		
		batch.begin();
		font.draw(batch, "Credits", (float) (SCREEN_WIDTH * 0.1), (float) (SCREEN_HEIGHT - (SCREEN_HEIGHT * 0.02)));
		batch.end();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
