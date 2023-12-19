package com.gruppo3.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {

        public enum PlayerDirection {
                EAST,
                NORTH,
                WEAST,
                SOUTH;
        }

        Rectangle playerBox;
        PlayerDirection playerDirection = PlayerDirection.SOUTH;

        Animation<TextureRegion>[] idleAnimation;
        Animation<TextureRegion>[] walkAnimation;

        private static Player player;

        private Player() {

                int frameDimensionX = 16;
                int frameDimensionY = 32;

                this.playerBox = new Rectangle();

                playerBox.x = 1920 / 2 - 64 / 2;
                playerBox.y = 1080 / 2 - 64 / 2;
                // the bottom screen edge
                playerBox.width = frameDimensionX;
                playerBox.height = 24;

                // load animation
                int numAnimations = 4;
                int numFrames = 6;

                Texture playerIdle = new Texture("player_idle.png");
                Texture playerWalk = new Texture("player_walk.png");

                idleAnimation = new Animation[numAnimations];
                walkAnimation = new Animation[numAnimations];

                for (int i = 0; i < numAnimations; i++) {
                        TextureRegion[] framesIdle = new TextureRegion[numFrames];
                        TextureRegion[] framesWalk = new TextureRegion[numFrames];
                        for (int j = 0; j < numFrames; j++) {
                                framesIdle[j] = new TextureRegion(playerIdle, (i * numFrames + j) * frameDimensionX,
                                                0, frameDimensionX, frameDimensionY);
                                framesWalk[j] = new TextureRegion(playerWalk, (i * numFrames + j) * frameDimensionX,
                                                0, frameDimensionX, frameDimensionY);
                        }
                        idleAnimation[i] = new Animation<>(0.1f, framesIdle);
                        walkAnimation[i] = new Animation<>(0.1f, framesWalk);
                }
        }

        public Rectangle getPlayerBox() {
                return playerBox;
        }

        public static Player getPlayer() {
                if (player == null)
                        player = new Player();

                return player;
        }

        public PlayerDirection getPlayerDirection() {
                return playerDirection;
        }

        public void setPlayerDirection(PlayerDirection direction) {
                this.playerDirection = direction;
        }

        public Animation<TextureRegion> getIdleAnimation(PlayerDirection direction) {
                return idleAnimation[direction.ordinal()];
        }

        public Animation<TextureRegion> getWalkAnimation(PlayerDirection direction) {
                return walkAnimation[direction.ordinal()];
        }
}
