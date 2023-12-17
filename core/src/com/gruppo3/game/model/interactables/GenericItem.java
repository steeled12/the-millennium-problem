package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.DialogController;
import com.gruppo3.game.model.dialog.Dialog;

//Item generico per dimostrazione
public class GenericItem implements Item {
    Texture texture;
    Rectangle box;
    Dialog dialog;

    public GenericItem(Texture texture) {
        this.texture = texture;
        this.box = new Rectangle(0, 0, 32, 32);
    }
    @Override
    public Texture getTexture(){
        return texture;
    
    }
    @Override
    public Rectangle getBox(){
        return box;
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public Dialog getDialog(){
        return dialog;
    }

    @Override
    public void action(DialogController dialogController) {
        dialogController.startDialog(dialog);
    }
}