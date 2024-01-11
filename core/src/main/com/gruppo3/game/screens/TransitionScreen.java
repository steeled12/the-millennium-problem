package com.gruppo3.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.LevelController;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.level.LevelStrategy;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.Player;

public class TransitionScreen implements Screen {
	private LevelStrategy currentScreen;
	private LevelStrategy nextScreen;
    private GameScreen gameScreen;
	private MyGame game;
    float playerX, playerY;

	private float alpha = 0;
	private boolean fadeDirection = true;

	public TransitionScreen(LevelStrategy current, LevelStrategy next, MyGame game, float playerX, float playerY) {
        this.game = game;
		this.currentScreen = current;
		this.nextScreen = next;
        this.gameScreen = game.currentScreen;
        this.playerX = playerX;
        this.playerY = playerY;
	}

	@Override
	public void show() {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/door.wav"));
		sound.play(SettingController.gameVolume);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT); 

		GameScreen.levelController.setLevel(nextScreen);
        if(playerX != 0 || playerY != 0){
            Player.getPlayer().getPlayerBox().x = this.playerX;
            Player.getPlayer().getPlayerBox().y = this.playerY;
        }

		Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
		Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		game.shapeRenderer.setColor(0, 0, 0, alpha);
		game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		game.shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.shapeRenderer.end();
		Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

		if (alpha >= 1) {
			fadeDirection = false;
		}
		else if (alpha <= 0 && !fadeDirection) {
			game.setScreen(gameScreen);
		}
		alpha += fadeDirection == true ? 0.02 : -0.02;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}