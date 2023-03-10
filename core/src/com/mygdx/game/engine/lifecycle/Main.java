package com.mygdx.game.engine.lifecycle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.engine.TestEntity;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.engine.screen.GameScreen;
import com.mygdx.game.engine.screen.MainMenuScreen;
import com.mygdx.game.engine.screen.PauseScreen;
import com.mygdx.game.engine.screen.ScoreboardScreen;

public class Main extends Game {
	MainMenuScreen mainMenuScreen;
	GameScreen gameScreen;
	PauseScreen pauseScreen;
	ScoreboardScreen scoreboardScreen;
	public EntityManager entityManager;
	Texture img_test;

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont() {
		this.font = font;
	}

	private SpriteBatch batch;
	private BitmapFont font;

	TestEntity test;

	public final int HEIGHT = 480;
	public final int WIDTH = 800;

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}

	public void setPauseScreen(PauseScreen pauseScreen) {
		this.pauseScreen = pauseScreen;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

	public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
		this.mainMenuScreen = mainMenuScreen;
	}

	@Override
	public void create () {
		font = new BitmapFont();

		// Create Pause menu and main menu
		mainMenuScreen = new MainMenuScreen(this);
		pauseScreen = new PauseScreen(this);
		scoreboardScreen = new ScoreboardScreen(this);

		batch = new SpriteBatch();
		font.getData().setScale(1.5f);
		entityManager = new EntityManager();
		entityManager.setPlayer(WIDTH);
		this.setScreen(mainMenuScreen);
		// Create game screen
		gameScreen = new GameScreen(this);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		// img_test.dispose();
	}
}
