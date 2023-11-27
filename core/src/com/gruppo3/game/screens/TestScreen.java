package com.gruppo3.game.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class TestScreen implements Screen {

    final MyGame game;

    World world;
    Box2DDebugRenderer debugRenderer;

    Texture playerImage;
    OrthographicCamera camera;
    Rectangle player;

    float playerSpeed = 300f;

    public TestScreen(final MyGame game) {
        this.game = game;

        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        // load the images for the droplet and the player, 64x64 pixels each
        playerImage = new Texture("player.png");

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        // create a Rectangle to logically represent the player
        player = new Rectangle();
        player.x = 1920 / 2 - 64 / 2; // center the player horizontally
        player.y = 1080 / 2 - 64 / 2; // bottom left corner of the player is 20 pixels above
        // the bottom screen edge
        player.width = 64;
        player.height = 64;

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

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the player and
        // all drops
        game.batch.begin();
        game.batch.draw(playerImage, player.x, player.y, player.width, player.height);

        game.batch.end();

        if (Gdx.input.isKeyPressed(Keys.LEFT))
            player.x -= playerSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            player.x += playerSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed((Keys.UP))) {
            player.y += playerSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed((Keys.DOWN))) {
            player.y -= playerSpeed * Gdx.graphics.getDeltaTime();
        }

        // make sure the player stays within the screen bounds
        if (player.x < 0)
            player.x = 0;
        if (player.x > 1920 - 64)
            player.x = 1920 - 64;

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