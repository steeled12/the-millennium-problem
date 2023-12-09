package com.gruppo3.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.NPCController;
import com.gruppo3.game.controller.PlayerController;
import com.gruppo3.game.controller.OptionBoxController;
import com.gruppo3.game.model.interactables.NPC;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gruppo3.game.ui.DialogBox;
import com.gruppo3.game.ui.OptionBox;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.InputMultiplexer;
import com.gruppo3.game.dialog.Dialog;
import com.gruppo3.game.controller.DialogController;
import com.gruppo3.game.dialog.LinearDialogNode;
import com.gruppo3.game.dialog.ChoiceDialogNode;



public class TestScreen implements Screen {

    private final MyGame game;

    private Texture playerImage;
    public static OrthographicCamera camera;
    private PlayerController playerController;
    private NPCController npcController = new NPCController();
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Stage stage;
    private Table dialogRoot;
    private DialogBox dialogBox;
    private OptionBox optionBox;
    private InputMultiplexer multiplexer;
    private Dialog dialog;
    private DialogController dialogcontroller;

    private ScreenViewport gameViewport;
    private ScreenViewport uiViewport;

    public TestScreen(final MyGame game) {
        this.game = game;
        gameViewport = new ScreenViewport();
        // Initialize camera, map, and renderer
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        map = new TmxMapLoader().load("test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        // Initialize game elements
        playerImage = new Texture("player.png");
        npcController.add(new NPC(playerImage));
        playerController = new PlayerController();
        
        initUI();
        dialogcontroller = new DialogController(dialogBox, optionBox);
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(0, dialogcontroller);
        multiplexer.addProcessor(1, playerController);
        Gdx.input.setInputProcessor(multiplexer);
        dialog = new Dialog();
        LinearDialogNode node1 = new LinearDialogNode("Ciao avventuriero!", 0);
        ChoiceDialogNode node2 = new ChoiceDialogNode("Come stai?", 1);
        LinearDialogNode node3 = new LinearDialogNode("Ah ok!", 2);

        node1.setPointer(1);
        node2.addChoice("Bene, grazie!", 2);
        node2.addChoice("Suca", 2);

        dialog.addNode(node1);
        dialog.addNode(node2); 
        dialog.addNode(node3);

        dialogcontroller.startDialog(dialog);
    }

    private void initUI() {
        uiViewport = new ScreenViewport();
        stage = new Stage(uiViewport);

        stage.getViewport().update(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, true);

        dialogRoot = new Table();
		dialogRoot.setFillParent(true);
		stage.addActor(dialogRoot);
		
		dialogBox = new DialogBox(game.skin);
		dialogBox.setVisible(false);
		
		optionBox = new OptionBox(game.skin);
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
        ScreenUtils.clear(1, 1, 1, 1);
        renderGame();
        renderUI(); 
    }

    private void renderGame() {
        renderer.setView(camera);
        renderer.render(new int[] { 0, 1 });
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        gameViewport.apply();
        game.batch.begin();
		if (!dialogBox.isVisible()) {
			playerController.updateInput();
            game.batch.draw(playerController.getTextureToRender(), playerController.player.getPlayerBox().x, playerController.player.getPlayerBox().y);
		}
        game.batch.end();

        renderer.render(new int[] { 3 });
        
    }

    private void renderUI() {
        dialogcontroller.update(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        gameViewport.update(width, height, true);

        uiViewport.update(width/2, height/2, true);
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
