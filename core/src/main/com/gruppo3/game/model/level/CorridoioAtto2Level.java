package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.util.Action;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.MyGame;

public class CorridoioAtto2Level extends LevelStrategy {

    public CorridoioAtto2Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto2/Piano_terra.tmx");
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

        Player.getPlayer().getPlayerBox().x = 33;
        Player.getPlayer().getPlayerBox().y = 1;
    }

    @Override
    public void init() {
        // items
        ScriptableObject portaAula6Sx = new ScriptableObject(new Rectangle(4, 15, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                        new Aula6Level(), (MyGame) Gdx.app.getApplicationListener(), 4, 1);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        ScriptableObject portaAula6Dx = new ScriptableObject(new Rectangle(12, 15, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                        new Aula6Level(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaAula6Sx);
        scriptableObjectsController.scriptableObjectsList.add(portaAula6Dx);

        ScriptableObject portaAula4Dx = new ScriptableObject(new Rectangle(64, 15, 2, 2), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.containsKey("atto4")) {
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new Aula4Level(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                } else {
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new Aula4Atto4Level(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                }
            }
        };
        ScriptableObject portaAula4Sx = new ScriptableObject(new Rectangle(58, 15, 2, 2), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.containsKey("atto4")) {
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new Aula4Level(), (MyGame) Gdx.app.getApplicationListener(), 4, 1);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                } else {
                    TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                            new Aula4Atto4Level(), (MyGame) Gdx.app.getApplicationListener(), 4, 1);
                    ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                }
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaAula4Dx);
        scriptableObjectsController.scriptableObjectsList.add(portaAula4Sx);

        ScriptableObject macchinetta = new ScriptableObject(new Rectangle(27, 1, 2, 1), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.containsKey("bibitaComprata")) {
                    Dialog dialog = new Dialog();
                    LinearDialogNode node = new LinearDialogNode("Ho già comprato una bibita", 0);
                    dialog.addNode(node);
                    GameScreen.dialogController.startDialog(dialog);
                } else {
                    GameScreen.savedInformation.put("bibitaComprata", "true");
                    Dialog dialog = new Dialog();
                    ChoiceDialogNode node;
                    if (GameScreen.savedInformation.containsKey("parlatoAPortiere")) {
                        node = new ChoiceDialogNode("(Potrei comprare una bibita per corrompere\nil portiere...)", 0);
                    } else {
                        node = new ChoiceDialogNode("[Vuoi comprare una bibita?]", 0);
                    }
                    node.addChoice("Sì", -1, new Action() {
                        @Override
                        public void action() {
                            PickableItem bibita = new PickableItem("bibita", "bibita.png");
                            itemController.addItemToInventory(bibita);
                        }
                    });
                    node.addChoice("No");
                    dialog.addNode(node);
                    GameScreen.dialogController.startDialog(dialog);

                }
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(macchinetta);

        Dialog portaChiusa = new Dialog();
        LinearDialogNode portaChiusaNode0 = new LinearDialogNode("[La porta è chiusa]", 0);
        LinearDialogNode portaChiusaNode1 = new LinearDialogNode("Posso chiedere a qualcuno di aprirla", 1);
        portaChiusaNode0.setPointer(1);
        portaChiusa.addNode(portaChiusaNode0);
        portaChiusa.addNode(portaChiusaNode1);

        Dialog portaAperta = new Dialog();
        LinearDialogNode portaApertaNode0 = new LinearDialogNode("[La porta è aperta]", 0);
        ChoiceDialogNode portaApertaNode1 = new ChoiceDialogNode("[Dove vuoi andare?]", 1);
        ChoiceDialogNode portaApertaNode2 = new ChoiceDialogNode("(Sono davvero pronto\nper scendere?)", 2);
        portaApertaNode0.setPointer(1);
        portaApertaNode1.addChoice("Primo Piano", -1, new Action() {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                        new PrimoPianoAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 38, 20);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        });
        portaApertaNode1.addChoice("Sotterranei", 2);
        portaApertaNode2.addChoice("Sì", -1, new Action() {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                        new SotterraneiAtto2Level(), (MyGame) Gdx.app.getApplicationListener(), 1.5f, 1);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        });
        portaApertaNode2.addChoice("No");

        portaAperta.addNode(portaApertaNode0);
        portaAperta.addNode(portaApertaNode1);
        portaAperta.addNode(portaApertaNode2);

        ScriptableObject porta = new ScriptableObject(new Rectangle(34, 18, 2, 2), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.containsKey("portaScale")) {
                    GameScreen.dialogController.startDialog(portaAperta);
                } else {
                    GameScreen.dialogController.startDialog(portaChiusa);
                }
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(porta);

        NPC portiere = new Portiere(new Texture(Gdx.files.internal("characters/portiere.png")));
        portiere.getNpcBox().x = 51;
        portiere.getNpcBox().y = 18;
        npcController.npcList.add(portiere);

        NPC studente1 = new NPC(new Texture(Gdx.files.internal("characters/studente1.png")));
        studente1.getNpcBox().x = 25;
        studente1.getNpcBox().y = 7;
        studente1.setNPCDirection(NPC.Direction.EAST);
        npcController.add(studente1);

        Dialog studente1Dialog = new Dialog();
        LinearDialogNode studente1Node0 = new LinearDialogNode(
                "Studente:\nEhi! Hai visto oggi il rettore?\nUltimamente passa molto tempo qui", 0);
        LinearDialogNode studente1Node1 = new LinearDialogNode("Studente:\nÈ pure molto simpatico", 1);
        LinearDialogNode studente1Node2 = new LinearDialogNode("Studente:\nChissà, forse ci darà un nuovo laboratorio!",
                2);

        studente1Node0.setPointer(1);
        studente1Node1.setPointer(2);

        studente1Dialog.addNode(studente1Node0);
        studente1Dialog.addNode(studente1Node1);
        studente1Dialog.addNode(studente1Node2);
        studente1.setDialog(studente1Dialog);

        NPC studente2 = new NPC(new Texture(Gdx.files.internal("characters/studente2.png")));
        studente2.getNpcBox().x = 40;
        studente2.getNpcBox().y = 7;
        studente2.setNPCDirection(NPC.Direction.WEST);
        npcController.add(studente2);

        Dialog studente2Dialog = new Dialog();
        LinearDialogNode studente2Node0 = new LinearDialogNode(
                "Studentessa:\nSono tutti entusiasti che il rettore sia qui...", 0);
        LinearDialogNode studente2Node1 = new LinearDialogNode(
                "Studentessa:\nIo non mi fido... non tutto è\nsempre come sembra", 1);

        studente2Node0.setPointer(1);

        studente2Dialog.addNode(studente2Node0);
        studente2Dialog.addNode(studente2Node1);

        studente2.setDialog(studente2Dialog);

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
