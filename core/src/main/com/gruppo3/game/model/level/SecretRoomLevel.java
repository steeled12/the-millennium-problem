package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.gruppo3.game.model.interactables.Item;
import com.gruppo3.game.model.interactables.GenericItem;
import com.gruppo3.game.model.interactables.PickableItem;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.util.Action;
import com.gruppo3.game.controller.SettingController;

public class SecretRoomLevel extends LevelStrategy {

    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/Menu.ogg"));

    public SecretRoomLevel() {
        super();

        // map
        map = new TmxMapLoader().load("map/atto3/stanza-segreta.tmx");
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
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        Player.getPlayer().getPlayerBox().x = 14.5f;
        Player.getPlayer().getPlayerBox().y = 0;
    }

    @Override
    public void init() {

        // item e npc
        ScriptableObject computer = new ScriptableObject(new Rectangle(15,15,1,1), true) {
            @Override
            public void action() {
                Dialog computerDialog = new Dialog();
                

                for(Item item : Player.getPlayer().getInventory()) {
                    if(item.getName().equalsIgnoreCase("appunti sulla ricerca")) {
                        LinearDialogNode computerNode0 = new LinearDialogNode("Il computer è protetto da password", 0);
                        ChoiceDialogNode computerNode1 = new ChoiceDialogNode("Inserisci la password", 1);
                        LinearDialogNode computerNode2 = new LinearDialogNode("Password corretta", 2, new Action() {
                            @Override
                            public void action() {
                                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/correct-choice.mp3"));
                                sound.play(SettingController.gameVolume);
                            }
                        });
                        LinearDialogNode computerNode3 = new LinearDialogNode("Password errata", 3, new Action() {
                            @Override
                            public void action() {
                                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/invalid-selection.mp3"));
                                sound.play(SettingController.gameVolume);
                            }
                        });
                        LinearDialogNode computerNode4 = new LinearDialogNode(
                                "Forse posso trovare qualche indizio in giro per la stanza", 4);
                        computerNode0.setPointer(1);
                            computerNode1.addChoice("Mappa1984", 2);
                            computerNode1.addChoice("Password123", 3);
                            computerNode1.addChoice("Tramezzino-Ciambella", 3);
                            computerNode3.setPointer(4);
                            LinearDialogNode computerNode5 = new LinearDialogNode(
                                    "[Tra i file del computer trovi un complesso e lungo\narticolo che spiega la soluzione al problema P=NP]",
                                    5);
                            LinearDialogNode computerNode6 = new LinearDialogNode(
                                    "[Dopo un'attenta lettura, riesci bene o male a comprendere qualcosa]",
                                    6);
                            LinearDialogNode computerNode7 = new LinearDialogNode(
                                    "[Per quanto riduttivo,\nla soluzione al problema P=NP permette di risolvere\nproblemi complessi alla stessa velocità di quelli semplici]",
                                    7);
                            LinearDialogNode computerNode8 = new LinearDialogNode(
                                    "[È uno dei problemi del millennio,\ne si pensava che nessuno lo avrebbe mai risolto]",
                                    8);
                            LinearDialogNode computerNode9 = new LinearDialogNode(
                                    "(Il professore è riuscito a risolverlo...)", 9);
                            LinearDialogNode computerNode10 = new LinearDialogNode(
                                    "(Ho un brutto presentimento,\ndevo tornare di sopra)", 10, new Action() {
                                @Override
                                public void action() {
                                    ScriptableObject porta = new ScriptableObject(new Rectangle(14, 0, 2, 1),
                                            true) {
                                        @Override
                                        public void action() {
                                            TransitionScreen fadeScreen = new TransitionScreen(
                                                    GameScreen.levelController.getCurrentLevel(),
                                                    new SotterraneiAtto3Level(),
                                                    (MyGame) Gdx.app.getApplicationListener(), 1.5f, 7);
                                            ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
                                            Player.getPlayer().setPlayerDirection(Player.PlayerDirection.SOUTH);
                                        }
                                    };
                                    GameScreen.levelController
                                            .getCurrentLevel().scriptableObjectsController.scriptableObjectsList
                                            .add(porta);
                                }
                            });
                            computerNode2.setPointer(5);
                            computerNode5.setPointer(6);
                            computerNode6.setPointer(7);
                            computerNode7.setPointer(8);
                            computerNode8.setPointer(9);
                            computerNode9.setPointer(10);
                            computerDialog.addNode(computerNode0);
                            computerDialog.addNode(computerNode1);
                            computerDialog.addNode(computerNode2);
                            computerDialog.addNode(computerNode3);
                            computerDialog.addNode(computerNode4);
                            computerDialog.addNode(computerNode5);
                            computerDialog.addNode(computerNode6);
                            computerDialog.addNode(computerNode7);
                            computerDialog.addNode(computerNode8);
                            computerDialog.addNode(computerNode9);
                            computerDialog.addNode(computerNode10);
                        break;
                    }
                    computerDialog.addNode(new LinearDialogNode("Prima di accedere voglio esplorare per bene la stanza", 0));
                }
                GameScreen.dialogController.startDialog(computerDialog);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(computer);

        // tutti i dialoghi

        GenericItem cassetto = new GenericItem("cassetto");
        itemController.addwithId(cassetto, 29);
        Dialog cassDialog = new Dialog();
        LinearDialogNode cassNode0 = new LinearDialogNode("Nel cassetto c'è un foglietto", 0);
        LinearDialogNode cassNode1 = new LinearDialogNode(
                "\"Non dimenticare: la password è custodita dalla natura\"", 1, new Action() {
                    @Override
                    public void action() {
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-realization.mp3"));
                        sound.play(SettingController.gameVolume);
                    }
                });
        cassNode0.setPointer(1);
        cassDialog.addNode(cassNode0);
        cassDialog.addNode(cassNode1);

        cassetto.setDialog(cassDialog);

        GenericItem pianta = new GenericItem("pianta");
        itemController.addwithId(pianta, 33);
        Dialog piantaDialog = new Dialog();
        LinearDialogNode piantaNode0 = new LinearDialogNode("Questa pianta ha qualcosa di strano", 0);
        ChoiceDialogNode piantaNode1 = new ChoiceDialogNode("Ispezioni la pianta?", 1);
        LinearDialogNode piantaNode2 = new LinearDialogNode("Una delle foglie ha una scritta", 2);
        LinearDialogNode piantaNode3 = new LinearDialogNode("\"Password: Mappa1984\"", 3, new Action() {
            @Override
            public void action() {
                GameScreen.savedInformation.put("passwordPianta", "true");
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-realization.mp3"));
                sound.play(SettingController.gameVolume);
            }
        });
        LinearDialogNode piantaNode4 = new LinearDialogNode("...chi scrive una password su una pianta di plastica?", 4);
        piantaNode0.setPointer(1);
        piantaNode1.addChoice("Si", 2);
        piantaNode1.addChoice("No");
        piantaNode2.setPointer(3);
        piantaNode3.setPointer(4);
        piantaDialog.addNode(piantaNode0);
        piantaDialog.addNode(piantaNode1);
        piantaDialog.addNode(piantaNode2);
        piantaDialog.addNode(piantaNode3);
        piantaDialog.addNode(piantaNode4);
        pianta.setDialog(piantaDialog);

        GenericItem libreria = new GenericItem("libreria");
        itemController.addwithId(libreria, 4);
        Dialog libDialog = new Dialog();
        LinearDialogNode libNode0 = new LinearDialogNode("Un libro sporge leggermente \n dalla libreria", 0);
        LinearDialogNode libNode1 = new LinearDialogNode("Il titolo è \"Appunti sulla ricerca\"", 1);
        ChoiceDialogNode libNode2 = new ChoiceDialogNode("Vuoi leggere?", 2);
        LinearDialogNode libNode3 = new LinearDialogNode(
                "\"Sono passati 15 anni dall'inizio della mia ricerca\n sul problema P = NP\"", 3);
        LinearDialogNode libNode4 = new LinearDialogNode(
                "\"Ero stato preso per pazzo per aver dedicato la mia vita a questo", 4);
        LinearDialogNode libNode5 = new LinearDialogNode("\"Ma ora ho trovato la soluzione!\"", 5);
        LinearDialogNode libNode6 = new LinearDialogNode("\"Eppure...\"", 6);
        LinearDialogNode libNode7 = new LinearDialogNode(
                "\"Le implicazioni di questo risultato sono incomprendibili.\nMesso nelle mani sbagliate, non so cosa potrebbe succedere\"",
                7);
        LinearDialogNode libNode8 = new LinearDialogNode("\"Non posso rischiare.\nSigillerò questa stanza\"", 8);
        ChoiceDialogNode libNode9 = new ChoiceDialogNode("Gli appunti si concludono, vuoi rileggere", 9);
        LinearDialogNode libNode10 = new LinearDialogNode("......", 10);
        LinearDialogNode libNode11 = new LinearDialogNode("!!!!!!", 11, new Action() {
            @Override
            public void action() {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-badum.mp3"));
                sound.play(SettingController.gameVolume);
                Music music = Gdx.audio.newMusic(Gdx.files.internal("music/suspenseMusic.mp3"));
                music.setVolume(SettingController.musicVolume);
                music.setLooping(true);
                setMusic(music);
            }
        });
        LinearDialogNode libNode12 = new LinearDialogNode(
                "Questa scrittura è del professore.\nPerché non mi aveva parlato della stanza e di tutto questo?", 12);
        LinearDialogNode libNode13 = new LinearDialogNode(
                "Questa scoperta di cui parla, sembra essere il fulcro di\n tutto ciò che sta succedendo", 13);
        LinearDialogNode libNode14 = new LinearDialogNode(
                "Devo continuare ad indagare", 14);
        LinearDialogNode libNode15 = new LinearDialogNode("Mi conviene tenere con me questi appunti", 15);
        LinearDialogNode libNode16 = new LinearDialogNode("[Hai raccolto Appunti sulla Ricerca]", 16, new Action() {
            @Override
            public void action() {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-selectjingle.mp3"));
                sound.play(SettingController.gameVolume);
                Player.getPlayer().getInventory().add(new PickableItem("Appunti sulla Ricerca",
                        "map/atto3/appunti.png"));
                GameScreen.updateInventoryUI();
            }
        });
        LinearDialogNode libNode17 = new LinearDialogNode(
                "Devo scoprire l'uso della ricerca\ne chi ha avuto accesso al computer di questa stanza", 17);

        libNode0.setPointer(1);
        libNode1.setPointer(2);
        libNode2.addChoice("Sì", 3);
        libNode2.addChoice("No");
        libNode3.setPointer(4);
        libNode4.setPointer(5);
        libNode5.setPointer(6);
        libNode6.setPointer(7);
        libNode7.setPointer(8);
        libNode8.setPointer(9);
        libNode9.addChoice("Sì", 3);
        libNode9.addChoice("No", 10);
        libNode10.setPointer(11);
        libNode11.setPointer(12);
        libNode12.setPointer(13);
        libNode13.setPointer(14);
        libNode14.setPointer(15);
        libNode15.setPointer(16);
        libNode16.setPointer(17);

        libDialog.addNode(libNode0);
        libDialog.addNode(libNode1);
        libDialog.addNode(libNode2);
        libDialog.addNode(libNode3);
        libDialog.addNode(libNode4);
        libDialog.addNode(libNode5);
        libDialog.addNode(libNode6);
        libDialog.addNode(libNode7);
        libDialog.addNode(libNode8);
        libDialog.addNode(libNode9);
        libDialog.addNode(libNode10);
        libDialog.addNode(libNode11);
        libDialog.addNode(libNode12);
        libDialog.addNode(libNode13);
        libDialog.addNode(libNode14);
        libDialog.addNode(libNode15);
        libDialog.addNode(libNode16);
        libDialog.addNode(libNode17);

        libreria.setDialog(libDialog);

        Player.getPlayer().setPlayerDirection(Player.PlayerDirection.NORTH);
    }

    public static void setMusic(Music music) {
        SecretRoomLevel.music = music;
        music.play();
    }

    @Override
    public Music getMusic() {
        return this.music;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
        music.dispose();
    }
}