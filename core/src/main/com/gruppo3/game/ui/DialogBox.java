package com.gruppo3.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.gruppo3.game.controller.SettingController;

public class DialogBox extends Table {
    private String targetText = "";
    private float animTimer = 0f;
    private float animationTotalTime = 0f;
    private float TIME_PER_CHAR = 0.05f;
    private STATE state = STATE.IDLE;
    private Label textLabel;
    private Sound typingSound;

    private enum STATE {
        IDLE,
        ANIMATING
    }

    public DialogBox(Skin skin) {
        super(skin);
        textLabel = new Label("", skin);
        this.add(textLabel).expand().align(Align.left).pad(5f);
        this.setBackground("dialoguebox");
        this.typingSound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-blipmale.wav"));
        this.typingSound.setVolume(0, SettingController.option.getFloat("musicVolume", SettingController.gameVolume));
    }

    public void animateText(String text) {
        targetText = text;
        animTimer = 0f;
        animationTotalTime = targetText.length() * TIME_PER_CHAR;
        state = STATE.ANIMATING;
    }

    public boolean isFinished() {
        return state == STATE.IDLE;
    }

    private void setText(String text) {
        if (!text.contains("\n")) {
            text = text + "\n";
        }
        textLabel.setText(text);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (state == STATE.ANIMATING) {
            animTimer += delta;
            if (animTimer >= animationTotalTime) {
                state = STATE.IDLE;
                animTimer = animationTotalTime;
            }
            int numChars = (int) (animTimer / TIME_PER_CHAR);
            if (animTimer % 0.08f <= delta) {
                typingSound.play();
            }
            setText(targetText.substring(0, numChars));
        }
    }

    @Override
    public float getPrefWidth() {
        return 200f;
    }

}