package com.geometrydash.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeometryDashScreen implements Screen {
	private final GeometryDash game;
	private SpriteBatch spriteBatch;
	private Texture groundTexture;
	private Sprite textureSprite;

	public GeometryDashScreen(GeometryDash game) {
		this.game = game;
		create();
	}

	public void create() {
		groundTexture = new Texture(Gdx.files.internal("img.png"));
		spriteBatch = new SpriteBatch();
		textureSprite = new Sprite(groundTexture);
		textureSprite.setBounds(0, 0, 600, 300);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		textureSprite.draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		groundTexture.dispose();
		spriteBatch.dispose();
	}
}
