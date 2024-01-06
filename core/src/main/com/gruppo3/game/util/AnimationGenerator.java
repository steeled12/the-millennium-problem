package com.gruppo3.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationGenerator {
    public static Animation<TextureRegion> createAnimation(Texture texture, int pixelWidth, int pixelHeight,
            int numFrames,
            float animationSpeed) {

        TextureRegion[] frames = new TextureRegion[numFrames];

        for (int j = 0; j < numFrames; j++) {
            frames[j] = new TextureRegion(texture, j * pixelWidth,
                    0, pixelWidth, pixelHeight);
        }
        return new Animation<>(animationSpeed, frames);
    }

}
