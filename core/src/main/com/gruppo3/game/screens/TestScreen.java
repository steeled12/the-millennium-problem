package com.gruppo3.game.screens;

import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.MyGame.GameState;
import com.gruppo3.game.controller.*;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.menus.PauseMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    float unitScale;
    MenuController menuController;

    public TestScreen(final MyGame game) {
        this.game = game;
        this.stateTime = 0f;
        this.unitScale = 1 / 16f;
        game.gameState = GameState.RUNNING;

        gameViewport = new ScreenViewport();
        // Initialize camera, map, and renderer
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32, 32);
        map = new TmxMapLoader().load("map/tutorial/tutorialMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        // scaling a game units
        MapLayer collisionObjectLayer = TestScreen.map.getLayers().get("Collisioni");
        for (MapObject object : collisionObjectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                rect.x *= unitScale;
                rect.y *= unitScale;
                rect.width *= unitScale;
                rect.height *= unitScale;
            }
        }

        initUI();

        playerController = new PlayerController();
        dialogController = new DialogController(dialogBox, optionBox);
        npcController = new NPCController();
        pauseController = new PauseController(game);
        this.menuController = new MenuController();
        this.menuController.changeState(new PauseMenu(menuController));

        interactionController = new InteractionController(npcController.npcList, this.itemList);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(0, pauseController);
        multiplexer.addProcessor(1, dialogController);
        multiplexer.addProcessor(2, interactionController);
        multiplexer.addProcessor(3, playerController);
        Gdx.input.setInputProcessor(multiplexer);

        // Creo un NPC

        NPC npc = new NPC(
                new Texture("Modern_Interiors_Free_v2.2/Modern tiles_Free/Characters_free/Amelia_idle_anim_16x16.png"));
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
        uiViewport = new ExtendViewport(400, 300);
        stage = new Stage(uiViewport);

        stage.getViewport().update(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5, true);

        dialogRoot = new Table();
        dialogRoot.setFillParent(true);
        dialogRoot.setWidth(300);
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
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        ScreenUtils.clear(0, 0, 0, 1);
        if (game.gameState.equals(GameState.RUNNING)) {
            multiplexer.removeProcessor(menuController.getStage());
            if (!dialogBox.isVisible()) {
                playerController.updateInput();
            }
        }

        renderGame();
        renderUI();

        if (game.gameState.equals(GameState.PAUSED)) {
            if (!multiplexer.getProcessors().contains(menuController.getStage(), true)) {
                multiplexer.addProcessor(4, menuController.getStage());
            }
            menuController.getStage().act();
            menuController.getStage().draw();
        }
    }

    private void renderGame() {
        Gdx.app.log("player position", Player.getPlayer().getPlayerBox().x + " " + Player.getPlayer().getPlayerBox().y);
        renderer.setView(camera);
        renderer.render(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        gameViewport.apply();
        game.batch.begin();
        for (NPC npc : npcController.npcList) {
            game.batch.draw(npc.getIdleAnimation(npc.getNPCDirection()).getKeyFrame(stateTime, true), npc.getNpcBox().x,
                    npc.getNpcBox().y, 1, 2);
        }

        game.batch.draw(playerController.getAnimationToRender().getKeyFrame(stateTime, true),
                Player.getPlayer().getPlayerBox().x,
                Player.getPlayer().getPlayerBox().y,
                1, 2);

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
        camera.viewportWidth = 32f;
        camera.viewportHeight = 32f * height / width;
        camera.update();
        gameViewport.update(width, height, true);

        stage.getViewport().update(width, height, true);
        menuController.getStage().getViewport().update(width, height, true);
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
