package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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

    public Player(String imgName, float speed, int[] mainKeyboardInputs, int[] altKeyboardInputs, int score) {
        this.imgName = imgName;
        this.sprite = new Sprite(new Texture(imgName));
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.speed = speed;
        this.mainKeyboardInputs = mainKeyboardInputs;
        this.altKeyboardInputs = altKeyboardInputs;
        this.score = score;
        lasers = new ArrayList<>();
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
}
