package com.mygdx.game;

public interface CustomInputProcessor {
    public boolean mouseClickDown(int screenX, int screenY, int button);
    public boolean mouseClickUp(int screenX, int screenY, int button);
    public boolean mouseMoved(int screenX, int screenY);
    public boolean scrolled(float amount);
}
