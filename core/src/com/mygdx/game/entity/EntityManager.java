package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.input.CustomInputProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EntityManager {
    // This class contains the attributes and methods for handling all entities.
    // Example:
    //  1. Creating entities
    //  2. Rendering (and drawing) entities
    //  3. Moving the entities
    private ArrayList<CollidableEntity<FallingObject>> fallingObjects;
    private CollidableEntity<Player> player;
    private ArrayList<Texture> fallingObjectImages;

    //to be initialised at the life cycle manager
    public EntityManager() {
        fallingObjects = new ArrayList<>();
        fallingObjectImages = new ArrayList<>();
        fallingObjectImages.add(new Texture(Gdx.files.internal("ufo.png")));
        //<a href="https://www.flaticon.com/free-icons/alien" title="alien icons">Alien icons created by Freepik - Flaticon</a>
        fallingObjectImages.add(new Texture(Gdx.files.internal("star.png")));
        //<a href="https://www.flaticon.com/free-icons/star" title="star icons">Star icons created by Freepik - Flaticon</a>
        fallingObjectImages.add(new Texture(Gdx.files.internal("rainbow_star.png")));
        //<a href="https://www.flaticon.com/free-icons/shapes-and-symbols" title="shapes and symbols icons">Shapes and symbols icons created by Smashicons - Flaticon</a>
    }

    public long spawnFallingObject(int screenWidth, int screenHeight)
    {
        //int index = (int) ((Math.random() * ((fallingObjectImages.size() - 1))) + 0);
        int index = (new Random()).nextInt(3);
        CollidableEntity<FallingObject> fallingObject =
                new CollidableEntity<>(
                        MathUtils.random(0, screenWidth - fallingObjectImages.get((int) index).getWidth()),
                        screenHeight,
                        new FallingObject(
                                index,
                                fallingObjectImages.get((int) index))
                );
        fallingObjects.add(fallingObject);
        return TimeUtils.nanoTime();
    }

    public void limitPlayerMovement(int screenWidth, int screenHeight)
    {
        if(player.getX() < 0)
            player.setX(0);
        if(player.getX() > screenWidth - player.getObject().getWidth())
            player.setX(screenWidth - player.getObject().getWidth());
        if(player.getY() < 0)
            player.setY(0);
        if(player.getY() > screenHeight - player.getObject().getHeight())
            player.setY(screenHeight - player.getObject().getHeight());
    }

    public void movePlayer(CustomInputProcessor inputProcessor)
    {
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[0]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[0]))
            player.setX(player.getX() - (200 * Gdx.graphics.getDeltaTime()));
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[1]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[1]))
            player.setX(player.getX() + (200 * Gdx.graphics.getDeltaTime()));
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[2]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[2]))
            player.setY(player.getY() + (200 * Gdx.graphics.getDeltaTime()));
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[3]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[3]))
            player.setY(player.getY() - (200 * Gdx.graphics.getDeltaTime()));
    }

    public int moveFallingObject()
    {
        int points = 0;
        Iterator<CollidableEntity<FallingObject>> iter = fallingObjects.iterator();
        while (iter.hasNext()) {
            CollidableEntity<FallingObject> fallingObject = iter.next();
            //fallingObject.getObject().setImage(new Texture(new Image()));
            fallingObject.setY(fallingObject.getY() - 200 * Gdx.graphics.getDeltaTime());
            if (fallingObject.getY() + fallingObject.getObject().getWidth() < 0)
                iter.remove();
            Rectangle playerBoundary = new Rectangle(player.getX(), player.getY(), player.getObject().getWidth(), player.getObject().getHeight());
            Rectangle fallingObjectBoundary = new Rectangle(fallingObject.getX(), fallingObject.getY(), fallingObject.getObject().getWidth(), fallingObject.getObject().getHeight());
            if (fallingObjectBoundary.overlaps(playerBoundary)) {
                switch (fallingObject.getObject().getType())
                {
                    case 0:
                        // minus health
                        points -= 1;
                        break;
                    case 1:
                        // gain points
                        points += 1;
                        break;
                    case 2:
                        // super power up, gain points and redirect to trivia quiz
                        points += 2;
                        break;
                }
                iter.remove();
                //return fallingObject.getObject().getType();
            }
        }
        //return -1;
        return points;
    }

    public ArrayList<CollidableEntity<FallingObject>> getFallingObjects() {
        return fallingObjects;
    }

    public void setFallingObjects(ArrayList<CollidableEntity<FallingObject>> fallingObjects) {
        this.fallingObjects = fallingObjects;
    }

    public CollidableEntity<Player> getPlayer() {
        return player;
    }

    public void setPlayer(CollidableEntity<Player> player) {
        this.player = player;
    }

    public ArrayList<Texture> getFallingObjectImages() {
        return fallingObjectImages;
    }

    public void setFallingObjectImages(ArrayList<Texture> fallingObjectImages) {
        this.fallingObjectImages = fallingObjectImages;
    }
}
