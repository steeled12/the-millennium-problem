package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.util.Action;

public abstract class ScriptableObject implements Action {
    Rectangle collidingBox;
    Boolean showInteractionWidget;

    public ScriptableObject(Rectangle box, Boolean showInteractionWidget) {
        this.collidingBox = box;
        this.showInteractionWidget = showInteractionWidget;
    }

    public Rectangle getBox() {
        return this.collidingBox;
    }

    public Boolean getShowInteractionWidget() {
        return showInteractionWidget;
    }
}
