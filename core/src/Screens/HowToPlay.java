package Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myfirstgdx.gdgame.GDGame;

public class HowToPlay extends Screens implements ApplicationListener {
	SpriteBatch batch;
	BitmapFont font;

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
		font = new BitmapFont(Gdx.files.internal("Fonts/Russo_One.ttf"));
		batch = new SpriteBatch();
		
		font.getData().setScale(2.0f);
		font.setColor(Color.WHITE);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
