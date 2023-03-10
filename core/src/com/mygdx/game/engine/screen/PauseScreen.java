package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import game.components.menu.Button;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.engine.input.CustomInputProcessor;

import java.util.ArrayList;

public class PauseScreen extends ScreenManager implements Screen {
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

    private CustomInputProcessor inputProcessor;
    private Main game;
    private ArrayList<Button> buttons;
    private ArrayList<String> buttonPath;
    public PauseScreen(Main game) {
        super(game);
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();
        buttons = new ArrayList<Button>();
        buttonPath = new ArrayList<String>();
        buttonPath.add("resume_button.png");
        buttonPath.add("quit_button.png");
        createScreenButtons(2, buttons, 75, buttonPath);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        for (Button button : buttons) {
                button.getBatch().begin();
                button.getBatch().draw(button.getTexture(), button.getBound().getX(), game.HEIGHT - button.getBound().getHeight() - button.getBound().getY());
                button.getBatch().end();
        }

        buttons.get(0).setButtonColor(Color.WHITE);
        buttons.get(1).setButtonColor(Color.WHITE);

        if (inputProcessor.mouseHoverOver(buttons.get(0).getBound())) {
            buttons.get(0).setButtonColor(Color.LIGHT_GRAY);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                  resume();
            }
        }
        if (inputProcessor.mouseHoverOver(buttons.get(1).getBound())) {
            buttons.get(1).setButtonColor(Color.LIGHT_GRAY);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                quit();
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

    }

    @Override
    public void dispose() {

    }

    public void quit() {
        this.game.entityManager = new EntityManager();
        this.game.entityManager.setPlayer(this.game.WIDTH);
        this.game.entityManager.resetFailingObjects();
        this.game.setGameScreen(new GameScreen(this.game));
        game.setScreen(game.getMainMenuScreen());
    }
}
