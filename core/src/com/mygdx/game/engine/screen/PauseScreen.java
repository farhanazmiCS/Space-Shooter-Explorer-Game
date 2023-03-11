package com.mygdx.game.engine.screen;

import  com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import game.components.game.Player;
import game.components.menu.Button;

import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.sound.SoundManager;
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

    private SpriteBatch batch;
    private Texture texture;

    private CustomInputProcessor inputProcessor;
    private Main game;
    private ArrayList<Button> buttons;
    private ArrayList<String> buttonPath;
    public PauseScreen(Main game) {
        super(game);
        this.game = game;

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

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
        SoundManager.stopMusic();
    }

    @Override
    public void dispose() {

    }

    public void quit() {
        this.game.entityManager = new EntityManager();
        CollidableEntity<Player> player = new CollidableEntity<>(
                this.game.WIDTH / 2 - 64 / 2,
                20,
                new Player(
                        "spaceship.png", //<a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
                        200,
                        new int[]{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
                        new int[]{Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S},
                        0,
                        10));
//        CollidableEntity<Player> player1 = new CollidableEntity<>(
//                (this.game.WIDTH / 2 - 64 / 2) + 64,
//                20,
//                new Player(
//                        "spaceship.png", //<a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
//                        200,
//                        new int[]{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
//                        new int[]{Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S},
//                        0,
//                        10));
//        CollidableEntity<Player> player2 = new CollidableEntity<>(
//                (this.game.WIDTH / 2 - 64 / 2) - 64,
//                20,
//                new Player(
//                        "spaceship.png", //<a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
//                        200,
//                        new int[]{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
//                        new int[]{Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S},
//                        0,
//                        10));
        this.game.entityManager.setPlayers(new ArrayList<CollidableEntity<Player>>());
        this.game.entityManager.getPlayers().add(player);
//        this.game.entityManager.getPlayers().add(player1);
//        this.game.entityManager.getPlayers().add(player2);
//        this.game.entityManager.setPlayer(player);
        this.game.entityManager.resetFailingObjects();
        this.game.setGameScreen(new GameScreen(this.game));
        this.game.setStoryboards(this.game.getScreenManager().generateStoryboards(this.game.getStoryboardImgPath()));
        this.game.setControlScreen(new ControlScreen(this.game, "controls.jpg"));
        game.setScreen(game.getMainMenuScreen());
    }
}
