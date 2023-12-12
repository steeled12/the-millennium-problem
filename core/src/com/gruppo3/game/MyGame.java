package com.gruppo3.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gruppo3.game.screens.MainMenuScreen;
import com.gruppo3.game.screens.TestScreen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gruppo3.game.util.SkinGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MyGame extends Game {
	public SpriteBatch batch;
	Texture img;
	private MyGame game;
	public static Skin skin;
	private AssetManager assetManager;


	@Override
	public void create() {
		assetManager = new AssetManager();
		
		batch = new SpriteBatch();
		game = this;
		assetManager.load("ui/uipack.atlas", TextureAtlas.class);
		assetManager.load("font/small_letters_font.fnt", BitmapFont.class);
		assetManager.finishLoading();
		skin = SkinGenerator.generateSkin(assetManager);
		setScreen(new MainMenuScreen());
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
