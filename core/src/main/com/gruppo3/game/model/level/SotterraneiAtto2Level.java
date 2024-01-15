package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.util.Action;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.Player.PlayerDirection;

public class SotterraneiAtto2Level extends LevelStrategy {

    public SotterraneiAtto2Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto3/CorridoioSotto.tmx");
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

        Player.getPlayer().getPlayerBox().x = 1;
        Player.getPlayer().getPlayerBox().y = 1;
    }

    @Override
    public void init() {
        // items
        ScriptableObject porta = new ScriptableObject(new Rectangle(1, 8, 2, 2), true) {
            @Override
            public void action() {
                Dialog dialog = new Dialog();

                for (Item item : Player.getPlayer().getInventory()) {
                    if (item.getName().equals("Chiave Segreta")) {
                        ChoiceDialogNode node0 = new ChoiceDialogNode("[Vuoi aprire la porta?]", 0);
                        node0.addChoice("Apri", -1, new Action() {
                            @Override
                            public void action() {
                                TransitionScreen fadeScreen = new TransitionScreen(
                                        GameScreen.levelController.getCurrentLevel(), new SecretRoomLevel(),
                                        (MyGame) Gdx.app.getApplicationListener(), 14,5f, 1);
                                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                            }
                        });
                        node0.addChoice("Non aprire");
                        dialog.addNode(node0);
                        GameScreen.dialogController.startDialog(dialog);
                        return;
                    }
                }
                LinearDialogNode node0 = new LinearDialogNode("La porta è chiusa a chiave", 0);
                LinearDialogNode node1 = new LinearDialogNode("Forse al piano terra posso trovare la chiave", 1);
                node0.setPointer(1);
                dialog.addNode(node0);
                dialog.addNode(node1);
                GameScreen.dialogController.startDialog(dialog);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(porta);

        ScriptableObject portaSx = new ScriptableObject(new Rectangle(1, 0, 2, 1), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                        new CorridoioAtto2Level(), (MyGame) Gdx.app.getApplicationListener(), 34, 17);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                Player.getPlayer().setPlayerDirection(PlayerDirection.SOUTH);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaSx);

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
