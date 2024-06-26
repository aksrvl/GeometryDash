package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * MenuGame class represents the main menu screen of the Geometry Dash game.
 */
public class MenuGame implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    Texture backgroundTexture;
    Texture playButton;
    Texture settingsButton;
    Texture skinsButton;
    Texture exitButton;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 300;
    private static final int SETTINGS_BUTTON_WIDTH = 200;
    private static final int SETTINGS_BUTTON_HEIGHT = 200;
    private static final int SKINS_BUTTON_WIDTH = 200;
    private static final int SKINS_BUTTON_HEIGHT = 200;
    private static final int EXIT_BUTTON_WIDTH = 120;
    private static final int EXIT_BUTTON_HEIGHT = 50;

    /**
     * Constructs a new MenuGame.
     *
     * @param game the Geometry Dash game instance
     */
    public MenuGame(final GeometryDashGame game){
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        playButton = new Texture("playbutton.png");
        settingsButton = new Texture("settingsbutton.png");
        skinsButton = new Texture("skinbutton.png");
        exitButton = new Texture("exitbutton.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
    }

    @Override
    public void show() {
        // This method is called when the screen is shown
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int play_x = Gdx.graphics.getWidth() / 2 - PLAY_BUTTON_WIDTH / 2;
        int play_y = Gdx.graphics.getHeight() / 2 - PLAY_BUTTON_HEIGHT / 2;

        int settings_x = play_x - PLAY_BUTTON_WIDTH;
        int settings_y = play_y + 20;

        int skins_x = play_x + PLAY_BUTTON_WIDTH + 90;
        int skins_y = play_y + 20;

        int exit_x = 1770;
        int exit_y = 1000;

        int inputX = Gdx.input.getX();
        int inputY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (inputX < play_x + PLAY_BUTTON_WIDTH && inputX > play_x && inputY < play_y + PLAY_BUTTON_HEIGHT && inputY > play_y) {
            game.batch.draw(playButton, play_x - 10, play_y - 20, PLAY_BUTTON_WIDTH + 20, PLAY_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new LevelsScreen(game));
            }
        } else {
            game.batch.draw(playButton, play_x, play_y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        if (inputX < settings_x + SETTINGS_BUTTON_WIDTH && inputX > settings_x && inputY < settings_y + SETTINGS_BUTTON_HEIGHT && inputY > settings_y) {
            game.batch.draw(settingsButton, settings_x - 10, settings_y - 20, SETTINGS_BUTTON_WIDTH + 20, SETTINGS_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new SettingsScreen(game));
            }
        } else {
            game.batch.draw(settingsButton, settings_x, settings_y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }

        if (inputX < skins_x + SKINS_BUTTON_WIDTH && inputX > skins_x && inputY < skins_y + SKINS_BUTTON_HEIGHT && inputY > skins_y) {
            game.batch.draw(skinsButton, skins_x - 10, skins_y - 20, SKINS_BUTTON_WIDTH + 20, SKINS_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new SkinsScreen(game));
            }
        } else {
            game.batch.draw(skinsButton, skins_x, skins_y, SKINS_BUTTON_WIDTH, SKINS_BUTTON_HEIGHT);
        }

        if (inputX < exit_x + EXIT_BUTTON_WIDTH && inputX > exit_x && inputY < exit_y + EXIT_BUTTON_HEIGHT && inputY > exit_y) {
            game.batch.draw(exitButton, exit_x - 10, exit_y - 20, EXIT_BUTTON_WIDTH + 20, EXIT_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButton, exit_x, exit_y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        // This method is called when the game is paused
    }

    @Override
    public void resume() {
        // This method is called when the game is resumed
    }

    @Override
    public void hide() {
        // This method is called when the screen is hidden
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playButton.dispose();
        settingsButton.dispose();
        skinsButton.dispose();
        exitButton.dispose();
    }
}
