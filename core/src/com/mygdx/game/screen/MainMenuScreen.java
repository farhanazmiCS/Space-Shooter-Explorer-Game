package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Button;
import com.mygdx.game.input.CustomInputProcessor;
import com.mygdx.game.Main;

import java.util.ArrayList;

public class MainMenuScreen implements Screen {
    CustomInputProcessor inputProcessor = new CustomInputProcessor();
    final Main game;
    Button startButton;

    OrthographicCamera camera;
    private Rectangle startBound;
    ArrayList<Rectangle> bound;
    ArrayList<Button> buttons;

    public MainMenuScreen(final Main game) {
        this.game = game;
        buttons = new ArrayList<Button>();

        // startBound = new Rectangle(startButton.getX(), startButton.getY(), startButton.getWidth(), startButton.getHeight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        createScreenButtons(3, buttons, 75);
    }

    public void createScreenButtons(int numberOfButtons, ArrayList<Button> buttons, float distance) {
        int i;
        Button button;

        for (i = 0; i < numberOfButtons; i++) {
            if (i == 0) {
                button = new Button(150, 50, 325, game.HEIGHT - 200, game);
                buttons.add(button);
            }
            else {
                button = new Button(150, 50, 325, game.HEIGHT - 200 - distance * i, game);
                buttons.add(button);
            }
        }
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

        // Render buttons
        //System.out.println(buttons.size());
        for (Button button : buttons) {
            ShapeRenderer renderer = button.getShapeRenderer();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.rect(button.getBound().getX(), button.getBound().getY(), button.getBound().getWidth(), button.getBound().getHeight());
            renderer.end();
        }
        game.getBatch().end(); // Anything after end() will NOT be displayed

        buttons.get(0).setButtonColor(Color.RED);
        buttons.get(1).setButtonColor(Color.BLUE);
        buttons.get(2).setButtonColor(Color.GREEN);

        // System.out.println(buttons.get(0).getBound());
        // System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());

        if (inputProcessor.mouseHoverOver(buttons.get(0).getBound())) {
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                game.setScreen(new GameScreen(game));
            }
        }
        if (inputProcessor.mouseHoverOver(buttons.get(2).getBound())) {
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                quit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    public void play() {

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

    }

    @Override
    public void dispose() {

    }
}
