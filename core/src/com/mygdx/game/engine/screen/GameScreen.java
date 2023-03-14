package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import game.components.game.HealthBar;
import game.components.menu.Button;

import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.collision.CollidableEntity;
import game.components.game.Asteroid;
import game.components.game.Laser;
import game.components.game.Player;
import com.mygdx.game.engine.input.CustomInputProcessor;

import game.components.game.UFO;

public class GameScreen implements Screen {
    private Main game;
    private Button pauseButton;
    private OrthographicCamera camera;
    private long lastDropTime;
    private long lastShootTime;
    private long lastShootTimeUFO;
    private CustomInputProcessor inputProcessor;

    private Texture background;
    private Viewport viewport;
    private int backgroundOffset;

    private SpriteBatch batch;
//    private ShapeRenderer shapeRenderer;
    private HealthBar healthBars;

    public Button getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;
    }

    public long getLastDropTime() {
        return lastDropTime;
    }

    public void setLastDropTime(long lastDropTime) {
        this.lastDropTime = lastDropTime;
    }

    public CustomInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    public float getSpawnRate() {
        return spawnRate;
    }

    public float getSpawnRateMultiplier() {
        return spawnRateMultiplier;
    }

    private final float spawnRate = 1000000000;
    private final float spawnRateMultiplier = 0.15f;

    private int distance = 0;

    public GameScreen(final Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();
        // this.world = new World(new Vector2(0, -9.81f), false);
        //spawnRaindrop();
        this.game.entityManager.spawnFallingObject(this.game.WIDTH, this.game.HEIGHT);

        this.game.entityManager.spawnUFO(this.game.WIDTH, this.game.HEIGHT); // For now, only generate 1 UFO

        // Pause and resume button
        pauseButton = new Button(150, 66, 640, 420, "pause_button.png", game); // Pause button

        // create the camera and the SpritegetBatch()
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        viewport = new StretchViewport(game.WIDTH, game.HEIGHT, camera);
        background = new Texture("background_game.jpg");
        backgroundOffset = 0;

        batch = new SpriteBatch();
//        shapeRenderer = new ShapeRenderer();
        healthBars = new HealthBar(0, 410, 300, 20);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        SoundManager.playMusic(SoundManager.ScreenType.GAME);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        // tell the camera to update its matrices.
        camera.update();

        // background
        batch.begin();
        // Scrolling background
        backgroundOffset += 4;
        if (backgroundOffset % game.HEIGHT == 0) {
            backgroundOffset = 0;
        }
        batch.draw(background, 0, -backgroundOffset+game.HEIGHT, game.WIDTH, game.HEIGHT);
        batch.draw(background, 0, -backgroundOffset, game.WIDTH, game.HEIGHT);
        batch.end();

        pauseButton.getBatch().begin();
        pauseButton.getBatch().draw(pauseButton.getTexture(), 640, 420);

        pauseButton.getBatch().end();

        pauseButton.setButtonColor(Color.WHITE);

        // Pause button logic
        if (inputProcessor.mouseHoverOver(pauseButton.getBound())) {
            pauseButton.setButtonColor(Color.LIGHT_GRAY);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                pause();
            }
        }

        // tell the SpritegetBatch() to render in the
        // coordinate system specified by the camera.
        game.getBatch().setProjectionMatrix(camera.combined);

        healthBars.drawHealthBars(this.game.entityManager.getPlayers());

        game.getBatch().begin();

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            lastDropTime = this.game.entityManager.spawnFallingObject(this.game.WIDTH, this.game.HEIGHT);

        if (TimeUtils.nanoTime() - lastShootTime > spawnRate * spawnRateMultiplier)
        {
            for (CollidableEntity<Player> player: this.game.entityManager.getPlayers())
            {
                lastShootTime = this.game.entityManager.spawnLasers(inputProcessor, player);
            }
            //lastShootTime = this.game.entityManager.spawnLasers(inputProcessor, this.game.entityManager.getPlayer());
        }

        this.game.entityManager.moveLasers();

        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
        {
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
                for (CollidableEntity<Laser> laser : ufo.getObject().getLasers())
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
            ufo.getObject().moveLasers();
        }



//        if (this.game.entityManager.getPlayer().getObject().getLasers().size() > 0)
//        {
//            for (CollidableEntity<Laser> laser : this.game.entityManager.getPlayer().getObject().getLasers()) {
//                game.getBatch().draw(
//                        laser.getObject().getSprite(),
//                        laser.getX(),
//                        laser.getY(),
//                        laser.getObject().getWidth(),
//                        laser.getObject().getHeight()
//                );
//            }
//        }

        distance += 1;

        game.getFont().draw(game.getBatch(), "Distance Travelled: " + distance + " km", 10, 470);
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
//        CollidableEntity<Player> player = this.game.entityManager.getPlayer();
//        game.getBatch().draw(
//                player.getObject().getSprite(),
//                player.getX(),
//                player.getY(),
//                player.getObject().getWidth(),
//                player.getObject().getHeight()
//        );
//
//        // Draw afterburner
//        if (player.getObject().getAfterburner().getVisibility()) {
//            player.getObject().getAfterburner().getBatch().begin();
//            player.getObject().getAfterburner().getBatch().draw(player.getObject().getAfterburner().getTexture(), player.getObject().getAfterburner().getX(), player.getObject().getAfterburner().getY());
//            player.getObject().getAfterburner().getBatch().end();
//        }

        for (CollidableEntity<Asteroid> fallingObject : this.game.entityManager.getFallingObjects()) {
            game.getBatch().draw(fallingObject.getObject().getImage(), fallingObject.getX(), fallingObject.getY());
        }

        for (CollidableEntity<UFO> ufo : this.game.entityManager.getUFOs()) {
            game.getBatch().draw(ufo.getObject().getTexture(), ufo.getX(), ufo.getY());
        }

        for (int i = 0; i < this.game.entityManager.getPlayers().size(); i++)
        {
            CollidableEntity<Player> player = this.game.entityManager.getPlayers().get(i);
            int move_result = player.getObject().movePlayer(player, inputProcessor);
            player.getObject().getAfterburner().setVisibility(move_result == 2);

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
                    lastShootTimeUFO = ufo.getObject().fireWeapon(ufo);
                }
                //lastShootTime = this.game.entityManager.spawnLasers(inputProcessor, this.game.entityManager.getPlayer());
            }


//            this.game.entityManager.getUFOs().get(0).getObject().moveUFO(this.game.entityManager.getUFOs().get(0), 150, this.game.WIDTH);

            player.getObject().limitPlayerMovement(player, this.game.WIDTH, this.game.HEIGHT);

            int point = this.game.entityManager.moveFallingObject();
            switch (point)
            {
                case -1:
                    //minus health
                    player.getObject().setCurrentHealth(player.getObject().getCurrentHealth() - 1);
                    if (player.getObject().getCurrentHealth() == 0)
                    {
                        if (this.game.entityManager.getPlayers().size() == 1)
                        {
                            //game over screen
                            game.setScreen(game.getGameOverScreen());
                        }
                        else
                        {
                            this.game.entityManager.getPlayers().remove(player);
                        }
                    }
                    break;
                case 1:
                    //add 1 point
                    break;
                case 2:
                    //add 2 points and redirect to trivia quiz
                    //spawnRate /= 10;
                    break;
            }
            player.getObject().setScore((int) distance);
            //dropsGathered += point;
        }

//        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
//        {
//            int move_result = player.getObject().movePlayer(player, inputProcessor);
//            player.getObject().getAfterburner().setVisibility(move_result == 2);
//
//            player.getObject().getAfterburner().setX(player.getX());
//            player.getObject().getAfterburner().setY(player.getY() - 50);
//
//            for (CollidableEntity<UFO> ufo : this.game.entityManager.getUFOs())
//            {
//                ufo.getObject().moveUFO(ufo, 150, this.game.WIDTH);
//            }
//
////            this.game.entityManager.getUFOs().get(0).getObject().moveUFO(this.game.entityManager.getUFOs().get(0), 150, this.game.WIDTH);
//
//            player.getObject().limitPlayerMovement(player, this.game.WIDTH, this.game.HEIGHT);
//
//            int point = this.game.entityManager.moveFallingObject();
//            switch (point)
//            {
//                case -1:
//                    //minus health
//                    player.getObject().setCurrentHealth(player.getObject().getCurrentHealth() - 1);
//                    if (player.getObject().getCurrentHealth() == 0)
//                    {
//                        if (this.game.entityManager.getPlayers().size() == 1)
//                        {
//                            //game over screen
//                            game.setScreen(game.getGameOverScreen());
//                        }
//                        else
//                        {
//                            this.game.entityManager.getPlayers().remove(player);
//                        }
//                    }
//                    break;
//                case 1:
//                    //add 1 point
//                    break;
//                case 2:
//                    //add 2 points and redirect to trivia quiz
//                    //spawnRate /= 10;
//                    break;
//            }
//            player.getObject().setScore((int) distance);
//            //dropsGathered += point;
//        }

//        int move_result = this.game.entityManager.getPlayer().getObject().movePlayer(this.game.entityManager.getPlayer(), inputProcessor);
//        this.game.entityManager.getPlayer().getObject().getAfterburner().setVisibility(move_result == 2);
//
//        this.game.entityManager.getPlayer().getObject().getAfterburner().setX(this.game.entityManager.getPlayer().getX());
//        this.game.entityManager.getPlayer().getObject().getAfterburner().setY(this.game.entityManager.getPlayer().getY() - 50);
//
//        this.game.entityManager.getUFOs().get(0).getObject().moveUFO(this.game.entityManager.getUFOs().get(0), 150, this.game.WIDTH);
//
//        this.game.entityManager.getPlayer().getObject().limitPlayerMovement(this.game.entityManager.getPlayer(), this.game.WIDTH, this.game.HEIGHT);
//
//        int point = this.game.entityManager.moveFallingObject();
//        switch (point)
//        {
//            case -1:
//                //minus health
//                break;
//            case 1:
//                //add 1 point
//                break;
//            case 2:
//                //add 2 points and redirect to trivia quiz
//                //spawnRate /= 10;
//                break;
//        }
//        this.game.entityManager.getPlayer().getObject().setScore((int) distance);
//        //dropsGathered += point;

        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
        game.setScreen(game.getPauseScreen());
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        SoundManager.stopMusic();
    }

    public void quit() {
    }

    @Override
    public void dispose() {
        healthBars.getBatch().dispose();
        healthBars.getShapeRenderer().dispose();
        healthBars.getFont().dispose();
    }
}
