package game.components.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.engine.lifecycle.Main;

public class Button {
    private Main game;
    private Rectangle rectangle;

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

    public Button(int width, int height, float x, float y, String imgPath, Main game) {
        this.game = game;
        this.rectangle = new Rectangle(x, game.HEIGHT - height - y, width, height);
        this.texture = new Texture(imgPath);
        this.sprite = new Sprite(this.texture);
        this.batch = new SpriteBatch();
        this.visibility = false;
    }

    public void setButtonColor(Color color) {
        this.batch.begin();
        this.batch.setColor(color);
        this.batch.end();
    }

    public Rectangle getBound() {
        return rectangle;
    }
}
