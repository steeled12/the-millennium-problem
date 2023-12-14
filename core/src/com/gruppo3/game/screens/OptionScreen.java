package com.gruppo3.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.SettingController;

public class OptionScreen implements Screen {
    MyGame game;
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;

    public OptionScreen(final MyGame game){
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
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        final Slider musicVolumeSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        musicVolumeSlider.setValue(SettingController.musicVolume);
        musicVolumeSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.musicVolume = musicVolumeSlider.getValue();
                return false;
            }
        });

        final Slider gameVolumeSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        gameVolumeSlider.setValue(SettingController.gameVolume);
        gameVolumeSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.gameVolume = gameVolumeSlider.getValue();
                return false;
            }
        });

        final Slider maxFpsSlider = new Slider( 30, 244, 1,false, skin);
        maxFpsSlider.setValue(SettingController.maxFps);
        maxFpsSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.maxFps =  (int) maxFpsSlider.getValue();
                return false;
            }
        });

        final CheckBox fullscreenCheckbox = new CheckBox(null, skin);
        fullscreenCheckbox.setChecked( SettingController.fullscreen );
        fullscreenCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.fullscreen = fullscreenCheckbox.isChecked();
                return false;
            }
        });

        final CheckBox vsyncCheckbox = new CheckBox(null, skin);
        vsyncCheckbox.setChecked( SettingController.vsync );
        vsyncCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.vsync = vsyncCheckbox.isChecked();
                return false;
            }
        });

        TextButton applyButton = new TextButton("Apply", skin);
        TextButton backButton = new TextButton("Back", skin);

        //Add listeners to buttons
        applyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingController.apply();
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        //Add buttons to table
        mainTable.add(new Label("Music volume: ", skin));
        mainTable.add(musicVolumeSlider);
        mainTable.row();
        mainTable.add(new Label("Game volume: ", skin));
        mainTable.add(gameVolumeSlider);
        mainTable.row();
        mainTable.add(new Label("Fps: ", skin));
        mainTable.add(maxFpsSlider);
        mainTable.row();
        mainTable.add(new Label("Fullscreen: ", skin));
        mainTable.add(fullscreenCheckbox);
        mainTable.row();
        mainTable.add(new Label("Vsync: ", skin));
        mainTable.add(vsyncCheckbox);
        mainTable.row();
        mainTable.add(backButton);
        mainTable.add(applyButton);

        //Add table to stage
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
