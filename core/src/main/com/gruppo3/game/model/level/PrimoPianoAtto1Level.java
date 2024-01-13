package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.util.Action;

public class PrimoPianoAtto1Level extends LevelStrategy {
    public PrimoPianoAtto1Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto1/Primo_piano.tmx");

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
        // scriptable
        ScriptableObject portaStanzaRettangolo = new ScriptableObject(new Rectangle(51, 14, 2, 2), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.containsKey("atto")) {
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new StanzaRettangoloAtto2Level(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                } else {
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new StanzaRettangoloAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                }

            }
        };

        ScriptableObject portaRettore = new ScriptableObject(new Rectangle(34, 11, 2, 2), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.containsKey("atto4")) {
                    Dialog dialog = new Dialog();
                    LinearDialogNode node1 = new LinearDialogNode(
                            "(È la stanza del rettore.\nDa qui non si torna indietro)", 0);
                    ChoiceDialogNode node2 = new ChoiceDialogNode("[Vuoi entrare?]", 1);
                    node2.addChoice("Sì", -1, new Action() {
                        @Override
                        public void action() {
                            TransitionScreen fadeScreen = new TransitionScreen(
                                    GameScreen.levelController.getCurrentLevel(),
                                    new StanzaRettoreLevel(), (MyGame) Gdx.app.getApplicationListener(), 14.5f, 2);
                            ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);

                        }
                    });
                    node2.addChoice("No");
                    node1.setPointer(1);
                    dialog.addNode(node1);
                    dialog.addNode(node2);
                    GameScreen.dialogController.startDialog(dialog);
                } else {
                    Dialog dialog = new Dialog();
                    LinearDialogNode node1 = new LinearDialogNode("(È la stanza del rettore.\nMeglio non disturbare)",
                            0);
                    dialog.addNode(node1);
                    GameScreen.dialogController.startDialog(dialog);
                }
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaRettore);

        ScriptableObject scale = new ScriptableObject(new Rectangle(37, 20, 3, 2), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.containsKey("parlatoProfessore")) {
                    GameScreen.savedInformation.put("atto", "atto2");
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new CorridoioAtto2Level(), (MyGame) Gdx.app.getApplicationListener(), 32, 17);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                } else {
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new CorridoioAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 31, 19);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                }
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaStanzaRettangolo);
        scriptableObjectsController.scriptableObjectsList.add(scale);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
