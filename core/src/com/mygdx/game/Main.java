package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends Game {
	Texture img_test;

	SpriteBatch test_batch;

	TestEntity test;

	public final int HEIGHT = 480;
	public final int WIDTH = 800;
	
	@Override
	public void create () {
		test_batch = new SpriteBatch();
		img_test = new Texture("orange_ball.png");
		test = new TestEntity(0, 0, img_test);
	}

	@Override
	public void render () {
		super.render();
		ScreenUtils.clear(255, 255, 255, 255);
		test.Render(test_batch);
	}
	
	@Override
	public void dispose () {
		test_batch.dispose();
		img_test.dispose();
	}
}
