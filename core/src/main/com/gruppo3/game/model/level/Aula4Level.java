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
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.util.Action;

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


    }

    @Override
    public void init() {
                // items
        /* ScriptableObject portaAula6 = new ScriptableObject(new Rectangle(4, 15, 2, 2), true) {
            @Override
            public void action() {
                gameScreen.levelToLoad = "Aula6";
                gameScreen.getLevelController().setLevel(new Aula6Level());
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaAula6);

        ScriptableObject portaAula4 = new ScriptableObject(new Rectangle(64, 11, 2, 2), true) {
            @Override
            public void action() {
                gameScreen.levelToLoad = "Aula4";
                gameScreen.getLevelController().setLevel(new Aula4Level());
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaAula4); */

        NPC portiere = new NPC(new Texture(Gdx.files.internal("characters/portiere.png")));
        portiere.getNpcBox().x = 51;
        portiere.getNpcBox().y = 19;
        npcController.npcList.add(portiere);
        
        Dialog portiereDialog = new Dialog();
        LinearDialogNode portiereNode0 = new LinearDialogNode("Buonasera, potrebbe aprire la porta?", 0);
        LinearDialogNode portiereNode1 = new LinearDialogNode("...", 1);
        LinearDialogNode portiereNode2 = new LinearDialogNode("...", 2);
        LinearDialogNode portiereNode3 = new LinearDialogNode("Che lezione hai?", 3);
        LinearDialogNode portiereNode4 = new LinearDialogNode("Nessuna, ma devo incontrare un professore", 4);
        LinearDialogNode portiereNode5 = new LinearDialogNode("...", 5);
        LinearDialogNode portiereNode6 = new LinearDialogNode("...", 6);
        LinearDialogNode portiereNode7 = new LinearDialogNode("Sembra si sia bloccato.\nMeglio cercare un altro modo per entrare", 7);
        portiereNode0.setPointer(1);
        portiereNode1.setPointer(2);
        portiereNode2.setPointer(3);
        portiereNode3.setPointer(4);
        portiereNode4.setPointer(5);
        portiereNode5.setPointer(6);
        portiereNode6.setPointer(7);
        portiereDialog.addNode(portiereNode0);
        portiereDialog.addNode(portiereNode1);
        portiereDialog.addNode(portiereNode2);
        portiereDialog.addNode(portiereNode3);
        portiereDialog.addNode(portiereNode4);
        portiereDialog.addNode(portiereNode5);
        portiereDialog.addNode(portiereNode6);
        portiereDialog.addNode(portiereNode7);
        portiere.setDialog(portiereDialog);

        NPC studente1 = new NPC(new Texture(Gdx.files.internal("characters/studente1.png")));
        studente1.getNpcBox().x = 40;
        studente1.getNpcBox().y = 5; 
        npcController.add(studente1);

        Dialog studente1Dialog = new Dialog();
        LinearDialogNode studente1Node0 = new LinearDialogNode("Finalmente! Sono riuscito a superare l'esame", 0);
        LinearDialogNode studente1Node1 = new LinearDialogNode("È stato un incubo,\nma finalmente il professore GianRaffa mi ha promosso", 1);
        LinearDialogNode studente1Node2 = new LinearDialogNode("Forse ho fatto pietà all'altra professoressa in commissione...", 2);

        studente1Node0.setPointer(1);
        studente1Node1.setPointer(2);

        studente1Dialog.addNode(studente1Node0);
        studente1Dialog.addNode(studente1Node1);
        studente1Dialog.addNode(studente1Node2);
        studente1.setDialog(studente1Dialog);

        NPC studente2 = new NPC(new Texture(Gdx.files.internal("characters/studente2.png")));
        studente2.getNpcBox().x = 40;
        studente2.getNpcBox().y = 7;
        npcController.add(studente2);

        Dialog studente2Dialog = new Dialog();
        LinearDialogNode studente2Node0 = new LinearDialogNode("Ti chiedi chi sono io?", 0);
        LinearDialogNode studente2Node1 = new LinearDialogNode("Sono il migliore studente di informatica di sempre!", 1);
        LinearDialogNode studente2Node2 = new LinearDialogNode("...o almeno lo sarò quando finalmente riuscirò a superare Analisi...", 2);
        LinearDialogNode studente2Node3 = new LinearDialogNode("A che ci sei, sapresti dirmi cosa è un polinomio di Taylor???", 3);

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
        npcController.add(rettore);

        Dialog rettoreDialog = new Dialog();
        LinearDialogNode rettoreNode0 = new LinearDialogNode("BUONGIORNO STUDENTE!", 0);
        LinearDialogNode rettoreNode1 = new LinearDialogNode("Sono io! Il magnifico rettore!", 1);
        LinearDialogNode rettoreNode2 = new LinearDialogNode("Sono qui per... supervisionare lo svolgimento\ndi alcuni lavori!", 2);
        LinearDialogNode rettoreNode3 = new LinearDialogNode("Devi sapere che io, in quanto rettore,\nho il dovere di assicurarmi che sia tutto a posto!", 3);
        LinearDialogNode rettoreNode4 = new LinearDialogNode("Questo mazzo di chiavi mi permette di accedere a tutta l'università, vedi che bello?!", 4, new Action() {
            @Override
            public void action() {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-realization.wav"));
                sound.play(SettingController.gameVolume);
            }
        });
        LinearDialogNode rettoreNode5 = new LinearDialogNode("Ecco, ora ti saluto, ho un sacco di cose da fare!", 5);

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

    public void changeMap(String mapName, float x, float y) {
        this.map = new TmxMapLoader().load(mapName);
        
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
        this.renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        Player.getPlayer().getPlayerBox().x = x;
        Player.getPlayer().getPlayerBox().y = y;
        
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
