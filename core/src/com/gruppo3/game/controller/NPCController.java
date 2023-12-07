package com.gruppo3.game.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.NPC;

public class NPCController {
    List<NPC> npcList = new ArrayList<>();

    public void add(NPC npc) {
        npcList.add(npc);
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        for (NPC npc : npcList) {
            new MyGame().batch.draw(npc.getNpcImage(), npc.getNpcBox().x, npc.getNpcBox().y);
        }

        batch.end();

        touchInputCheck();
    }

    public void touchInputCheck() {
        if (Gdx.input.justTouched()) {
            for (NPC npc : npcList) {
                Rectangle npcBounds = npc.getNpcBox();
                if (npcBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    System.out.println("Hai cliccato su un NPC!");
                }
            }
        }
    }
}
