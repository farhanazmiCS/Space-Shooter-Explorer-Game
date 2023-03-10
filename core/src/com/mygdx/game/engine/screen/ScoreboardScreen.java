package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.engine.Button;
import com.mygdx.game.engine.lifecycle.Main;

import com.mygdx.game.engine.input.CustomInputProcessor;

//extends ScreenManager implements Screen

public class ScoreboardScreen implements Screen{
    public CustomInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    private CustomInputProcessor inputProcessor;
    private Button button;
    private Main game;
    public ScoreboardScreen(Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);


        ShapeRenderer renderer = button.getShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.end();

        button.setButtonColor(Color.YELLOW); // Quit to Main Menu

        if (inputProcessor.mouseHoverOver(button.getBound())) {
            button.setButtonColor(Color.ORANGE);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                game.setScreen(game.getMainMenuScreen());
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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
