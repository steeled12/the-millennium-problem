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
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.Cat;
import com.gruppo3.game.model.interactables.PickableItem;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.util.Action;

public class TutorialLevel extends LevelStrategy {

    Sound duckSound, messageSound;

    public TutorialLevel() {
        super();

        // map
        this.map = new TmxMapLoader().load("maps/tutorial/Tutorial_map.tmx");

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
    }

    @Override
    public void init() {
        // Player
        Player.getPlayer().getPlayerBox().setPosition(6, 13);

        // NPC
        Cat cat = new Cat(
                new Texture("cat.png"));
        cat.getNpcBox().x = 14;
        cat.getNpcBox().y = 16;
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
                            "[Apri il frigorifero.\nForse ti aiuta a farti tornare in mente qualcosa...]", 0));
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
                        "(Ma poi perchè non ho aperto i regali?!)", 1));
                GameScreen.dialogController.startDialog(dialog);
            }
        };

        ScriptableObject pc = new ScriptableObject(new Rectangle(8, 11, 3, 2), true) {
            @Override
            public void action() {
                Dialog dialog = new Dialog();
                int sceltaPointer = 0;
                if (!GameScreen.savedInformation.get("turialDoor").equals("pc")) {
                    dialog.addNode(new LinearDialogNode("Vediamo un pò chi ha scritto...", 0).setPointer(1));
                    dialog.addNode(new LinearDialogNode("Il professore Rettangolo?!", 1).setPointer(2));
                    dialog.addNode(new LinearDialogNode(
                            "Che cos'è questo messaggio, non riesco a leggerlo.\nPossibile che sia CRITTOGRAFATO?!", 2)
                            .setPointer(3));
                    sceltaPointer = 3;
                }

                ChoiceDialogNode scelta = new ChoiceDialogNode("TEMPO DI...", sceltaPointer);
                scelta.addChoice("DECIFRARLO!", -1, new Action() {
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
                        GameScreen.levelController.setLevel(new SecretRoomLevel());
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

        scriptableObjectsController.scriptableObjectsList.add(paperella);
        scriptableObjectsController.scriptableObjectsList.add(porta);
        scriptableObjectsController.scriptableObjectsList.add(albero);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
