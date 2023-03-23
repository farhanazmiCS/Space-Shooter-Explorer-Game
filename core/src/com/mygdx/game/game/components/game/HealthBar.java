package com.mygdx.game.game.components.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.engine.collision.CollidableEntity;

import java.util.ArrayList;

import com.mygdx.game.game.components.game.player.Player;

public class HealthBar {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private int startingX;
    private int startingY;
    private int width;
    private int height;

    public HealthBar(int startingX, int startingY, int width, int height) {
        this.startingX = startingX;
        this.startingY = startingY;
        this.width = width;
        this.height = height;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }

    public void drawHealthBars(ArrayList<CollidableEntity<Player>> players)
    {


        for (int i = 0; i < players.size(); i++)
        {
            batch.begin();
            font.draw(batch, "Hull Integrity: Player " + (i + 1), startingX, startingY - (i * (height + 15)));
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            CollidableEntity<Player> player = players.get(i);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(startingX,startingY - (i * (height + 15)),width,height);
            float current_health = player.getObject().getCurrentHealth();
            float max_health = player.getObject().getMaxHealth();
            float health_percentage = (current_health / max_health);
            if (current_health <= max_health && current_health > max_health / 2)
            {
                shapeRenderer.setColor(Color.GREEN);
            }
            else if (current_health <= max_health / 2 && current_health > max_health / 3)
            {
                shapeRenderer.setColor(Color.ORANGE);
            }
            else if (current_health <= max_health / 3)
            {
                shapeRenderer.setColor(Color.RED);
            }
            shapeRenderer.rect(startingX,startingY - (i * (height + 15)),(width * health_percentage),height);
            shapeRenderer.end();
        }


    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public int getStartingX() {
        return startingX;
    }

    public void setStartingX(int startingX) {
        this.startingX = startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    public void setStartingY(int startingY) {
        this.startingY = startingY;
    }

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
}
