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

    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/Menu.mp3"));

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
                Gdx.app.log("SecretRoomLevel", "Computer");
                Dialog computerDialog = new Dialog();
                if(Player.getPlayer().getInventory().isEmpty()){
                    computerDialog.addNode(new LinearDialogNode("Prima di accedere voglio esplorare per bene la stanza", 0));
                    GameScreen.dialogController.startDialog(computerDialog);
                    return;
                }
                for(Item item : Player.getPlayer().getInventory()) {
                    Gdx.app.log("SecretRoomLevel", ""+"entered for");
                    if(item.getName().equalsIgnoreCase("appunti sulla ricerca")) {
                        Gdx.app.log("SecretRoomLevel", ""+"entered if");
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
                        if(GameScreen.savedInformation.containsKey("passwordPianta")) {
                            computerNode1.addChoice("Mappa1984", 2);
                        }
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
                    Gdx.app.log("SecretRoomLevel", ""+"nodosenza");
                    computerDialog.addNode(new LinearDialogNode("Prima di accedere voglio esplorare per bene la stanza", 0));
                }
                GameScreen.dialogController.startDialog(computerDialog);
            }
        };


        ScriptableObject libreria = new ScriptableObject(new Rectangle(8,16,2,3), true) {
            @Override
            public void action(){
                Dialog libDialog = new Dialog();

                if(Player.getPlayer().getInventory().isEmpty()){
                    Gdx.app.log("SecretRoomLevel", "entered if");
                    libDialog.addNode(new LinearDialogNode("Prima di accedere voglio esplorare per bene la stanza", 0));
                    GameScreen.dialogController.startDialog(libDialog);
                    return;
                }

                for(Item item : Player.getPlayer().getInventory()) {
                    if(item.getName().equalsIgnoreCase("appunti sulla ricerca")) {
                        ChoiceDialogNode libNode0 = new ChoiceDialogNode("[Vuoi rileggere gli appunti?]", 0);
                        libNode0.addChoice("Sì", 1);
                        libNode0.addChoice("No");
                        libDialog.addNode(libNode0);
                        libDialog.addNode(new LinearDialogNode(
                            "\"Sono passati 15 anni dall'inizio della mia ricerca\n sul problema P = NP\"", 1).setPointer(2));
                        libDialog.addNode(new LinearDialogNode(
                                "\"Ero stato preso per pazzo per aver dedicato la mia vita a questo", 2).setPointer(3));
                        libDialog.addNode(new LinearDialogNode("\"Ma ora ho trovato la soluzione!\"", 3).setPointer(4));
                        libDialog.addNode(new LinearDialogNode("\"Eppure...\"", 4).setPointer(5));
                        libDialog.addNode(new LinearDialogNode(
                                "\"Le implicazioni di questo risultato sono incomprensibili.\nMesso nelle mani sbagliate, non so cosa potrebbe succedere\"",
                                5).setPointer(6));
                        libDialog.addNode(new LinearDialogNode("\"Non posso rischiare.\nSigillerò questa stanza\"", 6));
                        break;
                    }
                    libDialog.addNode(new LinearDialogNode("Un libro sporge leggermente \n dalla libreria", 0).setPointer(1));
                    libDialog.addNode(new LinearDialogNode("Il titolo è \"Appunti sulla ricerca\"", 1).setPointer(2));
                    ChoiceDialogNode libNode2 = new ChoiceDialogNode("Vuoi leggere?", 2);
                    libNode2.addChoice("Sì", 3);
                    libNode2.addChoice("No");
                    libDialog.addNode(libNode2);
                    libDialog.addNode(new LinearDialogNode(
                        "\"Sono passati 15 anni dall'inizio della mia ricerca\n sul problema P = NP\"", 3).setPointer(4));
                    libDialog.addNode(new LinearDialogNode(
                            "\"Ero stato preso per pazzo per aver dedicato la mia vita a questo", 4).setPointer(5));
                    libDialog.addNode(new LinearDialogNode("\"Ma ora ho trovato la soluzione!\"", 5).setPointer(6));
                    libDialog.addNode(new LinearDialogNode("\"Eppure...\"", 6).setPointer(7));
                    libDialog.addNode(new LinearDialogNode(
                            "\"Le implicazioni di questo risultato sono incomprensibili.\nMesso nelle mani sbagliate, non so cosa potrebbe succedere\"",
                            7).setPointer(8));
                    libDialog.addNode(new LinearDialogNode("\"Non posso rischiare.\nSigillerò questa stanza\"", 8).setPointer(9));
                    ChoiceDialogNode libNode9 = new ChoiceDialogNode("Gli appunti si concludono, vuoi rileggere", 9);
                    libNode9.addChoice("Sì", 3);
                    libNode9.addChoice("No", 10);
                    libDialog.addNode(libNode9);
                    libDialog.addNode(new LinearDialogNode("......", 10).setPointer(11));
                    libDialog.addNode(new LinearDialogNode("!!!!!!", 11, new Action() {
                        @Override
                        public void action() {
                            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-badum.mp3"));
                            sound.play(SettingController.gameVolume);
                            Music music = Gdx.audio.newMusic(Gdx.files.internal("music/suspenseMusic.mp3"));
                            music.setVolume(SettingController.musicVolume);
                            music.setLooping(true);
                            setMusic(music);
                        }
                    }).setPointer(12));
                    libDialog.addNode(new LinearDialogNode(
                                "Questa scrittura è del professore.\nPerché non mi aveva parlato della stanza e di tutto questo?", 12).setPointer(13));
                    libDialog.addNode(new LinearDialogNode(
                                "Questa scoperta di cui parla, sembra essere il fulcro di\n tutto ciò che sta succedendo", 13).setPointer(14));
                    libDialog.addNode(new LinearDialogNode(
                                "Devo continuare ad indagare", 14).setPointer(15));
                    libDialog.addNode(new LinearDialogNode("Mi conviene tenere con me questi appunti", 15).setPointer(16));
                    libDialog.addNode(new LinearDialogNode("[Hai raccolto Appunti sulla Ricerca]", 16, new Action() {
                            @Override
                            public void action() {
                                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-selectjingle.mp3"));
                                sound.play(SettingController.gameVolume);
                                Player.getPlayer().getInventory().add(new PickableItem("Appunti sulla Ricerca",
                                        "map/atto3/appunti.png"));
                                GameScreen.updateInventoryUI();
                            }
                    }).setPointer(17));
                    libDialog.addNode(new LinearDialogNode(
                            "Devo scoprire l'uso della ricerca\ne chi ha avuto accesso al computer di questa stanza", 17));
                }
                GameScreen.dialogController.startDialog(libDialog);

            }
        };
        scriptableObjectsController.scriptableObjectsList.add(libreria);
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