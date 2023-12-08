package com.gruppo3.game.model;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class NpcDialog extends Dialog {

    private Label npcNameLabel;
    private Image npcImage;
    private Label dialogTextLabel;

    public NpcDialog(String title, Skin skin, String buttonText, final Runnable action) {
        super(title, skin);

        npcNameLabel = new Label("", skin);
        npcImage = new Image();
        dialogTextLabel = new Label("", skin);

        getContentTable().add(npcImage).padRight(10);
        getContentTable().add(npcNameLabel).left().top().padTop(10).padBottom(10).row();
        getContentTable().add(dialogTextLabel).center().colspan(2).padBottom(30).row();

        TextButton button = new TextButton(buttonText, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (action != null) {
                    action.run();
                }
                hide();
            }
        });

        button(button);
        pad(20);
        getBackground().setMinHeight(200);
        getBackground().setMinWidth(300);
    }

    public void setNpcInfo(String npcName, Texture npcTexture) {
        npcNameLabel.setText(npcName);
        npcImage.setDrawable(new TextureRegionDrawable(new TextureRegion(npcTexture)));
    }

    public void setDialogText(String text) {
        dialogTextLabel.setText(text);
    }
}
