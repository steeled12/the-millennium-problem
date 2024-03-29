package com.gruppo3.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.screens.MenuScreen;
import com.gruppo3.game.screens.GameScreen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gruppo3.game.util.SkinGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGame extends Game {

	public enum GameState {
		RUNNING,
		PAUSED,
	}

	public GameState gameState = GameState.RUNNING;
	public SpriteBatch batch;
	Texture img;
	public static Skin skin;
	private AssetManager assetManager;
	public GameScreen currentScreen;
	public ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		assetManager.load("ui/uipack.atlas", TextureAtlas.class);
		assetManager.load("font/small_letters_font.fnt", BitmapFont.class);
		assetManager.finishLoading();
		skin = SkinGenerator.generateSkin(assetManager);
		SettingController.load();
		SettingController.apply();
		setScreen(new MenuScreen());
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
