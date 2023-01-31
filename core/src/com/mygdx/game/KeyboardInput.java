package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;

public abstract class KeyboardInput extends InputManagement implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
