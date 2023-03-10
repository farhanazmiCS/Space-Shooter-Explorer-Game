package com.mygdx.game.engine.collision;

import com.mygdx.game.engine.entity.Entity;

public class CollidableEntity<T> extends Entity<T> {
    private float prevX;
    private float prevY;

    public CollidableEntity(float x, float y, T object) {
        super(x, y, object);
        this.prevX = x;
        this.prevY = y;
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