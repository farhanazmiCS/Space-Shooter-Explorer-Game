package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.input.CustomInputProcessor;

import java.util.*;
import java.util.stream.Collectors;

import game.components.game.PlayerScore;
import game.components.menu.Button;

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

    private ArrayList<PlayerScore> playerScores;

    public ScoreboardScreen(Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();
        this.backButton = new Button(150, 66, 640, 20, "back_to_menu_button.png", game);
        table = new Table();
        playerScores = new ArrayList<>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        SoundManager.playMusic(SoundManager.ScreenType.SCORE);
        Preferences prefs = this.game.getPrefs();
        String data = prefs.getString("data");
        String[] data_split = data.split("\n");
        playerScores = new ArrayList<>();
        if (!data.equals(""))
        {
            for (String row : data_split)
            {
                String[] row_split = row.split(",");
                PlayerScore score = new PlayerScore(row_split[0].trim(), Integer.parseInt(row_split[1].trim()), Integer.parseInt(row_split[2].trim()));

                playerScores.add(score);
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        this.batch.begin();
        this.batch.draw(this.texture, 0, 0);
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
        //display score
        game.getBatch().begin(); // Anything after begin() will be displayed
        StringBuilder time_column_content = new StringBuilder("Score Board\nDate\n");
        StringBuilder distance_column_content = new StringBuilder("\nDistance Traveled\n");
        StringBuilder aliens_killed_column_content = new StringBuilder("\nAliens Killed\n");
        Collections.sort(playerScores);
        if (playerScores.size() > 15)
        {
            for (PlayerScore score : playerScores.subList(0, 15))
            {
                time_column_content.append(score.getStartTime()).append('\n');
                distance_column_content.append(score.getDistanceTravelled()).append("km\n");
                aliens_killed_column_content.append(score.getAliensKilled()).append('\n');
            }
        }
        else
        {
            if (playerScores.size() > 0)
            {
                for (PlayerScore score : playerScores)
                {
                    time_column_content.append(score.getStartTime()).append('\n');
                    distance_column_content.append(score.getDistanceTravelled()).append("km\n");
                    aliens_killed_column_content.append(score.getAliensKilled()).append('\n');
                }
            }
            else
            {
                time_column_content.append("Play the game to get a score on the board");
            }
        }
        GlyphLayout time_column = new GlyphLayout();
        time_column.setText(game.getFont(), time_column_content, Color.WHITE, game.WIDTH, Align.left, true);
        GlyphLayout distance_column = new GlyphLayout();
        distance_column.setText(game.getFont(), distance_column_content, Color.WHITE, game.WIDTH, Align.left, true);
        GlyphLayout aliens_killed_column = new GlyphLayout();
        aliens_killed_column.setText(game.getFont(), aliens_killed_column_content, Color.WHITE, game.WIDTH, Align.left, true);
        game.getFont().draw(game.getBatch(), time_column, 0, this.game.HEIGHT);
        game.getFont().draw(game.getBatch(), distance_column, 300, this.game.HEIGHT);
        game.getFont().draw(game.getBatch(), aliens_killed_column, 500, this.game.HEIGHT);
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
