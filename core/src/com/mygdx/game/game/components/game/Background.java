package com.mygdx.game.game.components.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    private Texture texture;

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private SpriteBatch batch;
    public Background(String imgPath) {
        this.texture = new Texture(imgPath);
        this.batch = new SpriteBatch();
    }
}
