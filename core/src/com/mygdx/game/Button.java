package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor {
    private Main game;
    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    private ShapeRenderer shapeRenderer;

    private Rectangle rectangle;

    public Button(int width, int height, float x, float y, Main game) {
        this.game = game;
        this.rectangle = new Rectangle(x, game.HEIGHT - height - y, width, height);
        this.shapeRenderer = new ShapeRenderer();
    }

    public Rectangle getBound() {
        return rectangle;
    }

}
