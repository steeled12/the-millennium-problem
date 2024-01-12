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
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;

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

        if (GameScreen.savedInformation.containsKey("messaggioDecifrato")){
        // scriptable
        ScriptableObject nota = new ScriptableObject(new Rectangle(9.5f, 8, 1, 1.5f), true) {
            @Override
            public void action() {
                Dialog dialog = new Dialog();

                ChoiceDialogNode node1 = new ChoiceDialogNode(
                        "[Trovi una nota\nVuoi leggerla?]", 0);
                node1.addChoice("Si", 1);
                node1.addChoice("No");
                dialog.addNode(node1);

                dialog.addNode(
                        new LinearDialogNode(
                                "\"Con questa scoperta posso diventare ricchissimo\ne mandare nel caos tutta l'economia!\"",
                                1));

                GameScreen.dialogController.startDialog(dialog);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(nota);
    }

        Dialog dialogTrofeo = new Dialog();
        dialogTrofeo.addNode(new LinearDialogNode(
                "(Quanti trofei...)", 0).setPointer(1));
        dialogTrofeo.addNode(new LinearDialogNode(
                "(Non pensavo che il professore fosse così rinominato)", 1));

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
