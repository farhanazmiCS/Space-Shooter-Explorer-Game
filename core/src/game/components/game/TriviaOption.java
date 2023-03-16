package game.components.game;

import game.components.menu.Button;

public class TriviaOption {
    private Boolean isCorrect;
    private Button button;

    public TriviaOption(Boolean isCorrect, Button button) {
        this.isCorrect = isCorrect;
        this.button = button;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
