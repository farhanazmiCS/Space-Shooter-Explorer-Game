package com.mygdx.game.game.screens.menu;

import  com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.game.components.game.player.Player;
import com.mygdx.game.game.components.ui.Button;
import com.mygdx.game.game.screens.game.ControlScreen;
import com.mygdx.game.game.screens.game.GameScreen;

import com.mygdx.game.engine.screen.ScreenManager;
import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.engine.input.CustomInputProcessor;

import java.util.ArrayList;

public class PauseScreen implements Screen {
    public CustomInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    private SpriteBatch batch;
    private Texture texture;

    private CustomInputProcessor inputProcessor;
    private Main game;
    private ArrayList<Button> buttons;
    private ArrayList<String> buttonPath;
    public PauseScreen(Main game, ScreenManager screenManager) {
        this.game = game;

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

        this.inputProcessor = new CustomInputProcessor();

        buttons = new ArrayList<Button>();
        buttonPath = new ArrayList<String>();
        buttonPath.add("resume_button.png");
        buttonPath.add("quit_button.png");
        screenManager.createScreenButtons(2, buttons, 75, buttonPath);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        SoundManager.playMusic(SoundManager.ScreenType.PAUSE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        this.batch.begin();
        this.batch.draw(this.texture, 0, 0);
        this.batch.end();

        for (Button button : buttons) {
                button.getBatch().begin();
                button.getBatch().draw(button.getTexture(), button.getBound().getX(), game.HEIGHT - button.getBound().getHeight() - button.getBound().getY());
                button.getBatch().end();
        }

        buttons.get(0).setButtonColor(Color.WHITE);
        buttons.get(1).setButtonColor(Color.WHITE);

        if (inputProcessor.mouseHoverOver(buttons.get(0).getBound())) {
            buttons.get(0).setButtonColor(Color.LIGHT_GRAY);
            if (!buttons.get(0).isActive()) {
                buttons.get(0).setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                  resume();
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
                quit();
            }
        }
        else {
            buttons.get(1).setActive(false);
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
        texture.dispose();
        batch.dispose();
    }

    public void quit() {
        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers()) {
            player.getObject().savePlayerData(game);
        }
        this.game.entityManager = new EntityManager(this.game);
        this.game.entityManager.setPlayers(1, this.game.WIDTH);
        this.game.entityManager.resetFailingObjects();
        this.game.getScreenManager().setGameScreen(new GameScreen(this.game));
        this.game.getScreenManager().setStoryboards(this.game.getScreenManager().generateStoryboards(this.game.getScreenManager().getStoryboardImgPath(), "Story"));
        this.game.getScreenManager().setControlScreen(new ControlScreen(this.game, "controls.jpg"));
        game.setScreen(game.getScreenManager().getMainMenuScreen());
    }
}
