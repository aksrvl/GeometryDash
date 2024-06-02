package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    private Stage stage;
    private Skin skin;
    private Slider volumeSlider;
    Texture backgroundTexture;
    Texture returnButton;
    private static final int RETURN_BUTTON_WIDTH = 140;
    private static final int RETURN_BUTTON_HEIGHT = 70;

    public SettingsScreen(GeometryDashGame game){
        this.game = game;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        returnButton = new Texture("returnbutton.png");

        backgroundTexture = new Texture(Gdx.files.internal("SettingsBackground.png"));

        // Set up the stage and skin
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Load the skin file


        // Create a volume slider using the skin
        volumeSlider = new Slider(0, 1, 0.01f, false, skin);
        volumeSlider.setValue(game.getVolume()); // Set the initial volume to the current volume
        volumeSlider.setHeight(200);

        // Add a listener to update the volume when the slider value changes
        volumeSlider.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                game.setVolume(volumeSlider.getValue());
            }
        });

        // Use a table to the slider
        Table table = new Table();
        table.setFillParent(true);
        table.center(); // Center the table on the screen
        table.add(volumeSlider).width(400).padBottom(120); // Adjust the width and padding as needed

        // Add the table to the stage
        stage.addActor(table);

        // Set the input processor to handle input events for the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        // Update the camera
        camera.update();
        // Render the background
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int return_x = 30;
        int return_y = 980;
        int inputX = Gdx.input.getX();
        int inputY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (inputX < return_x + RETURN_BUTTON_WIDTH && inputX > return_x && inputY < return_y + RETURN_BUTTON_HEIGHT && inputY > return_y) {
            game.batch.draw(returnButton, return_x - 10, return_y - 20, RETURN_BUTTON_WIDTH + 20, RETURN_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MenuGame(game));
            }
        } else {
            game.batch.draw(returnButton, return_x, return_y, RETURN_BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);
        }

        game.batch.end();

        // Render the stage containing the slider
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);

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
        returnButton.dispose();
        stage.dispose();
        skin.dispose();
    }
}
