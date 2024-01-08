package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.DialogController;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.util.Action;
import com.badlogic.gdx.audio.Music;
import com.gruppo3.game.model.level.SecretRoomLevel;
import com.badlogic.gdx.audio.Sound;

//Item generico per dimostrazione
public class Computer implements Item {
    Texture texture;
    Rectangle box;
    Dialog dialog;

    public Computer() {
        this.box = new Rectangle(0, 0, 32, 32);
        this.dialog = new Dialog();
        LinearDialogNode node0 = new LinearDialogNode("Questo computer richiede una password", 0);
        ChoiceDialogNode node1 = new ChoiceDialogNode("Inserisci la password", 1);
        LinearDialogNode node2 = new LinearDialogNode("Password corretta", 2);
        LinearDialogNode node3 = new LinearDialogNode("Password errata", 3, new Action() {
            @Override
            public void action() {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/heheheha.mp3"));
                sound.play();
            }
        });

        node0.setPointer(1);
        node1.addChoice("Mappa1984", 2, new Action() {
            @Override
            public void action() {
                Music music = Gdx.audio.newMusic(Gdx.files.internal("music/cocoremix.mp3"));
                SecretRoomLevel.setMusic(music);
            }
        });
        node1.addChoice("Password", 3);
        node1.addChoice("1234", 3);

        dialog.addNode(node0);
        dialog.addNode(node1);
        dialog.addNode(node2);
        dialog.addNode(node3);

        this.setDialog(dialog);

    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public String getTexturePath() {
        return "";
    }

    @Override
    public Rectangle getBox() {
        return box;
    }

    @Override
    public String getName() {
        return "Computer";
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return dialog;
    }

    @Override
    public void action(DialogController dialogController) {
        dialogController.startDialog(dialog);
    }
}