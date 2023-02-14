package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Button;
import com.mygdx.game.Main;
import com.mygdx.game.entity.CollidableEntity;
import com.mygdx.game.entity.FallingObject;
import com.mygdx.game.entity.Player;
import com.mygdx.game.input.CustomInputProcessor;

public class GameScreen extends ScreenManager implements Screen {
    private Main game;
    private Button pauseButton;
    private boolean isPaused = false;
    OrthographicCamera camera;
    long lastDropTime;
    CustomInputProcessor inputProcessor = new CustomInputProcessor();
    float spawnRate = 1000000000;
    float spawnRateMultiplier = 1f;

    public GameScreen(final Main game) {
        super(game);
        this.game = game;
        // this.world = new World(new Vector2(0, -9.81f), false);
        //spawnRaindrop();
        this.game.entityManager.spawnFallingObject(this.game.WIDTH, this.game.HEIGHT);

        // Pause and resume button
        pauseButton = new Button(150, 50, 640, 420, game); // Pause button

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

        // tell the SpritegetBatch() to render in the
        // coordinate system specified by the camera.
        game.getBatch().setProjectionMatrix(camera.combined);


        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Items Collected: " + this.game.entityManager.getPlayer().getObject().getScore(), 10, 470);
        CollidableEntity<Player> player = this.game.entityManager.getPlayer();
        game.getBatch().draw(
                player.getObject().getSprite(),
                player.getX(),
                player.getY(),
                player.getObject().getWidth(),
                player.getObject().getHeight()
        );

        for (CollidableEntity<FallingObject> fallingObject : this.game.entityManager.getFallingObjects()) {
            game.getBatch().draw(fallingObject.getObject().getImage(), fallingObject.getX(), fallingObject.getY());
        }

        this.game.entityManager.movePlayer(inputProcessor);

        // Pause button
        ShapeRenderer renderer = pauseButton.getShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(pauseButton.getBound().getX(), game.HEIGHT - pauseButton.getBound().getHeight() - pauseButton.getBound().getY(), pauseButton.getBound().getWidth(), pauseButton.getBound().getHeight());
        renderer.end();

        pauseButton.setButtonColor(Color.WHITE);

        // Pause button logic
        if (!isPaused) {
            if (inputProcessor.mouseHoverOver(pauseButton.getBound())) {
                if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
//                game.setScreen(new GameScreen(game));
                    pause();
                    // dispose();
                }
            }
        }
        else {
            if (inputProcessor.mouseHoverOver(pauseButton.getBound())) {
                if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
//                game.setScreen(new GameScreen(game));
                    resume();
                    // dispose();
                }
            }
        }

        this.game.entityManager.limitPlayerMovement(this.game.WIDTH, this.game.HEIGHT);

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
                //spawnRateMultiplier = 0.8f;
                spawnRate /= 10;
                break;
        }
        this.game.entityManager.getPlayer().getObject().setScore(this.game.entityManager.getPlayer().getObject().getScore() + point);
        //dropsGathered += point;
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        isPaused = true;
        game.setScreen(game.getPauseScreen());
        pauseButton.setButtonColor(Color.CLEAR);
        dispose();
    }

    @Override
    public void resume() {
        isPaused = false;
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
