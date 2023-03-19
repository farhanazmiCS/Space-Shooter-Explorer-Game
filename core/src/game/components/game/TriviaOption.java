package game.components.game;

import com.mygdx.game.engine.lifecycle.Main;

import game.components.ui.Button;

public class TriviaOption extends Button{
    private Boolean isCorrect;

    public TriviaOption(int width, int height, float x, float y, String imgPath, Main game, String name, Boolean isCorrect) {
        super(width, height, x, y, imgPath, game, name);
        this.isCorrect = isCorrect;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
