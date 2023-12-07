package com.gruppo3.game.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class NpcDialog extends Dialog {

    private Label npcNameLabel;
    private Image npcImage;
    private Label dialogTextLabel;

    public NpcDialog(String title, Skin skin) {
        super(title, skin);
        
        npcNameLabel = new Label("", skin);
        npcImage = new Image(); 
        dialogTextLabel = new Label("", skin);

        getContentTable().add(npcImage).padRight(10);
        getContentTable().add(npcNameLabel).left().top().padTop(10).padBottom(10).row();
        getContentTable().add(dialogTextLabel).center().colspan(2).padBottom(30).row();

        button("ok", "OK");

        pad(20);
        getBackground().setMinHeight(200);
        getBackground().setMinWidth(300);
    }

    public void setNpcInfo(String npcName, Texture npcTexture) { //valutare se usare drawable o texture
        npcNameLabel.setText(npcName);
        npcImage.setDrawable(new TextureRegionDrawable(new TextureRegion(npcTexture)));
    }

    public void setDialogText(String text) {
        dialogTextLabel.setText(text);
    }

    @Override
    protected void result(Object object) {
        if (object.equals("OK")) {

            hide();
        }
    }
}