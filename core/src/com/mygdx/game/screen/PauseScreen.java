package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Button;
import com.mygdx.game.Main;
import com.mygdx.game.entity.CollidableEntity;
import com.mygdx.game.entity.EntityManager;
import com.mygdx.game.entity.Player;
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
        this.game.entityManager = new EntityManager();
        this.game.entityManager.setPlayer(new CollidableEntity<Player>(
                this.game.WIDTH / 2 - 64 / 2,
                20,
                new Player(
                        "spaceship.png", //<a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
                        200,
                        new int[]{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
                        new int[]{Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S},
                        0)));
        this.game.setGameScreen(new GameScreen(this.game));
        game.setScreen(game.getMainMenuScreen());
    }
}
