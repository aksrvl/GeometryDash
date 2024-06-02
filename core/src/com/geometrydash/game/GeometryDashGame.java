package com.geometrydash.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeometryDashGame extends Game {
	public static GeometryDashGame INSTANCE;
	private int widthScreen, heightScreen;
	public OrthographicCamera orthographicCamera;
	public SpriteBatch batch;

	public GeometryDashGame(){
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
