package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.myfirstgdx.gdgame.GDGame;
import com.badlogic.gdx.utils.I18NBundle;
import java.util.Locale;

public class Awards extends Screens{
	// FONT PROPERTIES 
	SpriteBatch batch;
	BitmapFont font;
	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Russo_One.ttf"));

	// LANGUAGE PROPERTIES
	Locale locale = Locale.getDefault();
	I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("res/strings/strings.xml"), locale);


	public Awards(final GDGame game) {
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
		font.draw(batch, bundle.get("btn_three"), (float) (SCREEN_WIDTH * 0.1), (float) (SCREEN_HEIGHT - (SCREEN_HEIGHT * 0.02)));
		batch.end();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
