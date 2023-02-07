package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    CustomInputProcessor inputProcessor = new CustomInputProcessor();
    final LifeCycleManager game;
    Button buttonInactive;
    Button buttonActive;

    OrthographicCamera camera;
    private Rectangle startBound;

    public MainMenuScreen(final LifeCycleManager game) {
        this.game = game;

        buttonActive = new Button(150, 150, 325, 165, "start.png");
        buttonInactive = new Button(150, 150, 325, 165, "start.png");

        startBound = new Rectangle(buttonActive.getX(), buttonActive.getY(), buttonActive.getWidth(), buttonActive.getHeight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin(); // Anything after begin() will be displayed

        //game.font.draw(game.getBatch(), "PLAY", 800/2, 480/2);
        //game.font.draw(game.getBatch(), "Tap anywhere to begin!", 100, 100);
        inputProcessor.mouseClicked(Input.Buttons.LEFT);
        inputProcessor.mouseClicked(Input.Buttons.RIGHT);
        //if (Gdx.input.getX() > 500/2 && Gdx.input.getX() < 400 && game.HEIGHT - Gdx.input.getY() > 400/2 && game.HEIGHT - Gdx.input.getY() < 350) {
        if (inputProcessor.mouseHoverOver(startBound)) {
            game.getBatch().setColor(Color.GRAY);
            game.getBatch().draw(buttonActive, 325, 165);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                game.setScreen(new com.mygdx.game.GameScreen(game));
                dispose();
            }
        } else {
            game.getBatch().setColor(Color.WHITE);
            game.getBatch().draw(buttonInactive, 325, 165);
        }
        if (Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY() + "\n");
        }
        game.getBatch().setColor(Color.WHITE);
        game.getBatch().end(); // Anything after end() will NOT be displayed
    }

    @Override
    public void resize(int width, int height) {

    }

    public void play() {

    }

    public void quit() {

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
