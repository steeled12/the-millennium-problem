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
        Player.getPlayer().getPlayerBox().setPosition(10, 20);

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        SaveController.save();

        verify(preferencesMock).putFloat("playerX", 10);
        verify(preferencesMock).putFloat("playerY", 20);
        verify(preferencesMock).putString("time", currentTime);
        verify(preferencesMock).flush();
    }


    @Test
    public void testDeleteSave() {
        SaveController.deleteSave();

        verify(preferencesMock).clear();
        verify(preferencesMock).flush();
    }

    @Test
    public void testLoadSave() {
        int numSave = 2;

        Preferences preferences = Gdx.app.getPreferences("save_" + numSave);

        SaveController.loadSave(numSave);

        assertSame(preferences, SaveController.currentSave);

    }

}
