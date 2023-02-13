package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Button;
import com.mygdx.game.Main;
import com.mygdx.game.input.CustomInputProcessor;

import java.util.ArrayList;

public class PauseScreen extends ScreenManager implements Screen {
    CustomInputProcessor inputProcessor = new CustomInputProcessor();
    private Main game;
    private ArrayList<Button> buttons;
    public PauseScreen(Main game) {
        super(game);
        this.game = game;
        buttons = new ArrayList<Button>();
        createScreenButtons(2, buttons, 75);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        for (Button button : buttons) {
            ShapeRenderer renderer = button.getShapeRenderer();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.rect(button.getBound().getX(), game.HEIGHT - button.getBound().getHeight() - button.getBound().getY(), button.getBound().getWidth(), button.getBound().getHeight());
            renderer.end();
        }

        buttons.get(0).setButtonColor(Color.BLUE); // Resume
        buttons.get(1).setButtonColor(Color.ORANGE); // Quit to Main Menu

        if (inputProcessor.mouseHoverOver(buttons.get(0).getBound())) {
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
//                game.setScreen(new GameScreen(game));
                  resume();
            }
        }
        if (inputProcessor.mouseHoverOver(buttons.get(1).getBound())) {
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
        game.setScreen(game.getMainMenuScreen());
    }
}
