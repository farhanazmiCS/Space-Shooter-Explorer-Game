package com.mygdx.game.engine.entity;

import com.badlogic.gdx.Input;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.lifecycle.Main;

import com.mygdx.game.game.components.game.enemy.Asteroid;
import com.mygdx.game.game.components.game.player.Player;
import com.mygdx.game.game.components.game.enemy.UFO;
import com.mygdx.game.game.components.planets.Planet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EntityManager {
    private ArrayList<CollidableEntity<Player>> players;
    private ArrayList<CollidableEntity<Asteroid>> asteroids;
    private ArrayList<CollidableEntity<UFO>> UFOs;

    public ArrayList<CollidableEntity<Planet>> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<CollidableEntity<Planet>> planets) {
        this.planets = planets;
    }

    private ArrayList<CollidableEntity<Planet>> planets;

    public String[] getAvailablePlanets() {
        return availablePlanets;
    }

    public void setAvailablePlanets(String[] availablePlanets) {
        this.availablePlanets = availablePlanets;
    }

    private String[] availablePlanets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Neptune", "Uranus"};
    private Main game;

    public EntityManager(Main game) {
        asteroids = new ArrayList<CollidableEntity<Asteroid>>();
        UFOs = new ArrayList<CollidableEntity<UFO>>();
        planets = new ArrayList<CollidableEntity<Planet>>();
        this.game = game;
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
                            100),
                    64,
                    64);
            players.add(player);
        }
        this.players = players;
    }

    public ArrayList<CollidableEntity<UFO>> getUFOs() {
        return this.UFOs;
    }

    public void resetFailingObjects()
    {
        for (CollidableEntity<Asteroid> asteroid : asteroids)
        {
            asteroid.getObject().getImage().dispose();
        }
        asteroids = new ArrayList<CollidableEntity<Asteroid>>();
    }

    public ArrayList spawnEnemy(String entityType) {
        if (entityType.equals("Asteroid")) {
            Random random = new Random();
            int[] possibleX = {250, 250, 300, 350, 400, 450, 650};
            int chance = random.nextInt(possibleX.length);
            Asteroid asteroid = new Asteroid("asteroid.png");
            CollidableEntity<Asteroid> asteroidEntity = new CollidableEntity<Asteroid>(
                    possibleX[chance],
                    800,
                    asteroid, 64, 64);
            asteroids.add(asteroidEntity);
            return asteroids;
        } else if (entityType.equals("UFO")) {
            Random random = new Random();
            int max = 5;
            ArrayList<Integer> possibleX = new ArrayList<Integer>();
            Integer[] elementsToAdd = {100, 200, 300, 400, 500, 600, 700};
            possibleX.addAll(Arrays.asList(elementsToAdd));
            int numUFO = random.nextInt(max) + 1;
            for (int i = 0; i < numUFO; i++) {
                UFO ufoObject = new UFO("alien.png");
                int x = possibleX.get(random.nextInt(possibleX.size()));
                if (possibleX.contains(x)) {
                    int index = possibleX.indexOf(x);
                    possibleX.remove(index);
                }
                int y = 460; // Put beyond the screen first
                CollidableEntity<UFO> ufoEntity = new CollidableEntity<UFO>(
                        x,
                        y,
                        ufoObject, 64, 64);
                UFOs.add(ufoEntity);
            }
            return UFOs;
        }
        return null;
    }

    public void spawnPlanets() {
        Random random = new Random();
        String[] availablePlanets = {"Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Neptune", "Uranus"};
        for (int i = 0; i < availablePlanets.length; i++) {
            int posX = random.nextInt(this.game.WIDTH - 125);
            Planet p = new Planet(availablePlanets[i], String.format("%s.png", availablePlanets[i]));
            CollidableEntity<Planet> planet = new CollidableEntity<Planet>(posX, 600, p, 125, 125);
            planets.add(planet);
        }
    }
}
