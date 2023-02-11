package Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.myfirstgdx.gdgame.GDGame;

public class HowToPlay extends Screens implements ApplicationListener {
	SpriteBatch batch;
	BitmapFont font;
	
	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Russo_One.ttf"));

	public HowToPlay(GDGame game) {
		super(game);
		
		create();
		render();
		dispose();
	}

	@Override
	public void draw(float delta) {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		font = generator.generateFont(parameter);
		
	}

	@Override
	public void render() {
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		font.draw(batch, "Hello World!", 100, 100);
		batch.end();
	}
	
	 @Override
	  public void dispose() {
	    batch.dispose();
	    font.dispose();
	  }

}