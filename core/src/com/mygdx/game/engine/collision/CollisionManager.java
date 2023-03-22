package com.mygdx.game.engine.collision;

import java.util.ArrayList;

import com.mygdx.game.game.components.game.enemy.Asteroid;
import com.mygdx.game.game.components.game.Laser;
import com.mygdx.game.game.components.game.player.Player;
import com.mygdx.game.game.components.planets.Planet;

public interface CollisionManager {
    // This class contains the methods to which the entities behave when a collision occurs.
    // void limitPlayerMovement(E screenWidth, E screenHeight);
    boolean asteroidCollision(CollidableEntity<Player> player, CollidableEntity<Asteroid> asteroid);
    boolean laserCollision(CollidableEntity entity, ArrayList<CollidableEntity<Laser>> lasers);
    boolean planetCollision(CollidableEntity<Player> player, CollidableEntity<Planet> planet);
}
