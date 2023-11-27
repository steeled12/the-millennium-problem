package com.gruppo3.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gruppo3.game.screens.TestScreen;

public class MyGame extends Game {
	SpriteBatch batch;
	Texture img;
	private MyGame game;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		game = this;
		setScreen(new TestScreen(game));
	}

	@Override
	public void render () 
	{
		super.render();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
