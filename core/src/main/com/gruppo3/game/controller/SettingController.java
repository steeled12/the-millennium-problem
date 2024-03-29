package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gruppo3.game.screens.GameScreen;

public class SettingController {
    private static final String SAVE_NAME = "settings";
    public static Preferences option = Gdx.app.getPreferences(SAVE_NAME);

    public static float musicVolume = .8f;
    public static float gameVolume = .8f;
    public static int maxFps = 60;
    public static boolean fullscreen = true;
    public static boolean vsync = false;

    public static void load() {
        option = Gdx.app.getPreferences(SAVE_NAME);
        musicVolume = option.getFloat("musicVolume", musicVolume);
        gameVolume = option.getFloat("gameVolume", gameVolume);
        maxFps = option.getInteger("maxFps", maxFps);
        fullscreen = option.getBoolean("fullscreen", fullscreen);
        vsync = option.getBoolean("vsync", vsync);
    }

    public static void save() {
        option.flush();
    }

    public static void apply() {
        option.putFloat("musicVolume", musicVolume);
        if(GameScreen.levelController != null)
            GameScreen.levelController.setMusicVolume(musicVolume);
        option.putFloat("gameVolume", gameVolume);
        option.putInteger("maxFps", maxFps);
        option.putBoolean("fullscreen", fullscreen);
        option.putBoolean("vsync", vsync);

        Gdx.graphics.setForegroundFPS(maxFps);
        if (fullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(800, 400);
        }
        Gdx.graphics.setVSync(vsync);

        save();
    }

}
