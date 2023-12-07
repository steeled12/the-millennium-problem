package com.gruppo3.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.NPCController;
import com.gruppo3.game.controller.PlayerController;
import com.gruppo3.game.model.interactables.NPC;

public class TestScreen implements Screen {

    final MyGame game;

    World world;
    Box2DDebugRenderer debugRenderer;

    Texture playerImage;
    public static OrthographicCamera camera;
    PlayerController playerController = new PlayerController();
    NPCController npcController = new NPCController();
    public static TiledMap map;
    OrthogonalTiledMapRenderer renderer;

    public TestScreen(final MyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        map = new TmxMapLoader().load("test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        npcController.add(new NPC(new Texture("player.png")));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        renderer.setView(camera);
        renderer.render(new int[] { 0 });
        renderer.render(new int[] { 1 });

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        playerController.updateInput();
        game.batch.draw(playerController.player.getPlayerImage(), playerController.player.getPlayerBox().x,
                playerController.player.getPlayerBox().y);
        game.batch.end();
        renderer.render(new int[] { 3 });
    }

    @Override
    public void resize(int width, int height) {
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
    }
}
