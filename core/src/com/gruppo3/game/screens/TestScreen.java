package com.gruppo3.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.NPCController;
import com.gruppo3.game.controller.PlayerController;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.NpcDialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TestScreen implements Screen {

    private final MyGame game;

    private Texture playerImage;
    public static OrthographicCamera camera;
    private PlayerController playerController = new PlayerController();
    private NPCController npcController = new NPCController();
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Stage stage;
    private NpcDialog dialog;

    private ExtendViewport gameViewport;
    private ExtendViewport uiViewport;

    public TestScreen(final MyGame game) {
        this.game = game;

        // Initialize camera, map, and renderer
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        map = new TmxMapLoader().load("test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        // Initialize game elements
        playerImage = new Texture("player.png");
        npcController.add(new NPC(playerImage));

        // Initialize game viewport with a constant aspect ratio
        gameViewport = new ExtendViewport(800, 480, camera) {
            @Override
            public void update(int screenWidth, int screenHeight, boolean centerCamera) {
                float aspectRatio = 16f / 9f; // Set your desired aspect ratio
                setMinWorldWidth(getMinWorldHeight() * aspectRatio);
                setMaxWorldWidth(getMaxWorldHeight() * aspectRatio);
                super.update(screenWidth, screenHeight, centerCamera);
            }
        };

        // Initialize UI viewport for rendering the dialog at the bottom
        uiViewport = new ExtendViewport(800, 100);

        // Initialize UI
        stage = new Stage(uiViewport);
        Gdx.input.setInputProcessor(stage);

        dialog = new NpcDialog("", new Skin(Gdx.files.internal("uiskin.json")));

        // Set NPC information and text
        dialog.setNpcInfo("Pippo IL BRO", playerImage);
        dialog.setDialogText("Ao fra benvenuto a casa mia");

        // Set dialog position to the bottom of the screen
        dialog.setPosition(uiViewport.getWorldWidth() / 2f - dialog.getWidth() / 2f, 10);

        // Show the dialog in the stage
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        // Render game elements
        renderGame();

        // Render UI
        renderUI();
    }

    private void renderGame() {
        // Update the game viewport
        gameViewport.apply();

        // Set the projection matrix for rendering game elements
        renderer.setView(camera);
        renderer.render(new int[] { 0, 1, 3 });

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        playerController.updateInput();
        game.batch.draw(playerController.getTextureToRender(),
                playerController.player.getPlayerBox().x, playerController.player.getPlayerBox().y);
        game.batch.end();
    }

    private void renderUI() {
        // Update and render UI
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update the game viewport
        gameViewport.update(width, height, true);

        // Update the UI viewport
        uiViewport.update(width, height, true);
        // Set dialog position to the bottom of the screen
        dialog.setPosition(uiViewport.getWorldWidth() / 2f - dialog.getWidth() / 2f, 10);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        playerImage.dispose();
        stage.dispose();
        map.dispose();
        renderer.dispose();
    }
}
