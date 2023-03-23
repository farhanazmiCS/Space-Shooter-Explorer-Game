package com.mygdx.game.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.game.components.game.player.Player;
import com.mygdx.game.game.components.ui.Button;

import java.sql.Timestamp;

public class ControlScreen implements Screen {
    private String imgPath;
    private Texture texture;
    private SpriteBatch batch;
    private final Main game;
    private CustomInputProcessor inputProcessor;
    private OrthographicCamera camera;
    private Button playButton;

    private float buttonShowDelay = 0.5f; // seconds
    private Timer.Task buttonShowTask;


    public ControlScreen(final Main game, String imgPath) {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.imgPath = imgPath;
        this.texture = new Texture(this.imgPath);
        this.batch = new SpriteBatch();
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        this.playButton = new Button(150, 66, 640, 20, "play_button.png", game);

        buttonShowTask = new Timer.Task() {
            @Override
            public void run() {
                playButton.setVisibility(true);
            }
        };
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        Timer.schedule(buttonShowTask, buttonShowDelay);
        SoundManager.playMusic(SoundManager.ScreenType.CONTROL);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        this.batch.begin();
        this.batch.draw(this.texture, 0, 0);
        this.batch.end();

        if (playButton.getVisibility()) {
            playButton.getBatch().begin();
            playButton.getBatch().draw(playButton.getTexture(), 640, 20);
            playButton.getBatch().end();
        }

        playButton.setButtonColor(Color.WHITE);

        // Next button logic
        if (inputProcessor.mouseHoverOver(playButton.getBound()) && playButton.getVisibility()) {
            playButton.setButtonColor(Color.LIGHT_GRAY);
            if (!playButton.isActive()) {
                playButton.setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                play();
            }
        }
        else {
            playButton.setActive(false);
        }
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
        SoundManager.stopMusic();
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }

    public void play() {
        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
        {
            player.getObject().setStartTime(new Timestamp(System.currentTimeMillis()));
        }
        this.game.setScreen(this.game.getScreenManager().getGameScreen());
    }
}
