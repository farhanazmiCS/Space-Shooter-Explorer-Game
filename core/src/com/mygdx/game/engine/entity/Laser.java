package com.mygdx.game.engine.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Laser {
    private String imgName;
    private Sprite sprite;
    private float width;
    private float height;
    private float speed;

    public Laser(String imgName, float speed) {
        this.imgName = imgName;
        this.sprite = new Sprite(new Texture(imgName));
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.speed = speed;
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
}
