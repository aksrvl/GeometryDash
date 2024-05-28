package com.geometrydash.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeometryDashScreen extends Game {
	public static GeometryDashScreen INSTANCE;
	private int widthScreen, heightScreen;
	public OrthographicCamera orthographicCamera;
	public SpriteBatch batch;

	public GeometryDashScreen(){
		INSTANCE = this;
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		setScreen(new MenuGame(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
