package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    private Main game;
    private Button quitButton;
    private Rectangle quitBound;
    Texture dropImage;
    Texture bucketImage;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    CustomInputProcessor inputProcessor = new CustomInputProcessor();

    public GameScreen(final Main game) {
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
        spawnRaindrop();

        // Quit button
        this.quitBound = new Rectangle(500, 165, 150, 150);
        quitButton = new Button(150, 150, 500, 165, "quit.png");

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
        quitButton.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Drops Collected: " + dropsGathered, 10, 470);
        game.getBatch().draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.getBatch().draw(dropImage, raindrop.x, raindrop.y);
        }

        // process user input (THIS ONE SHOULD BE CONTROLLED VIA ENTITYMANAGER CLASS, under a Move() function)
        if (inputProcessor.keyDown(Input.Keys.LEFT))
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (inputProcessor.keyDown(Input.Keys.RIGHT))
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        if (inputProcessor.keyDown(Input.Keys.UP))
            bucket.y += 200 * Gdx.graphics.getDeltaTime();
        if (inputProcessor.keyDown(Input.Keys.DOWN))
            bucket.y -= 200 * Gdx.graphics.getDeltaTime();

        if (inputProcessor.mouseHoverOver(quitBound)) {
            quitButton.getBatch().setColor(Color.GRAY);
            quitButton.getBatch().draw(quitButton, 500, 165);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                quit();
            }
        } else {
            quitButton.getBatch().setColor(Color.WHITE);
            quitButton.getBatch().draw(quitButton, 500, 165);
        }

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                // dropSound.play();
                iter.remove();
            }
        }
        quitButton.getBatch().end();
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void quit() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    @Override
    public void dispose() {

    }
}
