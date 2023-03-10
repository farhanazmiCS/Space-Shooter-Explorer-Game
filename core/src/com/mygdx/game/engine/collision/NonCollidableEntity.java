package com.mygdx.game.engine.collision;

import com.mygdx.game.engine.entity.Entity;

public class NonCollidableEntity<T> extends Entity<T> {
    public NonCollidableEntity(float x, float y, T object) {
        super(x, y, object);
    }
}
