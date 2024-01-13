package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.gruppo3.game.controller.DialogController;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.util.Action;
import com.gruppo3.game.util.AnimationGenerator;

public class Portiere extends NPC {

    private final LinearDialogNode portiereNode7; // Nodo da modificare in metodo action

    public Portiere(Texture texture) {
        super(texture);
        int numFrames = 9;
        this.idleAnimation = new Animation[1];
        this.idleAnimation[0] = AnimationGenerator.createAnimation(texture, 16, 32, numFrames, 0.1f);
        this.idleAnimation[0].setPlayMode(Animation.PlayMode.NORMAL);
        this.loopingAnimation = false;
        this.NPCDirection = Direction.SOUTH;
        dialog = new Dialog();
        LinearDialogNode portiereNode0 = new LinearDialogNode("Buonasera, potrebbe aprire la porta?", 0);
        LinearDialogNode portiereNode1 = new LinearDialogNode("Portiere:\n...", 1);
        LinearDialogNode portiereNode2 = new LinearDialogNode("Portiere:\n...", 2);
        LinearDialogNode portiereNode3 = new LinearDialogNode("Portiere:\nChe lezione hai?", 3);
        LinearDialogNode portiereNode4 = new LinearDialogNode("Nessuna, ma devo incontrare un professore", 4);
        LinearDialogNode portiereNode5 = new LinearDialogNode("Portiere:\n...", 5);
        LinearDialogNode portiereNode6 = new LinearDialogNode("Portiere:\n...", 6);
        this.portiereNode7 = new LinearDialogNode(
                "Sembra si sia bloccato.\nForse posso cercare qualcosa per\nattirare la sua attenzione", 7);
        portiereNode0.setPointer(1);
        portiereNode1.setPointer(2);
        portiereNode2.setPointer(3);
        portiereNode3.setPointer(4);
        portiereNode4.setPointer(5);
        portiereNode5.setPointer(6);
        portiereNode6.setPointer(7);
        dialog.addNode(portiereNode0);
        dialog.addNode(portiereNode1);
        dialog.addNode(portiereNode2);
        dialog.addNode(portiereNode3);
        dialog.addNode(portiereNode4);
        dialog.addNode(portiereNode5);
        dialog.addNode(portiereNode6);
        dialog.addNode(portiereNode7);

        this.dialog = dialog;
    }

    @Override
    public void action(DialogController dialogController) {
        int index = 8;

        if (GameScreen.savedInformation.containsKey("chiaveSegreta")) {
            Dialog dfinale = new Dialog();
            this.dialog = dfinale;
            LinearDialogNode finaleNode0 = new LinearDialogNode("Portiere:\n...", 0);
            dialog.addNode(finaleNode0);
            dialogController.startDialog(this.dialog);
            return;
        }
        if(!GameScreen.savedInformation.containsKey("bibitaComprata")) {
            dialogController.startDialog(this.dialog);
            return;
        }
        
        if(GameScreen.savedInformation.containsKey("bibitaComprata") && GameScreen.savedInformation.containsKey("parlatoAPortiere")) {
            Dialog portiereDialog = new Dialog();
            this.dialog = portiereDialog;
            index = 0;
        } else {
            GameScreen.savedInformation.put("parlatoAPortiere", "true");
            portiereNode7.setPointer(8);
            index = 8;
        }

        LinearDialogNode portiereNode8 = new LinearDialogNode("Le può interessare una bibita\nin cambio della porta?",
                index);
        LinearDialogNode portiereNode9 = new LinearDialogNode("Portiere:\n...", index + 1);
        LinearDialogNode portiereNode10 = new LinearDialogNode("Portiere:\n...", index + 2);
        LinearDialogNode portiereNode11 = new LinearDialogNode("Portiere:\nVa bene, dai qua", index + 3);
        LinearDialogNode portiereNode12 = new LinearDialogNode("[Hai dato la bibita al portiere]", index + 4,
                new Action() {
                    @Override
                    public void action() {
                        for (Item item : Player.getPlayer().getInventory()) {
                            if (item.getName().equals("bibita")) {
                                Player.getPlayer().getInventory().remove(item);
                                break;
                            }
                        }
                        GameScreen.updateInventoryUI();
                    }
                });
        LinearDialogNode portiereNode13 = new LinearDialogNode(
                "Portiere:\nNon penserai mica che ti avrei\naperto la porta in cambio di una bibita?", index + 5);
        LinearDialogNode portiereNode14 = new LinearDialogNode(
                "Portiere:\nPerò posso darti questa chiave che\nho trovato tempo fa...\nanche se sembra inutile",
                index + 6);
        LinearDialogNode portiereNode15 = new LinearDialogNode("[Hai ottenuto la chiave della stanza segreta]",
                index + 7, new Action() {
                    @Override
                    public void action() {
                        GameScreen.levelController.getItemController()
                                .addItemToInventory(new PickableItem("Chiave Segreta", "key.png"));
                        GameScreen.updateInventoryUI();
                        GameScreen.savedInformation.put("chiaveSegreta", "true");
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-selectjingle.mp3"));
                        sound.play(SettingController.gameVolume);
                    }
                });
        portiereNode8.setPointer(index + 1);
        portiereNode9.setPointer(index + 2);
        portiereNode10.setPointer(index + 3);
        portiereNode11.setPointer(index + 4);
        portiereNode12.setPointer(index + 5);
        portiereNode13.setPointer(index + 6);
        portiereNode14.setPointer(index + 7);
        dialog.addNode(portiereNode8);
        dialog.addNode(portiereNode9);
        dialog.addNode(portiereNode10);
        dialog.addNode(portiereNode11);
        dialog.addNode(portiereNode12);
        dialog.addNode(portiereNode13);
        dialog.addNode(portiereNode14);
        dialog.addNode(portiereNode15);

        dialogController.startDialog(this.dialog);
    }
}
