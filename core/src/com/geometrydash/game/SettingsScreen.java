package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The screen for adjusting game settings.
 */
public class SettingsScreen implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    private Stage stage;
    private Skin skin;
    private Slider volumeSlider;
    Texture backgroundTexture;
    Texture returnButton;
    Texture songsButton;
    Texture creditsButton;
    Texture howToPlayButton;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 130;
    private static final int RETURN_BUTTON_WIDTH = 140;
    private static final int RETURN_BUTTON_HEIGHT = 70;

    /**
     * Constructs a new SettingsScreen.
     *
     * @param game The GeometryDashGame instance.
     */
    public SettingsScreen(GeometryDashGame game){
        this.game = game;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        returnButton = new Texture("returnbutton.png");
        howToPlayButton = new Texture("HowPlayButton.png");
        songsButton = new Texture("SongsButton.png");
        creditsButton = new Texture("CreditsButton.png");
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
        // Implementation not needed for this screen.
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

        if (inputX < 600 + BUTTON_WIDTH && inputX > 600 && inputY < 400 + BUTTON_HEIGHT && inputY > 400) {
            game.batch.draw(creditsButton, 600-10, 400-20, BUTTON_WIDTH+20, BUTTON_HEIGHT+20);
            if (Gdx.input.isTouched()) {
                game.setScreen(new CreditsScreen(game));
            }
        } else {
            game.batch.draw(creditsButton, 600, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if (inputX < 1000 + BUTTON_WIDTH && inputX > 1000 && inputY < 400 + 150 && inputY > 400) {
            game.batch.draw(songsButton, 1000-10, 400-20, BUTTON_WIDTH+20, 150+20);
            if (Gdx.input.isTouched()) {
                game.setScreen(new SongsScreen(game));
            }
        } else {
            game.batch.draw(songsButton, 1000, 400, BUTTON_WIDTH, 150);
        }

        if (inputX < 800 + BUTTON_WIDTH && inputX > 800 && inputY < 300 + 100 && inputY > 300) {
            game.batch.draw(howToPlayButton, 800-10, 300-20, BUTTON_WIDTH+20, 100+20);
            if (Gdx.input.isTouched()) {
                game.setScreen(new HowToPlayScreen(game));
            }
        } else {
            game.batch.draw(howToPlayButton, 800, 300, BUTTON_WIDTH, 100);
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
        // Implementation not needed for this screen.
    }

    @Override
    public void resume() {
        // Implementation not needed for this screen.
    }

    @Override
    public void hide() {
        // Implementation not needed for this screen.
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        returnButton.dispose();
        stage.dispose();
        skin.dispose();
    }
}
