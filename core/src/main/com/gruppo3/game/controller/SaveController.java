package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
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
        saveInventory();
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
        if (currentSave.contains("playerX") && currentSave.contains("playerY")) {
            Player.getPlayer().getPlayerBox().setPosition(currentSave.getFloat("playerX"),
                    currentSave.getFloat("playerY"));
        }

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

    private static void saveLevel() {
        // TODO
    }

    public static boolean isEmpty() {
        return currentSave.get().isEmpty();
    }

    private static void saveInventory() {
        currentSave.putString("Inventario", new Json().toJson(Player.getPlayer().getInventory()));
    }
}
