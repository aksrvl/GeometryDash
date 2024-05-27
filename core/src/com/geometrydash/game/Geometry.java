package com.geometrydash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Geometry extends ApplicationAdapter {
	public SpriteBatch spriteBatch;
	public Texture groundTexture;
	public Sprite textureSprite;
	
	@Override
	public void create () {
		groundTexture = new Texture(Gdx.files.internal("img.png"));
		spriteBatch = new SpriteBatch();
		textureSprite = new Sprite(groundTexture);
		textureSprite.setBounds(0,0,600, 300);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		textureSprite.draw(spriteBatch);
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		groundTexture.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		spriteBatch.getProjectionMatrix().setToOrtho2D(0,0, width, height);
	}
}
