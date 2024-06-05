package com.geometrydash.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GeometryDashGame extends Game {
	public static GeometryDashGame INSTANCE;
	private int widthScreen, heightScreen;
	public OrthographicCamera orthographicCamera;
	public SpriteBatch batch;
	private Music backgroundMusic;
	private Preferences preferences;

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

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/backgroundMusic.mp3"));
		backgroundMusic.setLooping(true);
		// Load volume settings
		preferences = Gdx.app.getPreferences("GeometryDashPreferences");
		float volume = preferences.getFloat("volume", 1.0f); // default volume is 1.0
		backgroundMusic.setVolume(volume);
		backgroundMusic.play();

		setScreen(new MenuGame(this));
	}
	public void setVolume(float volume) {
		backgroundMusic.setVolume(volume);
		preferences.putFloat("volume", volume);
		preferences.flush();
	}

	public float getVolume() {
		return backgroundMusic.getVolume();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void stopMusic() {
		if (backgroundMusic.isPlaying()) {
			backgroundMusic.stop();
		}
	}
}
