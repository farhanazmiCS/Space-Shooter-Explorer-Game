package com.mygdx.game.engine.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.engine.entity.Entity;

public class CollidableEntity<T> extends Entity<T>{
    private float prevX;
    private float prevY;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    private Rectangle rectangle;

    public CollidableEntity(float x, float y, T object) {
        super(x, y, object);
        this.rectangle = new Rectangle();
        this.prevX = x;
        this.prevY = y;
        this.rectangle.setWidth(50);
        this.rectangle.setHeight(50);
    }

    public float getPrevX() {
        return prevX;
    }

    public void setPrevX(float prevX) {
        this.prevX = prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public void setPrevY(float prevY) {
        this.prevY = prevY;
    }
}
