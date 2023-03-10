package game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    public UFO(String imgPath) {
        this.imgPath = imgPath;
        this.texture = new Texture(this.imgPath);
        this.speed = 200;
    }

    @Override
    public CollidableEntity moveFallingObject() {
        return null;
    }


    @Override
    public void moveUFO(CollidableEntity ufo, CollidableEntity player, int speed) {
        if (dir == "left") {
            moveLeft(ufo, player);
        }
        if (dir == "right") {
            moveRight(ufo, player);
        }
        if (ufo.getX() <= 0) {
            dir = "right";
        }
        if (ufo.getX() >= 300) {
            dir = "left";
        }
    }

    public void moveLeft(CollidableEntity ufo, CollidableEntity player) {
        ufo.setX(ufo.getX() - this.speed * Gdx.graphics.getDeltaTime());
    }

    public void moveRight(CollidableEntity ufo, CollidableEntity player) {
        ufo.setX(ufo.getX() + this.speed * Gdx.graphics.getDeltaTime());
    }

    @Override
    public long fireWeapon(CollidableEntity ufo) {
            CollidableEntity<Laser> laser = new CollidableEntity<Laser>(
                    ufo.getX(),
                    ufo.getY(),
                    new Laser(
                            "laser.png", //<a href="https://www.flaticon.com/free-icons/laser" title="laser icons">Laser icons created by Freepik - Flaticon</a>
                            200));
            this.lasers.add(laser);
            return TimeUtils.nanoTime();
    }

    public ArrayList<CollidableEntity<Laser>> getLasers() {
        return lasers;
    }

}
