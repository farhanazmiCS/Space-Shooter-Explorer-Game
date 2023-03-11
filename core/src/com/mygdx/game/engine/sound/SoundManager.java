package com.mygdx.game.engine.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager {

    // Background music tracks for different screens
    private static Music menuMusic;
    private static Music gameMusic;
    private static Music controlMusic;
    private static Music scoreMusic;
    private static Music pauseMusic;

    // Whether or not the music is currently playing
    private static boolean isPlaying = false;

    // Play the background music for the specified screen
    public static void playMusic(ScreenType screenType) {
        // Stop any currently playing music
        stopMusic();

        // Load the appropriate music track based on the screen type
        switch (screenType) {
            case MAIN_MENU:
                if (menuMusic == null) {
                    menuMusic = Gdx.audio.newMusic(Gdx.files.internal("game.wav"));
                    menuMusic.setLooping(true);
                }
                menuMusic.play();
                break;
            case GAME:
                if (gameMusic == null) {
                    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("game.wav"));
                    gameMusic.setLooping(true);
                }
                gameMusic.play();
                break;
            case CONTROL:
                if (controlMusic == null) {
                    controlMusic = Gdx.audio.newMusic(Gdx.files.internal("game.wav"));
                    controlMusic.setLooping(true);
                }
                controlMusic.play();
                break;
            case SCORE:
                if (scoreMusic == null) {
                    scoreMusic = Gdx.audio.newMusic(Gdx.files.internal("game.wav"));
                    scoreMusic.setLooping(true);
                }
                scoreMusic.play();
                break;
            case PAUSE:
                if (pauseMusic == null) {
                    pauseMusic = Gdx.audio.newMusic(Gdx.files.internal("game.wav"));
                    pauseMusic.setLooping(true);
                }
                pauseMusic.play();
                break;
        }

        // Set isPlaying to true
        isPlaying = true;
    }

    // Stop the currently playing music
    public static void stopMusic() {
        if (menuMusic != null) {
            menuMusic.stop();
        }
        if (gameMusic != null) {
            gameMusic.stop();
        }
        if (controlMusic != null) {
            controlMusic.stop();
        }
        if (scoreMusic != null) {
            scoreMusic.stop();
        }
        if (pauseMusic != null) {
            pauseMusic.stop();
        }

        // Set isPlaying to false
        isPlaying = false;
    }

    // Check if music is currently playing
    public static boolean isPlaying() {
        return isPlaying;
    }

    // Enum for different screen types
    public enum ScreenType {
        MAIN_MENU, GAME, CONTROL, SCORE, PAUSE
    }
}