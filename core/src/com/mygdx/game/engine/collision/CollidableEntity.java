package com.mygdx.game.engine.collision;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.engine.entity.Entity;

import java.util.ArrayList;

import game.components.game.Asteroid;
import game.components.game.Laser;
import game.components.game.Player;

public class CollidableEntity<T> extends Entity<T> implements CollisionManager{
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
        this.rectangle.setWidth(50);
        this.rectangle.setHeight(50);
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
        for (int j = 0; j < lasers.size(); j++) {
            lasers.get(j).setRectangle(new Rectangle(lasers.get(j).getX(), lasers.get(j).getY(), lasers.get(j).getObject().getWidth(), lasers.get(j).getObject().getHeight()));
            if (entity.getRectangle().overlaps(lasers.get(j).getRectangle())) {
                System.out.println("Hit!\n");
                return true;
            }
        }
        return false;
    }
}
