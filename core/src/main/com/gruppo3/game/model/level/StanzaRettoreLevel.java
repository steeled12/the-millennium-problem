package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.interactables.Item;

public class StanzaRettoreLevel extends LevelStrategy {

        private NPC professore;
        private NPC rettore;
        public StanzaRettoreLevel() {
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
                this.music = Gdx.audio.newMusic(Gdx.files.internal("music/stanza-rettore.mp3"));
                music.setLooping(true);

        }

        @Override
        public void init() {
                Player.getPlayer().getPlayerBox().x = 14.5f;
                Player.getPlayer().getPlayerBox().y = 2;
                music.setVolume(SettingController.musicVolume);
                // dialogo iniziale
                music.play();
/*                 GameScreen.savedInformation.put("parlatoARettore", "true");
                GameScreen.savedInformation.put("nota", "true"); */

                professore = new NPC(
                                new Texture("characters/professoreRettangolo.png"));
                professore.getNpcBox().x = 22;
                professore.getNpcBox().y = 11;
                professore.setNPCDirection(NPC.Direction.WEST);
                Dialog profDialog = new Dialog();
                profDialog.addNode(
                                new LinearDialogNode(
                                                "Professore Rettangolo:\nRagazzo! Mi dispiace che tu sia stato coinvolto...",
                                                0)
                                                .setPointer(1));
                profDialog.addNode(
                                new LinearDialogNode(
                                                "Professore Rettangolo:\n...ma non crederai mica che sia io il colpevole?!",
                                                1)
                                                .setPointer(2));
                ChoiceDialogNode node2 = new ChoiceDialogNode(
                                "(Voglio incolpare il professore?)", 2);
                node2.addChoice("Sì", 3);
                node2.addChoice("No");
                profDialog.addNode(node2);
                profDialog.addNode(new LinearDialogNode("Professore Rettangolo:\n!!!", 3, () -> {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-objection.mp3"));
                        sound.play(SettingController.gameVolume);
                        GameScreen.savedInformation.put("colpevoleScelto", "true");
                        TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                                                        new AttoFinaleLevel(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                        ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                }));
                professore.setDialog(profDialog);
                npcController.add(professore);

                rettore = new NPC(
                                new Texture("characters/rettore.png"));
                rettore.getNpcBox().x = 18;
                rettore.getNpcBox().y = 11;
                rettore.setNPCDirection(NPC.Direction.EAST);
                npcController.add(rettore);
                Dialog rettoreDialog = new Dialog();
                rettoreDialog.addNode(
                                new LinearDialogNode(
                                                "Rettore:\nForza, vai dal professore e incolpalo!\nNon abbiamo molto tempo!",
                                                0)
                                                .setPointer(1));
                ChoiceDialogNode rettoreNode1 = new ChoiceDialogNode("(Voglio incolpare il rettore?)", 1);
                rettoreNode1.addChoice("Sì", 3);
                rettoreNode1.addChoice("No");
                rettoreDialog.addNode(rettoreNode1);
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\n!!!", 3, () -> {
                        music.stop();
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-objection.mp3"));
                        sound.play(SettingController.gameVolume);
                }).setPointer(4));
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\nCOSA STAI DICENDO?!", 4).setPointer(5));
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\nSAI CHI SONO IO?!", 5).setPointer(6));
                rettoreDialog
                                .addNode(new LinearDialogNode("Uomo misterioso:\nPer favore, un po' di silenzio", 6)
                                                .setPointer(7));
                rettoreDialog.addNode(
                                new LinearDialogNode(
                                                "Uomo misterioso:\nRagazzo, spero tu possa dimostrare ciò che affermi.",
                                                7)
                                                .setPointer(8));
                ChoiceDialogNode rettoreNode2 = new ChoiceDialogNode(
                                "(Posso dimostrarlo?)", 8);
                rettoreNode2.addChoice("Sì!", 9);
                rettoreNode2.addChoice("Rinuncia", 35, () -> fadeMusic());
                rettoreDialog.addNode(rettoreNode2);
                rettoreDialog.addNode(new LinearDialogNode("Certo che posso!!", 9, () -> {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-objection.mp3"));
                        sound.play(SettingController.gameVolume);
                        setMusic(Gdx.audio.newMusic(Gdx.files.internal("music/accusa.mp3")));
                }).setPointer(10));
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\nTU...", 10).setPointer(11));
                rettoreDialog.addNode(
                                new LinearDialogNode("Rettore:\n...sentiamo...\nperché mai avrei dovuto farlo?!", 11)
                                                .setPointer(12));
                ChoiceDialogNode node3 = new ChoiceDialogNode(
                                "(Cosa può dimostrare che avesse\nun movente?)", 12);
                node3.addChoice("Mostra gli appunti sulla ricerca", 13);
                node3.addChoice("Rinuncia", 35, () -> fadeMusic());
                rettoreDialog.addNode(node3);
                rettoreDialog.addNode(new LinearDialogNode(
                                "Il rettore è venuto a conoscenza della ricerca\ne di come poteva essere usata per scopi personali!",
                                13).setPointer(14));
                rettoreDialog.addNode(new LinearDialogNode("[Mostri gli appunti]", 14).setPointer(15));
                rettoreDialog.addNode(new LinearDialogNode("Uomo misterioso:\n...interessante...", 15).setPointer(16));
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\nMa...ma...", 16).setPointer(17));
                rettoreDialog.addNode(new LinearDialogNode(
                                "Rettore:\nIo non avevo mica accesso alla stanza in cui\nil professore svolgeva la sua ricerca!",
                                17)
                                .setPointer(18));
                ChoiceDialogNode node4 = new ChoiceDialogNode(
                                "(Cosa può dimostrare che avesse\naccesso alla stanza?)", 18);
                if (GameScreen.savedInformation.containsKey("parlatoARettore")) {
                        node4.addChoice("Il mazzo di chiavi", 19);
                }
                node4.addChoice("Rinuncia", 35, () -> fadeMusic());
                rettoreDialog.addNode(node4);
                rettoreDialog.addNode(new LinearDialogNode(
                                "Il rettore ha un mazzo di chiavi\nche gli permette di accedere a tutte le stanze!", 19)
                                .setPointer(20));
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\n!!!", 20).setPointer(21));
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\nADESSO BASTA!\nSONO ACCUSE RIDICOLE!", 21, () -> {
                        music.stop();
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-badum.mp3"));
                        sound.play(SettingController.gameVolume);
                }).setPointer(22));
                rettoreDialog.addNode(
                                new LinearDialogNode(
                                                "Rettore:\nPuoi dire quello che vuoi!\nMa non hai alcuna prova decisiva!",
                                                22)
                                                .setPointer(23));
                rettoreDialog.addNode(new LinearDialogNode(
                                "Uomo misterioso:\nÈ vero ragazzo, per quanto interessante,\nserve una prova decisiva...",
                                23)
                                .setPointer(24));
                ChoiceDialogNode node5 = new ChoiceDialogNode(
                                "(Ho una prova decisiva?)", 24);
                for (Item item : Player.getPlayer().getInventory()) {
                        if (item.getName().equalsIgnoreCase("nota")) {
                                node5.addChoice("Sì!", 25);
                                break;
                        }
                }
                node5.addChoice("Rinuncia", 35);
                rettoreDialog.addNode(node5);
                rettoreDialog.addNode(new LinearDialogNode("CERTO!", 25,
                                () -> setMusic(Gdx.audio.newMusic(Gdx.files.internal("music/accusa_variation.mp3"))))
                                .setPointer(26));
                rettoreDialog.addNode(
                                new LinearDialogNode("Io e il professore abbiamo decifrato un messaggio...", 26)
                                                .setPointer(27));
                rettoreDialog.addNode(
                                new LinearDialogNode("[Mostri la nota trovata\nnella stanza del professore]", 27).setPointer(28));
                rettoreDialog.addNode(new LinearDialogNode("Uomo misterioso:\nMa questo...\n!!!", 28).setPointer(29));
                rettoreDialog.addNode(
                                new LinearDialogNode("Professore Rettangolo:\nL'hai trovato!", 29).setPointer(30));
                rettoreDialog.addNode(new LinearDialogNode(
                                "Il professore non avrebbe avuto motivo\ndi chiedermi di decifrarlo se fosse stato\n lui il colpevole!",
                                30).setPointer(31));
                rettoreDialog.addNode(new LinearDialogNode(
                                "Uomo misterioso:\n...anche noi avevamo intercettato e decifrato\nquesto messaggio...",
                                31)
                                .setPointer(32));
                rettoreDialog.addNode(
                                new LinearDialogNode("Uomo misterioso:\n...ma la tua testimonianza cambia tutto...", 32,
                                                        () -> Gdx.audio.newSound(Gdx.files.internal("sound/sfx-realization.mp3")).play(SettingController.gameVolume))
                                                .setPointer(33));
                rettoreDialog.addNode(
                                new LinearDialogNode("Uomo misterioso:\nSignor Rettore, deve venire con me.", 33)
                                                .setPointer(34));
                rettoreDialog.addNode(new LinearDialogNode("Rettore:\n!!!", 34, () -> {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-guilty.ogg"));
                        sound.play(SettingController.gameVolume);
                        music.setOnCompletionListener(new Music.OnCompletionListener() {
                                @Override
                                public void onCompletion(Music music) {
                                        TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                                        new AttoFinaleLevel(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                                        ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                                }
                        });
                        fadeMusic();
                        GameScreen.savedInformation.put("colpevoleScelto", "Rettore");

                }));
                rettoreDialog.addNode(new LinearDialogNode("Uomo misterioso:\n...", 35).setPointer(36));
                rettoreDialog.addNode(new LinearDialogNode(
                                "Uomo misterioso:\nNon so che intenzioni avessi...ma questo non è abbastanza\n per provare l'innocenza del professore",
                                36).setPointer(37));
                rettoreDialog
                                .addNode(new LinearDialogNode("Uomo misterioso:\nProfessore, deve venire con me.", 37)
                                                .setPointer(38));
                rettoreDialog.addNode(new LinearDialogNode("Professore Rettangolo:\n!!!", 38, () -> {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-guilty.ogg"));
                        sound.play(SettingController.gameVolume);
                        GameScreen.savedInformation.put("colpevoleScelto", "Professore Rettangolo");
                        TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(),
                                                        new AttoFinaleLevel(), (MyGame) Gdx.app.getApplicationListener(), 0, 0);
                        ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                }));
                rettore.setDialog(rettoreDialog);

                NPC uomoMisterioso = new NPC(
                                new Texture("characters/uomo_misterioso.png"));
                uomoMisterioso.getNpcBox().x = 20;
                uomoMisterioso.getNpcBox().y = 13;
                uomoMisterioso.setNPCDirection(NPC.Direction.SOUTH);
                npcController.add(uomoMisterioso);
                Dialog uomoDialog = new Dialog();
                uomoDialog.addNode(new LinearDialogNode("Uomo misterioso:\n...", 0));
                uomoMisterioso.setDialog(uomoDialog);

                startingDialog();
        }

        public void setMusic(Music music) {
                this.music.stop();
                this.music = music;
                this.music.setLooping(true);
                music.setVolume(SettingController.musicVolume);
                this.music.play();
        }

        private void startingDialog() {
                Dialog dialog = new Dialog();
                dialog.addNode(new LinearDialogNode("Rettore:\nAh, sei tu! Finalmente sei arrivato!", 0).setPointer(1));
                dialog.addNode(new LinearDialogNode(
                                "Rettore:\nVieni qui! Ho invitato questo gentiluomo\nper arrestare il professor Rettangolo",
                                1)
                                .setPointer(2));
                dialog.addNode(new LinearDialogNode(
                                "Rettore:\nIl suo tentativo di usare la sua ricerca\nper i suoi scopi sarà bloccata!",
                                2)
                                .setPointer(3));
                dialog.addNode(new LinearDialogNode("Rettore:\nForza, aiutami a incolpare il professore.", 3)
                                .setPointer(4));
                dialog.addNode(
                                new LinearDialogNode(
                                                "Professore Rettangolo:\nRagazzo! Sii saggio, il colpevole è il rettore!",
                                                4, () -> professore.setNPCDirection(NPC.Direction.SOUTH))
                                                .setPointer(5));
                dialog.addNode(new LinearDialogNode("(È il momento...)", 5, () -> {
                        rettore.setNPCDirection(NPC.Direction.EAST);
                        professore.setNPCDirection(NPC.Direction.WEST);
                }).setPointer(6));
                dialog.addNode(new LinearDialogNode("(Chi credo sia colpevole?)", 6));
                GameScreen.dialogController.startDialog(dialog);
                rettore.setNPCDirection(NPC.Direction.SOUTH);
        }

        private void fadeMusic() {
                Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                                if (music.getVolume() >= 0.01f)
                                        music.setVolume(music.getVolume() - 0.01f);
                                else {
                                        music.stop();
                                        this.cancel();
                                }
                        }
                }, 0f, 0.1f);
        }

        @Override
        public void dispose() {
                map.dispose();
                renderer.dispose();
                music.dispose();
        }
}