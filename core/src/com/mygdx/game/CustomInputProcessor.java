package com.mygdx.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public interface CustomInputProcessor {
    public float getMouseX();

    public float getMouseY();

    void keyboardInput();

    public boolean mouseClickDown(int screenX, int screenY, int button);
    public boolean mouseClickUp(int screenX, int screenY, int button);

    public <T>boolean mouseHoverOver(T bound);

    public boolean scrolled(float amount);
}
