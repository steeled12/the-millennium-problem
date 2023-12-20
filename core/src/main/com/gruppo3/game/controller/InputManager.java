package com.gruppo3.game.controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {

    private InputProcessor currentInputProcessor;

    public InputManager() {
        // Set the default input processor
        setCurrentInputProcessor(null);
    }

    public void setCurrentInputProcessor(InputProcessor inputProcessor) {
        this.currentInputProcessor = inputProcessor != null ? inputProcessor : this;
        Gdx.input.setInputProcessor(currentInputProcessor);
    }

    // Implement InputProcessor methods
    @Override
    public boolean keyDown(int keycode) {
        // Handle key down events
        return currentInputProcessor.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        // Handle key up events
        return currentInputProcessor.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        // Handle key typed events
        return currentInputProcessor.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Handle touch down events
        return currentInputProcessor.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Handle touch up events
        return currentInputProcessor.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Handle touch dragged events
        return currentInputProcessor.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Handle mouse moved events
        return currentInputProcessor.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // Handle scrolled events
        return currentInputProcessor.scrolled(amountX, amountY);
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return currentInputProcessor.touchCancelled(screenX, screenY, pointer, button);
    }
}
