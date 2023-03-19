package com.mygdx.game.engine.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.lifecycle.Main;

import game.components.game.Asteroid;
import game.components.game.Player;
import game.components.game.UFO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class EntityManager {
    private ArrayList<CollidableEntity<Player>> players;
    private ArrayList<CollidableEntity<Asteroid>> asteroids;
    private ArrayList<CollidableEntity<UFO>> UFOs;
    private ArrayList<Texture> fallingObjectImages;
    private Main game;

    public EntityManager(Main game) {
        asteroids = new ArrayList<>();
        UFOs = new ArrayList<CollidableEntity<UFO>>();
        this.game = game;
    }

    public long spawnAsteroids(int screenWidth, int screenHeight) {
        int[] possibleX = {250, 250, 300, 350, 400, 450, 650};
        int chance = (new Random()).nextInt(possibleX.length) ;

        CollidableEntity<Asteroid> asteroid = new CollidableEntity<Asteroid>(possibleX[chance], 800, new Asteroid("asteroid.png"));
        asteroids.add(asteroid);
        return TimeUtils.nanoTime();
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

    public void savePlayerData()
    {
        // save score here
        Preferences prefs = game.getPrefs();
        StringBuilder data = new StringBuilder(prefs.getString("data"));
        SimpleDateFormat sdf3 = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");
        for (CollidableEntity<Player> player : game.entityManager.getPlayers())
        {
            String startTime = sdf3.format(player.getObject().getStartTime());
            int distanceTravelled = player.getObject().getScore();
            int aliensKilled = player.getObject().getAliensKilled();
            data.append(startTime).append(", ").append(distanceTravelled).append(", ").append(aliensKilled).append("\n");
        }
        prefs.putString("data", data.toString());
        System.out.println(data);
        prefs.flush();
    }

}
