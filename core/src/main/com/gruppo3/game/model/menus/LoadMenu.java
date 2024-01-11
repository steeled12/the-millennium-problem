package com.gruppo3.game.model.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.MenuController;
import com.gruppo3.game.controller.SaveController;
import com.gruppo3.game.screens.GameScreen;

public class LoadMenu extends MenuState {

    protected Stage stage;
    private Viewport viewport;
    MyGame game = (MyGame) Gdx.app.getApplicationListener();

    public LoadMenu(MenuController loader) {
        super(loader);

        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        // Create Table
        Table mainTable = new Table();
        // Set table to fill stage
        mainTable.setFillParent(true);

        // Create buttons
        TextButton load1Button = new TextButton("Vuoto", skin);
        TextButton load2Button = new TextButton("Vuoto", skin);
        TextButton load3Button = new TextButton("Vuoto", skin);
        TextButton backButton = new TextButton("Back", skin);

        // Add listeners to buttons
        if (SaveController.saveExists(0)) {
            load1Button.setText("Avvia");
            load1Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SaveController.loadSave(0);
                    GameScreen gameScreen = new GameScreen(game);
                    game.currentScreen = gameScreen;
                    game.setScreen(gameScreen);
                }
            });
        }
        if (SaveController.saveExists(1)) {
            load2Button.setText("Avvia");
            load2Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SaveController.loadSave(1);
                    GameScreen gameScreen = new GameScreen(game);
                    game.currentScreen = gameScreen;
                    game.setScreen(gameScreen);
                }
            });
        }
        if (SaveController.saveExists(2)) {
            load3Button.setText("Avvia");
            load3Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SaveController.loadSave(2);
                    GameScreen gameScreen = new GameScreen(game);
                    game.currentScreen = gameScreen;
                    game.setScreen(gameScreen);
                }
            });
        }
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loader.changeState(new MainMenu(loader));
            }
        });

        // Add buttons to table
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Save1: ", skin));
        mainTable.add(load1Button);
        if (SaveController.saveExists(0)) {
            mainTable.add(new Label(" " + SaveController.getSave(0).getString("time"), skin));
        }
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Save2: ", skin));
        mainTable.add(load2Button);
        if (SaveController.saveExists(1)) {
            mainTable.add(new Label(" " + SaveController.getSave(1).getString("time"), skin));
        }
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Save3: ", skin));
        mainTable.add(load3Button);
        if (SaveController.saveExists(2)) {
            mainTable.add(new Label(" " + SaveController.getSave(2).getString("time"), skin));
        }
        mainTable.row().spaceBottom(15);
        mainTable.add(backButton);

        // Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

}
