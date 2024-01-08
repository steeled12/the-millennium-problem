package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.interactables.Cat;
import com.gruppo3.game.model.interactables.PickableItem;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;

public class TutorialLevel extends LevelStrategy {

    Sound duckSound;

    public TutorialLevel() {
        super();

        // map
        this.map = new TmxMapLoader().load("maps/tutorial/Tutorial_map.tmx");
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
        // render
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        this.duckSound = Gdx.audio.newSound(Gdx.files.internal("sound/rubber_duck_sound.mp3"));
    }

    @Override
    public void init() {
        // NPC
        Cat cat = new Cat(
                new Texture("cat.png"));
        cat.getNpcBox().x = 14;
        cat.getNpcBox().y = 16;
        npcController.add(cat);

        // items
        PickableItem latte = new PickableItem("Latte", "milk.png");
        latte.getBox().x = 2;
        latte.getBox().y = 2;
        latte.getBox().width = 1;
        latte.getBox().height = 1;
        itemController.addWithOutId(latte);

        // scriptable
        ScriptableObject paperella = new ScriptableObject(new Rectangle(21, 14, 1, 1), true) {
            @Override
            public void action() {
                duckSound.play(SettingController.gameVolume);
            }
        };

        ScriptableObject porta = new ScriptableObject(new Rectangle(15, 18, 2, 2), true) {
            @Override
            public void action() {
                GameScreen.levelToLoad = "SecretRoomLevel";
                GameScreen.levelController.setLevel(new SecretRoomLevel());
            }
        };

        scriptableObjectsController.scriptableObjectsList.add(paperella);
        scriptableObjectsController.scriptableObjectsList.add(porta);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
