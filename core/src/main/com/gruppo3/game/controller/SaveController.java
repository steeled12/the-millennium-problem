package com.gruppo3.game.controller;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.model.Player;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.interactables.Item;
import com.gruppo3.game.model.interactables.PickableItem;

public class SaveController {

    private static final int MAX_SAVES = 3;
    private static final String SAVE_NAME = "save_";
    public static Preferences currentSave;

    static MyGame game = (MyGame) Gdx.app.getApplicationListener();

    public static void save() {

        /* Effetturare il save di tutti i valori necessari */
        savePlayerPosition(Player.getPlayer().getPlayerBox().x, Player.getPlayer().getPlayerBox().y);
        saveTime();
        saveInventory();
        saveLevel();
        saveSavedInformation();
        /* --- */

        currentSave.flush();
        Gdx.app.log("SaveController", "Salvato!");
    }

    public static void save(int numSave) {
        currentSave = Gdx.app.getPreferences(SAVE_NAME + String.valueOf(numSave));
        save();
    }

    public static void deleteSave(int numSave) {
        currentSave = Gdx.app.getPreferences(SAVE_NAME + String.valueOf(numSave));
        currentSave.clear();
        Gdx.app.log("SaveController", "!" + currentSave.get().toString());
        currentSave.flush();
        Gdx.app.log("SaveController", "Delete effettuato!");
    }

    public static void loadSave(int numSave) {
        currentSave = Gdx.app.getPreferences(SAVE_NAME + String.valueOf(numSave));

        /* Effetturare il load di tutti i valori necessari */
        Player.getPlayer().getPlayerBox().setPosition(currentSave.getFloat("playerX", 15),
                currentSave.getFloat("playerY", 8));
        Gdx.app.log("SaveController", "Posizione inizio load" + Player.getPlayer().getPlayerBox().x + " " + Player.getPlayer().getPlayerBox().y);
        Player.getPlayer().getInventory().clear();
        Json json = new Json();
        String inventoryString = currentSave.getString("inventory");

        if (inventoryString != null && !inventoryString.isEmpty()) {
            Map<String, String> itemDataMap = json.fromJson(LinkedHashMap.class, inventoryString);

            for (Map.Entry<String, String> entry : itemDataMap.entrySet()) {
                String itemName = entry.getKey();
                String texturePath = entry.getValue();

                PickableItem item = new PickableItem(itemName, texturePath);
                Player.getPlayer().getInventory().add(item);
            }
        }
        GameScreen.levelToLoad = currentSave.getString("level");
        GameScreen.savedInformation = (Map<String, String>) currentSave.get();
        Gdx.app.log("SaveController", "Posizione fine load" + Player.getPlayer().getPlayerBox().x + " " + Player.getPlayer().getPlayerBox().y);
        Gdx.app.log("SaveController", "Load effettuato!");
    }

    public static void newGame() {
        currentSave = null;
        Player.getPlayer().getInventory().clear();
        Player.getPlayer().getPlayerBox().setPosition(6, 13);
        GameScreen.levelToLoad = "StanzaRettoreLevel";
    }

    public static boolean saveExists(int numSave) {
        return !Gdx.app.getPreferences(SAVE_NAME + String.valueOf(numSave)).get().isEmpty();
    }

    private static void savePlayerPosition(float x, float y) {
        currentSave.putFloat("playerX", x);
        currentSave.putFloat("playerY", y);
        Gdx.app.log("SaveController", "Salvata posizione: " + x + " " + y);
    }

    private static void saveTime() {
        currentSave.putString("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        Gdx.app.log("SaveController", "Salvato tempo: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }

    public static boolean isEmpty() {
        return currentSave.get().isEmpty();
    }

    private static void saveInventory() {
        List<Item> inventory = Player.getPlayer().getInventory();
        Json json = new Json();
        Map<String, String> inventoryData = new LinkedHashMap<>();
        for (Item item : inventory) {
            inventoryData.put(item.getName(), item.getTexturePath());
        }
        currentSave.putString("inventory", json.toJson(inventoryData));

    }

    private static void saveLevel() {
        currentSave.putString("level", GameScreen.levelController.getCurrentLevel().getClass().getSimpleName());
    }

    public static Preferences getSave(int numSave) {
        return Gdx.app.getPreferences(SAVE_NAME + String.valueOf(numSave));
        
    }

    private static void saveSavedInformation() {
        currentSave.put(GameScreen.savedInformation);
    }
}
