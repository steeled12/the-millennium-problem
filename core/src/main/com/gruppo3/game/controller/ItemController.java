package com.gruppo3.game.controller;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.maps.MapLayer;
import com.gruppo3.game.model.interactables.Item;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.interactables.PickableItem;
import com.badlogic.gdx.maps.MapObject;
import java.util.LinkedHashMap;
import java.util.Map;
import com.gruppo3.game.model.Player;

public class ItemController extends InputAdapter {
    public List<Item> itemList = new ArrayList<>();
    private Map<Item, MapObject> itemObjectMap = new LinkedHashMap<>();
    TextureRegion animationFrame;

    // USARE SOLO CON OGGETTI CHE NON ANDRANNO IN INVENTARIO
    public void addwithId(Item item, int mapId) {
        MapLayer collisionObjectLayer = GameScreen.levelController.getMap().getLayers().get("Collisioni");
        RectangleMapObject itemObject = (RectangleMapObject) collisionObjectLayer.getObjects().get(mapId);
        item.getBox().x = itemObject.getRectangle().x;
        item.getBox().y = itemObject.getRectangle().y;
        item.getBox().width = itemObject.getRectangle().width;
        item.getBox().height = itemObject.getRectangle().height;
        itemList.add(item);
        itemObjectMap.put(item, itemObject);

    }

    public void addWithOutId(Item item) {
        itemList.add(item);
        MapLayer collisionObjectLayer = GameScreen.levelController.getMap().getLayers().get("Collisioni");
        RectangleMapObject itemObject = new RectangleMapObject(item.getBox().x, item.getBox().y, item.getBox().width,
                item.getBox().height);
        collisionObjectLayer.getObjects().add(itemObject);
        itemObjectMap.put(item, itemObject);
    }

    public void addItemToInventory(PickableItem item) {
        MapLayer collisionObjectLayer = GameScreen.levelController.getMap().getLayers().get("Collisioni");
        RectangleMapObject itemObject = (RectangleMapObject) itemObjectMap.get(item);
        collisionObjectLayer.getObjects().remove(itemObject);
        itemObjectMap.remove(item);
        itemList.remove(item);
        Player.getPlayer().getInventory().add(item);

    }

    public void removeItemFromInventory(PickableItem item) {
        Player.getPlayer().getInventory().remove(item);
    }

    /*
     * @Override
     * public boolean keyDown(int keycode) {
     * if (keycode == Keys.X) {
     * for (Item item : itemList) {
     * if (item.getBox().overlaps(Player.getPlayer().getPlayerBox())) {
     * item.action(TestScreen.dialogController);
     * return true;
     * }
     * }
     * }
     * return false;
     * }
     */
}
