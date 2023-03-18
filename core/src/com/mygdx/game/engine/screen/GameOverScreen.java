package com.mygdx.game.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.sound.SoundManager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import game.components.game.Player;
import game.components.menu.Button;

public class GameOverScreen extends ScreenManager implements Screen {
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
    public GameOverScreen(Main game) {
        super(game);
        this.game = game;

        texture = new Texture("game_over.jpg");
        batch = new SpriteBatch();

        this.inputProcessor = new CustomInputProcessor();

        buttons = new ArrayList<Button>();
        buttonPath = new ArrayList<String>();
        buttonPath.add("quit_button.png");
        createScreenButtons(1, buttons, 75, buttonPath);
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

        if (inputProcessor.mouseHoverOver(buttons.get(0).getBound())) {
            buttons.get(0).setButtonColor(Color.LIGHT_GRAY);
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
        texture.dispose();
        batch.dispose();
    }

    public void quit() {
        // save score here
        Preferences prefs = this.game.getPrefs();
        StringBuilder data = new StringBuilder(prefs.getString("data"));
        SimpleDateFormat sdf3 = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");
        for (CollidableEntity<Player> player : this.game.entityManager.getPlayers())
        {
            String startTime = sdf3.format(player.getObject().getStartTime());
            int distanceTravelled = player.getObject().getScore();
            int aliensKilled = player.getObject().getAliensKilled();
            data.append(startTime).append(", ").append(distanceTravelled).append(", ").append(aliensKilled).append("\n");
        }
        prefs.putString("data", data.toString());
        System.out.println(data);
        prefs.flush();
        this.game.entityManager = new EntityManager();
        this.game.entityManager.setPlayers(1, this.game.WIDTH);
        this.game.entityManager.resetFailingObjects();
        this.game.setGameScreen(new GameScreen(this.game));
        this.game.setStoryboards(this.game.getScreenManager().generateStoryboards(this.game.getStoryboardImgPath()));
        this.game.setControlScreen(new ControlScreen(this.game, "controls.jpg"));
        game.setScreen(game.getMainMenuScreen());
    }
}
