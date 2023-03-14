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
    private ArrayList<CollidableEntity<Asteroid>> fallingObjects;
    private ArrayList<CollidableEntity<UFO>> UFOs;
    private ArrayList<Texture> fallingObjectImages;
    public int noOfPlayers = 1;

    public EntityManager() {
        fallingObjects = new ArrayList<>();
        UFOs = new ArrayList<CollidableEntity<UFO>>();
        fallingObjectImages = new ArrayList<>();
        fallingObjectImages.add(new Texture(Gdx.files.internal("asteroid.png")));
        fallingObjectImages.add(new Texture(Gdx.files.internal("star.png")));
        fallingObjectImages.add(new Texture(Gdx.files.internal("rainbow_star.png")));
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

    public long spawnLasers(CustomInputProcessor inputProcessor, CollidableEntity<Player> player) {
        if (inputProcessor.keyDown(Input.Keys.SPACE) || inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
            ArrayList<CollidableEntity<Laser>> lasers = player.getObject().getLasers();
            CollidableEntity<Laser> laser = new CollidableEntity<>(
                    player.getX() + 15,
                    player.getY(),
                    new Laser(
                            "green_laser.png",
                            800));
            lasers.add(laser);
            return TimeUtils.nanoTime();
        }
        return 0;
    }

    public long spawnFallingObject(int screenWidth, int screenHeight) {
        int index = 0;
        int chance = (new Random()).nextInt(100) + 1;

        if (chance <= 5) {
            index = 2;
        } else if (chance > 6 & chance <= 20) {
            index = 1;
        }

        CollidableEntity<Asteroid> fallingObject =
                new CollidableEntity<>(
                        MathUtils.random(0, screenWidth - fallingObjectImages.get(index).getWidth()),
                        screenHeight,
                        new Asteroid(
                                index,
                                fallingObjectImages.get(index))
                );
        fallingObjects.add(fallingObject);
        return TimeUtils.nanoTime();
    }

    public int moveFallingObject() {
        Iterator<CollidableEntity<Asteroid>> iter = fallingObjects.iterator();
        while (iter.hasNext()) {
            CollidableEntity<Asteroid> fallingObject = iter.next();
            fallingObject.setY(fallingObject.getY() - 200 * Gdx.graphics.getDeltaTime());
            if (fallingObject.getY() + fallingObject.getObject().getWidth() < 0) {
                iter.remove();
            }
            for (CollidableEntity<Player> player : players) {
                if (asteroidCollision(player, fallingObject)) {
                    iter.remove();
                    switch (fallingObject.getObject().getType()) {
                        case 0:
                            // minus health
                            return -1;
                        case 1:
                            // gain points
                            return 1;
                        case 2:
                            // super power up, gain points and redirect to trivia quiz
                            return 2;
                    }
                }
            }
        }
        return 0;
    }

    public ArrayList<CollidableEntity<Asteroid>> getFallingObjects() {
        return fallingObjects;
    }

    public void setFallingObjects(ArrayList<CollidableEntity<Asteroid>> fallingObjects) {
        this.fallingObjects = fallingObjects;
    }

    public ArrayList<CollidableEntity<Player>> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<CollidableEntity<Player>> players) {
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
        for (Texture image : fallingObjectImages)
        {
            image.dispose();
        }
        for (CollidableEntity<Asteroid> fallingObject : fallingObjects)
        {
            fallingObject.getObject().getImage().dispose();
        }
        fallingObjects = new ArrayList<>();
        fallingObjectImages = new ArrayList<>();
        fallingObjectImages.add(new Texture(Gdx.files.internal("ufo.png")));
        fallingObjectImages.add(new Texture(Gdx.files.internal("star.png")));
        fallingObjectImages.add(new Texture(Gdx.files.internal("rainbow_star.png")));
    }

    // Factory method to add UFO
    public void spawnUFO() {
        int max = 5;
        Random random = new Random();
        int numUFO = random.nextInt(max);
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
