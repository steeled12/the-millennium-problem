package com.gruppo3.game.controller;

import java.util.List;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.interactables.NPC;
import com.badlogic.gdx.InputAdapter;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TestScreen;
import com.gruppo3.game.model.interactables.Item;
import com.gruppo3.game.model.interactables.NPC.Direction;

public class InteractionController extends InputAdapter {

    private List<NPC> npcList;
    private List<Item> itemList;

    public InteractionController(List<NPC> npcList, List<Item> itemList) {
        this.npcList = npcList;
        this.itemList = itemList;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.X) {
            if (itemList != null) {
                for (Item item : itemList) {
                    Rectangle expandedItemBox = new Rectangle(item.getBox());
                    float expansionAmount = 0.1f;
                    expandedItemBox.x -= expansionAmount / 2;
                    expandedItemBox.y -= expansionAmount / 2;
                    expandedItemBox.width += expansionAmount;
                    expandedItemBox.height += expansionAmount;
    
                    if (expandedItemBox.overlaps(Player.getPlayer().getPlayerBox())) {
                        item.action(TestScreen.dialogController);
                        return true;
                    }
                }
            }
            if (npcList != null) {
                for (NPC npc : npcList) {
                    Rectangle expandedNpcBox = new Rectangle(npc.getNpcBox());
                    float expansionAmount = 0.1f;
                    expandedNpcBox.x -= expansionAmount / 2;
                    expandedNpcBox.y -= expansionAmount / 2;
                    expandedNpcBox.width += expansionAmount;
                    expandedNpcBox.height += expansionAmount;
    
                    if (expandedNpcBox.overlaps(Player.getPlayer().getPlayerBox())) {
                        npc.action(TestScreen.dialogController);
                        switch (Player.getPlayer().getPlayerDirection()) {
                            case NORTH:
                                npc.setNPCDirection(Direction.SOUTH);
                                break;
                            case SOUTH:
                                npc.setNPCDirection(Direction.NORTH);
                                break;
                            case WEST:
                                npc.setNPCDirection(Direction.EAST);
                                break;
                            case EAST:
                                npc.setNPCDirection(Direction.WEST);
                                break;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}