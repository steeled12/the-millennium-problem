package com.gruppo3.game.model.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo3.game.controller.MenuController;
import com.gruppo3.game.controller.SettingController;

public class OptionMenu extends MenuState {

    Stage stage;
    private Viewport viewport;
    MenuState backState;

    public OptionMenu(MenuController loader, MenuState backState) {
        super(loader);

        this.backState = backState;

        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        // Create Table
        Table mainTable = new Table();
        // Set table to fill stage
        mainTable.setFillParent(true);

        // Create buttons
        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        musicVolumeSlider.setValue(SettingController.musicVolume);
        musicVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.musicVolume = musicVolumeSlider.getValue();
                return false;
            }
        });

        final Slider gameVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        gameVolumeSlider.setValue(SettingController.gameVolume);
        gameVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.gameVolume = gameVolumeSlider.getValue();
                return false;
            }
        });

        final Slider maxFpsSlider = new Slider(30, 244, 1, false, skin);
        maxFpsSlider.setValue(SettingController.maxFps);
        maxFpsSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.maxFps = (int) maxFpsSlider.getValue();
                return false;
            }
        });

        final CheckBox fullscreenCheckbox = new CheckBox(null, skin);
        fullscreenCheckbox.setChecked(SettingController.fullscreen);
        fullscreenCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.fullscreen = fullscreenCheckbox.isChecked();
                return false;
            }
        });

        final CheckBox vsyncCheckbox = new CheckBox(null, skin);
        vsyncCheckbox.setChecked(SettingController.vsync);
        vsyncCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                SettingController.vsync = vsyncCheckbox.isChecked();
                return false;
            }
        });

        TextButton applyButton = new TextButton("Apply", skin);
        TextButton backButton = new TextButton("Back", skin);

        // Add listeners to buttons
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingController.apply();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loader.changeState(backState);
            }
        });

        // Add buttons to table
        mainTable.row().spaceBottom(5);
        mainTable.add(new Label("Music volume: ", skin));
        mainTable.add(musicVolumeSlider);
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Game volume: ", skin));
        mainTable.add(gameVolumeSlider);
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Fps: ", skin));
        mainTable.add(maxFpsSlider);
        mainTable.row().spaceBottom(5);
        mainTable.add(new Label("Fullscreen: ", skin));
        mainTable.add(fullscreenCheckbox);
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Vsync: ", skin));
        mainTable.add(vsyncCheckbox);
        mainTable.row().spaceBottom(15);
        mainTable.add(backButton);
        mainTable.add(applyButton);

        // Add table to stage
        stage.addActor(mainTable);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    loader.changeState(backState);
                }
                return false;
            }
        });
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

}
