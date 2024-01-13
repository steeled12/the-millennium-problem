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

public class SaveMenu extends MenuState {

    MenuState backState;
    protected Stage stage;
    private Viewport viewport;
    MyGame game = (MyGame) Gdx.app.getApplicationListener();

    public SaveMenu(MenuController loader, MenuState backState) {
        super(loader);

        this.backState = backState;
        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        // Create Table
        Table mainTable = new Table();
        // Set table to fill stage
        mainTable.setFillParent(true);
        mainTable.setBackground(skin.newDrawable("textfield", r, g, b, 1));

        // Create buttons
        TextButton save1Button = new TextButton("Salva", skin);
        TextButton save2Button = new TextButton("Salva", skin);
        TextButton save3Button = new TextButton("Salva", skin);
        TextButton backButton = new TextButton("Back", skin);

        // Add listeners to buttons
        if (SaveController.saveExists(0)) {
            save1Button.setText("Sovrascrivi");
        }
        save1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.save(0);
                loader.changeState(backState);
            }
        });
        if (SaveController.saveExists(1)) {
            save2Button.setText("Sovrascrivi");
        }
        save2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.save(1);
                loader.changeState(backState);
            }
        });
        if (SaveController.saveExists(2)) {
            save3Button.setText("Sovrascrivi");
        }
        save3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.save(2);
                loader.changeState(backState);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loader.changeState(backState);
            }
        });

        // Add buttons to table
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Salvataggi", skin, "title")).colspan(4).center();
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Save1: ", skin));
        mainTable.add(save1Button);
        if (SaveController.saveExists(0)) {
            mainTable.add(new Label(" " + SaveController.getSave(0).getString("time"), skin));
        }
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Save2: ", skin));
        mainTable.add(save2Button);
        if (SaveController.saveExists(1)) {
            mainTable.add(new Label(" " + SaveController.getSave(1).getString("time"), skin));
        }
        mainTable.row().spaceBottom(10);
        mainTable.add(new Label("Save3: ", skin));
        mainTable.add(save3Button);
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
