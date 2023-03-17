package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.sound.SoundManager;

import java.util.ArrayList;

import game.components.game.Player;
import game.components.game.TriviaOption;
import game.components.game.TriviaQuestion;
import game.components.menu.Button;

public class ResultScreen extends ScreenManager implements Screen {

    public CustomInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    private SpriteBatch batch;
    private Texture texture;

    private CustomInputProcessor inputProcessor;
    private Main game;

    private float buttonShowDelay = 0.5f; // seconds
    private Timer.Task buttonShowTask;
    private Button nextButton;

    private String resultBG;

    public String getResultBG() {
        return resultBG;
    }

    public void setResultBG(String resultBG) {
        this.resultBG = resultBG;
        texture = new Texture(resultBG);
    }

    public ResultScreen(Main game) {
        super(game);
        this.game = game;

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

        this.inputProcessor = new CustomInputProcessor();

        this.nextButton = new Button(150, 66, 640, 20, "resume_button.png", game);

        nextButton.setVisibility(false);

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
        SoundManager.playMusic(SoundManager.ScreenType.PAUSE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        this.batch.begin();
        this.batch.draw(this.texture, 0, 0);
        this.batch.end();

        if (nextButton.getVisibility()) {
            nextButton.getBatch().begin();
            nextButton.getBatch().draw(nextButton.getTexture(), 640, 20);
            nextButton.getBatch().end();
        }

        nextButton.setButtonColor(Color.WHITE);

        // Next button logic
        if (inputProcessor.mouseHoverOver(nextButton.getBound()) && nextButton.getVisibility()) {
            nextButton.setButtonColor(Color.LIGHT_GRAY);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                game.setScreen(game.getGameScreen());
            }
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
        game.setScreen(game.getGameScreen());
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
}
