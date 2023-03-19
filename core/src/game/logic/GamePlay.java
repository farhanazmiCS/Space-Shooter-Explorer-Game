package game.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;

import game.components.game.Asteroid;
import game.components.game.HealthBar;
import game.components.game.Laser;
import game.components.game.Player;
import game.components.game.UFO;

import game.components.game.Background;

public class GamePlay extends Game {
    private Main game;
    private Background background;
    private int backgroundOffset;
    private CustomInputProcessor inputProcessor;
    private HealthBar healthBar;
    private int distance;
    private long lastDropTime;
    private long lastShootTime;
    private long lastShootTimeUFO;
    private final float spawnRate = 1000000000;
    private final float spawnRateMultiplier = 0.15f;

    private float lastTimeUFOSpawned = 0; // Variable to count the last time UFO was spawned

    public GamePlay(final Main game) {
        // Initialize the lifecycle manager and input processor
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        // Spawn the asteroids
        this.game.entityManager.spawnEnemy("Asteroid");

        // Scrolling background
        this.background = new Background("background_game.jpg");
        this.backgroundOffset = 0;

        // Healthbar
        this.healthBar = new HealthBar(0, 410, 300, 20);

        // For tracking the distance covered by the player
        this.distance = 0;
    }

    @Override
    public void create() {
        this.background = new Background("background_game.jpg");
    }

    public void render() {
        // background
        background.getBatch().begin();
        // Scrolling background
        backgroundOffset += 4;
        if (backgroundOffset % game.HEIGHT == 0) {
            backgroundOffset = 0;
        }
        background.getBatch().draw(background.getTexture(), 0, -backgroundOffset+game.HEIGHT, game.WIDTH, game.HEIGHT);
        background.getBatch().draw(background.getTexture(), 0, -backgroundOffset, game.WIDTH, game.HEIGHT);
        background.getBatch().end();

        // tell the SpritegetBatch() to render in the
        // coordinate system specified by the camera.
        // game.getBatch().setProjectionMatrix(camera.combined);

        healthBar.drawHealthBars(this.game.entityManager.getPlayers());

        game.getBatch().begin();

        if (this.game.entityManager.getUFOs().size() == 0 && distance % 500 == 0) {
            this.game.entityManager.spawnEnemy("UFO"); // For now, only generate 1 UFO
        }

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            this.game.entityManager.spawnEnemy("Asteroid");
            lastDropTime = TimeUtils.nanoTime();
        }

        if (TimeUtils.nanoTime() - lastShootTime > spawnRate * spawnRateMultiplier)
        {
            for (CollidableEntity<Player> player: this.game.entityManager.getPlayers())
            {
                player.getObject().spawnLasers(inputProcessor, player, this.game);
                lastShootTime = TimeUtils.nanoTime();
            }
        }

        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
        {
            player.getObject().moveLasers(player);
            if (player.getObject().getLasers().size() > 0)
            {
                for (CollidableEntity<Laser> laser : player.getObject().getLasers())
                {
                    game.getBatch().draw(
                            laser.getObject().getSprite(),
                            laser.getX(),
                            laser.getY(),
                            laser.getObject().getWidth(),
                            laser.getObject().getHeight()
                    );
                }
            }
        }

        for (CollidableEntity<UFO> ufo : this.game.entityManager.getUFOs())
        {
            if (ufo.getObject().getLasers().size() > 0)
            {
                for (CollidableEntity<Laser> laser : ufo.getObject().getLasers()) {
                    game.getBatch().draw(
                            laser.getObject().getSprite(),
                            laser.getX(),
                            laser.getY(),
                            laser.getObject().getWidth(),
                            laser.getObject().getHeight()
                    );
                }
            }
            ufo.getObject().moveLasers(ufo);
        }

        distance += 1;

        game.getFont().draw(game.getBatch(), "DISTANCE TRAVELLED: " + distance + " KM", 10, 470);
        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
        {
            game.getBatch().draw(
                    player.getObject().getSprite(),
                    player.getX(),
                    player.getY(),
                    player.getObject().getWidth(),
                    player.getObject().getHeight()
            );

            // Draw afterburner
            if (player.getObject().getAfterburner().getVisibility()) {
                player.getObject().getAfterburner().getBatch().begin();
                player.getObject().getAfterburner().getBatch().draw(player.getObject().getAfterburner().getTexture(), player.getObject().getAfterburner().getX(), player.getObject().getAfterburner().getY());
                player.getObject().getAfterburner().getBatch().end();
            }
        }

        for (CollidableEntity<Asteroid> fallingObject : this.game.entityManager.getAsteroids()) {
            game.getBatch().draw(fallingObject.getObject().getImage(), fallingObject.getX(), fallingObject.getY());
        }

        for (CollidableEntity<UFO> ufo : this.game.entityManager.getUFOs()) {
            game.getBatch().draw(ufo.getObject().getTexture(), ufo.getX(), ufo.getY());
        }

        // Code for checking player's health and setting afterburner (NOTE: NEED TO CHANGE --> DOES NOT FULFILL SINGLE RESPONSIBILITY!)
        for (int i = 0; i < this.game.entityManager.getPlayers().size(); i++)
        {
            CollidableEntity<Player> player = this.game.entityManager.getPlayers().get(i);
            int move_result = player.getObject().movePlayer(player, inputProcessor);
            player.getObject().getAfterburner().setVisibility(move_result == 2);
            if (move_result == 2) {
                backgroundOffset += 4;
                if (backgroundOffset % game.HEIGHT == 0) {
                    backgroundOffset = 0;
                }
            }

            player.getObject().getAfterburner().setX(player.getX());
            player.getObject().getAfterburner().setY(player.getY() - 50);

            // Spawning the ufo (Moving the ufo down beyond the screen)
            for (CollidableEntity<UFO> ufo : this.game.entityManager.getUFOs())
            {
                if (ufo.getY() > 330) {
                    ufo.getObject().moveUFO(ufo, 100, this.game.WIDTH);
                }
            }

            if (TimeUtils.nanoTime() - lastShootTimeUFO > spawnRate * spawnRateMultiplier)
            {
                for (CollidableEntity<UFO> ufo: this.game.entityManager.getUFOs())
                {
                    lastShootTimeUFO = ufo.getObject().fireWeapon(ufo, game);
                    if (player.laserCollision(player, ufo.getObject().getLasers())) {
                        // Check collision between player and UFO lasers
                        System.out.println("Player " + i + " hit!");
                        player.getObject().setCurrentHealth(player.getObject().getCurrentHealth() - 1);
                    }
                    if (ufo.laserCollision(ufo, player.getObject().getLasers())) {
                        System.out.println("UFO " + ufo + " hit!");
                        ufo.getObject().setHealth(ufo.getObject().getHealth() - 2);
                    }
                }
                //lastShootTime = this.game.entityManager.spawnLasers(inputProcessor, this.game.entityManager.getPlayer());
            }


//            this.game.entityManager.getUFOs().get(0).getObject().moveUFO(this.game.entityManager.getUFOs().get(0), 150, this.game.WIDTH);

            player.getObject().limitPlayerMovement(player, this.game.WIDTH, this.game.HEIGHT);

            // Asteroid movement and collision
            for (int j = 0; j < this.game.entityManager.getAsteroids().size(); j++) {
                CollidableEntity<Asteroid> asteroid = this.game.entityManager.getAsteroids().get(j);
                asteroid.getObject().dropAsteroid(asteroid);
                if (asteroid.asteroidCollision(player, asteroid)) {
                    player.getObject().setCurrentHealth(player.getObject().getCurrentHealth() - 10);
                    this.game.entityManager.getAsteroids().remove(asteroid);
                    j--;
                }
                if (asteroid.getY() < 0) {
                    this.game.entityManager.getAsteroids().remove(asteroid);
                }
            }

            if (player.getObject().getCurrentHealth() <= 0)
            {
                if (this.game.entityManager.getPlayers().size() == 1)
                {
                    //game over screen
                    game.setScreen(game.getGameOverScreen());
                }
                else
                {
                    this.game.entityManager.getPlayers().remove(player);
                    i--;
                }
            }
            player.getObject().setScore((int) distance);
            //dropsGathered += point;

            for (int u = 0; u < this.game.entityManager.getUFOs().size(); u++) {
                if (this.game.entityManager.getUFOs().get(u).getObject().getHealth() == 0) {
                    this.game.entityManager.getUFOs().remove(u);
                    u--;
                    player.getObject().setAliensKilled(player.getObject().getAliensKilled() + 1);
                }
            }
        }

        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
        {
            if (player.getObject().getScore() % 1000 == 0)
            {
                //go to trivia screen
                game.setScreen(game.getTriviaScreen());
            }
        }

        game.getBatch().end();
    }
}
