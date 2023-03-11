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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import game.components.game.Asteroid;
import game.components.game.Laser;
import game.components.game.Player;
import game.components.game.UFO;

public class EntityManager implements CollisionManager<CollidableEntity<Player>, CollidableEntity<Asteroid>, CollidableEntity<UFO>, Integer> {
    // This class contains the attributes and methods for handling all entities.
    // Example:
    //  1. Creating entities
    //  2. Rendering (and drawing) entities
    //  3. Moving the entities
    private CollidableEntity<Player> player;
    private ArrayList<CollidableEntity<Asteroid>> fallingObjects;
    private ArrayList<CollidableEntity<UFO>> UFOs;
    private ArrayList<Texture> fallingObjectImages;

    //to be initialised at the life cycle manager
    public EntityManager() {
        fallingObjects = new ArrayList<>();
        UFOs = new ArrayList<CollidableEntity<UFO>>();
        fallingObjectImages = new ArrayList<>();
        fallingObjectImages.add(new Texture(Gdx.files.internal("ufo.png")));
        //<a href="https://www.flaticon.com/free-icons/alien" title="alien icons">Alien icons created by Freepik - Flaticon</a>
        fallingObjectImages.add(new Texture(Gdx.files.internal("star.png")));
        //<a href="https://www.flaticon.com/free-icons/star" title="star icons">Star icons created by Freepik - Flaticon</a>
        fallingObjectImages.add(new Texture(Gdx.files.internal("rainbow_star.png")));
        //<a href="https://www.flaticon.com/free-icons/shapes-and-symbols" title="shapes and symbols icons">Shapes and symbols icons created by Smashicons - Flaticon</a>

    }

    public void moveLasers()
    {
        if (player.getObject().getLasers().size() > 0)
        {
            for (int i = 0; i < player.getObject().getLasers().size(); i++)
            {
                CollidableEntity<Laser> laser = player.getObject().getLasers().get(i);
                laser.setY(laser.getY() + (laser.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
                player.getObject().getLasers().set(i, laser);
            }
        }
    }

    public void moveLasers(CollidableEntity<UFO> ufo)
    {
        if (ufo.getObject().getLasers().size() > 0)
        {
            for (int i = 0; i < ufo.getObject().getLasers().size(); i++)
            {
                CollidableEntity<Laser> laser = ufo.getObject().getLasers().get(i);
                laser.setY(laser.getY() - (laser.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
                ufo.getObject().getLasers().set(i, laser);
            }
        }
    }

    public long spawnLasers(CustomInputProcessor inputProcessor, CollidableEntity<Player> player) // Used by player
    {
        if (inputProcessor.keyDown(Input.Keys.SPACE) || inputProcessor.mouseClicked(Input.Buttons.LEFT))
        {
            CollidableEntity<Laser> laser = new CollidableEntity<>(
                    player.getX() + 15,
                    player.getY(),
                    new Laser(
                            "green_laser.png", //<a href="https://www.flaticon.com/free-icons/laser" title="laser icons">Laser icons created by Freepik - Flaticon</a>
                            800));
            player.getObject().getLasers().add(laser);
            return TimeUtils.nanoTime();
        }
        return 0;
    }

    public long spawnLasers(CollidableEntity<UFO> ufo) // Showcasing Polymorphism (spawnLaser for alien)
    {
        CollidableEntity<Laser> laser = new CollidableEntity<>(
                ufo.getX(),
                ufo.getY(),
                new Laser(
                        "green_laser.png", //<a href="https://www.flaticon.com/free-icons/laser" title="laser icons">Laser icons created by Freepik - Flaticon</a>
                        800));
            ufo.getObject().getLasers().add(laser);
            return TimeUtils.nanoTime();
    }



    public long spawnFallingObject(int screenWidth, int screenHeight)
    {
        //int index = (int) ((Math.random() * ((fallingObjectImages.size() - 1))) + 0);
        int index = 0;
        int chance = (new Random()).nextInt(100) + 1;

        if (chance <= 10)
        {
            index = 2;
        }
        else if (chance > 11 & chance <= 40)
        {
            index = 1;
        }

        CollidableEntity<Asteroid> fallingObject =
                new CollidableEntity<>(
                        MathUtils.random(0, screenWidth - fallingObjectImages.get((int) index).getWidth()),
                        screenHeight,
                        new Asteroid(
                                index,
                                fallingObjectImages.get((int) index))
                );
        fallingObjects.add(fallingObject);
        return TimeUtils.nanoTime();
    }

    public Integer moveFallingObject() // This should also be part of the "Asteroid" class now (Part of Game component, not engine)
    {
        Iterator<CollidableEntity<Asteroid>> iter = fallingObjects.iterator();
        while (iter.hasNext()) {
            CollidableEntity<Asteroid> fallingObject = iter.next();
            fallingObject.setY(fallingObject.getY() - 200 * Gdx.graphics.getDeltaTime());
            if (fallingObject.getY() + fallingObject.getObject().getWidth() < 0)
                iter.remove();
            if (asteroidCollision(player, fallingObject))
            {
                iter.remove();
                switch (fallingObject.getObject().getType())
                {
                    case 0:
                        // minus health
                        return  -1;
                    case 1:
                        // gain points
                        return 1;
                    case 2:
                        // super power up, gain points and redirect to trivia quiz
                        return 2;
                }
            }
        }
        return 0;
    }

    // @Override
    // public void limitPlayerMovement(Integer screenWidth, Integer screenHeight) {

    // }

    public ArrayList<CollidableEntity<Asteroid>> getFallingObjects() {
        return fallingObjects;
    }

    public void setFallingObjects(ArrayList<CollidableEntity<Asteroid>> fallingObjects) {
        this.fallingObjects = fallingObjects;
    }

    public CollidableEntity<Player> getPlayer() {
        return player;
    }

    public void setPlayer(int WIDTH) {
        this.player = new CollidableEntity<>(
                WIDTH / 2 - 64 / 2,
                20,
                new Player(
                        "spaceship.png", //<a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
                        200,
                        new int[]{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
                        new int[]{Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S},
                        0,
                        10));
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
        //<a href="https://www.flaticon.com/free-icons/alien" title="alien icons">Alien icons created by Freepik - Flaticon</a>
        fallingObjectImages.add(new Texture(Gdx.files.internal("star.png")));
        //<a href="https://www.flaticon.com/free-icons/star" title="star icons">Star icons created by Freepik - Flaticon</a>
        fallingObjectImages.add(new Texture(Gdx.files.internal("rainbow_star.png")));
        //<a href="https://www.flaticon.com/free-icons/shapes-and-symbols" title="shapes and symbols icons">Shapes and symbols icons created by Smashicons - Flaticon</a>
    }

    // Factory method to add UFO
    public void spawnUFO(int numUFO) {
        for (int i = 0; i < numUFO; i++) {
            CollidableEntity<UFO> ufo = new CollidableEntity<UFO>(400, 350, new UFO("alien.png"));
            UFOs.add(ufo);
        }
    }

    @Override
    public boolean asteroidCollision(CollidableEntity<Player> player, CollidableEntity<Asteroid> asteroid) {
        Rectangle playerBoundary = new Rectangle(player.getX(), player.getY(), player.getObject().getWidth(), player.getObject().getHeight());
        Rectangle fallingObjectBoundary = new Rectangle(asteroid.getX(), asteroid.getY(), asteroid.getObject().getWidth(), asteroid.getObject().getHeight());
        return fallingObjectBoundary.overlaps(playerBoundary);
    }
}
