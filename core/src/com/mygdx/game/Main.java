package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entity.CollidableEntity;
import com.mygdx.game.entity.EntityManager;
import com.mygdx.game.entity.Player;
import com.mygdx.game.screen.MainMenuScreen;

public class Main extends Game {
	public EntityManager entityManager;
	Texture img_test;

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont() {
		this.font = font;
	}

	private SpriteBatch batch;
	private BitmapFont font;

	TestEntity test;

	public final int HEIGHT = 480;
	public final int WIDTH = 800;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		entityManager = new EntityManager();
		entityManager.setPlayer(new CollidableEntity<Player>(
				WIDTH / 2 - 64 / 2,
				20,
				new Player(
						"spaceship.png", //<a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
						200,
						new int[]{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
						new int[]{Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S})));
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		// img_test.dispose();
	}
}
