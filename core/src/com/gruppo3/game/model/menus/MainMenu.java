package com.gruppo3.game.model.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.MenuController;
import com.gruppo3.game.screens.TestScreen;

public class MainMenu extends MenuState {

    Stage stage;
    private Viewport viewport;
    MyGame game = (MyGame) Gdx.app.getApplicationListener();

    public MainMenu(MenuController loader) {
        super(loader);

        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        // Create Table
        Table mainTable = new Table();
        // Set table to fill stage
        mainTable.setFillParent(true);

        // Create buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton loadButton = new TextButton("Load save", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TestScreen(game));
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loader.changeState(new SavesMenu(loader));
            }
        });
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loader.changeState(new OptionMenu(loader));
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to table
        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(loadButton);
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.row();
        mainTable.add(exitButton);

        // Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

}
