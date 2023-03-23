package com.mygdx.game.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.game.components.ui.Button;

import com.mygdx.game.engine.screen.ScreenManager;
import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;

import java.util.ArrayList;

public class MainMenuScreen implements Screen {
    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    private CustomInputProcessor inputProcessor;
    private final Main game;
    private OrthographicCamera camera;
    private ArrayList<Button> buttons;

    private SpriteBatch batch;
    private Texture texture;

    private ArrayList<String> buttonImagePath;

    private boolean soundPlayed;

    private ScoreboardScreen scoreboardScreen;

    public MainMenuScreen(final Main game, ScreenManager screenManager) {
        this.game = game;

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

        scoreboardScreen = new ScoreboardScreen(game);

        buttons = new ArrayList<Button>();
        buttonImagePath = new ArrayList<String>();
        this.inputProcessor = new CustomInputProcessor();

        buttonImagePath.add("play_button.png");
        buttonImagePath.add("leaderboard_button.png");
        buttonImagePath.add("quit_button.png");

        screenManager.createScreenButtons(3, buttons, 75, buttonImagePath);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        for (Button button : buttons) {
            button.setActive(false);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        SoundManager.playMusic(SoundManager.ScreenType.MAIN_MENU);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        this.batch.begin();
        this.batch.draw(this.texture, 0, 0);
        this.batch.end();

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin(); // Anything after begin() will be displayed
        game.getFont().draw(game.getBatch(), "BEYOND US", game.WIDTH / 2 - 70, 400);
        game.getBatch().end(); // Anything after end() will NOT be displayed

        // Render buttons
        //System.out.println(buttons.size());
        for (Button button : buttons) {
            button.getBatch().begin();
            button.getBatch().draw(button.getTexture(), button.getBound().getX(), game.HEIGHT - button.getBound().getHeight() - button.getBound().getY());
            button.getBatch().end();
        }

        buttons.get(0).setButtonColor(Color.WHITE);
        buttons.get(1).setButtonColor(Color.WHITE);
        buttons.get(2).setButtonColor(Color.WHITE);

        // System.out.println(buttons.get(0).getBound());
        // System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());

        if (inputProcessor.mouseHoverOver(buttons.get(0).getBound())) {
            buttons.get(0).setButtonColor(Color.LIGHT_GRAY);
            if (!buttons.get(0).isActive()) {
                buttons.get(0).setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
//                SoundManager.playButtonClick();
                play();
                this.game.getSoundManager().playButtonClick();
            }
        }
        else {
            buttons.get(0).setActive(false);
        }

        if (inputProcessor.mouseHoverOver(buttons.get(1).getBound())) {
            buttons.get(1).setButtonColor(Color.LIGHT_GRAY);
            if (!buttons.get(1).isActive()) {
                buttons.get(1).setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                this.game.getSoundManager().playButtonClick();
                game.setScreen(scoreboardScreen);
            }
        } else {
            buttons.get(1).setActive(false);
        }

        if (inputProcessor.mouseHoverOver(buttons.get(2).getBound())) {
            buttons.get(2).setButtonColor(Color.LIGHT_GRAY);
            if (!buttons.get(2).isActive()) {
                buttons.get(2).setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                this.game.getSoundManager().playButtonClick();
                quit();
            }
        } else {
            buttons.get(2).setActive(false);
        }
    }
    @Override
    public void resize(int width, int height) {

    }

    public void play() {
        game.setScreen(game.getScreenManager().getStoryboards().get(0));
    }

    public void quit() {
        Gdx.app.exit();
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
}
