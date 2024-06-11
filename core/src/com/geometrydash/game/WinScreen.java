package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * The WinScreen class implements the Screen interface to display the win screen
 * in the Geometry Dash game. It shows a background, an exit button, and the
 * number of attempts taken to win the level.
 */
public class WinScreen implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    Texture backgroundTexture;
    Texture exitButton;
    private static final int EXIT_BUTTON_WIDTH = 120;
    private static final int EXIT_BUTTON_HEIGHT = 50;
    BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    /**
     * Constructs a new WinScreen.
     *
     * @param game the Geometry Dash game instance
     */
    public WinScreen(GeometryDashGame game) {
        this.game = game;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        exitButton = new Texture("exitbutton.png");
        backgroundTexture = new Texture(Gdx.files.internal("wonbg.png"));

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("01198_AgentMedDBNormal.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 140;
        fontParameter.borderWidth = 4;
        fontParameter.borderColor = Color.WHITE;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.
    }

    /**
     * Renders the screen.
     *
     * @param v the time in seconds since the last render
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        int exit_x = 1770;
        int exit_y = 1000;

        int inputX = Gdx.input.getX();
        int inputY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (inputX < exit_x + EXIT_BUTTON_WIDTH && inputX > exit_x && inputY < exit_y + EXIT_BUTTON_HEIGHT && inputY > exit_y) {
            game.batch.draw(exitButton, exit_x - 10, exit_y - 20, EXIT_BUTTON_WIDTH + 20, EXIT_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new LevelsScreen(game));
            }
        } else {
            game.batch.draw(exitButton, exit_x, exit_y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        String attempts = String.valueOf(GameScreen.attempts);
        font.draw(game.batch, attempts, 1200, 510);

        game.batch.end();
    }

    /**
     * Resizes the screen.
     *
     * @param width  the new width
     * @param height the new height
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        // Called when the Application is paused.
    }

    @Override
    public void resume() {
        // Called when the Application is resumed after pause.
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen for a Game.
    }

    @Override
    public void dispose() {
        // Called when this screen should release all resources.
        backgroundTexture.dispose();
        GameScreen.attempts = 1;
    }
}

