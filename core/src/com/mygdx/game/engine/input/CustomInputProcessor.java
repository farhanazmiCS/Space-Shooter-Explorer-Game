package com.mygdx.game.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class CustomInputProcessor implements InputProcessor {

    public boolean mouseClicked(int mouseButton) {
        if (Gdx.input.isButtonPressed(mouseButton)) {
            System.out.println(mouseButton);
            return true;
        }
        return false;
    }
    public boolean keyDown(int key) {
        if (Gdx.input.isKeyPressed(key)) {
            return true;
        }
        return false;
    }
    public boolean keyUp(int key) {
        if (!Gdx.input.isKeyPressed(key)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    /**
     *
     * @param bound A Rectangle object that acts as the bounded zone
     * @return true if cursor (mouse) is hovering within the bound, false otherwise
     */
    public <T>boolean mouseHoverOver(T bound) {
        if (bound instanceof Rectangle) {
            if (((Rectangle) bound).contains(Gdx.input.getX(), Gdx.input.getY())) return true;
        }
        else if (((Circle) bound).contains(Gdx.input.getX(), Gdx.input.getY())) return true;
        return false;
    }
}
