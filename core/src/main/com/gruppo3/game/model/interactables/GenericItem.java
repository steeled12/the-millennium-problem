package com.gruppo3.game.model.interactables;

import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.DialogController;
import com.gruppo3.game.model.dialog.Dialog;

//Item generico per dimostrazione
public class GenericItem implements Item {
    Texture texture;
    String texturePath;
    Rectangle box;
    Dialog dialog;
    String name;

    public GenericItem(String name, String texturePath) {
        this.name = name;
        this.texturePath = texturePath;
        this.texture = new Texture(texturePath);
        this.box = new Rectangle(0, 0, 32, 32);
    }

    public GenericItem(String name) {
        this.name = name;
        this.texture = null;
        this.box = new Rectangle(0, 0, 32, 32);
    }

    @Override
    public Texture getTexture() {
        return texture;

    }
    @Override
    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public Rectangle getBox() {
        return box;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return dialog;
    }

    @Override
    public void action(DialogController dialogController) {
        dialogController.startDialog(dialog);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        GenericItem otherItem = (GenericItem) obj;

        return name.equals(otherItem.name) &&
            (Objects.equals(texturePath, otherItem.texturePath));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, texturePath);
    }


}