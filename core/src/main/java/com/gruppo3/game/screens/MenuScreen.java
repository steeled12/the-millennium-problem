package com.gruppo3.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.MenuController;
import com.badlogic.gdx.audio.Music;
import com.gruppo3.game.controller.SettingController;

public class MenuScreen implements Screen {

    MenuController menuController;
    private OrthographicCamera camera;
    MyGame game = (MyGame) Gdx.app.getApplicationListener();
    Viewport textViewport;
    Music music;
    public MenuScreen() {
        this.menuController = new MenuController();

        camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        textViewport = new ScreenViewport();

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Menu.ogg")); 
    }

    @Override
    public void show() {
        music.setVolume(SettingController.option.getFloat("musicVolume", SettingController.musicVolume));
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuController.getStage().act();
        menuController.getStage().draw();
        Gdx.input.setInputProcessor(menuController.getStage());
        game.batch.begin();
        game.debugFont.draw(game.batch, "VERSION: DEVELOPER-BUILD_0.4",
                10,
                20);
        game.batch.end();
        
    }

    @Override
    public void resize(int width, int height) {
        menuController.getStage().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void dispose() {
        menuController.getStage().dispose();
        music.dispose();

    }

}
