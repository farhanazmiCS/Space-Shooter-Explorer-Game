package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Button extends Texture {
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private int width, height;
    private String imgPath;
    private float x, y;

    public Button(int width, int height, float x, float y, String imgPath) {
        super(imgPath);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

}
