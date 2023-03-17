package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.sound.SoundManager;

import java.util.ArrayList;
import java.util.Arrays;

import game.components.game.Player;
import game.components.game.TriviaOption;
import game.components.game.TriviaQuestion;
import game.components.menu.Button;

public class TriviaScreen extends ScreenManager implements Screen {

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

    private TriviaQuestion triviaQuestion;

    private Timer.Task resumeGameTask;

    public TriviaScreen(Main game) {
        super(game);
        this.game = game;

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

        ArrayList<TriviaOption> options = new ArrayList<>();

        this.inputProcessor = new CustomInputProcessor();
        int buttonWidth = this.game.WIDTH / 2;
        int buttonHeight = this.game.HEIGHT / 4;
        int buttonX = 0;
        int buttonY = 0;
        for (int i = 0; i < 4; i ++)
        {
            options.add(new TriviaOption(buttonWidth, buttonHeight, buttonX, buttonY, "blank_button.png", game, "", false));
        }
        options.get(0).setX(0);
        options.get(0).setY(0);
        options.get(0).setName("1 week");

        options.get(1).setX(0);
        options.get(1).setY(buttonHeight);
        options.get(1).setName("1 day");
        options.get(1).setCorrect(true);

        options.get(2).setX(buttonWidth);
        options.get(2).setY(0);
        options.get(2).setName("1 hour");

        options.get(3).setX(buttonWidth);
        options.get(3).setY(buttonHeight);
        options.get(3).setName("1 month");

        triviaQuestion = new TriviaQuestion(
                "How long does it take for a spacecraft to travel from Earth to the Moon?",
                options);
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

        for (TriviaOption option : this.triviaQuestion.getOptionButtons())
        {
            option.getBatch().begin();
            option.getBatch().draw(option.getTexture(), option.getX(), option.getY(), option.getWidth(), option.getHeight());
            option.getBatch().end();

            option.setButtonColor(Color.WHITE);

            game.getBatch().begin(); // Anything after begin() will be displayed
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(game.getFont(), option.getName(), Color.WHITE, option.getWidth(), Align.center, true);
//            game.getFont().draw(game.getBatch(), glyphLayout, button.getX() + (button.getWidth() / 2) - (glyphLayout.width / 2), button.getY() + (button.getHeight() / 2));
            game.getFont().draw(game.getBatch(), glyphLayout, option.getX(), option.getY() + (option.getHeight() / 2));
            game.getBatch().end(); // Anything after end() will NOT be displayed

            if (inputProcessor.mouseHoverOver(option.getBound())) {
                option.setButtonColor(Color.LIGHT_GRAY);
                if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                    //do something
                    if (option.getCorrect())
                    {
                        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
                        {
                            player.getObject().setCurrentHealth(player.getObject().getMaxHealth());
                        }
                        this.game.getResultScreen().setResultBG("correct_background.jpg");
                    }
                    else
                    {
                        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
                        {
                            player.getObject().setCurrentHealth(player.getObject().getCurrentHealth() - 10);
                        }
                        this.game.getResultScreen().setResultBG("wrong.jpg");
                    }
                    //go to result screen
                    game.setScreen(game.getResultScreen());
                }
            }
        }

        game.getBatch().begin(); // Anything after begin() will be displayed
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(game.getFont(), "this.triviaQuestion.getQuestion()\nthis.triviaQuestion.getQuestion()\nthis.triviaQuestion.getQuestion()\n", Color.WHITE, game.WIDTH, Align.center, true);
        game.getFont().draw(game.getBatch(), glyphLayout, 0, 400);
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
