package com.mygdx.game.game.components.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.input.CustomInputProcessor;
import com.mygdx.game.engine.lifecycle.Main;
import com.mygdx.game.game.components.game.Laser;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Player {
    private String imgName;
    private Sprite sprite;
    private float width;
    private float height;
    private float speed;
    private int[] mainKeyboardInputs; // Defines default player controls
    private int[] altKeyboardInputs; // Defines alternate player controls
    private int score;
    private ArrayList<CollidableEntity<Laser>> lasers;
    private int maxHealth;
    private int currentHealth;
    private int aliensKilled;
    private Timestamp startTime;

    public Afterburner getAfterburner() {
        return afterburner;
    }

    public void setAfterburner(Afterburner afterburner) {
        this.afterburner = afterburner;
    }

    private Afterburner afterburner;

    public Player(String imgName, float speed, int[] mainKeyboardInputs, int[] altKeyboardInputs, int score, int health) {
        this.imgName = imgName;
        this.sprite = new Sprite(new Texture(imgName));
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.speed = speed;
        this.mainKeyboardInputs = mainKeyboardInputs;
        this.altKeyboardInputs = altKeyboardInputs;
        this.score = score;
        lasers = new ArrayList<>();
        this.afterburner = new Afterburner("afterburner.png");
        this.maxHealth = health;
        this.currentHealth = health;
        aliensKilled = 0;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int[] getMainKeyboardInputs() {
        return mainKeyboardInputs;
    }

    public void setMainKeyboardInputs(int[] mainKeyboardInputs) {
        this.mainKeyboardInputs = mainKeyboardInputs;
    }

    public int[] getAltKeyboardInputs() {
        return altKeyboardInputs;
    }

    public void setAltKeyboardInputs(int[] altKeyboardInputs) {
        this.altKeyboardInputs = altKeyboardInputs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<CollidableEntity<Laser>> getLasers() {
        return lasers;
    }

    public void setLasers(ArrayList<CollidableEntity<Laser>> lasers) {
        this.lasers = lasers;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void limitPlayerMovement(CollidableEntity<Player> player, Integer screenWidth, Integer screenHeight) {
        if(player.getX() < 0)
            player.setX(0);
        if(player.getX() > screenWidth - player.getObject().getWidth())
            player.setX(screenWidth - player.getObject().getWidth());
        if(player.getY() < 0)
            player.setY(0);
        if(player.getY() > screenHeight/2 - player.getObject().getHeight())
            player.setY(screenHeight/2 - player.getObject().getHeight());
    }

    public int movePlayer(CollidableEntity<Player> player, CustomInputProcessor inputProcessor) {
        int move_result = -1;
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[0]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[0])) {
            player.setX(player.getX() - (player.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
            move_result = 0;
        }
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[1]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[1])) {
            player.setX(player.getX() + (player.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
            move_result = 1;
        }
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[2]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[2])) {
            player.setY(player.getY() + (player.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
            move_result = 2;
        }
        if (inputProcessor.keyDown(player.getObject().getMainKeyboardInputs()[3]) ||
                inputProcessor.keyDown(player.getObject().getAltKeyboardInputs()[3])) {
            player.setY(player.getY() - (player.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
            move_result = 3;
        }
        return move_result;
    }

    public int getAliensKilled() {
        return aliensKilled;
    }

    public void setAliensKilled(int aliensKilled) {
        this.aliensKilled = aliensKilled;
    }

    public void moveLasers(CollidableEntity<Player> player) {
        ArrayList<CollidableEntity<Laser>> lasers = player.getObject().getLasers();
        for (int i = 0; i < lasers.size(); i++) {
            CollidableEntity<Laser> laser = lasers.get(i);
            laser.setY(laser.getY() + (laser.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
            lasers.set(i, laser);
        }
    }

    public void spawnLasers(CustomInputProcessor inputProcessor, CollidableEntity<Player> player, Main game) {
        if (inputProcessor.keyDown(Input.Keys.SPACE) || inputProcessor.mouseClicked(Input.Buttons.LEFT)) {
            ArrayList<CollidableEntity<Laser>> lasers = player.getObject().getLasers();
            CollidableEntity<Laser> laser = new CollidableEntity<>(
                    player.getX() + 15,
                    player.getY(),
                    new Laser(
                            "green_laser.png",
                            800));
            lasers.add(laser);
            game.getSoundManager().playLaserSound();
        }
    }

}
