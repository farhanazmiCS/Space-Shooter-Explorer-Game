package com.mygdx.game.engine.behavior;

import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.lifecycle.Main;

public interface BehaviourManager {
    void moveUFO(CollidableEntity ufo, int speed, int width);

    long fireWeapon(CollidableEntity ufo, Main game);
}
