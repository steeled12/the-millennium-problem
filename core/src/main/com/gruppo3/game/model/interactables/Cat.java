package com.gruppo3.game.model.interactables;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gruppo3.game.controller.DialogController;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.util.Action;

public class Cat extends NPC {
    Animation<TextureRegion> animation;
    Sound meowSound1, meowSound2;

    public Cat(Texture texture) {
        super(texture);
        int frameDimensionX = 16;
        int frameDimensionY = 16;
        int numAnimations = 1;
        int numFrames = 4;

        this.meowSound1 = Gdx.audio.newSound(Gdx.files.internal("sound/meow1.mp3"));
        this.meowSound2 = Gdx.audio.newSound(Gdx.files.internal("sound/meow2.mp3"));

        for (int i = 0; i < numAnimations; i++) {
            TextureRegion[] framesIdle = new TextureRegion[numFrames];

            for (int j = 0; j < numFrames; j++) {
                framesIdle[j] = new TextureRegion(npcTexture, (i * numFrames + j) * frameDimensionX * 2,
                        0, frameDimensionX, frameDimensionY);
            }
            animation = new Animation<>(0.1f, framesIdle);
        }

        dialog = new Dialog();

        ChoiceDialogNode node1 = new ChoiceDialogNode("Meow!", 0);
        LinearDialogNode node2 = new LinearDialogNode("Prrrup!", 1);
        LinearDialogNode node3 = new LinearDialogNode("Dovrei dare del cibo al gatto!", 2);

        node1.addChoice("Accarezza", 1, new Action() {
            @Override
            public void action() {
                meowSound1.play(SettingController.gameVolume);
            }
        });
        node1.addChoice("Non accarezzare", 2);

        dialog.addNode(node1);
        dialog.addNode(node2);
        dialog.addNode(node3);

        super.dialog = dialog;
    }

    @Override
    public Animation<TextureRegion> getIdleAnimation(Direction direction) {
        return animation;
    }

    @Override
    public void action(DialogController dialogController) {
        for (Item item : Player.getPlayer().getInventory()) {
            // se ha il latte
            if (item.getName() == "Latte") {
                Gdx.audio.newSound(Gdx.files.internal("sound/success.mp3")).play(SettingController.gameVolume);
                Dialog latteDialog = new Dialog();
                latteDialog.addNode(new LinearDialogNode("Hai dato il latte al gatto", 0));
                System.out.println("Inventario:" + Player.getPlayer().getInventory().toString());
                Player.getPlayer().getInventory().remove(item);
                GameScreen.updateInventoryUI();
                System.out.println("Inventario:" + Player.getPlayer().getInventory().toString());
                dialogController.startDialog(latteDialog);
                return;
            }
        }
        if (new Random().nextInt(100) < 95) {
            meowSound1.play(SettingController.gameVolume);
        } else {
            meowSound2.play(SettingController.gameVolume);
        }

        dialogController.startDialog(this.dialog);
    }
}
