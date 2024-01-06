package com.gruppo3.game.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.gruppo3.game.model.interactables.Item;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.gruppo3.game.screens.TestScreen;
import com.badlogic.gdx.Gdx;

public class ItemController extends InputAdapter {
    public List<Item> itemList = new ArrayList<>();
    TextureRegion animationFrame;


    public void addwithId(Item item, int mapId) {
        MapLayer collisionObjectLayer = TestScreen.map.getLayers().get("Collisioni");
        RectangleMapObject itemObject = (RectangleMapObject) collisionObjectLayer.getObjects().get(mapId);
        Gdx.app.log("ItemController", "Item added with id: " + mapId + " at position: " + itemObject.getRectangle().x + " " + itemObject.getRectangle().y);
        item.getBox().x = itemObject.getRectangle().x;
        item.getBox().y = itemObject.getRectangle().y;
        item.getBox().width = itemObject.getRectangle().width;
        item.getBox().height = itemObject.getRectangle().height;
        itemList.add(item);
        
    }

    public void addWithOutId(Item item) {
        itemList.add(item);
        MapLayer collisionObjectLayer = TestScreen.map.getLayers().get("Collisioni");
        RectangleMapObject itemObject = new RectangleMapObject(item.getBox().x, item.getBox().y, item.getBox().width, item.getBox().height);
        collisionObjectLayer.getObjects().add(itemObject);
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
