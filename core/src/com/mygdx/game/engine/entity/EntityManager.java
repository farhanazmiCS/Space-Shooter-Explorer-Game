package com.mygdx.game.engine.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.collision.CollisionManager;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;

import game.components.game.Asteroid;
import game.components.game.Laser;
import game.components.game.Player;
import game.components.game.UFO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class EntityManager implements CollisionManager {
    private ArrayList<CollidableEntity<Player>> players;
    private ArrayList<CollidableEntity<Asteroid>> asteroids;
    private ArrayList<CollidableEntity<UFO>> UFOs;
    private ArrayList<Texture> fallingObjectImages;

    public EntityManager() {
        asteroids = new ArrayList<>();
        UFOs = new ArrayList<CollidableEntity<UFO>>();
    }

    public void moveLasers() {
        for (CollidableEntity<Player> player : players) {
            ArrayList<CollidableEntity<Laser>> lasers = player.getObject().getLasers();
            for (int i = 0; i < lasers.size(); i++) {
                CollidableEntity<Laser> laser = lasers.get(i);
                laser.setY(laser.getY() + (laser.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
                lasers.set(i, laser);
            }
        }
    }

    public long spawnLasers(CustomInputProcessor inputProcessor, CollidableEntity<Player> player, Main game) {
        if (inputProcessor.keyDown(Input.Keys.SPACE) || inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
            ArrayList<CollidableEntity<Laser>> lasers = player.getObject().getLasers();
            CollidableEntity<Laser> laser = new CollidableEntity<>(
                    player.getX() + 15,
                    player.getY(),
                    new Laser(
                            "green_laser.png",
                            800));
            lasers.add(laser);
            game.getSoundManager().playLaserSound();
            return TimeUtils.nanoTime();
        }
        return 0;
    }

    public long spawnAsteroids(int screenWidth, int screenHeight) {
        int[] possibleX = {250, 250, 300, 350, 400, 450, 650};
        int chance = (new Random()).nextInt(possibleX.length) ;

        CollidableEntity<Asteroid> asteroid = new CollidableEntity<Asteroid>(possibleX[chance], 800, new Asteroid("asteroid.png"));
        asteroids.add(asteroid);
        return TimeUtils.nanoTime();
    }

    public int moveFallingObject() {
        Iterator<CollidableEntity<Asteroid>> iter = asteroids.iterator();
        while (iter.hasNext()) {
            CollidableEntity<Asteroid> fallingObject = iter.next();
            fallingObject.setY(fallingObject.getY() - 200 * Gdx.graphics.getDeltaTime());
            if (fallingObject.getY() + fallingObject.getObject().getWidth() < 0) {
                iter.remove();
            }
            for (CollidableEntity<Player> player : players) {
                if (asteroidCollision(player, fallingObject)) {
                    iter.remove();
                    return 1;
                }
            }
        }
        return 0;
    }

    public ArrayList<CollidableEntity<Asteroid>> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(ArrayList<CollidableEntity<Asteroid>> asteroids) {
        this.asteroids = asteroids;
    }

    public ArrayList<CollidableEntity<Player>> getPlayers() {
        return players;
    }

    public void setPlayers(int noOfPlayers, int WIDTH) {
        ArrayList<CollidableEntity<Player>> players = new ArrayList<>();
        // THIS SHOULD BE IN ENTITY MANAGER!!!!
        for (int i = 0; i < noOfPlayers; i++)
        {
            CollidableEntity<Player> player = new CollidableEntity<>(
                    WIDTH / 2 - 64 / 2 + (i * 64),
                    20,
                    new Player(
                            "spaceship.png", //<a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
                            200,
                            new int[]{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
                            new int[]{Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S},
                            0,
                            100));
            players.add(player);
        }
        this.players = players;
    }

    public ArrayList<Texture> getFallingObjectImages() {
        return fallingObjectImages;
    }

    public ArrayList<CollidableEntity<UFO>> getUFOs() {
        return this.UFOs;
    }

    public void setFallingObjectImages(ArrayList<Texture> fallingObjectImages) {
        this.fallingObjectImages = fallingObjectImages;
    }

    public void resetFailingObjects()
    {
        for (CollidableEntity<Asteroid> asteroid : asteroids)
        {
            asteroid.getObject().getImage().dispose();
        }
        asteroids = new ArrayList<CollidableEntity<Asteroid>>();
    }

    // Factory method to add UFO
    public void spawnUFO() {
        int max = 5;
        Random random = new Random();
        int numUFO = random.nextInt(max) + 1;
        ArrayList<Integer> possibleX = new ArrayList<Integer>();
        Integer[] elementsToAdd = {100, 200, 300, 400, 500, 600, 700};
        possibleX.addAll(Arrays.asList(elementsToAdd));
        for (int i = 0; i < numUFO; i++) {
            UFO ufoObject = new UFO("alien.png");
            int x = possibleX.get(random.nextInt(possibleX.size()));
            if (possibleX.contains(x)) {
                int index = possibleX.indexOf(x);
                possibleX.remove(index);
            }
            int y = 460; // Put beyond the screen first
            CollidableEntity<UFO> ufo = new CollidableEntity<UFO>(
                    x,
                    y,
                    ufoObject);
            UFOs.add(ufo);
        }
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
