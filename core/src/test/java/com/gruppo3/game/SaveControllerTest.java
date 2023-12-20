package com.gruppo3.game;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.gruppo3.game.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.gruppo3.game.controller.SaveController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import com.gruppo3.game.MyGame;

public class SaveControllerTest {

    private Preferences mockSave;
    HeadlessApplication headlessApplication;
    MyGame game;

    @BeforeEach
    public void setup() {
        mockSave = new MockPreferences();
        SaveController.currentSave = mockSave;
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(new MyGame(), config);
        }
    }

    @Test
    public void testSave() {
        // Arrange
        float playerX = 10.0f;
        float playerY = 20.0f;
        LocalDateTime now = LocalDateTime.now();
        String expectedTime = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        // Act
        SaveController.save();

        // Assert
        Assertions.assertEquals(playerX, mockSave.getFloat("playerX"));
        Assertions.assertEquals(playerY, mockSave.getFloat("playerY"));
        Assertions.assertEquals(expectedTime, mockSave.getString("time"));
        Assertions.assertFalse(SaveController.isEmpty());
    }

    @Test
    public void testSaveWithNumSave() {
        // Arrange
        int numSave = 1;

        // Act
        SaveController.save(numSave);

        // Assert
        Assertions.assertEquals(numSave, ((MockPreferences) mockSave).getNumSave());
    }

    @Test
    public void testDeleteSave() {
        // Arrange
        mockSave.putFloat("playerX", 10.0f);
        mockSave.putFloat("playerY", 20.0f);
        mockSave.putString("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        // Act
        SaveController.deleteSave();

        // Assert
        Assertions.assertTrue(mockSave.get().isEmpty());
    }

    @Test
    public void testLoadSave() {
        // Arrange
        float playerX = 10.0f;
        float playerY = 20.0f;
        mockSave.putFloat("playerX", playerX);
        mockSave.putFloat("playerY", playerY);

        // Act
        SaveController.loadSave(0);

        // Assert
        Assertions.assertEquals(playerX, Player.getPlayer().getPlayerBox().x);
        Assertions.assertEquals(playerY, Player.getPlayer().getPlayerBox().y);
    }

    @Test
    public void testIsEmpty() {
        // Act
        boolean isEmpty = SaveController.isEmpty();

        // Assert
        Assertions.assertTrue(isEmpty);
    }

    // Mock Preferences class for testing
    private static class MockPreferences implements Preferences {
        private int numSave;

        public int getNumSave() {
            return numSave;
        }

        @Override
        public Preferences putBoolean(String key, boolean val) {
            return null;
        }

        @Override
        public Preferences putInteger(String key, int val) {
            return null;
        }

        @Override
        public Preferences putLong(String key, long val) {
            return null;
        }

        @Override
        public Preferences putFloat(String key, float val) {
            return null;
        }

        @Override
        public Preferences putString(String key, String val) {
            return null;
        }

        @Override
        public Preferences put(Map<String, ?> vals) {
            return null;
        }

        @Override
        public boolean getBoolean(String key) {
            return false;
        }

        @Override
        public int getInteger(String key) {
            return 0;
        }

        @Override
        public long getLong(String key) {
            return 0;
        }

        @Override
        public float getFloat(String key) {
            return 0;
        }

        @Override
        public String getString(String key) {
            return null;
        }

        @Override
        public boolean getBoolean(String key, boolean defValue) {
            return false;
        }

        @Override
        public int getInteger(String key, int defValue) {
            return 0;
        }

        @Override
        public long getLong(String key, long defValue) {
            return 0;
        }

        @Override
        public float getFloat(String key, float defValue) {
            return 0;
        }

        @Override
        public String getString(String key, String defValue) {
            return null;
        }

        @Override
        public Map<String, ?> get() {
            return null;
        }

        @Override
        public boolean contains(String key) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public void remove(String key) {

        }

        @Override
        public void flush() {

        }
    }
}
