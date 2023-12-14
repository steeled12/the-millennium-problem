package com.gruppo3.game.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.interactables.NPC;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TestScreen;

public class NPCController extends InputAdapter {
    public List<NPC> npcList = new ArrayList<>();
    TextureRegion animationFrame;

    public void add(NPC npc) {
        npcList.add(npc);
    }

    public TextureRegion getTextureToRender() {
        return animationFrame;
    }

/*     @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.X) {
            for (NPC npc : npcList) {
                if (npc.getNpcBox().overlaps(Player.getPlayer().getPlayerBox())) {
                    npc.action(TestScreen.dialogController);
                    return true;
                }
            }
        }
        return false;
    } */
}
