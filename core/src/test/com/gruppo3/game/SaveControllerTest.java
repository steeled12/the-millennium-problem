package com.gruppo3.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.gruppo3.game.controller.SaveController;
import com.gruppo3.game.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class SaveControllerTest {

    @Mock
    private Preferences preferencesMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        SaveController.currentSave = preferencesMock;
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new MyGame(), config);
        Gdx.gl = mock(GL20.class);
    }

    @Test
    public void testSave() {

        // Mock player position
        Player.getPlayer().getPlayerBox().setPosition(10, 20);

        // Mock current time
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        // Call the save method
        SaveController.save();

        // Verify that the player position and time are saved correctly
        verify(preferencesMock).putFloat("playerX", 10);
        verify(preferencesMock).putFloat("playerY", 20);
        verify(preferencesMock).putString("time", currentTime);
        verify(preferencesMock).flush();
    }


    @Test
    public void testDeleteSave() {
        // Call the deleteSave method
        SaveController.deleteSave();

        // Verify that the preferences are cleared and flushed
        verify(preferencesMock).clear();
        verify(preferencesMock).flush();
    }

    @Test
    public void testLoadSave() {
        int numSave = 2;

        // Mock preferences for the given numSave
        Preferences preferences = Gdx.app.getPreferences("save_" + numSave);

        // Call the loadSave method
        SaveController.loadSave(numSave);

        // Verify that the currentSave is set to the mocked preferences
        assertSame(preferences, SaveController.currentSave);

    }

}
