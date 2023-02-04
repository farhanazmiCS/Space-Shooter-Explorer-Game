package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TestEntity extends Rectangle {
    private Vector2 touchPos;
    private Sprite sprite;
    public TestEntity(float x, float y, Texture img) {
        super();
        this.x = x;
        this.y = y;
        this.touchPos = new Vector2(x, y);
        this.sprite = new Sprite(img);
    }

    public void Draw(SpriteBatch batch) {
        sprite.setPosition(touchPos.x, touchPos.y);
        sprite.draw(batch);
    }

    public void Render(SpriteBatch batch) {
        batch.begin();
        this.Movement();
        this.Draw(batch);
        batch.end();
    }

    public void Movement() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());
            this.x = touchPos.x - 100 / 2;
            this.y = 480 - 100 / 2 - touchPos.y;
            touchPos.x = this.x;
            touchPos.y = this.y;
        }
    }
}
