package game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import game.components.ui.Button;

import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.engine.sound.SoundManager;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.engine.input.CustomInputProcessor;

import game.logic.GamePlay;

public class GameScreen implements Screen {
    private Main game;
    private Button pauseButton;
    private OrthographicCamera camera;
    private CustomInputProcessor inputProcessor;
    private Viewport viewport;
    private GamePlay gamePlay;

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private SpriteBatch batch;

    public Button getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;
    }

    public CustomInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(CustomInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    public GameScreen(final Main game) {
        this.game = game;
        this.inputProcessor = new CustomInputProcessor();

        // Pause and resume button
        this.pauseButton = new Button(150, 66, 640, 420, "pause_button.png", game); // Pause button

        // Set Camera and Viewport
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
        this.viewport = new StretchViewport(game.WIDTH, game.HEIGHT, camera);

        // Game logic
        this.gamePlay = new GamePlay(this.game);

        // Spritebatch for the camera
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        SoundManager.playMusic(SoundManager.ScreenType.GAME);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        // tell the camera to update its matrices.
        camera.update();

        // tell the SpritegetBatch() to render in the
        // coordinate system specified by the camera.
        game.getBatch().setProjectionMatrix(camera.combined);

        // Render the game
        this.gamePlay.render();

        // Render the Pause Buttpn
        pauseButton.getBatch().begin();
        pauseButton.getBatch().draw(pauseButton.getTexture(), this.game.WIDTH - pauseButton.getWidth(), this.game.HEIGHT - pauseButton.getHeight());
        pauseButton.getBatch().end();
        pauseButton.setButtonColor(Color.WHITE);

        // Pause button logic
        if (inputProcessor.mouseHoverOver(pauseButton.getBound())) {
            pauseButton.setButtonColor(Color.LIGHT_GRAY);
            if (!pauseButton.isActive()) {
                pauseButton.setActive(true);
                this.game.getSoundManager().playButtonHover();
            }
            if (inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
                pause();
            }
        }
        else {
            pauseButton.setActive(false);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
        game.setScreen(game.getPauseScreen());
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        SoundManager.stopMusic();
    }

    public void quit() {
    }

    @Override
    public void dispose() {

    }
}
