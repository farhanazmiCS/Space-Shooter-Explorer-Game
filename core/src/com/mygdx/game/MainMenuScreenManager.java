package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreenManager implements CustomInputProcessor, Screen {



    @Override
    public float getMouseX() {
        return Gdx.input.getX();
    }

    @Override
    public float getMouseY() {
        return Gdx.input.getY();
    }

    @Override
    public void keyboardInput() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    public void play() {

    }

    public void quit() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean mouseClickDown(int screenX, int screenY, int button) {
        return false;
    }

    @Override
    public boolean mouseClickUp(int screenX, int screenY, int button) {
        return false;
    }

    /**
     *
     * @param bound A Rectangle object that acts as the bounded zone
     * @return true if cursor (mouse) is hovering within the bound, false otherwise
     */
    public <T> boolean mouseHoverOver(T bound) {
        if (bound instanceof Rectangle) {
            if (((Rectangle) bound).contains(Gdx.input.getX(), Gdx.input.getY())) return true;
        }
        else if (((Circle) bound).contains(Gdx.input.getX(), Gdx.input.getY())) return true;
        return false;
    }

    @Override
    public boolean scrolled(float amount) {
        return false;
    }
}
