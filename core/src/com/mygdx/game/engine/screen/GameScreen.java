package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import game.components.menu.Button;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.collision.CollidableEntity;
import game.components.game.FallingObject;
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
    private CustomInputProcessor inputProcessor;

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
    private final float spawnRateMultiplier = 0.5f;

    private int distance = 0;

    public GameScreen(final Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();
        // this.world = new World(new Vector2(0, -9.81f), false);
        //spawnRaindrop();
        this.game.entityManager.spawnFallingObject(this.game.WIDTH, this.game.HEIGHT);

        this.game.entityManager.spawnUFO(1); // For now, only generate 1 UFO

        // Pause and resume button
        pauseButton = new Button(150, 66, 640, 420, "pause_button.png", game); // Pause button

        // create the camera and the SpritegetBatch()
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        // tell the camera to update its matrices.
        camera.update();

        // Pause button

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

        game.getBatch().begin();

        if (TimeUtils.nanoTime() - lastShootTime > spawnRate * spawnRateMultiplier)
            lastShootTime = this.game.entityManager.spawnLasers(inputProcessor, this.game.entityManager.getPlayer());

        this.game.entityManager.moveLasers();

        if (this.game.entityManager.getPlayer().getObject().getLasers().size() > 0)
        {
            for (CollidableEntity<Laser> laser : this.game.entityManager.getPlayer().getObject().getLasers()) {
                game.getBatch().draw(
                        laser.getObject().getSprite(),
                        laser.getX(),
                        laser.getY(),
                        laser.getObject().getWidth(),
                        laser.getObject().getHeight()
                );
            }
        }

        distance += 1;

        game.getFont().draw(game.getBatch(), "Distance Travelled: " + distance + " km", 10, 470);
        CollidableEntity<Player> player = this.game.entityManager.getPlayer();
        game.getBatch().draw(
                player.getObject().getSprite(),
                player.getX(),
                player.getY(),
                player.getObject().getWidth(),
                player.getObject().getHeight()
        );

        for (CollidableEntity<UFO> ufo : this.game.entityManager.getUFOs()) {
            game.getBatch().draw(ufo.getObject().getTexture(), ufo.getX(), ufo.getY());
        }

        for (CollidableEntity<FallingObject> fallingObject : this.game.entityManager.getFallingObjects()) {
            game.getBatch().draw(fallingObject.getObject().getImage(), fallingObject.getX(), fallingObject.getY());
        }

        this.game.entityManager.getPlayer().getObject().movePlayer(this.game.entityManager.getPlayer(), inputProcessor);

        this.game.entityManager.getUFOs().get(0).getObject().moveUFO(this.game.entityManager.getUFOs().get(0), this.game.entityManager.getPlayer(), 150);

        this.game.entityManager.getPlayer().getObject().limitPlayerMovement(this.game.entityManager.getPlayer(), this.game.WIDTH, this.game.HEIGHT);

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > spawnRate * spawnRateMultiplier)
            lastDropTime = this.game.entityManager.spawnFallingObject(this.game.WIDTH, this.game.HEIGHT);
            //spawnRaindrop();

        int point = this.game.entityManager.moveFallingObject();
        switch (point)
        {
            case -1:
                //minus health
                break;
            case 1:
                //add 1 point
                break;
            case 2:
                //add 2 points and redirect to trivia quiz
                //spawnRate /= 10;
                break;
        }
        this.game.entityManager.getPlayer().getObject().setScore((int) distance);
        //dropsGathered += point;

        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

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

    }

    public void quit() {
    }

    @Override
    public void dispose() {

    }
}
