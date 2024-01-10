package com.gruppo3.game.model.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.util.Action;

public class StanzaRettangoloLevel extends LevelStrategy {
    public StanzaRettangoloLevel() {
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

        ChoiceDialogNode node1 = new ChoiceDialogNode(
                "Professore Rettangolo:\n Sei arrivato! Hai ricevuto il messaggio?", 0);

        node1.addChoice("Si");
        node1.addChoice("No", 2);

        dialog.addNode(node1);

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

        scriptableObjectsController.scriptableObjectsList.add(trofei1);
        scriptableObjectsController.scriptableObjectsList.add(trofei2);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
