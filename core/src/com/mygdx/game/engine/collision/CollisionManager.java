package com.mygdx.game.engine.collision;

import java.util.ArrayList;

import game.components.game.Asteroid;
import game.components.game.Laser;
import game.components.game.Player;

public interface CollisionManager {
    // This class contains the methods to which the entities behave when a collision occurs.
    // void limitPlayerMovement(E screenWidth, E screenHeight);
    boolean asteroidCollision(CollidableEntity<Player> player, CollidableEntity<Asteroid> fallingObject);
    boolean laserCollision(CollidableEntity entity, ArrayList<CollidableEntity<Laser>> lasers);
}
