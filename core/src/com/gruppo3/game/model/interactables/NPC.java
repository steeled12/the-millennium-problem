package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gruppo3.game.dialog.Dialog;
import com.gruppo3.game.controller.DialogController;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.gruppo3.game.screens.TestScreen;


public class NPC{
    Texture npcTexture;
    Rectangle npcBox;
    Animation<TextureRegion>[] idleAnimation;
    Dialog dialog;

    public enum Direction {
        EAST,
        NORTH,
        WEAST,
        SOUTH;
    }

    Direction NPCDirection = Direction.SOUTH;

    public enum MOVEMENT_STATE {
		MOVING,
		STILL,
		REFACING,
		;
	}

    public NPC(Texture npcTexture) {
        this.npcTexture = npcTexture;
        this.npcBox = new Rectangle();
        npcBox.x = 1920 / 2 - 64 / 2;
        npcBox.y = 1080 / 2 - 64 / 2;
        int frameDimensionX = 16;
        int frameDimensionY = 32;
        npcBox.width = frameDimensionX;
        npcBox.height = 24;
        int numAnimations = 4;
        int numFrames = 6;
        idleAnimation = new Animation[numAnimations];

        for (int i = 0; i < numAnimations; i++) {
            TextureRegion[] framesIdle = new TextureRegion[numFrames];

            for (int j = 0; j < numFrames; j++) {
                    framesIdle[j] = new TextureRegion(npcTexture, (i * numFrames + j) * frameDimensionX,
                                    0, frameDimensionX, frameDimensionY);
            }
            idleAnimation[i] = new Animation<>(0.1f, framesIdle);
        }
    }

    public Animation<TextureRegion> getIdleAnimation(Direction direction) {
        return idleAnimation[direction.ordinal()];
    }
    public void setNPCDirection(Direction direction) {
        this.NPCDirection = direction;
    }


    // public void create () {
    // for (int i = 0; i < 5; i++) {
    // Sprite npcSprite = new Sprite(npcImage);
    // npcSprite.setPosition();
    // }
    // }

    public void dispose() {
        npcTexture.dispose();
    }

    public Texture getNpcImage() {
        return npcTexture;
    }

    public Rectangle getNpcBox() {
        return npcBox;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void action(DialogController dialogController) {
        dialogController.startDialog(this.dialog);


    }
}