package com.gruppo3.game.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.gruppo3.game.screens.TestScreen;

public class InteractablesController {

    public InteractablesController(TiledMap layer) {
        MapObjects objects = layer.getLayers().get("Collisione").getObjects();

        for (MapObject object : objects) {
            MapProperties properties = object.getProperties();

        }
    }

}