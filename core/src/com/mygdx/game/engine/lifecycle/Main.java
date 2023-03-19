package com.mygdx.game.engine.lifecycle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.engine.entity.EntityManager;
import game.screens.game.ControlScreen;
import game.screens.game.GameOverScreen;
import game.screens.game.GameScreen;
import game.screens.menu.MainMenuScreen;
import game.screens.menu.PauseScreen;
import game.screens.game.ResultScreen;
import game.screens.menu.ScoreboardScreen;
import com.mygdx.game.engine.screen.ScreenManager;
import game.screens.game.TriviaScreen;
import game.screens.game.StoryboardScreen;
import com.mygdx.game.engine.sound.SoundManager;

import java.util.ArrayList;

public class Main extends Game {
	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}

	private ScreenManager screenManager;
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private PauseScreen pauseScreen;
	private GameOverScreen gameOverScreen;
	private ScoreboardScreen scoreboardScreen;
	private TriviaScreen triviaScreen;
	private ResultScreen resultScreen;
	private Preferences prefs;

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}

	private SoundManager soundManager;

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

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	private SpriteBatch batch;
	private BitmapFont font;
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

	public GameOverScreen getGameOverScreen() {
		return gameOverScreen;
	}

	public void setGameOverScreen(GameOverScreen gameOverScreen) {
		this.gameOverScreen = gameOverScreen;
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

	public TriviaScreen getTriviaScreen() {
		return triviaScreen;
	}

	public void setTriviaScreen(TriviaScreen triviaScreen) {
		this.triviaScreen = triviaScreen;
	}

	public ResultScreen getResultScreen() {
		return resultScreen;
	}

	public void setResultScreen(ResultScreen resultScreen) {
		this.resultScreen = resultScreen;
	}

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
		entityManager.setPlayers(3, WIDTH);

		prefs = Gdx.app.getPreferences("Player Data");

		// Generating storyboards for the game
		storyboardImgPath = new ArrayList<String>();
		storyboardImgPath.add("1.jpg");
		storyboardImgPath.add("2.jpg");
		storyboardImgPath.add("3.jpg");
		storyboards = this.screenManager.generateStoryboards(storyboardImgPath);

		batch = new SpriteBatch();

		// Create screens
		mainMenuScreen = new MainMenuScreen(this);
		pauseScreen = new PauseScreen(this);
		gameOverScreen = new GameOverScreen(this);
		scoreboardScreen = new ScoreboardScreen(this);
		triviaScreen = new TriviaScreen(this);
		resultScreen = new ResultScreen(this);
		controlScreen = new ControlScreen(this, "controls.jpg");
		gameScreen = new GameScreen(this);

		this.setScreen(mainMenuScreen);
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
		mainMenuScreen.dispose();
		pauseScreen.dispose();
		gameScreen.dispose();
		gameOverScreen.dispose();
		scoreboardScreen.dispose();
		triviaScreen.dispose();
		resultScreen.dispose();
		controlScreen.dispose();

		// Dispose Sound manager
		soundManager.dispose();
	}
}
