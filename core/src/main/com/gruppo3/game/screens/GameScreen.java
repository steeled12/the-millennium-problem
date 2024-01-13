package com.gruppo3.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.MyGame.GameState;
import com.gruppo3.game.controller.*;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.level.SecretRoomLevel;
import com.gruppo3.game.model.level.StanzaRettangoloAtto1Level;
import com.gruppo3.game.model.level.StanzaRettangoloAtto2Level;
import com.gruppo3.game.model.menus.PauseMenu;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gruppo3.game.ui.DialogBox;
import com.gruppo3.game.ui.OptionBox;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.InputMultiplexer;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.interactables.Item;
import com.gruppo3.game.model.level.TutorialLevel;
import com.gruppo3.game.model.level.CorridoioAtto2Level;
import com.gruppo3.game.model.level.CreditiLevel;
import com.gruppo3.game.model.level.PrimoPianoAtto1Level;
import com.gruppo3.game.model.level.AttoFinaleLevel;
import com.gruppo3.game.model.level.Aula4Atto1Level;
import com.gruppo3.game.model.level.Aula4Level;
import com.gruppo3.game.model.level.Aula6Atto1Level;
import com.gruppo3.game.model.level.Aula6Level;
import com.gruppo3.game.model.level.CorridoioAtto1Level;
import com.gruppo3.game.model.level.SotterraneiAtto2Level;
import com.gruppo3.game.model.level.SotterraneiAtto3Level;
import com.gruppo3.game.model.level.StanzaRettoreLevel;

public class GameScreen implements Screen {
    private final MyGame game;
    public static OrthographicCamera camera;
    private PlayerController playerController;
    private PauseController pauseController;
    MenuController menuController;
    public static LevelController levelController;
    public static DialogController dialogController;
    private static InteractionController interactionController;

    private Stage stage;
    private static Stage stageInventory;
    private Table dialogRoot;
    private DialogBox dialogBox;
    private OptionBox optionBox;

    private InputMultiplexer multiplexer;

    private ScreenViewport gameViewport;
    private ExtendViewport uiViewport;
    float stateTime;
    float timer;
    public static String levelToLoad = "TutorialLevel";
    int flag = 0;
    public static Map<String, String> savedInformation = new HashMap<>();

    public GameScreen(final MyGame game) {
        this.game = game;
        this.stateTime = 0f;
        this.gameViewport = new ScreenViewport();

        // Initialize camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32, 32);

        initUI();

        this.playerController = new PlayerController();
        /*
         * Gdx.app.log("GameScreen", "cotnroelr: " + Player.getPlayer().toString());
         * Gdx.app.log("fasf", "ASD" +
         * this.playerController.getAnimationToRender().toString());
         */
        dialogController = new DialogController(dialogBox, optionBox);
        this.pauseController = new PauseController(game);
        this.menuController = new MenuController();
        this.menuController.changeState(new PauseMenu(menuController));
        Gdx.app.log("GameScreen", "Loading level: " + levelToLoad);
        switch (levelToLoad) {
            case "TutorialLevel":
                levelController = new LevelController(new TutorialLevel());
                break;
            case "PrimoPianoAtto1Level":
                levelController = new LevelController(new PrimoPianoAtto1Level());
                break;
            case "StanzaRettangoloAtto1Level":
                levelController = new LevelController(new StanzaRettangoloAtto1Level());
                break;
            case "CorridoioAtto1Level":
                levelController = new LevelController(new CorridoioAtto1Level());
                break;
            case "Aula4Atto1Level":
                levelController = new LevelController(new Aula4Atto1Level());
                break;
            case "Aula6Atto1Level":
                levelController = new LevelController(new Aula6Atto1Level());
                break;
            case "CorridoioAtto2Level":
                levelController = new LevelController(new CorridoioAtto2Level());
                break;
            case "SotterraneiAtto2Level":
                levelController = new LevelController(new SotterraneiAtto2Level());
                break;
            case "SotterraneiAtto3Level":
                levelController = new LevelController(new SotterraneiAtto3Level());
                break;
            case "Aula4Level":
                levelController = new LevelController(new Aula4Level());
                break;
            case "Aula6Level":
                levelController = new LevelController(new Aula6Level());
                break;
            case "SecretRoomLevel":
                levelController = new LevelController(new SecretRoomLevel());
                break;
            case "StanzaRettangoloAtto2Level":
                levelController = new LevelController(new StanzaRettangoloAtto2Level());
                break;
            case "StanzaRettoreLevel":
                levelController = new LevelController(new StanzaRettoreLevel());
                break;
            case "AttoFinaleLevel":
                levelController = new LevelController(new AttoFinaleLevel());
                break;
            case "CreditLevel":
                levelController = new LevelController(new CreditiLevel());
                break;
            default:
                levelController = new LevelController(new TutorialLevel());
                break;
        }
        levelController.init();
        interactionController = new InteractionController(levelController.getNpcController(),
                levelController.getItemController(),
                levelController.getScriptableObjectsController());

        Gdx.app.log("uga", savedInformation.toString());

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(0, pauseController);
        multiplexer.addProcessor(1, dialogController);
        multiplexer.addProcessor(2, interactionController);
        multiplexer.addProcessor(3, playerController);
        Gdx.input.setInputProcessor(multiplexer);

        game.gameState = GameState.RUNNING;
        levelController.setMusicVolume(SettingController.musicVolume);
    }

    private void initUI() {
        uiViewport = new ExtendViewport(400, 300);
        // INVENTORY UI
        stageInventory = new Stage(new ScreenViewport());
        GameScreen.updateInventoryUI();

        // DIALOG UI
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
        camera.position.set(Player.getPlayer().getPlayerBox().x, Player.getPlayer().getPlayerBox().y,
                GameScreen.camera.position.z);
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

        // levelController.setMusicVolume(SettingController.musicVolume);
    }

    private void renderGame() {
        levelController.getRenderer().setView(camera);
        levelController.getRenderer().render();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        gameViewport.apply();
        game.batch.begin();

        float playerBoxY = Player.getPlayer().getPlayerBox().y;
        float playerBoxX = Player.getPlayer().getPlayerBox().x;

        // render NPC
        for (NPC npc : levelController.getNpcController().npcList) {
            game.batch.draw(npc.getFrame(stateTime),
                    npc.getNpcBox().x,
                    npc.getNpcBox().y, npc.getNpcBox().width, npc.getNpcBox().height);
        }

        // render ITEM
        for (Item item : levelController.getItemController().itemList) {
            if (item.getTexture() != null) {
                game.batch.draw(item.getTexture(), item.getBox().x, item.getBox().y, item.getBox().width,
                        item.getBox().height);
            }
        }

        // render PLAYER
        game.batch.draw(playerController.getAnimationToRender().getKeyFrame(stateTime,
                true),
                playerBoxX,
                playerBoxY,
                1, 2);

        // render MIGLIORATO
        for (MapLayer mapLayer : levelController.getMap().getLayers()) {
            if (mapLayer instanceof TiledMapTileLayer) {
                TiledMapTileLayer layer = (TiledMapTileLayer) mapLayer;

                for (int x = (int) playerBoxX; x < playerBoxX + 1; x++) {
                    for (int y = (int) playerBoxY; y < (int) playerBoxY + 2; y++) {
                        Cell cell = layer.getCell(x, y);

                        if (cell != null && cell.getTile() != null) {

                            if (y < playerBoxY) { // fix quando
                                                  // collisione
                                // System.out.println("Tile: " + x + " " + y);
                                game.batch.draw(cell.getTile().getTextureRegion(),
                                        x,
                                        y,
                                        1,
                                        1);

                                // se c'è una tile collegata sopra la renderizzo pure
                                cell = layer.getCell(x, y + 1);

                                if (cell != null && cell.getTile() != null) {
                                    y++;
                                    game.batch.draw(cell.getTile().getTextureRegion(),
                                            x,
                                            y,
                                            1,
                                            1);
                                    cell = layer.getCell(x, y + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (NPC npc : levelController.getNpcController().npcList) {
            if (Player.getPlayer().getPlayerBox().y >= npc.getNpcBox().y) {
                game.batch.draw(npc.getIdleAnimation(npc.getNPCDirection()).getKeyFrame(stateTime, true),
                        npc.getNpcBox().x,
                        npc.getNpcBox().y, npc.getNpcBox().width, npc.getNpcBox().height);
            }
        }

        // render WIDGET dell'interazione quando il player si avvicina
        interactionController.displayInteractionWidget(game.batch);

        game.batch.end();
    }

    private void renderUI() {
        dialogController.update(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        stageInventory.act();
        stageInventory.draw();
    }

    public static void updateInventoryUI() {
        stageInventory.clear(); // pulisco

        // Setting della tabella
        Table container = new Table();
        container.setFillParent(true);
        container.top().left();
        container.padTop(8);
        container.padLeft(8);

        Table inventoryTable = new Table(MyGame.skin);
        inventoryTable.setBackground("optionbox");
        inventoryTable.top().left();
        inventoryTable.pad(8);
        inventoryTable.row().spaceBottom(32);
        inventoryTable.add(new Label("Inventario:", MyGame.skin));

        // aggiungo gli items
        inventoryTable.row().spaceRight(8);
        for (Item item : Player.getPlayer().getInventory()) {
            Table itemTable = new Table();
            itemTable.padRight(5);
            Image itemImage = new Image(item.getTexture());
            itemImage.scaleBy(1);
            itemTable.add(itemImage);
            itemTable.row();
            itemTable.add(new Label(item.getName(), MyGame.skin));
            inventoryTable.add(itemTable);
        }
        container.add(inventoryTable);
        stageInventory.addActor(container);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 32f;
        camera.viewportHeight = 32f * height / width;
        camera.update();
        gameViewport.update(width, height, true);

        stage.getViewport().update(width, height, true);
        stageInventory.getViewport().update(width, height, true);
        menuController.getStage().getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        // music.setLooping(true);
    }

    @Override
    public void hide() {
        levelController.getCurrentLevel().dispose();
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
        levelController.dispose();
        // music.dispose();
    }

    public static void updateInteractionController() {
        interactionController.updateContollers(levelController.getNpcController(), levelController.getItemController(),
                levelController.getScriptableObjectsController());
    }
}
