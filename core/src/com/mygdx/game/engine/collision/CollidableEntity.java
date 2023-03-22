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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    private float width;
    private float height;

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
        this.width = this.rectangle.getWidth();
        this.height = this.rectangle.getHeight();
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
    public boolean checkCollision(CollidableEntity entity, CollidableEntity other) {
        entity.setRectangle(new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight()));
        other.setRectangle(new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight()));
        return entity.getRectangle().overlaps(other.getRectangle());
    }
}
