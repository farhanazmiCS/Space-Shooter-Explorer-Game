package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor {
    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    private ShapeRenderer shapeRenderer;
    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private int width, height;
    private float x, y;

    public Button(int width, int height, float x, float y) {
        this.shapeRenderer = new ShapeRenderer();
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Rectangle getBound() {
        Rectangle bound = new Rectangle(
                this.x,
                this.y,
                this.width,
                this.height
        );
        return bound;
    }

}
