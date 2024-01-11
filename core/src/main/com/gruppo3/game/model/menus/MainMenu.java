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
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.controller.SaveController;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

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
        mainTable.setBackground(skin.newDrawable("textfield", r, g, b, 1));

        Label title = new Label("The Millennium Problem", skin, "title");
        title.setFontScale(1.2f);

        // Create buttons
        TextButton playButton = new TextButton("Nuova Partita", skin);
        TextButton loadButton = new TextButton("Carica", skin);
        TextButton optionsButton = new TextButton("Impostazioni", skin);
        TextButton exitButton = new TextButton("Chiudi", skin);

        // Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.newGame();
                game.setScreen(new GameScreen(game));
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loader.changeState(new LoadMenu(loader));
            }
        });
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loader.changeState(new OptionMenu(loader, new MainMenu(loader)));
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to table
        mainTable.row().spaceBottom(10);
        mainTable.add(title);
        mainTable.row().spaceBottom(10);
        mainTable.add(playButton);
        mainTable.row().spaceBottom(10);
        mainTable.add(loadButton);
        mainTable.row().spaceBottom(10);
        mainTable.add(optionsButton);
        mainTable.row().spaceBottom(15);
        mainTable.add(exitButton);

        // Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

}
