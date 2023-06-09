package com.mygdx.game.game.screens.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.game.components.ui.Button;

public class StoryboardScreen implements Screen {
    private String imgPath;
    private Texture texture;
    private SpriteBatch batch;
    private final Main game;
    private CustomInputProcessor inputProcessor;
    private OrthographicCamera camera;
    private Button nextButton;

    private float buttonShowDelay = 0.5f; // seconds
    private Timer.Task buttonShowTask;


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    private int current;
    private String type;

    public StoryboardScreen(final Main game, String imgPath, String type) {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);

        this.imgPath = imgPath;
        this.texture = new Texture(this.imgPath);
        this.batch = new SpriteBatch();
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        this.type = type;

        if (type == "Planet") {
            this.nextButton = new Button(150, 66, 640, 400, "resume_button.png", game);
        }
        else {
            this.nextButton = new Button(150, 66, 640, 20, "next_button.png", game);
        }
        this.nextButton.setActive(false);

        buttonShowTask = new Timer.Task() {
            @Override
            public void run() {
                nextButton.setVisibility(true);
            }
        };
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        Timer.schedule(buttonShowTask, buttonShowDelay);
        SoundManager.playMusic(SoundManager.ScreenType.STORY);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        this.batch.begin();
        this.batch.draw(this.texture, 0, 0);
        this.batch.end();

        if (nextButton.getVisibility()) {
            nextButton.getBatch().begin();
            nextButton.getBatch().draw(nextButton.getTexture(), nextButton.getX(), nextButton.getY());
            nextButton.getBatch().end();
        }

        nextButton.setButtonColor(Color.WHITE);

        // Next button logic
        if (inputProcessor.mouseHoverOver(nextButton.getBound()) && nextButton.getVisibility()) {
            nextButton.setButtonColor(Color.LIGHT_GRAY);
            if (!nextButton.isActive()) {
                nextButton.setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                if (type == "Planet") {
                    resume();
                }
                else {
                    next();
                }
            }
        }
        else {
            nextButton.setActive(false);
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
        game.setScreen(game.getScreenManager().getGameScreen());
    }

    @Override
    public void hide() {
        SoundManager.stopMusic();
    }

    @Override
    public void dispose() {

    }

    public void next() {
        if (current < game.getScreenManager().getStoryboards().size() - 1) {
            game.setScreen(game.getScreenManager().getStoryboards().get(current + 1));
        }
        else {
            game.setScreen(game.getScreenManager().getControlScreen());
        }
    }
}
