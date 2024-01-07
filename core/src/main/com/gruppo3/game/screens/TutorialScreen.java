package com.gruppo3.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
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
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.interactables.Cat;
import com.badlogic.gdx.audio.Music;

public class TutorialScreen implements Screen {
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

    MenuController menuController;
    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/CoconutMall.mp3"));

    public TutorialScreen(final MyGame game) {
        this.game = game;
        this.stateTime = 0f;
        game.gameState = GameState.RUNNING;

        gameViewport = new ScreenViewport();

        // Initialize camera, map, and renderer
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 16);

        initUI();

        playerController = new PlayerController();
        dialogController = new DialogController(dialogBox, optionBox);
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
        renderer.render();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        gameViewport.apply();
        game.batch.begin();

        float playerBoxY = Player.getPlayer().getPlayerBox().y;
        float playerBoxX = Player.getPlayer().getPlayerBox().x;

        for (NPC npc : npcController.npcList) {
            if (playerBoxY < npc.getNpcBox().y) {
                game.batch.draw(npc.getIdleAnimation(npc.getNPCDirection()).getKeyFrame(stateTime, true),
                        npc.getNpcBox().x,
                        npc.getNpcBox().y, npc.getNpcBox().width, npc.getNpcBox().height);
            }
        }

        game.batch.draw(playerController.getAnimationToRender().getKeyFrame(stateTime, true),
                playerBoxX,
                playerBoxY,
                1, 2);
        System.out.println("Player position: " + playerBoxX + " " + playerBoxY);
        // render migliorato
        for (MapLayer mapLayer : map.getLayers()) {
            if (mapLayer instanceof TiledMapTileLayer) {
                TiledMapTileLayer layer = (TiledMapTileLayer) mapLayer;

                for (int x = (int) playerBoxX; x < playerBoxX + 1; x++) {
                    for (int y = (int) playerBoxY; y < (int) playerBoxY + 2; y++) {
                        Cell cell = layer.getCell(x, y);

                        if (cell != null && cell.getTile() != null) {

                            if (y < playerBoxY) {
                                System.out.println("Tile: " + x + " " + y);
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

        for (NPC npc : npcController.npcList) {
            if (Player.getPlayer().getPlayerBox().y >= npc.getNpcBox().y) {
                game.batch.draw(npc.getIdleAnimation(npc.getNPCDirection()).getKeyFrame(stateTime, true),
                        npc.getNpcBox().x,
                        npc.getNpcBox().y, npc.getNpcBox().width, npc.getNpcBox().height);
            }
        }

        // mostra widget dell'interazione quando il player si avvicina
        interactionController.displayInteractionWidget(game.batch);

        game.batch.end();
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
        music.play();
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
