package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.myfirstgdx.gdgame.GDGame;

public abstract class Screens extends InputAdapter implements Screen{
	public static final float SCREEN_WIDTH = 800;
	public static final float SCREEN_HEIGHT = 480;
	
	public static final float WORLD_HEIGHT = 4.8f;
	public static final float WORLD_WIDTH = 8f;
	
	public GDGame game;
	
	public OrthographicCamera oCamUI;
	public OrthographicCamera oCamBox2D;
	
	public SpriteBatch spriteBatch;
	public Stage stage;
	
	public Screens(GDGame game) {
		this.game = game;
		stage = new Stage(new StretchViewport(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT));
		
		oCamUI = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		oCamUI.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);
		
		oCamBox2D = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		oCamBox2D.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
		
		InputMultiplexer input = new InputMultiplexer(this, stage);
		Gdx.input.setInputProcessor(input);
		
		spriteBatch = new SpriteBatch();	
	}
	
	
	@Override
	public void render(float delta) {
		update(delta);
		// For it to update all the animations 
		stage.act(delta);
		// To clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Now we draw the game 
		draw(delta);
		// On the game we draw the elements of the user interface 
		stage.draw();
		
	}
	
	// For the case in which the screen change size
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO: implemented with buttons and not key codes
		if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
			if(this instanceof MainMenu) {
				Gdx.app.exit();
			} else {
				game.setScreen(new MainMenu(game));
			}
		}
		return super.keyDown(keycode);
	}
	
	public abstract void draw(float delta);
	public abstract void update(float delta);
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
		
}
