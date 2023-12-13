package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gruppo3.game.screens.TestScreen;

public class Save implements Preferences{
    public static void save() {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

            Preferences prefs = Gdx.app.getPreferences("My Preferences");

            if(!prefs.contains("key")) {
                prefs.putInteger("key", "1234");
            }

            int val = prefs.getInteger("key");
            val+=something;
            prefs.putInteger("key", val);
            prefs.flush();
        }
            
}
