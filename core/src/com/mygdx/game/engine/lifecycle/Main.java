package com.mygdx.game.engine.lifecycle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.engine.screen.ScreenManager;
import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.game.screens.game.ControlScreen;
import com.mygdx.game.game.screens.game.GameOverScreen;
import com.mygdx.game.game.screens.game.GameScreen;
import com.mygdx.game.game.screens.menu.MainMenuScreen;
import com.mygdx.game.game.screens.menu.PauseScreen;
import com.mygdx.game.game.screens.game.ResultScreen;
import com.mygdx.game.game.screens.menu.ScoreboardScreen;
import com.mygdx.game.game.screens.game.TriviaScreen;
import com.mygdx.game.game.screens.game.StoryboardScreen;

import java.util.ArrayList;

public class Main extends Game {
	private static Main instance = null;

	/*
		- In order to satisfy the singleton principle, this class cannot be instantiated from outside this class.
		- Instead, it can check if the Main instance is null, and if it is, the Main class will create a new Main instance.
		- Thus, the constructor is private.
	 */
	private Main() { }

	/*
		A static method to get the instance. If instance is null (No Main instance present),
		create a new Main instance.
	*/
	public static Main getInstance() {
		if (instance == null) {
			instance = new Main();
		}
		return instance;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}

	private ScreenManager screenManager;

	private Preferences prefs;

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}

	private SoundManager soundManager;

	public EntityManager entityManager;

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	private SpriteBatch batch;
	private BitmapFont font;
	public final int HEIGHT = 480;
	public final int WIDTH = 800;

	public Preferences getPrefs() {
		return prefs;
	}

	public void setPrefs(Preferences prefs) {
		this.prefs = prefs;
	}

	@Override
	public void create () {
		// BitmapFont for drawing text
		font = new BitmapFont();
		font.getData().setScale(1.5f);

		// Create Screen, Sound and Entity Managers
		screenManager = new ScreenManager(this);
		soundManager = new SoundManager();
		entityManager = new EntityManager(this);

		// Set players (Single Player game)
		entityManager.setPlayers(1, WIDTH);

		prefs = Gdx.app.getPreferences("Player Data");
		batch = new SpriteBatch();

		screenManager.instantiateScreens();
		this.setScreen(screenManager.getMainMenuScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		// Dispose batch
		batch.dispose();

		// Dispose screens
		screenManager.getMainMenuScreen().dispose();
		screenManager.getPauseScreen().dispose();
		screenManager.getGameScreen().dispose();
		screenManager.getGameOverScreen().dispose();
		screenManager.getScoreboardScreen().dispose();
		screenManager.getTriviaScreen().dispose();
		screenManager.getResultScreen().dispose();
		screenManager.getControlScreen().dispose();

		// Dispose Sound manager
		soundManager.dispose();
	}
}
