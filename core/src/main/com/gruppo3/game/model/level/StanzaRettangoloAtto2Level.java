package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.PickableItem;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.util.Action;

public class StanzaRettangoloAtto2Level extends LevelStrategy {
    public StanzaRettangoloAtto2Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto1/Stanza_rettangolo.tmx");

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
        // Player
        Player.getPlayer().getPlayerBox().setPosition(7.5f, 1);

        // items
        if (GameScreen.savedInformation.containsKey("mentito")
                && !GameScreen.savedInformation.containsKey("presoNota")) {
            PickableItem nota = new PickableItem("Nota", "nota.png");
            nota.getBox().setPosition(9, 8);
            nota.getBox().setSize(1, 1);

            Dialog dialog = new Dialog();
            dialog.addNode(new LinearDialogNode("[Trovi una nota per terra]", 0).setPointer(1));
            ChoiceDialogNode node1 = new ChoiceDialogNode(
                    "[Vuoi prenderla?]", 1);
            node1.addChoice("Sì", 2);
            node1.addChoice("No");
            dialog.addNode(node1);

            dialog.addNode(new LinearDialogNode(
                    "Nota:\n\"Con questa scoperta posso diventare ricchissimo\ne mandare nel caos tutta l'economia!\"",
                    2)
                    .setPointer(3));
            dialog.addNode(new LinearDialogNode(
                    "[Hai raccolto la nota]",
                    3, new Action() {
                        @Override
                        public void action() {
                            itemController.addItemToInventory(nota);
                            GameScreen.savedInformation.put("presoNota", "true");
                        }
                    }));

            nota.setDialog(dialog);
            itemController.itemList.add(nota);
        }

        // scriptable
        Dialog dialogTrofeo = new Dialog();
        dialogTrofeo.addNode(new LinearDialogNode(
                "(Quanti trofei...)", 0).setPointer(1));
        dialogTrofeo.addNode(new LinearDialogNode(
                "(Non pensavo che il professore fosse così rinomato)", 1));

        ScriptableObject trofei1 = new ScriptableObject(new Rectangle(4, 1, 1, 2), true) {
            @Override
            public void action() {
                GameScreen.dialogController.startDialog(dialogTrofeo);
            }
        };
        ScriptableObject trofei2 = new ScriptableObject(new Rectangle(11, 1, 1, 2), true) {
            @Override
            public void action() {
                GameScreen.dialogController.startDialog(dialogTrofeo);
            }
        };
        ScriptableObject portaPrimoPiano = new ScriptableObject(new Rectangle(7, 1, 2, 1), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                        new PrimoPianoAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 51, 14);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };

        scriptableObjectsController.scriptableObjectsList.add(trofei1);
        scriptableObjectsController.scriptableObjectsList.add(trofei2);
        scriptableObjectsController.scriptableObjectsList.add(portaPrimoPiano);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
