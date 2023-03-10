package game.components;

import com.badlogic.gdx.graphics.Texture;

public class FallingObject {
    private int type;
    private Texture image;
    private float width;
    private float height;

    public FallingObject(int type, Texture image) {
        this.type = type;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
