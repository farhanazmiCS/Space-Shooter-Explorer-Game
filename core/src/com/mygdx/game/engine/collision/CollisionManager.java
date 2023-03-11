package com.mygdx.game.engine.collision;

import game.components.game.Asteroid;
import game.components.game.Player;

public interface CollisionManager<P, F, E, I extends Number> {
    // This class contains the methods to which the entities behave when a collision occurs.
    // void limitPlayerMovement(E screenWidth, E screenHeight);
    boolean asteroidCollision(P player, F fallingObject);

    boolean asteroidCollision(CollidableEntity<Player> player, CollidableEntity<Asteroid> fallingObject);
}
