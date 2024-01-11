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

public class CorridoioAtto1Level extends LevelStrategy {

    public CorridoioAtto1Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto1/Piano_terra.tmx");
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

        // items
        ScriptableObject portaAula6Sx = new ScriptableObject(new Rectangle(4, 15, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new Aula6Atto1Level(), (MyGame) Gdx.app.getApplicationListener(),4,1);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        ScriptableObject portaAula6Dx = new ScriptableObject(new Rectangle(12, 15, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new Aula6Atto1Level(), (MyGame) Gdx.app.getApplicationListener(),0,0);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaAula6Sx);
        scriptableObjectsController.scriptableObjectsList.add(portaAula6Dx);

        ScriptableObject portaAula4Dx = new ScriptableObject(new Rectangle(64, 15, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new Aula4Atto1Level(), (MyGame) Gdx.app.getApplicationListener(),0,0);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        ScriptableObject portaAula4Sx = new ScriptableObject(new Rectangle(58, 15, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new Aula4Atto1Level(), (MyGame) Gdx.app.getApplicationListener(),4,1);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaAula4Dx);
        scriptableObjectsController.scriptableObjectsList.add(portaAula4Sx);

        ScriptableObject macchinetta = new ScriptableObject(new Rectangle(27, 1, 2, 1), true) {
            @Override
            public void action() {
                Dialog dialog = new Dialog();
                dialog.addNode(new LinearDialogNode("Vediamo un po'...", 0).setPointer(1));
                dialog.addNode(new LinearDialogNode("HANNO AUMENTATO DI NUOVO I PREZZI!", 1, new Action() {
                    @Override
                    public void action() {
                        Gdx.audio.newSound(Gdx.files.internal("sound/sfx-badum.mp3"))
                                .play(SettingController.gameVolume);
                    }
                }));
                GameScreen.dialogController.startDialog(dialog);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(macchinetta);

        ScriptableObject porta = new ScriptableObject(new Rectangle(30, 20, 3, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new PrimoPianoAtto1Level(), (MyGame) Gdx.app.getApplicationListener(),38,19);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(porta);

        NPC portiere = new NPC(new Texture(Gdx.files.internal("characters/portiere.png")));
        portiere.getNpcBox().x = 51;
        portiere.getNpcBox().y = 18;
        Dialog portiereDialog = new Dialog();
        portiereDialog.addNode(new LinearDialogNode("Portiere:\n...", 0).setPointer(1));
        portiereDialog.addNode(new LinearDialogNode("(Non sembra voglia o possa comunicare)", 0));
        portiere.setDialog(portiereDialog);
        npcController.npcList.add(portiere);

        NPC studente1 = new NPC(new Texture(Gdx.files.internal("characters/studente2.png")));
        studente1.getNpcBox().x = 40;
        studente1.getNpcBox().y = 5;
        studente1.setNPCDirection(NPC.Direction.WEST);
        npcController.add(studente1);

        Dialog studente1Dialog = new Dialog();
        LinearDialogNode studente1Node0 = new LinearDialogNode(
                "Studente:\nFinalmente! Sono riuscito a superare l'esame", 0);
        LinearDialogNode studente1Node1 = new LinearDialogNode(
                "Studente:\nÈ stato un incubo,\nma finalmente il professore GianRaffa mi ha promosso", 1);
        LinearDialogNode studente1Node2 = new LinearDialogNode(
                "Studente:\nForse ho fatto pietà all'altra professoressa in commissione...", 2);

        studente1Node0.setPointer(1);
        studente1Node1.setPointer(2);

        studente1Dialog.addNode(studente1Node0);
        studente1Dialog.addNode(studente1Node1);
        studente1Dialog.addNode(studente1Node2);
        studente1.setDialog(studente1Dialog);

        NPC studente2 = new NPC(new Texture(Gdx.files.internal("characters/studente1.png")));
        studente2.getNpcBox().x = 40;
        studente2.getNpcBox().y = 7;
        studente2.setNPCDirection(NPC.Direction.WEST);
        npcController.add(studente2);

        Dialog studente2Dialog = new Dialog();
        LinearDialogNode studente2Node0 = new LinearDialogNode("Studentessa:\nTi chiedi chi sono io?", 0);
        LinearDialogNode studente2Node1 = new LinearDialogNode(
                "Studentessa:\nSono la migliore studentessa di informatica di sempre!", 1);
        LinearDialogNode studente2Node2 = new LinearDialogNode(
                "Studentessa:\n...o almeno lo sarò quando finalmente riuscirò a superare Analisi...", 2);
        LinearDialogNode studente2Node3 = new LinearDialogNode(
                "Studentessa:\nA che ci sei, sapresti dirmi cosa è un polinomio di Taylor???", 3);

        studente2Node0.setPointer(1);
        studente2Node1.setPointer(2);
        studente2Node2.setPointer(3);

        studente2Dialog.addNode(studente2Node0);
        studente2Dialog.addNode(studente2Node1);
        studente2Dialog.addNode(studente2Node2);
        studente2Dialog.addNode(studente2Node3);

        studente2.setDialog(studente2Dialog);

        NPC rettore = new NPC(new Texture(Gdx.files.internal("characters/rettore.png")));
        rettore.getNpcBox().x = 27;
        rettore.getNpcBox().y = 17;
        rettore.setNPCDirection(NPC.Direction.SOUTH);
        npcController.add(rettore);

        Dialog rettoreDialog = new Dialog();
        LinearDialogNode rettoreNode0 = new LinearDialogNode("Uomo:\nBUONGIORNO STUDENTE!", 0);
        LinearDialogNode rettoreNode1 = new LinearDialogNode("Uomo:\nSono io! Il magnifico rettore!", 1);
        LinearDialogNode rettoreNode2 = new LinearDialogNode(
                "Rettore:\nSono qui per... supervisionare lo svolgimento\ndi alcuni lavori!", 2);
        LinearDialogNode rettoreNode3 = new LinearDialogNode(
                "Rettore:\nDevi sapere che io, in quanto rettore,\nho il dovere di assicurarmi che sia tutto a posto!",
                3);
        LinearDialogNode rettoreNode4 = new LinearDialogNode(
                "Rettore:\nQuesto mazzo di chiavi mi permette di accedere a tutta l'università, vedi che bello?!", 4,
                new Action() {
                    @Override
                    public void action() {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-realization.mp3"));
                        sound.play(SettingController.gameVolume);
                    }
                });
        LinearDialogNode rettoreNode5 = new LinearDialogNode(
                "Rettore:\nEcco, ora ti saluto, ho un sacco di cose da fare!", 5);

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

        Player.getPlayer().getPlayerBox().x = 33;
        Player.getPlayer().getPlayerBox().y = 1;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
