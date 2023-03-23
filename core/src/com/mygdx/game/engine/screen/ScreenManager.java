package com.mygdx.game.engine.screen;

import com.mygdx.game.game.components.ui.Button;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.game.screens.game.ControlScreen;
import com.mygdx.game.game.screens.game.GameOverScreen;
import com.mygdx.game.game.screens.game.GameScreen;
import com.mygdx.game.game.screens.game.ResultScreen;
import com.mygdx.game.game.screens.game.StoryboardScreen;
import com.mygdx.game.game.screens.menu.MainMenuScreen;
import com.mygdx.game.game.screens.menu.PauseScreen;
import com.mygdx.game.game.screens.menu.ScoreboardScreen;

import java.util.ArrayList;

public class ScreenManager {
    public ArrayList<String> getStoryboardImgPath() {
        return storyboardImgPath;
    }

    public void setStoryboardImgPath(ArrayList<String> storyboardImgPath) {
        this.storyboardImgPath = storyboardImgPath;
    }

    public ArrayList<String> getPlanetVisitImgPath() {
        return planetVisitImgPath;
    }

    public void setPlanetVisitImgPath(ArrayList<String> planetVisitImgPath) {
        this.planetVisitImgPath = planetVisitImgPath;
    }

    public ArrayList<StoryboardScreen> getStoryboards() {
        return storyboards;
    }

    public void setStoryboards(ArrayList<StoryboardScreen> storyboards) {
        this.storyboards = storyboards;
    }

    public ArrayList<StoryboardScreen> getVisitPlanetStoryboards() {
        return visitPlanetStoryboards;
    }

    public void setVisitPlanetStoryboards(ArrayList<StoryboardScreen> visitPlanetStoryboards) {
        this.visitPlanetStoryboards = visitPlanetStoryboards;
    }

    private ArrayList<String> storyboardImgPath;
    private ArrayList<String> planetVisitImgPath;
    private ArrayList<StoryboardScreen> storyboards;
    private ArrayList<StoryboardScreen> visitPlanetStoryboards;
    private MainMenuScreen mainMenuScreen;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;
    private ScoreboardScreen scoreboardScreen;
    private ControlScreen controlScreen;
    private GameScreen gameScreen;
    private Main game;
    public ScreenManager(Main game) {
        this.game = game;
    }

    public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
    }

    public void setPauseScreen(PauseScreen pauseScreen) {
        this.pauseScreen = pauseScreen;
    }

    public void setGameOverScreen(GameOverScreen gameOverScreen) {
        this.gameOverScreen = gameOverScreen;
    }

    public void setScoreboardScreen(ScoreboardScreen scoreboardScreen) {
        this.scoreboardScreen = scoreboardScreen;
    }

    public void setControlScreen(ControlScreen controlScreen) {
        this.controlScreen = controlScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void instantiateScreens() {
        // Create screens
        mainMenuScreen = new MainMenuScreen(this.game, this);
        pauseScreen = new PauseScreen(this.game, this);
        gameOverScreen = new GameOverScreen(this.game);
        scoreboardScreen = new ScoreboardScreen(this.game);
        controlScreen = new ControlScreen(this.game, "controls.jpg");
        gameScreen = new GameScreen(this.game);

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

        storyboards = this.generateStoryboards(storyboardImgPath, "Story");
        visitPlanetStoryboards = this.generateStoryboards(planetVisitImgPath, "Planet");
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public PauseScreen getPauseScreen() {
        return pauseScreen;
    }

    public GameOverScreen getGameOverScreen() {
        return gameOverScreen;
    }

    public ScoreboardScreen getScoreboardScreen() {
        return scoreboardScreen;
    }

    public ControlScreen getControlScreen() {
        return controlScreen;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void createScreenButtons(int numberOfButtons, ArrayList<Button> buttons, float distance, ArrayList<String> imgPath) {
        int i;
        Button button;

        for (i = 0; i < numberOfButtons; i++) {
            if (i == 0) {
                button = new Button(150, 66, 325, game.HEIGHT - 184, imgPath.get(i), game);
                buttons.add(button);
            }
            else {
                button = new Button(150, 66, 325, game.HEIGHT - 184 - distance * i, imgPath.get(i), game);
                buttons.add(button);
            }
        }
    }
    public ArrayList<StoryboardScreen> generateStoryboards(ArrayList<String> imgPaths, String type) {
        ArrayList<StoryboardScreen> storyboards = new ArrayList<StoryboardScreen>();
        for (int i = 0; i < imgPaths.size(); i++) {
            StoryboardScreen storyboard = new StoryboardScreen(this.game, imgPaths.get(i), type);
            storyboard.setCurrent(i);
            storyboards.add(storyboard);
        }
        return storyboards;
    }
}
