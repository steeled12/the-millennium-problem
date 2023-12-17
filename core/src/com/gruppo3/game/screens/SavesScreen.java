package com.gruppo3.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.SaveController;

public class SavesScreen implements Screen {
    MyGame game;
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;

    public SavesScreen(final MyGame game) {
        this.game = game;
        atlas = new TextureAtlas("flat-earth/skin/flat-earth-ui.atlas");
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        viewport = new ScreenViewport();
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        // Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        // Create Table
        Table mainTable = new Table();
        // Set table to fill stage
        mainTable.setFillParent(true);
        // Set alignment of contents in the table.
        mainTable.top();

        // Create buttons
        Label load1Label = new Label("Save1", skin);
        TextButton load1Button = new TextButton("Avvia", skin);
        TextButton load2Button = new TextButton("Avvia", skin);
        TextButton load3Button = new TextButton("Avvia", skin);
        TextButton backButton = new TextButton("Back", skin);

        // Add listeners to buttons
        load1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.loadSave(0);
                game.setScreen(new TestScreen(game));
            }
        });
        load2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.loadSave(1);
                game.setScreen(new TestScreen(game));
            }
        });
        load3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.loadSave(2);
                game.setScreen(new TestScreen(game));
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        // Add buttons to table
        mainTable.add(new Label("Save1: ", skin));
        mainTable.add(load1Button);
        mainTable.row();
        mainTable.add(new Label("Save2: ", skin));
        mainTable.add(load2Button);
        mainTable.row();
        mainTable.add(new Label("Save3: ", skin));
        mainTable.add(load3Button);
        mainTable.row();
        mainTable.add(backButton);

        // Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
