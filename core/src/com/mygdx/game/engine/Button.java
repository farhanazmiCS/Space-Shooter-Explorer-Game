package com.mygdx.game.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.engine.lifecycle.Main;

public class Button {
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

    public void setButtonColor(Color color) {
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(color);
        this.shapeRenderer.end();
    }

    public Color getButtonColor(Color color) {
        return this.shapeRenderer.getColor();
    }

    public Rectangle getBound() {
        return rectangle;
    }
}
