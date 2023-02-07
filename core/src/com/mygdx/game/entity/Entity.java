package com.mygdx.game.entity;

import java.util.Objects;

public abstract class Entity<T> implements Cloneable {
    private float x;
    private float y;
    private T object;

    public Entity(float x, float y, T object) {
        this.x = x;
        this.y = y;
        this.object = object;
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

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity<?> entity = (Entity<?>) o;
        return Float.compare(entity.getX(), getX()) == 0 && Float.compare(entity.getY(), getY()) == 0 && Objects.equals(getObject(), entity.getObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getObject());
    }
}
