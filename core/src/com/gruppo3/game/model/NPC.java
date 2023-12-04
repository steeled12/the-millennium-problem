package com.gruppo3.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class NPC {
    Texture npcImage;
    Rectangle npcBox;
    

    private NPC(Texture npcTexture) {
        this.npcImage = npcTexture;
        this.npcBox = new Rectangle();

        npcBox.x = 1920 / 2 - 64 / 2;
        npcBox.y = 1080 / 2 - 64 / 2;

        npcBox.width = 64;
        npcBox.height = 64;
    }

    // public void create () {
    //     for (int i = 0; i < 5; i++) {
    //         Sprite npcSprite = new Sprite(npcImage);
    //         npcSprite.setPosition();
    //     }
    // }

    public void dispose () {
        npcImage.dispose();
    }

    public Texture getNpcImage() {
        return npcImage;
    }

    public Rectangle getNpcBox() {
        return npcBox;
    }
}



