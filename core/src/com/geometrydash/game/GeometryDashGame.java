package com.geometrydash.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The main game class responsible for managing screens, rendering, and game resources.
 */
public class GeometryDashGame extends Game {
	/** The singleton instance of the game. */
	public static GeometryDashGame INSTANCE;

	/** The width of the screen. */
	private int widthScreen;

	/** The height of the screen. */
	private int heightScreen;

	/** The Orthographic camera used for rendering. */
	public OrthographicCamera orthographicCamera;

	/** The SpriteBatch used for rendering graphics. */
	public SpriteBatch batch;

	/** The background music played in the game. */
	public Music backgroundMusic;

	/** The preferences used for storing game settings. */
	private Preferences preferences;

	/**
	 * Creates a new GeometryDashGame instance.
	 */
	public GeometryDashGame() {
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

	/**
	 * Sets the volume of the background music.
	 *
	 * @param volume the volume level (0.0 to 1.0)
	 */
	public void setVolume(float volume) {
		backgroundMusic.setVolume(volume);
		preferences.putFloat("volume", volume);
		preferences.flush();
	}

	/**
	 * Gets the current volume of the background music.
	 *
	 * @return the current volume level
	 */
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

	/**
	 * Stops the background music if it's playing.
	 */
	public void stopMusic() {
		if (backgroundMusic.isPlaying()) {
			backgroundMusic.stop();
		}
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	/**
	 * Plays new music in the game.
	 *
	 * @param musicFile the file path of the new music
	 */
	public void playNewMusic(String musicFile) {
		// Stop the current music
		stopMusic();
		// Load and play the new music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(getVolume());
		backgroundMusic.play();
	}
}
