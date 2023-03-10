package com.mygdx.game.engine.collision;

public interface CollisionManager<P, F, E, I extends Number> {
    // This class contains the methods to which the entities behave when a collision occurs.
    // void limitPlayerMovement(E screenWidth, E screenHeight);
    boolean checkFallingObjectCollision(P player, F fallingObject);
}
