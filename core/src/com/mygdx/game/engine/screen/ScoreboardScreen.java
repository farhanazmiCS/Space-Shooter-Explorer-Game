package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    private BitmapFont font;

    private Table table;

    private ArrayList<Player> playScores;

    private Main game;
    public ScoreboardScreen(Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        this.backButton = new Button(150, 66, 640, 20, "back_to_menu_button.png", game);
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
//        float tableX = 100;
//        float tableY = 300;
//        float tableWidth = Gdx.graphics.getWidth() - 2 * tableX;
//        float tableHeight = Gdx.graphics.getHeight() - 2 * tableY;

//        Table table = new Table();
//        table.setBounds(tableX, tableY, tableWidth, tableHeight);
//
//        // Add the column headers
//        Label nameLabel = new Label("Player Name", skin);
//        Label scoreLabel = new Label("Score", skin);
//
//        table.add(nameLabel).padRight(50);
//        table.add(scoreLabel);
//
//        table.row();
//
//        // Add the player data
//        for (int i = 0; i < playerNames.size(); i++) {
//            String playerName = playerNames.get(i);
//            int score = scores.get(i);
//
//            Label nameValue = new Label(playerName, skin);
//            Label scoreValue = new Label(String.valueOf(score), skin);
//
//            table.add(nameValue).padRight(50);
//            table.add(scoreValue);
//
//            table.row();
//        }
//
//        table.draw(batch, 1);

//        font.draw(batch, "Player\t\tScore", 100, Gdx.graphics.getHeight() - 100); // column headers
//
//        int y = Gdx.graphics.getHeight() - 150; // start printing player scores below headers
////        for (Player player : players) {
//            font.draw(batch,  "Player 1"+ "\t\t" + this.getPlayScores(), 100, y);
//            y -= 50; // space between each row
//       // }


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

    @Override
    public void resize(int width, int height) {

    }

    public ArrayList<Player> getPlayScores() {
        return playScores;
    }

    public void setPlayScores(ArrayList<Player> playScores) {
        this.playScores = playScores;
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
