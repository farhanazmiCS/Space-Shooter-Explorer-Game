package game.components.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.engine.behavior.BehaviourManager;
import com.mygdx.game.engine.collision.CollidableEntity;

import java.util.ArrayList;

public class UFO implements BehaviourManager<CollidableEntity> {
    private String imgPath;
    private String dir = "left";

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private float speed;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    private Texture texture;

    private ArrayList<CollidableEntity<Laser>> lasers;

    private Rectangle rectangle;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private int health;

    public UFO(String imgPath) {
        this.imgPath = imgPath;
        this.texture = new Texture(this.imgPath);
        this.speed = 200;
        this.health = 10;
        this.lasers = new ArrayList<CollidableEntity<Laser>>();
    }

    @Override
    public CollidableEntity moveFallingObject() {
        return null;
    }


    @Override
    public void moveUFO(CollidableEntity ufo, int speed, int width) {
        moveDown(ufo);
    }

    public void moveLeft(CollidableEntity ufo) {
        ufo.setX(ufo.getX() - this.speed * Gdx.graphics.getDeltaTime());
    }

    public void moveRight(CollidableEntity ufo) {
        ufo.setX(ufo.getX() + this.speed * Gdx.graphics.getDeltaTime());
    }

    public void moveDown(CollidableEntity ufo) {
        ufo.setY(ufo.getY() - this.speed * Gdx.graphics.getDeltaTime());
    }

    private long lastShotTime = 0;
    private long shotDelay = 500000000; // 1 second in nanoseconds

    @Override
    public long fireWeapon(CollidableEntity ufo) {
        long currentTime = TimeUtils.nanoTime();
        if (currentTime - lastShotTime > shotDelay) {
            CollidableEntity<Laser> laser = new CollidableEntity<Laser>(
                    ufo.getX() + 35,
                    ufo.getY(),
                    new Laser(
                            "purple_laser.png",
                            500));
            this.lasers.add(laser);
            lastShotTime = currentTime;
        }
        return currentTime;
    }

    public void moveLasers(CollidableEntity<UFO> ufo) {
        if (this.lasers.size() > 0) {
            for (int i = 0; i < this.lasers.size(); i++)
            {
                CollidableEntity<Laser> l = this.lasers.get(i);
                l.setY(l.getY() - (l.getObject().getSpeed() * Gdx.graphics.getDeltaTime()));
                this.lasers.set(i, l);
            }
        }
    }


    public ArrayList<CollidableEntity<Laser>> getLasers() {
        return lasers;
    }

}
