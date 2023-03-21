package game.components.planets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.engine.behavior.BehaviourManager;
import com.mygdx.game.engine.collision.CollidableEntity;
import com.mygdx.game.engine.lifecycle.Main;

import game.components.game.enemy.Asteroid;

public class Planet implements BehaviourManager {
    private String planet;
    private Texture texture;
    private SpriteBatch batch;
    private Sprite sprite;
    private float width;
    private float height;

    public Planet(String planet, String imgPath) {
        this.planet = planet;
        this.texture = new Texture(imgPath);
        this.batch = new SpriteBatch();
        this.sprite = new Sprite(this.texture);
    }

    @Override
    public void moveUFO(CollidableEntity ufo, int speed, int width) {

    }

    @Override
    public long fireWeapon(CollidableEntity ufo, Main game) {
        return 0;
    }

    @Override
    public void dropAsteroid(CollidableEntity<Asteroid> asteroid) {

    }

    @Override
    public void dropPlanet(CollidableEntity<Planet> planet) {
        planet.setY(planet.getY() - 50 * Gdx.graphics.getDeltaTime());
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

}
