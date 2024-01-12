package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.DialogController;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.util.Action;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.gruppo3.game.controller.SettingController;

public class PickableItem extends GenericItem {

    public PickableItem(String name, String texturePath) {
        super(name, texturePath);
        dialog = new Dialog();

        LinearDialogNode node1 = new LinearDialogNode("Hai raccolto " + this.name, 0, new Action() {
            @Override
            public void action() {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-selectjingle.mp3"));
                sound.play(SettingController.gameVolume);
            }
        });

        dialog.addNode(node1);

        setDialog(dialog);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
    
    @Override
    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public Rectangle getBox() {
        return box;
    }

    @Override
    public String getName() {
        return name;
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
