package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HowToPlayScreen implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    Texture backgroundTexture;
    Texture exitButton;
    Texture play_1;
    Texture play_2;
    Texture play_3;
    Texture nextButton;
    private static final int EXIT_BUTTON_WIDTH = 160;
    private static final int EXIT_BUTTON_HEIGHT = 90;

    private static final int NEXT_BUTTON_WIDTH = 250;
    private static final int NEXT_BUTTON_HEIGHT = 100;
    private int currentPage = 0;
    private float transitionAlpha = 1f;  // Alpha value for transition
    private boolean isTransitioning = false;  // Transition flag
    private static final float TRANSITION_DURATION = 0.5f;  // Transition duration in seconds

    private Texture[] playScreens;

    public HowToPlayScreen(GeometryDashGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        exitButton = new Texture("ExitButtonCr.png");
        play_1 = new Texture("howToPlay1.png");
        play_2 = new Texture("howToPlay2.png");
        nextButton = new Texture("nextButton.png");
        backgroundTexture = new Texture(Gdx.files.internal("howToPlayBg.png"));
        playScreens = new Texture[] { play_1, play_2, play_2 };
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int inputX = Gdx.input.getX();
        int inputY = Gdx.graphics.getHeight() - Gdx.input.getY();
        int exit_X = 1750;
        int exit_Y = 950;
        int next_X = 830;
        int next_Y = 180;

        if (inputX < exit_X + EXIT_BUTTON_WIDTH && inputX > exit_X&& inputY < exit_Y + EXIT_BUTTON_HEIGHT && inputY > exit_Y) {
            game.batch.draw(exitButton, exit_X-10, exit_Y-20, EXIT_BUTTON_WIDTH+20, EXIT_BUTTON_HEIGHT+20);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new SettingsScreen(game));
            }
        } else {
            game.batch.draw(exitButton, exit_X, exit_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        if (inputX < next_X + NEXT_BUTTON_WIDTH && inputX > next_X && inputY < next_Y + NEXT_BUTTON_HEIGHT && inputY > next_Y) {
            game.batch.draw(nextButton, next_X-10, next_Y-20, NEXT_BUTTON_WIDTH+20, NEXT_BUTTON_HEIGHT+20);
            if (Gdx.input.isTouched()) {
                startTransition();
            }
        } else {
            game.batch.draw(nextButton, next_X, next_Y, NEXT_BUTTON_WIDTH, NEXT_BUTTON_HEIGHT);
        }

        renderCurrentPlayScreen(game.batch, delta);

        game.batch.end();
    }

    private void startTransition() {
        isTransitioning = true;
        transitionAlpha = 1f;
    }

    private void renderCurrentPlayScreen(SpriteBatch batch, float delta) {
        if (isTransitioning) {
            transitionAlpha -= delta / TRANSITION_DURATION;
            if (transitionAlpha <= 0) {
                transitionAlpha = 0;
                isTransitioning = false;
                currentPage = (currentPage + 1) % playScreens.length;
            }
        }

        Texture currentScreen = playScreens[currentPage];
        Texture nextScreen = playScreens[(currentPage + 1) % playScreens.length];

        batch.setColor(1, 1, 1, 1);
        batch.draw(currentScreen, 580, 300, 770, 484);
        if (isTransitioning) {
            batch.setColor(1, 1, 1, 1 - transitionAlpha);
            batch.draw(nextScreen, 580, 300, 770, 484);
        }

        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
        backgroundTexture.dispose();
        exitButton.dispose();
        play_1.dispose();
        play_2.dispose();
        nextButton.dispose();
    }
}
