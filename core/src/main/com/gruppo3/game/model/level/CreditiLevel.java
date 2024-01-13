package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.SaveController;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.Cat;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;

public class CreditiLevel extends LevelStrategy {
    public CreditiLevel() {
        super();
        // map
        this.map = new TmxMapLoader().load("map/credits/credits.tmx");

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
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/credits.mp3"));
        music.setLooping(false);
    }

    @Override
    public void init() {
        // Player
        music.setVolume(0);
        fadeMusic();
        music.play();
        Player.getPlayer().getPlayerBox().x = 8f;
        Player.getPlayer().getPlayerBox().y = 5;

        NPC peppe = new NPC(
                new Texture("characters/peppe.png"));
        peppe.getNpcBox().setPosition(6.5f, 8);
        peppe.setNPCDirection(NPC.Direction.SOUTH);

        NPC andrea = new NPC(
                new Texture("characters/andrea.png"));
        andrea.getNpcBox().setPosition(8, 8);
        andrea.setNPCDirection(NPC.Direction.SOUTH);

        NPC cristina = new NPC(
                new Texture("characters/cristina.png"));
        cristina.getNpcBox().setPosition(9.5f, 8);
        cristina.setNPCDirection(NPC.Direction.SOUTH);

        NPC gatto = new Cat(
                new Texture("cat.png"));
        gatto.getNpcBox().setPosition(8f, 4);

        Dialog dialogoGatto = new Dialog();

        ChoiceDialogNode sceltaGatto = new ChoiceDialogNode(
                "Gatto:\nSei soddisfatto del finale ottenuto?\nO vuoi provare un'altra scelta?", 0);
        sceltaGatto.addChoice("Voglio ricominciare da capo!", 1);
        sceltaGatto.addChoice("Voglio cambiare scelta!", 3);
        sceltaGatto.addChoice("Sono soddisfatto di quello che ho scelto!", 5);

        dialogoGatto.addNode(sceltaGatto);

        dialogoGatto
                .addNode(new LinearDialogNode(
                        "Gatto:\nQuesto è lo spirito giusto!\nBuona fortuna!",
                        1).setPointer(2));
        dialogoGatto
                .addNode(new LinearDialogNode(
                        "",
                        2, () -> {
                            SaveController.newGame();
                            TransitionScreen fadeScreen = new TransitionScreen(
                                    GameScreen.levelController.getCurrentLevel(),
                                    new TutorialLevel(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                            ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                        }));

        dialogoGatto
                .addNode(new LinearDialogNode(
                        "Gatto:\nNon sempre nella vita si può cambiare il passato!\nMa ti offrirò questa possibilità...Provaci!",
                        3).setPointer(4));
        dialogoGatto
                .addNode(new LinearDialogNode(
                        "",
                        4, () -> {
                            SaveController.newGame();
                            TransitionScreen fadeScreen = new TransitionScreen(
                                    GameScreen.levelController.getCurrentLevel(),
                                    new PrimoPianoAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 34.5f,
                                    12.5f);
                            ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                        }));
        dialogoGatto
                .addNode(new LinearDialogNode(
                        "Gatto:\nMagnifico, bisogna sempre essere soddisfatti\ndi quello che si è scelto!",
                        5));

        Dialog dialogoCredits = new Dialog();
        dialogoCredits.addNode(
                new LinearDialogNode("Peppe:\nGrazie per aver giocato al nostro gioco!", 0).setPointer(1));
        dialogoCredits.addNode(
                new LinearDialogNode("Cristina\nSperiamo ti sia piaciuto!", 1).setPointer(2));
        dialogoCredits
                .addNode(new LinearDialogNode(
                        "Andrea:\nLo sai che hai ottenuto il finale {Inserire finale}?",
                        2).setPointer(3));
        dialogoCredits
                .addNode(new LinearDialogNode(
                        "Andrea:\nIn base agli indizi trovati e scelte fatte\npuoi ottenere altri finali!\nRigioca per ottenerne altri!",
                        3).setPointer(4));
        dialogoCredits
                .addNode(new LinearDialogNode(
                        "Peppe:\nIn totale sono 3!",
                        4, () -> {
                            npcController.add(gatto);
                        }));

        peppe.setDialog(dialogoCredits);
        cristina.setDialog(dialogoCredits);
        andrea.setDialog(dialogoCredits);
        gatto.setDialog(dialogoGatto);

        npcController.add(peppe);
        npcController.add(andrea);
        npcController.add(cristina);

        Dialog dialogoFinale = new Dialog();
        dialogoFinale.addNode(
                new LinearDialogNode("Peppe:\nQuindi è questa la tua storia?", 0).setPointer(1));
        dialogoFinale.addNode(
                new LinearDialogNode("Cristina\nWow!", 1).setPointer(2));
        dialogoFinale
                .addNode(new LinearDialogNode("Andrea:\nPossiamo usarla per il nostro progetto?", 2));

        GameScreen.dialogController.startDialog(dialogoFinale);
        Gdx.app.log("AttoFinaleLevel", "Audio");
    }

    private void fadeMusic() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (music.getVolume() <= SettingController.musicVolume)
                    music.setVolume(music.getVolume() + 0.01f);
                else {
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
