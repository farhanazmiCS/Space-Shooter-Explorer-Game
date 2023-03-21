package com.mygdx.game.engine.screen;

import game.components.ui.Button;
import com.mygdx.game.engine.lifecycle.Main;
import game.screens.game.StoryboardScreen;

import java.util.ArrayList;

public class ScreenManager {
    private Main game;
    public ScreenManager(Main game) {
        this.game = game;
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
