package com.gruppo3.game.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.NPCController;
import com.gruppo3.game.controller.PlayerMovementController;
import com.gruppo3.game.model.NPC;
import com.gruppo3.game.model.Player;
import com.badlogic.gdx.maps.MapObject;

public class TestScreen implements Screen {

    final MyGame game;

    World world;
    Box2DDebugRenderer debugRenderer;

    Texture playerImage;
    public static OrthographicCamera camera;
    PlayerMovementController playerMovementController = new PlayerMovementController();
    NPCController npcController = new NPCController();
    public static TiledMap map;
    OrthogonalTiledMapRenderer renderer;


    public TestScreen(final MyGame game) {
        this.game = game;

        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        map = new TmxMapLoader().load("test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        npcController.add(new NPC(new Texture("player.png")));

        // Adding player to a TiledMapObjectLayer
        // Create new MapObject to store player object in.
        MapObject object = new MapObject();
        // Put the player object in the properties of the map object.
        object.getProperties().put("player", playerMovementController.player);
        // Then add the object to whatever layer you wish.
        map.getLayers().get("Player").getObjects().add(object);

        
        // load the images for the droplet and the player, 64x64 pixels each

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we
        // would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 0);

        // Create our body in the world using our body definition
        Body player = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = player.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(1, 1, 1, 1);
        renderer.setView(camera);
        renderer.render(new int[]{0});
        renderer.render(new int[]{1});
        // tell the camera to update its matrices.
        camera.update();

        npcController.render(game.batch);

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the player and
        // all drops
        game.batch.begin();
        
        playerMovementController.updateInput();
        Player player = (Player) map.getLayers().get("Player").getObjects().get(0).getProperties().get("player");
        game.batch.draw(player.getPlayerImage(), player.getPlayerBox().x, player.getPlayerBox().y);
    
        game.batch.end();
        renderer.render(new int[]{3});
        world.step(1 / 60f, 6, 2);
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
