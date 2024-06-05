package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreditsScreen implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    Texture backgroundTexture;
    Texture exitButton;
    private static final int EXIT_BUTTON_WIDTH = 160;
    private static final int EXIT_BUTTON_HEIGHT = 90;

    public CreditsScreen(GeometryDashGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        exitButton = new Texture("ExitButtonCr.png");
        backgroundTexture = new Texture(Gdx.files.internal("CreditsBg.png"));
    }

    @Override
    public void show() {}

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

        if (inputX < exit_X + EXIT_BUTTON_WIDTH && inputX > exit_X&& inputY < exit_Y + EXIT_BUTTON_HEIGHT && inputY > exit_Y) {
            game.batch.draw(exitButton, exit_X-10, exit_Y-20, EXIT_BUTTON_WIDTH+20, EXIT_BUTTON_HEIGHT+20);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new SettingsScreen(game));
            }
        } else {
            game.batch.draw(exitButton, exit_X, exit_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}



