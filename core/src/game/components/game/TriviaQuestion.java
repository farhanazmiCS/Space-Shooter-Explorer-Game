package game.components.game;

import java.util.ArrayList;

import game.components.menu.Button;

public class TriviaQuestion {
    private String question;
    private ArrayList<TriviaOption> optionButtons;

    public TriviaQuestion(String question, ArrayList<TriviaOption> optionButtons) {
        this.question = question;
        this.optionButtons = optionButtons;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<TriviaOption> getOptionButtons() {
        return optionButtons;
    }

    public void setOptionButtons(ArrayList<TriviaOption> optionButtons) {
        this.optionButtons = optionButtons;
    }
}
