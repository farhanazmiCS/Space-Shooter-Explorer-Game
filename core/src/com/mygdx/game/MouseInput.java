package com.mygdx.game;

public abstract class MouseInput extends InputManagement implements CustomInputProcessor {

    /**
     * Detects a finger (touch) or mouse click.
     * @param screenX is the x coordinate of the mouse, whereby the origin (0, 0) is the top-left corner of the screen.
     * @param screenY is the y coordinate of the mouse, whereby the origin (0, 0) is the top-left corner of the screen.
     * @param button is the button clicked (e.g., left click, right click etc.)
     * @return Boolean.True if clicked, Boolean.False if released
     */
    @Override
    public boolean mouseClickDown(int screenX, int screenY, int button) {
        return false;
    }

    /**
     * Detects when a mouse click is released from the "clicked" state
     * @param screenX is the x coordinate of the mouse, whereby the origin (0, 0) is the top-left corner of the screen.
     * @param screenY is the y coordinate of the mouse, whereby the origin (0, 0) is the top-left corner of the screen.
     * @param button is the button that is released (e.g., left click release, right click release etc.)
     * @return Boolean.True if released, Boolean.False if clicked
     */
    @Override
    public boolean mouseClickUp(int screenX, int screenY, int button) {
        return false;
    }

    /**
     * Detects when a mouse is moved.
     * @param screenX is the x coordinate of the mouse, whereby the origin (0, 0) is the top-left corner of the screen.
     * @param screenY is the y coordinate of the mouse, whereby the origin (0, 0) is the top-left corner of the screen.
     * @return Boolean.True if the mouse movement was processed, Boolean.False otherwise
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Detects a mouse scroll.
     * @param amount which can be -1 or 1, depending on the direction of the scroll wheel.
     * @return Boolean.True if scroll event is handled, Boolean.False otherwise.
     */
    @Override
    public boolean scrolled(float amount) {
        return false;
    }
}
