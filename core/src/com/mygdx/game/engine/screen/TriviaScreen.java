package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.sound.SoundManager;

import java.util.ArrayList;

import game.components.menu.Button;

public class TriviaScreen extends ScreenManager implements Screen {

    private ArrayList<Button> options;
    public CustomInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    private SpriteBatch batch;
    private Texture texture;

    private CustomInputProcessor inputProcessor;
    private Main game;
    public TriviaScreen(Main game) {
        super(game);
        this.game = game;

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

        options = new ArrayList<Button>();

        this.inputProcessor = new CustomInputProcessor();
        int buttonWidth = this.game.WIDTH / 2;
        int buttonHeight = this.game.HEIGHT / 4;
        int buttonX = 0;
        int buttonY = 0;
        for (int i = 0; i < 4; i ++)
        {
            Button button = new Button(buttonWidth, buttonHeight, buttonX, buttonY, "blank_button.png", game);
            options.add(button);
        }
        options.get(0).setX(0);
        options.get(0).setY(0);

        options.get(1).setX(0);
        options.get(1).setY(buttonHeight);

        options.get(2).setX(buttonWidth);
        options.get(2).setY(0);

        options.get(3).setX(buttonWidth);
        options.get(3).setY(buttonHeight);
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


        for (Button button : this.options)
        {
            button.getBatch().begin();
            button.getBatch().draw(button.getTexture(), button.getX(), button.getY(), button.getWidth(), button.getHeight());

            button.getBatch().end();

            button.setButtonColor(Color.WHITE);

            if (inputProcessor.mouseHoverOver(button.getBound())) {
                button.setButtonColor(Color.LIGHT_GRAY);
                if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                    //do something
                    resume();
                }
            }
        }



        game.getBatch().begin(); // Anything after begin() will be displayed
        game.getFont().draw(game.getBatch(), "GAME OVER", game.WIDTH / 2 - 70, 400);
        game.getBatch().end(); // Anything after end() will NOT be displayed
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
        texture.dispose();
        batch.dispose();
    }
}
