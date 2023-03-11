package com.mygdx.game.engine.behavior;

import com.mygdx.game.engine.collision.CollidableEntity;

public interface BehaviourManager<E> {
    E moveFallingObject();

    void moveUFO(CollidableEntity ufo, int speed);

    long fireWeapon(CollidableEntity ufo);
}
