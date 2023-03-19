package game.components.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.engine.lifecycle.Main;

public class Button {
    private Main game;
    private Rectangle rectangle;

    private float x;
    private float y;
    private float width;
    private float height;
    private String name;

    private String imgPath;

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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    private boolean visibility;

    private Sprite sprite;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active;

    public Button(int width, int height, float x, float y, String imgPath, Main game) {
        this.game = game;
        this.rectangle = new Rectangle(x, game.HEIGHT - height - y, width, height);
        this.texture = new Texture(imgPath);
        this.sprite = new Sprite(this.texture);
        this.batch = new SpriteBatch();
        this.visibility = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = false;
    }

    public Button(int width, int height, float x, float y, String imgPath, Main game, String name) {
        this(width, height, x, y, imgPath, game);
        this.name = name;
    }

    public void setButtonColor(Color color) {
        this.batch.begin();
        this.batch.setColor(color);
        this.batch.end();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        this.rectangle = new Rectangle(x, game.HEIGHT - height - y, width, height);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.rectangle = new Rectangle(x, game.HEIGHT - height - y, width, height);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rectangle getBound() {
        return rectangle;
    }
}
