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
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.gruppo3.game.model.interactables.Item;

public class InteractionController extends InputAdapter{

    private List<NPC> npcList;
    private List<Item> itemList;


    public InteractionController(List<NPC> npcList, List<Item> itemList) {
        this.npcList = npcList;
        this.itemList = itemList;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.X) {
            if(itemList != null){
                for (Item item : itemList) {
                    if (item.getBox().overlaps(Player.getPlayer().getPlayerBox())) {
                        item.action(TestScreen.dialogController);
                        return true;
                    }
                }
            }
            if(npcList != null){
                for (NPC npc : npcList) {
                    if (npc.getNpcBox().overlaps(Player.getPlayer().getPlayerBox())) {
                        npc.action(TestScreen.dialogController);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}