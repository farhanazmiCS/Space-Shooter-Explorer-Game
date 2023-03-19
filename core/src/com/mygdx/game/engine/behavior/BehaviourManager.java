package com.mygdx.game.engine.behavior;

import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.lifecycle.Main;

import java.util.ArrayList;

import game.components.game.Asteroid;
import game.components.game.Player;

public interface BehaviourManager {
    void moveUFO(CollidableEntity ufo, int speed, int width);
    long fireWeapon(CollidableEntity ufo, Main game);
    void dropAsteroid(CollidableEntity<Asteroid> asteroid);
}
