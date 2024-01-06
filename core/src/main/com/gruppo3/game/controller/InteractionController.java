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
import com.gruppo3.game.model.interactables.PickableItem;
import com.badlogic.gdx.Gdx;

public class InteractionController extends InputAdapter {

    private NPCController npcController;
    private ItemController itemController;

    public InteractionController(NPCController npcController, ItemController itemController) {
        this.npcController = npcController;
        this.itemController = itemController;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.X) {
            if (itemController.itemList != null) {
                for (Item item : itemController.itemList) {
                    Rectangle expandedItemBox = new Rectangle(item.getBox());
                    float expansionAmount = 0.1f;
                    expandedItemBox.x -= expansionAmount / 2;
                    expandedItemBox.y -= expansionAmount / 2;
                    expandedItemBox.width += expansionAmount;
                    expandedItemBox.height += expansionAmount;
    
                    if (expandedItemBox.overlaps(Player.getPlayer().getPlayerBox())) {
                        if(item instanceof PickableItem){
                            Gdx.app.log("InteractionController", "Aggiunto oggetto all'inventario");
                            itemController.addItemToInventory((PickableItem) item);
                        }
                        item.action(TestScreen.dialogController);
                        return true;
                    }
                }
            }
            if (npcController.npcList != null) {
                for (NPC npc : npcController.npcList) {
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