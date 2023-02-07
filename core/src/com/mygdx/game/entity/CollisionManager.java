package com.mygdx.game.entity;

public interface CollisionManager<T> {
    // This class contains the methods to which the entities behave when a collision occurs.
    boolean IsColliding(T entity);
    void StopColliding(T entity);
}
