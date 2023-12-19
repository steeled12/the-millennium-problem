package com.gruppo3.game.screens;

import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.MyGame.GameState;
import com.gruppo3.game.controller.*;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.interactables.NPC.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gruppo3.game.ui.DialogBox;
import com.gruppo3.game.ui.OptionBox;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.InputMultiplexer;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.interactables.Item;

public class TestScreen implements Screen {
    private final MyGame game;
    public static OrthographicCamera camera;
    private PlayerController playerController;
    private PauseController pauseController;
    private NPCController npcController;
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Stage stage;
    private Stage pauseStage;
    private Table dialogRoot;
    private DialogBox dialogBox;
    private OptionBox optionBox;
    private InputMultiplexer multiplexer;
    private Dialog dialog;
    public static DialogController dialogController;
    private InteractionController interactionController;
    private List<Item> itemList;

    private ScreenViewport gameViewport;
    private ExtendViewport uiViewport;
    float stateTime;

    public TestScreen(final MyGame game) {
        this.game = game;
        this.stateTime = 0f;
        game.gameState = GameState.RUNNING;

        gameViewport = new ScreenViewport();
        // Initialize camera, map, and renderer
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        map = new TmxMapLoader().load("map/tutorial/tutorialMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        initUI();

        playerController = new PlayerController();
        dialogController = new DialogController(dialogBox, optionBox);
        npcController = new NPCController();
        pauseController = new PauseController(game);

        interactionController = new InteractionController(npcController.npcList, this.itemList);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(0, pauseController);
        multiplexer.addProcessor(1, dialogController);
        multiplexer.addProcessor(2, interactionController);
        multiplexer.addProcessor(3, playerController);
        Gdx.input.setInputProcessor(multiplexer);

        // Creo un NPC

        NPC npc = new NPC(
                new Texture("Modern_Interiors_Free_v2.2/Modern tiles_Free/Characters_free/Alex_sit3_16x16.png"));
        npcController.add(npc);
        dialog = new Dialog();
        LinearDialogNode node1 = new LinearDialogNode("Ciao avventuriero!", 0);
        ChoiceDialogNode node2 = new ChoiceDialogNode("Come stai?", 1);
        LinearDialogNode node3 = new LinearDialogNode("Ah ok!", 2);

        node1.setPointer(1);
        node2.addChoice("Bene, grazie!", 2);
        node2.addChoice("No", 2);

        dialog.addNode(node1);
        dialog.addNode(node2);
        dialog.addNode(node3);

        npc.setDialog(dialog);
    }

    private void initUI() {
        uiViewport = new ExtendViewport(800, 480);
        stage = new Stage(uiViewport);

        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        dialogRoot = new Table();
        dialogRoot.setFillParent(true);
        stage.addActor(dialogRoot);

        dialogBox = new DialogBox(MyGame.skin);
        dialogBox.setVisible(false);

        optionBox = new OptionBox(MyGame.skin);
        optionBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(optionBox)
                .expand()
                .align(Align.right)
                .space(8f)
                .row();
        dialogTable.add(dialogBox)
                .expand()
                .align(Align.bottom)
                .space(8f)
                .row();

        dialogRoot.add(dialogTable).expand().align(Align.bottom);

        /*** PAUSE UI ***/
        pauseStage = new Stage(uiViewport);

        TextureAtlas atlas = new TextureAtlas("flat-earth/skin/flat-earth-ui.atlas");
        Skin skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"), atlas);

        // Create Table
        Table mainTable = new Table();
        // Set table to fill stage
        mainTable.setFillParent(true);

        // Create buttons
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton saveButton = new TextButton("Save", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Add listeners to buttons
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.gameState = GameState.RUNNING;
            }
        });
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveController.save();
            }
        });
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionScreen(game));
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to table
        mainTable.add(resumeButton);
        mainTable.row();
        mainTable.add(saveButton);
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.row();
        mainTable.add(exitButton);

        // Add table to stage
        pauseStage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {

        stateTime += delta;

        if (game.gameState.equals(GameState.RUNNING)) {
            multiplexer.removeProcessor(pauseStage);
            ScreenUtils.clear(1, 1, 1, 1);
            renderGame();
            renderUI();
        }

        if (game.gameState.equals(GameState.PAUSED)) {
            if (!multiplexer.getProcessors().contains(pauseStage, true)) {
                multiplexer.addProcessor(4, pauseStage);
            }
            pauseStage.act();
            pauseStage.draw();
        }
    }

    private void renderGame() {
        renderer.setView(camera);
        renderer.render(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        gameViewport.apply();
        game.batch.begin();
        for (NPC npc : npcController.npcList) {
            game.batch.draw(npc.getIdleAnimation(Direction.NORTH).getKeyFrame(stateTime, true), npc.getNpcBox().x,
                    npc.getNpcBox().y);
        }
        if (!dialogBox.isVisible()) {
            playerController.updateInput();
        }
        game.batch.draw(playerController.getAnimationToRender().getKeyFrame(stateTime, true),
                Player.getPlayer().getPlayerBox().x,
                Player.getPlayer().getPlayerBox().y);
        game.batch.end();

        renderer.render(new int[] { 10, 11 });
    }

    private void renderUI() {
        dialogController.update(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        gameViewport.update(width, height, true);

        stage.getViewport().update(width, height, true);
        pauseStage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        game.gameState = GameState.PAUSED;
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        map.dispose();
        renderer.dispose();
    }
}
