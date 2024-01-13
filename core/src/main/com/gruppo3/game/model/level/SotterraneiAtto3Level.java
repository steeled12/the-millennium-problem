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
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.util.Action;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.Player.PlayerDirection;

public class SotterraneiAtto3Level extends LevelStrategy {

    public SotterraneiAtto3Level() {
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
    }

    @Override
    public void init() {
        GameScreen.savedInformation.put("atto4", "true");
        // items
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

        NPC rettore = new NPC(new Texture(Gdx.files.internal("characters/rettore.png")));
        rettore.getNpcBox().x = 1;
        rettore.getNpcBox().y = 5;
        rettore.setNPCDirection(NPC.Direction.NORTH);
        npcController.add(rettore);

        Dialog rettoreDialog = new Dialog();
        LinearDialogNode rettoreNode0 = new LinearDialogNode("Rettore:\nTU!", 0);
        LinearDialogNode rettoreNode1 = new LinearDialogNode("Rettore:\nCosa...cosa ci fai qui?!", 1);
        LinearDialogNode rettoreNode2 = new LinearDialogNode(
                "Uhm... io...", 2);
        LinearDialogNode rettoreNode3 = new LinearDialogNode(
                "Rettore:\nCapisco, anche tu sospetti del professore Rettangolo...",
                3);
        LinearDialogNode rettoreNode4 = new LinearDialogNode(
                "Rettore:\nAnche io pensavo che ci fosse qualcosa sotto.", 4,
                new Action() {
                    @Override
                    public void action() {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-realization.mp3"));
                        sound.play(SettingController.gameVolume);
                    }
                });
        LinearDialogNode rettoreNode5 = new LinearDialogNode(
                "Rettore:\nAdesso però lascia tutto a me,\nfai come se non avessi visto niente.", 5);

        rettoreNode0.setPointer(1);
        rettoreNode1.setPointer(2);
        rettoreNode2.setPointer(3);
        rettoreNode3.setPointer(4);
        rettoreNode4.setPointer(5);

        rettoreDialog.addNode(rettoreNode0);
        rettoreDialog.addNode(rettoreNode1);
        rettoreDialog.addNode(rettoreNode2);
        rettoreDialog.addNode(rettoreNode3);
        rettoreDialog.addNode(rettoreNode4);
        rettoreDialog.addNode(rettoreNode5);

        rettore.setDialog(rettoreDialog);

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
