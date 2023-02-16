package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Button;
import com.mygdx.game.input.CustomInputProcessor;
import com.mygdx.game.Main;

import java.util.ArrayList;

public class MainMenuScreen extends ScreenManager implements Screen {
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

    public MainMenuScreen(final Main game) {
        super(game);
        this.game = game;
        buttons = new ArrayList<Button>();
        this.inputProcessor = new CustomInputProcessor();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        createScreenButtons(3, buttons, 75);
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
            renderer.rect(button.getBound().getX(), game.HEIGHT - button.getBound().getHeight() - button.getBound().getY(), button.getBound().getWidth(), button.getBound().getHeight());
            renderer.end();
        }
        game.getBatch().end(); // Anything after end() will NOT be displayed

        buttons.get(0).setButtonColor(Color.BLUE); // Play
        buttons.get(1).setButtonColor(Color.YELLOW); // [Placeholder]
        buttons.get(2).setButtonColor(Color.RED); // Quit

        // System.out.println(buttons.get(0).getBound());
        // System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());

        if (inputProcessor.mouseHoverOver(buttons.get(0).getBound())) {
            buttons.get(0).setButtonColor(Color.SKY);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                play();
            }
        }
        if (inputProcessor.mouseHoverOver(buttons.get(2).getBound())) {
            buttons.get(2).setButtonColor(Color.PINK);
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                quit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    public void play() {
        game.setScreen(game.getGameScreen());
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
