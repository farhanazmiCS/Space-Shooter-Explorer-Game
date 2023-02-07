package com.mygdx.game.entity;

public class NonCollidableEntity<T> extends Entity<T>{
    public NonCollidableEntity(float x, float y, T object) {
        super(x, y, object);
    }
}
