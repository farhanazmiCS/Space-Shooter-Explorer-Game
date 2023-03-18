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

import java.util.ArrayList;

import game.components.game.PlayerScore;
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

    private ArrayList<PlayerScore> playerScores;

    private StringBuilder scoreboardContent;

    public ScoreboardScreen(Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        texture = new Texture("main_menu_background_resized.png");
        batch = new SpriteBatch();
        this.backButton = new Button(150, 66, 640, 20, "back_to_menu_button.png", game);
        table = new Table();
        playerScores = new ArrayList<>();
        scoreboardContent = new StringBuilder("Date\t\tDistance Traveled\t\tAliens Killed\n");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        SoundManager.playMusic(SoundManager.ScreenType.SCORE);
        Preferences prefs = this.game.getPrefs();
        String data = prefs.getString("data");
        String[] data_split = data.split("\n");
        String header = String.format("| %-44s | %-20s | %15s |%n", "Date", "Distance Traveled", "Aliens Killed");
        scoreboardContent = new StringBuilder(header);
        playerScores = new ArrayList<>();
        for (String row : data_split)
        {
            String[] row_split = row.split(",");
            System.out.println(row_split[0].trim());
            System.out.println(row_split[1].trim());
            System.out.println(row_split[2].trim());
            PlayerScore score = new PlayerScore(row_split[0].trim(), Integer.parseInt(row_split[1].trim()), Integer.parseInt(row_split[2].trim()));

            scoreboardContent.append(score);

            playerScores.add(score);

//            for (String cell : row_split)
//            {
//                scoreboardContent.append(cell).append("\t\t");
//            }
        }
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

        //display score
        game.getBatch().begin(); // Anything after begin() will be displayed
        StringBuilder time_column_content = new StringBuilder("Score Board\nDate\n");
        StringBuilder distance_column_content = new StringBuilder("\nDistance Traveled\n");
        StringBuilder aliens_killed_column_content = new StringBuilder("\nAliens Killed\n");
        for (PlayerScore score : playerScores)
        {
            time_column_content.append(score.getStartTime()).append('\n');
            distance_column_content.append(score.getDistanceTravel()).append("km\n");
            aliens_killed_column_content.append(score.getAliensKilled()).append('\n');
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
