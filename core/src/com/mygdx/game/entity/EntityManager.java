package com.mygdx.game.entity;

import java.util.ArrayList;

public class EntityManager {
    // This class contains the attributes and methods for handling all entities.
    // Example:
    //  1. Creating entities
    //  2. Rendering (and drawing) entities
    //  3. Moving the entities
    private ArrayList<CollidableEntity<Enemy>> enemies;
    private CollidableEntity<Player> player;

    //to be initialised at the life cycle manager
    public EntityManager() {
        enemies = new ArrayList<>();
    }

    public void SpawnEnemies()
    {

    }



    public CollidableEntity<Player> getPlayer() {
        return player;
    }

    public void setPlayer(CollidableEntity<Player> player) {
        this.player = player;
    }
}
