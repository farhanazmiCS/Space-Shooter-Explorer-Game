package com.mygdx.game.engine.screen;

import com.mygdx.game.engine.Button;
import com.mygdx.game.engine.lifecycle.Main;

import java.util.ArrayList;

public class ScreenManager {
    private Main game;
    public ScreenManager(Main game) {
        this.game = game;
    }
    public void createScreenButtons(int numberOfButtons, ArrayList<Button> buttons, float distance) {
        int i;
        Button button;

        for (i = 0; i < numberOfButtons; i++) {
            if (i == 0) {
                button = new Button(150, 50, 325, game.HEIGHT - 200, game);
                buttons.add(button);
            }
            else {
                button = new Button(150, 50, 325, game.HEIGHT - 200 - distance * i, game);
                buttons.add(button);
            }
        }
    }
}
