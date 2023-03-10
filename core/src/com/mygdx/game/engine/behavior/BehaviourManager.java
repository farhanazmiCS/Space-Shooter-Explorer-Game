package com.mygdx.game.engine.behavior;

import com.mygdx.game.engine.collision.CollidableEntity;

import game.components.UFO;

public interface BehaviourManager<E> {
    E moveFallingObject();

    void moveUFO(CollidableEntity ufo);

    long fireWeapon(CollidableEntity ufo);
}
