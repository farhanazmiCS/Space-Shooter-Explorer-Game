package com.mygdx.game.engine.collision;

import java.util.ArrayList;

import com.mygdx.game.game.components.game.Laser;
import com.mygdx.game.game.components.game.player.Player;

public interface CollisionManager {
    // This class contains the methods to which the entities behave when a collision occurs.
    // void limitPlayerMovement(E screenWidth, E screenHeight);
    boolean checkCollision(CollidableEntity<Player> player, CollidableEntity other);
}
