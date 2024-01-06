package com.gruppo3.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.gruppo3.game.model.interactables.Computer;
import com.gruppo3.game.model.interactables.GenericItem;
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
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.gruppo3.game.model.interactables.Cat;
import com.gruppo3.game.model.interactables.Item;

public class TestScreen implements Screen {
    private final MyGame game;
    public static OrthographicCamera camera;
    private PlayerController playerController;
    private PauseController pauseController;
    private NPCController npcController;
    private ItemController itemController;
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Stage stage;
    private Table dialogRoot;
    private DialogBox dialogBox;
    private OptionBox optionBox;
    private InputMultiplexer multiplexer;
    public static DialogController dialogController;
    private InteractionController interactionController;
    private ScreenViewport gameViewport;
    private ExtendViewport uiViewport;
    float stateTime;
    float unitScale;
    float timer;
    MenuController menuController;
    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/CoconutMall.mp3"));
    int flag = 0;

    public TestScreen(final MyGame game) {
        this.game = game;
        this.stateTime = 0f;
        this.unitScale = 1 / 16f;
        game.gameState = GameState.RUNNING;

        gameViewport = new ScreenViewport();
        // Initialize camera, map, and renderer
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32, 32);
        map = new TmxMapLoader().load("map/atto3/stanza-segreta.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        // scaling a game units
        MapLayer collisionObjectLayer = this.map.getLayers().get("Collisioni");
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
        itemController = new ItemController();
        pauseController = new PauseController(game);
        this.menuController = new MenuController();
        this.menuController.changeState(new PauseMenu(menuController));

        interactionController = new InteractionController(npcController, itemController);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(0, pauseController);
        multiplexer.addProcessor(1, dialogController);
        multiplexer.addProcessor(2, interactionController);
        multiplexer.addProcessor(3, playerController);
        Gdx.input.setInputProcessor(multiplexer);

        Computer computer = new Computer();
        itemController.addwithId(computer, 3);

        Cat npc = new Cat(new Texture("map/atto3/cat.png"));
        npc.getNpcBox().x = 10;
        npc.getNpcBox().y = 10;
        npcController.add(npc);

        // tutti i dialoghi

        GenericItem libreria = new GenericItem("libreria");
        itemController.addwithId(libreria, 4);
        Dialog libDialog = new Dialog();
        LinearDialogNode libNode0 = new LinearDialogNode("Un foglietto sporge leggermente \n dalla libreria", 0);
        ChoiceDialogNode libNode1 = new ChoiceDialogNode("Leggi il foglietto?", 1);
        LinearDialogNode linNode2 = new LinearDialogNode("\"Il vero grande fratello nacque quell\'anno\"", 2);

        libNode0.setPointer(1);
        libNode1.addChoice("Si", 2);
        libNode1.addChoice("No");

        libDialog.addNode(libNode0);
        libDialog.addNode(libNode1);
        libDialog.addNode(linNode2);

        libreria.setDialog(libDialog);

        GenericItem cassetto = new GenericItem("cassetto");
        itemController.addwithId(cassetto, 34);
        Dialog cassDialog = new Dialog();
        LinearDialogNode cassNode0 = new LinearDialogNode("Ci sono delle scritte incise sul cassetto", 0);
        LinearDialogNode cassNode1 = new LinearDialogNode(
                "\"Sono la struttura migliore, ma tutti si \n dimenticano di me\"", 1);

        cassDialog.addNode(cassNode0);
        cassDialog.addNode(cassNode1);

        cassetto.setDialog(cassDialog);

        /*
         * PickableItem pippo = new PickableItem("caminoPippo", new
         * Texture("map/atto3/Living_Room_Singles_111.png"));
         * pippo.getBox().x = 10;
         * pippo.getBox().y = 10;
         * pippo.getBox().width = 1;
         * pippo.getBox().height = 1;
         * itemController.addWithOutId(pippo);
         */

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

        music.setVolume(SettingController.musicVolume);
    }

    private void renderGame() {
        renderer.setView(camera);
        renderer.render(new int[] { 0 });
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        gameViewport.apply();
        game.batch.begin();
        for (NPC npc : npcController.npcList) {
            game.batch.draw(npc.getIdleAnimation(npc.getNPCDirection()).getKeyFrame(stateTime, true), npc.getNpcBox().x,
                    npc.getNpcBox().y, 1, 1);
        }

        for (Item item : itemController.itemList) {
            if (item.getTexture() != null) {
                game.batch.draw(item.getTexture(), item.getBox().x, item.getBox().y, item.getBox().width,
                        item.getBox().height);
            }
        }

        game.batch.draw(playerController.getAnimationToRender().getKeyFrame(stateTime, true),
                Player.getPlayer().getPlayerBox().x,
                Player.getPlayer().getPlayerBox().y,
                1, 2);
        renderer.render(new int[] { 1, 2 });
        // mostra widget dell'interazione quando il player si avvicina
        interactionController.displayInteractionWidget(game.batch);

        game.batch.end();

    }

    private void renderUI() {
        dialogController.update(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        stage.draw();
    }

    public static void setMusic(Music music) {
        TestScreen.music = music;
        music.play();
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
        music.setLooping(true);
    }

    @Override
    public void hide() {
        music.stop();
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
        music.dispose();
    }
}
