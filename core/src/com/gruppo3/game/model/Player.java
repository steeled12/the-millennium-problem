package com.gruppo3.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player {
        Texture playerImage;
        Rectangle playerBox;

        final static Player player = new Player();

        private Player() {
                this.playerImage = new Texture("player.png");
                this.playerBox = new Rectangle();

                playerBox.x = 1920 / 2 - 64 / 2;
                playerBox.y = 1080 / 2 - 64 / 2;
                // the bottom screen edge
                playerBox.width = 64;
                playerBox.height = 64;
        }

        public Texture getPlayerImage() {
                return playerImage;
        }

        public Rectangle getPlayerBox() {
                return playerBox;
        }

        public static Player getPlayer() {
                return player;
        }


}
