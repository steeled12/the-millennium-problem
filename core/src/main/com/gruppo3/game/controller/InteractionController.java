package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.interactables.NPC;
import com.badlogic.gdx.InputAdapter;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.interactables.Item;
import com.gruppo3.game.model.interactables.NPC.Direction;
import com.gruppo3.game.model.interactables.PickableItem;
import com.gruppo3.game.model.interactables.ScriptableObject;

public class InteractionController extends InputAdapter {

    private NPCController npcController;
    private ItemController itemController;
    private ScriptableObjectsController scriptableObjectsController;
    private Texture textureInteractionWidget;

    public InteractionController(NPCController npcController, ItemController itemController,
            ScriptableObjectsController scriptableObjectsController) {
        this.npcController = npcController;
        this.itemController = itemController;
        this.scriptableObjectsController = scriptableObjectsController;
        this.textureInteractionWidget = new Texture(Gdx.files.internal("ui/X.png"));
    }

    public void displayInteractionWidget(SpriteBatch batch) {
        Player player = Player.getPlayer();
        for (NPC npc : npcController.npcList) {
            double distance = Math.sqrt(Math.pow(player.getPlayerBox().x - npc.getNpcBox().x, 2)
                    + Math.pow(player.getPlayerBox().y - npc.getNpcBox().y, 2));
            if (distance < 1.8f) {
                batch.draw(textureInteractionWidget, npc.getNpcBox().x,
                        npc.getNpcBox().y + npc.getNpcBox().getHeight(), .8f, .8f);
            }
        }

        for (Item item : itemController.itemList) {
            double distance = Math.sqrt(Math.pow(player.getPlayerBox().x - item.getBox().x, 2)
                    + Math.pow(player.getPlayerBox().y - item.getBox().y, 2));
            if (distance < 1.8f) {
                batch.draw(textureInteractionWidget, item.getBox().x,
                        item.getBox().y + item.getBox().getHeight() + 1f, .8f, .8f);
            }
        }

        for (ScriptableObject scriptableObject : scriptableObjectsController.scriptableObjectsList) {
            if (scriptableObject.getShowInteractionWidget()) {
                double distance = Math.sqrt(Math.pow(player.getPlayerBox().x - scriptableObject.getBox().x, 2)
                        + Math.pow(player.getPlayerBox().y - scriptableObject.getBox().y, 2));
                if (distance < 1.8f) {
                    batch.draw(textureInteractionWidget,
                            (scriptableObject.getBox().x * 2 + scriptableObject.getBox().getWidth()) / 2 - .5f,
                            scriptableObject.getBox().y + scriptableObject.getBox().getHeight(), .8f, .8f);
                }
            }
        }
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
                        if (item instanceof PickableItem) {
                            Gdx.app.log("InteractionController", "Aggiunto oggetto all'inventario");
                            itemController.addItemToInventory((PickableItem) item);
                            GameScreen.savedInformation.put("isPicked" + item.getName(), "true");
                        }
                        item.action(GameScreen.dialogController);
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
                        npc.action(GameScreen.dialogController);
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

            if (!scriptableObjectsController.scriptableObjectsList.isEmpty()) {
                for (ScriptableObject scriptableObject : scriptableObjectsController.scriptableObjectsList) {
                    Rectangle expandedItemBox = new Rectangle(scriptableObject.getBox());
                    float expansionAmount = 0.1f;
                    expandedItemBox.x -= expansionAmount / 2;
                    expandedItemBox.y -= expansionAmount / 2;
                    expandedItemBox.width += expansionAmount;
                    expandedItemBox.height += expansionAmount;

                    if (expandedItemBox.overlaps(Player.getPlayer().getPlayerBox())) {
                        scriptableObject.action();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void updateContollers(NPCController npcController, ItemController itemController,
            ScriptableObjectsController scriptableObjectsController) {
        this.npcController = npcController;
        this.itemController = itemController;
        this.scriptableObjectsController = scriptableObjectsController;
    }

}