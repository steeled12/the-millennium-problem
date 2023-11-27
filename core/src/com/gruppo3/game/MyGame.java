package com.gruppo3.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gruppo3.game.screens.TestScreen;

public class MyGame extends Game {
	public SpriteBatch batch;
	Texture img;
	private MyGame game;

	@Override
	public void create() {
		batch = new SpriteBatch();
		game = this;
		setScreen(new TestScreen(game));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
