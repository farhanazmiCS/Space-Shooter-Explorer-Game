package com.mygdx.game.engine.behavior;

import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.lifecycle.Main;

import com.mygdx.game.game.components.game.enemy.Asteroid;
import com.mygdx.game.game.components.planets.Planet;

public interface BehaviourManager {
    void moveUFO(CollidableEntity ufo, int speed, int width);
    long fireWeapon(CollidableEntity ufo, Main game);
    void dropAsteroid(CollidableEntity<Asteroid> asteroid);
    void dropPlanet(CollidableEntity<Planet> planet);
}
