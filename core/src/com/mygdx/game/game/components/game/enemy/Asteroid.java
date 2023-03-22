package com.mygdx.game.game.components.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.engine.behavior.BehaviourManager;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.lifecycle.Main;

import com.mygdx.game.game.components.planets.Planet;

public class Asteroid implements BehaviourManager {
    private Texture image;
    private float width;
    private float height;

    public Asteroid(String imgPath) {
        this.image = new Texture(imgPath);
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

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

    @Override
    public void moveUFO(CollidableEntity ufo, int speed, int width) {}

    @Override
    public long fireWeapon(CollidableEntity ufo, Main game) {
        return 0;
    }

    @Override
    public void dropAsteroid(CollidableEntity<Asteroid> asteroid) {
        asteroid.setY(asteroid.getY() - 200 * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dropPlanet(CollidableEntity<Planet> planet) {

    }
}
