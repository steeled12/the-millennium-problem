package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.screens.GameScreen;

public class AttoFinaleLevel extends LevelStrategy {
    public AttoFinaleLevel() {
        super();
        // map
        this.map = new TmxMapLoader().load("map/atto4/stanza_rettore.tmx");

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
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/endingMusic.mp3"));
        music.setLooping(false);
        music.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                GameScreen.levelController.setLevel(new CreditiLevel());
            }
        });
    }

    @Override
    public void init() {
        // Player
        music.setVolume(SettingController.musicVolume);
        music.play();

        Player.getPlayer().getPlayerBox().x = 14.5f;
        Player.getPlayer().getPlayerBox().y = 6;

        NPC uomoMisterioso = new NPC(
                new Texture("characters/uomo_misterioso.png"));
        uomoMisterioso.getNpcBox().x = 20;
        uomoMisterioso.getNpcBox().y = 13;
        uomoMisterioso.setNPCDirection(NPC.Direction.SOUTH);

        NPC professore = new NPC(
                new Texture("characters/professoreRettangolo.png"));
        professore.getNpcBox().x = 22;
        professore.getNpcBox().y = 11;
        professore.setNPCDirection(NPC.Direction.SOUTH);

        NPC rettore = new NPC(
                new Texture("characters/rettore.png"));
        rettore.getNpcBox().x = 18;
        rettore.getNpcBox().y = 11;
        rettore.setNPCDirection(NPC.Direction.SOUTH);

        NPC poliziotto1 = new NPC(
                new Texture("characters/poliziotto1.png"));
        poliziotto1.getNpcBox().setPosition(13.4f, 4);
        poliziotto1.setNPCDirection(NPC.Direction.SOUTH);

        NPC poliziotto2 = new NPC(
                new Texture("characters/poliziotto2.png"));
        poliziotto2.getNpcBox().setPosition(15.6f, 4);
        poliziotto2.setNPCDirection(NPC.Direction.SOUTH);

        npcController.add(uomoMisterioso);
        npcController.add(rettore);
        npcController.add(professore);
        npcController.add(poliziotto1);
        npcController.add(poliziotto2);

        Gdx.app.log("AttoFinaleLevel", "Audio");
        startingDialog(professore, rettore);
    }

    private void startingDialog(NPC professore, NPC rettore) {
        NPC colpevole;
        String colpevoleNome = GameScreen.savedInformation.getOrDefault("colpevoleScelto", "Professore Rettangolo");
        if (colpevoleNome.equals("Rettore")) {
            colpevole = rettore;
        } else {
            colpevoleNome = "Professore Rettangolo";
            colpevole = professore;
        }

        colpevole.getNpcBox().setPosition(14.5f, 4);
        Dialog dialogoFinale = new Dialog();
        dialogoFinale.addNode(
                new LinearDialogNode("Il " + colpevoleNome + " venne arrestato in quel momento.", 0).setPointer(1));
        dialogoFinale.addNode(
                new LinearDialogNode("I servizi segreti italiani non rivelarono mai il perchè", 1).setPointer(2));
        dialogoFinale.addNode(new LinearDialogNode("Ma forse è meglio così", 2).setPointer(3));
        dialogoFinale.addNode(new LinearDialogNode(
                "Solo l'esistenza di quella scoperta potrebbe di nuovo\nmettere in pericolo il mondo intero...", 3)
                .setPointer(4));

        if (colpevoleNome.equals("Rettore")) {
            dialogoFinale.addNode(new LinearDialogNode(
                    "...Dopo qualche mese, nell'università di Palermo,\nvenne eletto un nuovo Rettore", 4, () -> {
                        fadeMusic();
                    }).setPointer(10));
        } else {
            dialogoFinale.addNode(new LinearDialogNode(
                    "...Dopo qualche mese, tutte le banche del mondo,\nsubirono un attacco hacker", 4).setPointer(5));
            dialogoFinale.addNode(new LinearDialogNode(
                    "Smettendo completamente di funzionare e facendo crollare\nl'intero pianeta nel caos", 5, () -> {
                        fadeMusic();
                    })
                    .setPointer(10));
        }

        GameScreen.dialogController.startDialog(dialogoFinale);
    }

    private void fadeMusic() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (music.getVolume() >= 0.1f)
                    music.setVolume(music.getVolume() - 0.01f);
                else {
                    music.setPosition(9999);
                    this.cancel();
                }
            }
        }, 1f, 0.1f);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
