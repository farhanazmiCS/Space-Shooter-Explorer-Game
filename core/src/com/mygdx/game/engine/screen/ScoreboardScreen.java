package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.input.CustomInputProcessor;

import java.util.ArrayList;

import game.components.menu.Button;
import game.components.game.Player;

public class ScoreboardScreen implements Screen{
    public CustomInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    private CustomInputProcessor inputProcessor;
    private Button backButton;

    private SpriteBatch batch;
    private Texture texture;
    private Main game;

    private Table table;

    private ArrayList<Player> playScores;

    public ScoreboardScreen(Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

        this.backButton = new Button(150, 66, 640, 20, "back_to_menu_button.png", game);
        table = new Table();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        SoundManager.playMusic(SoundManager.ScreenType.SCORE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        this.batch.begin();
        this.batch.draw(this.texture, 0, 0);

        // Draw the scoreboard table
        float tableX = 10;
        float tableY = 30;
        float tableWidth = Gdx.graphics.getWidth() - 2 * tableX;
        float tableHeight = Gdx.graphics.getHeight() - 2 * tableY;

        table.setBounds(tableX, tableY, tableWidth, tableHeight);

        // Add column headers
        //table.add("Player Name").width(100);
        //table.add("Score").width(100);
//        table.row();

        table.draw(batch, 1f);

        this.batch.end();

        backButton.getBatch().begin();
        backButton.getBatch().draw(backButton.getTexture(), 640, 20);
        backButton.getBatch().end();

        backButton.setButtonColor(Color.WHITE);

        // Back to Menu button logic
        if (inputProcessor.mouseHoverOver(backButton.getBound())) {
            backButton.setButtonColor(Color.LIGHT_GRAY);
            if (!backButton.isActive()) {
                backButton.setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                game.setScreen(game.getMainMenuScreen());
            }
        }
        else {
            backButton.setActive(false);
        }
    }

    public ArrayList<Player> getPlayScores() {
        return playScores;
    }

    public void setPlayScores(ArrayList<Player> playScores) {
        this.playScores = playScores;
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
        SoundManager.stopMusic();
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }


}
