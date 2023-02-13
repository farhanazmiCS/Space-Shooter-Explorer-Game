package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
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
    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    CustomInputProcessor inputProcessor = new CustomInputProcessor();
    float spawnRate = 1000000000;
    float spawnRateMultiplier = 1f;

    public GameScreen(final Main game) {
        super(game);
        this.game = game;
        // this.world = new World(new Vector2(0, -9.81f), false);
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above

        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        //spawnRaindrop();
        this.game.entityManager.spawnFallingObject(this.game.WIDTH, this.game.HEIGHT);

        // Pause and resume button
            pauseButton = new Button(150, 50, 640, 420, game); // Pause button

        // create the camera and the SpritegetBatch()
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    /**
     * Maybe for this function, can be for spawining different types of things?
     * Example: Spawn environment, spawn player etc.
     */
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
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

        // begin a new getBatch() and draw the bucket and
        // all drops
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Drops Collected: " + this.game.entityManager.getPlayer().getObject().getScore(), 10, 470);
        //game.getBatch().draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        CollidableEntity<Player> player = this.game.entityManager.getPlayer();
        game.getBatch().draw(
                player.getObject().getSprite(),
                player.getX(),
                player.getY(),
                player.getObject().getWidth(),
                player.getObject().getHeight()
        );
//        for (Rectangle raindrop : raindrops) {
//            game.getBatch().draw(dropImage, raindrop.x, raindrop.y);
//        }
        for (CollidableEntity<FallingObject> fallingObject : this.game.entityManager.getFallingObjects()) {
            game.getBatch().draw(fallingObject.getObject().getImage(), fallingObject.getX(), fallingObject.getY());
        }

        // process user input (THIS ONE SHOULD BE CONTROLLED VIA ENTITYMANAGER CLASS, under a Move() function)
//        if (inputProcessor.keyDown(Input.Keys.LEFT))
//            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//        if (inputProcessor.keyDown(Input.Keys.RIGHT))
//            bucket.x += 200 * Gdx.graphics.getDeltaTime();
//        if (inputProcessor.keyDown(Input.Keys.UP))
//            bucket.y += 200 * Gdx.graphics.getDeltaTime();
//        if (inputProcessor.keyDown(Input.Keys.DOWN))
//            bucket.y -= 200 * Gdx.graphics.getDeltaTime();
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

        // make sure the bucket stays within the screen bounds
//        if (bucket.x < 0)
//            bucket.x = 0;
//        if (bucket.x > 800 - 64)
//            bucket.x = 800 - 64;
        this.game.entityManager.limitPlayerMovement(this.game.WIDTH, this.game.HEIGHT);

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > spawnRate * spawnRateMultiplier)
            lastDropTime = this.game.entityManager.spawnFallingObject(this.game.WIDTH, this.game.HEIGHT);
            //spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
//        Iterator<Rectangle> iter = raindrops.iterator();
//        while (iter.hasNext()) {
//            Rectangle raindrop = iter.next();
//            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
//            if (raindrop.y + 64 < 0)
//                iter.remove();
//            if (raindrop.overlaps(bucket)) {
//                dropsGathered++;
//                // dropSound.play();
//                iter.remove();
//            }
//        }
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
