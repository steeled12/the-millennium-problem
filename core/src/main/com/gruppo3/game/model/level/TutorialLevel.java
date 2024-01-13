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
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.Cat;
import com.gruppo3.game.model.interactables.PickableItem;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.util.Action;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.MyGame;

public class TutorialLevel extends LevelStrategy {

    Sound duckSound, messageSound;

    public TutorialLevel() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/tutorial/Tutorial_map.tmx");

        // pc acceso o spento
        this.map.getLayers().get("Messaggio").setVisible(false);
        if (GameScreen.savedInformation.containsKey("turialDoor")) {
            this.map.getLayers().get("Messaggio").setVisible(true);
        }
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
        this.messageSound = Gdx.audio.newSound(Gdx.files.internal("sound/message_sound.mp3"));
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/tutorialMusic.mp3"));
        this.music.setLooping(true);
        // Player.getPlayer().getPlayerBox().setPosition(6, 13);
    }

    @Override
    public void init() {
        // Musica
        this.music.play();

        // Player

        // NPC
        Cat cat = new Cat(
                new Texture("cat.png"));
        cat.getNpcBox().x = 14;
        cat.getNpcBox().y = 16;
        cat.getNpcBox().height = 1;
        npcController.add(cat);
        // scriptable
        ScriptableObject frigorifero = new ScriptableObject(new Rectangle(10, 7, 1, 3), true) {
            @Override
            public void action() {
                Dialog dialog = new Dialog();
                if (!GameScreen.savedInformation.containsKey("isPickedLatte")) {
                    ChoiceDialogNode scelta = new ChoiceDialogNode("C'è del latte, potrei darlo al gatto", 0);
                    scelta.addChoice("Prendi il latte!", -1, new Action() {
                        @Override
                        public void action() {
                            PickableItem latte = new PickableItem("Latte", "milk.png");
                            itemController.addItemToInventory(latte);
                        }
                    });
                    scelta.addChoice("Non prenderlo");
                    dialog.addNode(scelta);
                } else {
                    dialog.addNode(new LinearDialogNode(
                            "[Apri il frigorifero.\nForse ti aiuta a far tornare in mente qualcosa...]", 0));
                }
                GameScreen.dialogController.startDialog(dialog);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(frigorifero);

        ScriptableObject paperella = new ScriptableObject(new Rectangle(21, 14, 1, 1), true) {
            @Override
            public void action() {
                duckSound.play(SettingController.gameVolume);
            }
        };

        ScriptableObject albero = new ScriptableObject(new Rectangle(20, 8, 2, 4), true) {
            @Override
            public void action() {
                Dialog dialog = new Dialog();
                dialog.addNode(new LinearDialogNode(
                        "(Forse dovrei smontarlo...)", 0).setPointer(1));
                dialog.addNode(new LinearDialogNode(
                        "(Ma poi perché non ho aperto i regali?!)", 1));
                GameScreen.dialogController.startDialog(dialog);
            }
        };

        ScriptableObject pc = new ScriptableObject(new Rectangle(8, 11, 3, 2), true) {
            @Override
            public void action() {
                Dialog dialog = new Dialog();
                int sceltaPointer = 0;
                if (!GameScreen.savedInformation.get("turialDoor").equals("pc")) {
                    dialog.addNode(new LinearDialogNode("Vediamo un po' chi ha scritto...", 0).setPointer(1));
                    dialog.addNode(new LinearDialogNode("Il professore Rettangolo?!", 1).setPointer(2));
                    dialog.addNode(new LinearDialogNode(
                            "Che cos'è questo messaggio? Non riesco a leggerlo.\nPossibile che sia CRITTOGRAFATO?!", 2)
                            .setPointer(3));
                    sceltaPointer = 3;
                }

                ChoiceDialogNode scelta = new ChoiceDialogNode("TEMPO DI...", sceltaPointer);
                scelta.addChoice("DECIFRARLO!", 4, new Action() {
                    @Override
                    public void action() {
                        GameScreen.savedInformation.put("turialDoor", "done");
                    }
                });
                scelta.addChoice("Andare a lezione, non ho tempo per questa cosa", -1, new Action() {
                    @Override
                    public void action() {
                        GameScreen.savedInformation.put("turialDoor", "pc");
                    }
                });
                dialog.addNode(scelta);
                dialog.addNode(new LinearDialogNode("[Bip bup, bup bip...]", 4).setPointer(5));
                dialog.addNode(new LinearDialogNode("[BOP ??]", 5).setPointer(6));
                dialog.addNode(
                        new LinearDialogNode("\"Non...istic Po...mial / Rettangolo / Scoperta / Soldi / Caos\"", 6)
                                .setPointer(7));
                dialog.addNode(new LinearDialogNode("Non ha molto senso ma è quello che riesco a decifrare...", 7));
                GameScreen.dialogController.startDialog(dialog);
            }
        };

        ScriptableObject porta = new ScriptableObject(new Rectangle(15, 18, 2, 2), true) {
            @Override
            public void action() {

                if (!GameScreen.savedInformation.containsKey("turialDoor")) {
                    GameScreen.savedInformation.put("turialDoor", "messaggio");
                }

                switch (GameScreen.savedInformation.get("turialDoor")) {
                    case "done":
                        TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                                new CorridoioAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 34, 2);
                        ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                        Dialog arrivoDialog = new Dialog();
                        arrivoDialog.addNode(new LinearDialogNode("Finalmente sono arrivato...", 0).setPointer(1));
                        arrivoDialog.addNode(
                                new LinearDialogNode("(Dovrei parlare con il professore per chiedergli del messaggio)",
                                        1)
                                        .setPointer(2));
                        arrivoDialog
                                .addNode(
                                        new LinearDialogNode("(Dovrebbe trovarsi al primo piano nella sua stanza)", 2));
                        GameScreen.dialogController.startDialog(arrivoDialog);
                        break;

                    case "pc":
                        Dialog dialog2 = new Dialog();
                        dialog2.addNode(new LinearDialogNode(
                                "(L'istinto ti dice che forse dovresti decifrare il messaggio...)", 0));
                        GameScreen.dialogController.startDialog(dialog2);
                        break;
                    case "leggere":
                        Dialog dialog3 = new Dialog();
                        dialog3.addNode(new LinearDialogNode("(Dovrei andarla a leggere)", 0));
                        GameScreen.dialogController.startDialog(dialog3);
                        break;

                    case "messaggio":
                        map.getLayers().get("Messaggio").setVisible(true);
                        scriptableObjectsController.scriptableObjectsList.add(pc);
                        messageSound.play(SettingController.gameVolume);
                        Dialog dialog = new Dialog();
                        dialog.addNode(new LinearDialogNode("Una email in questo momento?", 0));
                        GameScreen.dialogController.startDialog(dialog);
                        GameScreen.savedInformation.put("turialDoor", "leggere");
                        break;

                    default:
                        break;
                }
            }
        };

        if (GameScreen.savedInformation.containsKey("turialDoor")) {
            scriptableObjectsController.scriptableObjectsList.add(pc);
        }
        scriptableObjectsController.scriptableObjectsList.add(paperella);
        scriptableObjectsController.scriptableObjectsList.add(porta);
        scriptableObjectsController.scriptableObjectsList.add(albero);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        music.dispose();
        duckSound.dispose();
        messageSound.dispose();
    }

}
