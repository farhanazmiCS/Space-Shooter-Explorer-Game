package com.mygdx.game.engine.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.engine.entity.Entity;

import java.util.ArrayList;

import com.mygdx.game.game.components.game.enemy.Asteroid;
import com.mygdx.game.game.components.game.Laser;
import com.mygdx.game.game.components.game.player.Player;
import com.mygdx.game.game.components.planets.Planet;

public class CollidableEntity<T> extends Entity<T> implements CollisionManager {
    private float prevX;
    private float prevY;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    private Rectangle rectangle;

    public CollidableEntity(float x, float y, T object) {
        super(x, y, object);
        this.rectangle = new Rectangle();
        this.prevX = x;
        this.prevY = y;
        this.rectangle.setWidth(75);
        this.rectangle.setHeight(75);
    }

    public float getPrevX() {
        return prevX;
    }

    public void setPrevX(float prevX) {
        this.prevX = prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public void setPrevY(float prevY) {
        this.prevY = prevY;
    }

    @Override
    public boolean asteroidCollision(CollidableEntity<Player> player, CollidableEntity<Asteroid> asteroid) {
        player.setRectangle(new Rectangle(player.getX(), player.getY(), player.getObject().getWidth(), player.getObject().getHeight()));
        asteroid.setRectangle(new Rectangle(asteroid.getX(), asteroid.getY(), asteroid.getObject().getWidth(), asteroid.getObject().getHeight()));
        return player.getRectangle().overlaps(asteroid.getRectangle());
    }

    @Override
    public boolean laserCollision(CollidableEntity entity, ArrayList<CollidableEntity<Laser>> lasers) {
        entity.setRectangle(new Rectangle(entity.getX(), entity.getY(), entity.getRectangle().getWidth(), entity.getRectangle().getHeight()));
        for (int i = 0; i < lasers.size(); i++) {
            CollidableEntity<Laser> laser = lasers.get(i);
            laser.setRectangle(new Rectangle(laser.getX(), laser.getY(), laser.getObject().getWidth(), laser.getObject().getHeight()));
            if (entity.getRectangle().overlaps(laser.getRectangle())) {
                System.out.println("Hit!\n");
                lasers.remove(laser);
                return true;
            }
            if (laser.getY() > 480) {
                lasers.remove(laser);
            }
        }
        return false;
    }

    @Override
    public boolean planetCollision(CollidableEntity<Player> player, CollidableEntity<Planet> planet) {
        player.setRectangle(new Rectangle(player.getX(), player.getY(), player.getObject().getWidth(), player.getObject().getHeight()));
        planet.setRectangle(new Rectangle(planet.getX(), planet.getY(), planet.getObject().getWidth(), planet.getObject().getHeight()));
        return player.getRectangle().overlaps(planet.getRectangle());
    }
}
