package com.gruppo3.game.controller;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.maps.MapLayer;
import com.gruppo3.game.model.interactables.NPC;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.gruppo3.game.screens.TestScreen;

public class NPCController extends InputAdapter {
    public List<NPC> npcList = new ArrayList<>();
    TextureRegion animationFrame;

    public void add(NPC npc) {
        npcList.add(npc);
        MapLayer collisionObjectLayer = TestScreen.map.getLayers().get("Collisioni");
        RectangleMapObject npcObject = new RectangleMapObject(npc.getNpcBox().x, npc.getNpcBox().y,
                npc.getNpcBox().width, npc.getNpcBox().height / 3);
        collisionObjectLayer.getObjects().add(npcObject);
    }

    public TextureRegion getTextureToRender() {
        return animationFrame;
    }

    /*
     * @Override
     * public boolean keyDown(int keycode) {
     * if (keycode == Keys.X) {
     * for (NPC npc : npcList) {
     * if (npc.getNpcBox().overlaps(Player.getPlayer().getPlayerBox())) {
     * npc.action(TestScreen.dialogController);
     * return true;
     * }
     * }
     * }
     * return false;
     * }
     */
}
