package game.components.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Asteroid {
    private Texture image;
    private float width;
    private float height;

    public Asteroid(String imgPath) {
        this.image = new Texture(imgPath);
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
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
}
