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
	ArrayList<StoryboardScreen> visitPlanetStoryboards;

	public ArrayList<String> getStoryboardImgPath() {
		return storyboardImgPath;
	}

	public void setStoryboardImgPath(ArrayList<String> storyboardImgPath) {
		this.storyboardImgPath = storyboardImgPath;
	}

	ArrayList<String> storyboardImgPath;
	ArrayList<String> planetVisitImgPath;
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

	public ArrayList<StoryboardScreen> getVisitPlanetStoryboards() {
		return visitPlanetStoryboards;
	}

	public void setVisitPlanetStoryboards(ArrayList<StoryboardScreen> visitPlanetStoryboards) {
		this.visitPlanetStoryboards = visitPlanetStoryboards;
	}

	public ArrayList<String> getPlanetVisitImgPath() {
		return planetVisitImgPath;
	}

	public void setPlanetVisitImgPath(ArrayList<String> planetVisitImgPath) {
		this.planetVisitImgPath = planetVisitImgPath;
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

		// Generating storyboards for the game
		storyboardImgPath = new ArrayList<String>();
		storyboardImgPath.add("1.jpg");
		storyboardImgPath.add("2.jpg");
		storyboardImgPath.add("3.jpg");
		storyboardImgPath.add("Demo.jpg");

		planetVisitImgPath = new ArrayList<String>();
		planetVisitImgPath.add("Mercury_Visit.jpg");
		planetVisitImgPath.add("Venus_Visit.jpg");
		planetVisitImgPath.add("Mars_Visit.jpg");
		planetVisitImgPath.add("Jupiter_Visit.jpg");
		planetVisitImgPath.add("Saturn_Visit.jpg");
		planetVisitImgPath.add("Neptune_Visit.jpg");
		planetVisitImgPath.add("Uranus_Visit.jpg");

		storyboards = this.screenManager.generateStoryboards(storyboardImgPath, "Story");
		visitPlanetStoryboards = this.screenManager.generateStoryboards(planetVisitImgPath, "Planet");

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
