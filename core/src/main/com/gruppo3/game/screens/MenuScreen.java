package com.gruppo3.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.MenuController;
import com.badlogic.gdx.audio.Music;
import com.gruppo3.game.controller.SettingController;

public class MenuScreen implements Screen {

    MenuController menuController;
    private OrthographicCamera camera;
    MyGame game = (MyGame) Gdx.app.getApplicationListener();
    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/Menu.ogg"));
    BitmapFont debugFont;
    SpriteBatch batch;

    public MenuScreen() {
        this.menuController = new MenuController();
        this.batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        music.setLooping(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("font/pkmnrsi.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 12;
        this.debugFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuController.getStage().act();
        menuController.getStage().draw();
        Gdx.input.setInputProcessor(menuController.getStage());
        this.batch.begin();
        this.debugFont.draw(this.batch, "VERSION: DEVELOPER-BUILD_0.4",
                10,
                15);
        this.batch.end();
        music.setVolume(SettingController.musicVolume);
    }

    @Override
    public void resize(int width, int height) {
        menuController.getStage().getViewport().update(width, height, true);
        // Actor table = menuController.getStage().getActors().first();
        // table.setSize(width / 1.5f, height / 1.5f);
        // table.setPosition((width - table.getWidth()) / 2, (height -
        // table.getHeight()) / 2);
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
        debugFont.dispose();
        batch.dispose();
    }
}
