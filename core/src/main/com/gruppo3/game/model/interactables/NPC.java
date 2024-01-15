package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.controller.DialogController;

public class NPC {
    Texture npcTexture;
    Rectangle npcBox;
    Animation<TextureRegion>[] idleAnimation;
    Dialog dialog;
    boolean loopingAnimation;

    public enum Direction {
        EAST,
        NORTH,
        WEST,
        SOUTH;
    }

    Direction NPCDirection = Direction.EAST;

    public enum MOVEMENT_STATE {
        MOVING,
        STILL,
        REFACING,
        ;
    }

    public NPC(Texture npcTexture) {
        this.npcTexture = npcTexture;
        this.npcBox = new Rectangle();
        npcBox.x = 10f;
        npcBox.y = 8;
        int frameDimensionX = 16;
        int frameDimensionY = 32;
        npcBox.width = 1;
        npcBox.height = 2;
        int numAnimations = 4;
        int numFrames = 6;
        this.loopingAnimation = true;
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
        if(direction.ordinal() >= idleAnimation.length)
            return idleAnimation[0];
        return idleAnimation[direction.ordinal()];
    }

    public TextureRegion getFrame(float stateTime) {
        return this.getIdleAnimation(this.getNPCDirection()).getKeyFrame(stateTime, loopingAnimation);
    }

    public void setNPCDirection(Direction direction) {
        this.NPCDirection = direction;
    }

    public Direction getNPCDirection() {
        return NPCDirection;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NPC)) {
            return false;
        }
        NPC npc1 = (NPC) o;
        return npc1.getNpcBox().equals(this.getNpcBox());
    }
}