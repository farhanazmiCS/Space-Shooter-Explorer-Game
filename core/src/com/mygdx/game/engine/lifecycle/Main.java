package com.mygdx.game.engine.lifecycle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.engine.TestEntity;
import com.mygdx.game.engine.entity.EntityManager;
import com.mygdx.game.engine.screen.ControlScreen;
import com.mygdx.game.engine.screen.GameScreen;
import com.mygdx.game.engine.screen.MainMenuScreen;
import com.mygdx.game.engine.screen.PauseScreen;
import com.mygdx.game.engine.screen.ScoreboardScreen;
import com.mygdx.game.engine.screen.ScreenManager;
import com.mygdx.game.engine.screen.storyboard.StoryboardScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends Game {
	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}

	ScreenManager screenManager;
	MainMenuScreen mainMenuScreen;
	GameScreen gameScreen;
	PauseScreen pauseScreen;
	ScoreboardScreen scoreboardScreen;

	public ControlScreen getControlScreen() {
		return controlScreen;
	}

	public void setControlScreen(ControlScreen controlScreen) {
		this.controlScreen = controlScreen;
	}

	ControlScreen controlScreen;

	public ArrayList<StoryboardScreen> getStoryboards() {
		return storyboards;
	}

	public void setStoryboards(ArrayList<StoryboardScreen> storyboards) {
		this.storyboards = storyboards;
	}

	ArrayList<StoryboardScreen> storyboards;

	public ArrayList<String> getStoryboardImgPath() {
		return storyboardImgPath;
	}

	public void setStoryboardImgPath(ArrayList<String> storyboardImgPath) {
		this.storyboardImgPath = storyboardImgPath;
	}

	ArrayList<String> storyboardImgPath;
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

	public ScoreboardScreen getScoreboardScreen() {
		return scoreboardScreen;
	}

	public void setScoreboardScreen(ScoreboardScreen scoreboardScreen) {
		this.scoreboardScreen = scoreboardScreen;
	}

	@Override
	public void create () {
		font = new BitmapFont();

		// Create new screen/scene manager
		screenManager = new ScreenManager(this);

		// Create Pause menu, main menu and scoreboard menu
		mainMenuScreen = new MainMenuScreen(this);
		pauseScreen = new PauseScreen(this);
		scoreboardScreen = new ScoreboardScreen(this);

		storyboardImgPath = new ArrayList<String>();

		storyboardImgPath.add("1.jpg");
		storyboardImgPath.add("2.jpg");
		storyboardImgPath.add("3.jpg");

		storyboards = this.screenManager.generateStoryboards(storyboardImgPath);

		controlScreen = new ControlScreen(this, "controls.jpg");

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
