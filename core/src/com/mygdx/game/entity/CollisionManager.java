package com.mygdx.game.entity;

public interface CollisionManager<P, F, E> {
    // This class contains the methods to which the entities behave when a collision occurs.
    void limitPlayerMovement(E screenWidth, E screenHeight);
    boolean checkFallingObjectCollision(P player, F fallingObject);
}
