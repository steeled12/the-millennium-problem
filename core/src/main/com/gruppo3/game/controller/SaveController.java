package com.gruppo3.game.controller;

import com.gruppo3.game.model.interactables.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gruppo3.game.model.Player;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveController {

    private static final int MAX_SAVES = 3;
    private static final String SAVE_NAME = "save_";
    public static Preferences currentSave;

    public static void save() {
        // salvo nel primo file save libero
        if (currentSave == null) {
            int i = 0;
            loadSave(i);
            while (!isEmpty() && i < MAX_SAVES) {
                loadSave(++i);
            }
        }

        /* Effetturare il save di tutti i valori necessari */
        savePlayerPosition(Player.getPlayer().getPlayerBox().x, Player.getPlayer().getPlayerBox().y);
        saveTime();

        /* --- */

        currentSave.flush();
        Gdx.app.log("SaveController", "Salvato!");
    }

    public static void save(int numSave) {
        loadSave(numSave);
        save();
    }

    public static void deleteSave() {
        currentSave.clear();
        currentSave.flush();
        Gdx.app.log("SaveController", "Delete effettuato!");
    }

    public static void loadSave(int numSave) {
        currentSave = Gdx.app.getPreferences(SAVE_NAME + String.valueOf(numSave));

        /* Effetturare il load di tutti i valori necessari */
        Player.getPlayer().getPlayerBox().setPosition(currentSave.getFloat("playerX", 8),
                currentSave.getFloat("playerY", 8));
        Gdx.app.log("SaveController", "Load effettuato!");
    }

    private static void savePlayerPosition(float x, float y) {
        currentSave.putFloat("playerX", x);
        currentSave.putFloat("playerY", y);
        Gdx.app.log("SaveController", "PlayerPosition salvata!");
    }

    private static void saveTime() {
        currentSave.putString("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }

    public static boolean isEmpty() {
        return currentSave.get().isEmpty();
    }

    private static void saveInventory(List<Item> inventory) {
        Map<String, List<Item>> inventoryMap = new HashMap<>();
        // TODO

    }
}
