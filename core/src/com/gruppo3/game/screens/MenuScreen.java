package com.gruppo3.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.MenuController;

public class MenuScreen implements Screen {

    MenuController menuController;
    private OrthographicCamera camera;
    MyGame game = (MyGame) Gdx.app.getApplicationListener();
    Viewport textViewport;

    public MenuScreen() {
        this.menuController = new MenuController();

        camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        textViewport = new ScreenViewport();
    }

    @Override
    public void show() {
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

    }

    @Override
    public void dispose() {
        menuController.getStage().dispose();
    }

}
