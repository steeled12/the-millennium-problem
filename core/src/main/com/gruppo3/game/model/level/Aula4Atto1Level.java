package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;

public class Aula4Atto1Level extends LevelStrategy {

    public Aula4Atto1Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto2/aula41.tmx");
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
        Player.getPlayer().getPlayerBox().x = 25;
        Player.getPlayer().getPlayerBox().y = 1;
    }

    @Override
    public void init() {
        // items
        ScriptableObject portaSx = new ScriptableObject(new Rectangle(4, 0, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new CorridoioAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 58, 14);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaSx);

        ScriptableObject portaDx = new ScriptableObject(new Rectangle(24, 0, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new CorridoioAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 64, 14);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);

            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaDx);

        NPC peppe = new NPC(new Texture(Gdx.files.internal("characters/peppe.png")));
        peppe.getNpcBox().x = 1;
        peppe.getNpcBox().y = 1;
        npcController.add(peppe);

        Dialog peppeDialog = new Dialog();

        peppeDialog.addNode(new LinearDialogNode("(Cosa stanno facendo questi tre?)", 0).setPointer(1));
        peppeDialog.addNode(new LinearDialogNode("Ragazzi cosa state facendo?", 1).setPointer(2));
        peppeDialog.addNode(new LinearDialogNode("Peppe:\nCiao! Ma chi sei?", 2).setPointer(3));
        peppeDialog.addNode(new LinearDialogNode("Io sono..", 3).setPointer(4));
        peppeDialog.addNode(
                new LinearDialogNode("Cristina:\nStiamo facendo il progetto di Ingegneria e Sicurezza del Software", 4)
                        .setPointer(5));
        peppeDialog.addNode(
                new LinearDialogNode("E di cosa si tratta?", 5)
                        .setPointer(6));
        peppeDialog.addNode(
                new LinearDialogNode("Andrea:\nBoh", 6)
                        .setPointer(7));
        peppeDialog.addNode(
                new LinearDialogNode("(Non mi sembrano messi bene)", 7)
                        .setPointer(8));
        peppeDialog.addNode(
                new LinearDialogNode("(In caso più tardi ripasso)", 8));

        peppe.setDialog(peppeDialog);

        NPC cristina = new NPC(new Texture(Gdx.files.internal("characters/cristina.png")));
        cristina.getNpcBox().x = 1;
        cristina.getNpcBox().y = 2;
        npcController.add(cristina);
        cristina.setDialog(peppeDialog);

        NPC andrea = new NPC(new Texture(Gdx.files.internal("characters/andrea.png")));
        andrea.getNpcBox().x = 1;
        andrea.getNpcBox().y = 3;
        npcController.add(andrea);
        andrea.setDialog(peppeDialog);

        
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
