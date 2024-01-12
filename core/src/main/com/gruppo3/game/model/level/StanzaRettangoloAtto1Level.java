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
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.util.Action;

public class StanzaRettangoloAtto1Level extends LevelStrategy {
    public StanzaRettangoloAtto1Level() {
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

        // NPC
        NPC professore = new NPC(
                new Texture("characters/professoreRettangolo.png"));
        professore.getNpcBox().x = 8;
        professore.getNpcBox().y = 12;

        Dialog dialog = new Dialog();

        dialog.addNode(new LinearDialogNode("Professore Rettangolo:\nSei arrivato! Ti aspettavo!", 0).setPointer(1));

        ChoiceDialogNode node1 = new ChoiceDialogNode(
                "Sei riuscito a decifrare il messaggio?", 1);
        node1.addChoice("Si", 8, new Action() {
            @Override
            public void action() {
                GameScreen.savedInformation.put("mentito", "true");
            }
        });
        node1.addChoice("No", 2);
        dialog.addNode(node1);

        dialog.addNode(
                new LinearDialogNode("Professore Rettangolo:\nAh pensavo che ce l'avresti fatta...", 2).setPointer(3));
        dialog.addNode(
                new LinearDialogNode("Professore Rettangolo:\n...forse mi sbagliavo ...", 3).setPointer(4));
        dialog.addNode(
                new LinearDialogNode("Professore Rettangolo:\n...ma allora perchè...", 4).setPointer(5));
        dialog.addNode(
                new LinearDialogNode("Professore Rettangolo:\n!!!", 5).setPointer(20));

        dialog.addNode(new LinearDialogNode(
                "[Mostri il messaggio decifrato al professore]", 8).setPointer(9));
        dialog.addNode(new LinearDialogNode(
                "Non sono riuscito a decifrarlo tutto", 9).setPointer(10));
        dialog.addNode(new LinearDialogNode(
                "Professore Rettangolo:\n...possibile che punti a quella cosa?...", 10).setPointer(11));
        dialog.addNode(new LinearDialogNode(
                "Professore Rettangolo:\n FORSE è stata TROVATA?!.", 11)
                .setPointer(20));

        dialog.addNode(new LinearDialogNode(
                "Professore Rettangolo:\n Va bene grazie dell'aiuto ma ora, DEVI ANDARE..", 20)
                .setPointer(21));
        dialog.addNode(new LinearDialogNode("(Che cosa è appena successo?)", 21, new Action() {
            @Override
            public void action() {
                GameScreen.savedInformation.put("atto", "atto2");
            }
        }));
        professore.setDialog(dialog);

        npcController.add(professore);

        // scriptable
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
