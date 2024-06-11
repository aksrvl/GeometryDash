package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * LevelsScreen class represents the screen where players can select different levels to play.
 */
public class LevelsScreen implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    Texture levelButton_0;
    Texture levelButton_1;
    Texture levelButton_2;
    Texture levelButton_3;
    Texture backgroundTexture;
    Texture returnButton;
    private static final int RETURN_BUTTON_WIDTH = 140;
    private static final int RETURN_BUTTON_HEIGHT = 70;
    private static final int CHOSEN_BUTTON_WIDTH = 250;
    private static final int CHOSEN_BUTTON_HEIGHT = 250;
    public static int selectedLevel = -1;

    /**
     * Constructs a new LevelsScreen.
     *
     * @param game the Geometry Dash game instance
     */
    public LevelsScreen(GeometryDashGame game) {
        this.game = game;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        returnButton = new Texture("returnbutton.png");
        levelButton_0 = new Texture("level_icons/level0Icon.png");
        levelButton_1 = new Texture("level_icons/level1Icon.png");
        levelButton_2 = new Texture("level_icons/level2Icon.png");
        levelButton_3 = new Texture("level_icons/level3Icon.png");
        backgroundTexture = new Texture(Gdx.files.internal("level_icons/LevelBackground.png"));
    }

    @Override
    public void show() {
        // This method is called when the screen is shown
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Update the camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        // Draw the background
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

        renderSkinButton(330, 400, levelButton_0, 0);
        renderSkinButton(700, 400, levelButton_1, 1);
        renderSkinButton(1070, 400, levelButton_2, 2);
        renderSkinButton(1440, 400, levelButton_3, 3);

        game.batch.end();
    }

    /**
     * Renders a level selection button.
     *
     * @param x the x-coordinate of the button
     * @param y the y-coordinate of the button
     * @param skinTexture the texture of the button
     * @param skinIndex the index of the level associated with the button
     */
    private void renderSkinButton(int x, int y, Texture skinTexture, int skinIndex) {
        int inputX = Gdx.input.getX();
        int inputY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (inputX < x + CHOSEN_BUTTON_WIDTH && inputX > x && inputY < y + CHOSEN_BUTTON_HEIGHT && inputY > y) {
            game.batch.draw(skinTexture, x - 10, y - 20, CHOSEN_BUTTON_WIDTH + 20, CHOSEN_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                selectedLevel = skinIndex; // Update the selected level index
            }
        } else {
            game.batch.draw(skinTexture, x, y, CHOSEN_BUTTON_WIDTH, CHOSEN_BUTTON_HEIGHT);
        }
        if (selectedLevel == 0) {
            this.dispose();
            game.playNewMusic("music/trainingLevel.mp3");
            game.setScreen(new GameScreen(camera, game));
        }else if(selectedLevel == 1){
            this.dispose();
            game.playNewMusic("music/level2.mp3");
            game.setScreen(new GameScreen(camera, game));
        }else if(selectedLevel == 2){
            this.dispose();
            game.playNewMusic("music/level1.mp3");
            game.setScreen(new GameScreen(camera, game));
        }else if(selectedLevel == 3){
            this.dispose();
            game.playNewMusic("music/level3.mp3");
            game.setScreen(new GameScreen(camera, game));
        }
    }

    @Override
    public void resize(int i, int i1) {
        // This method is called when the screen is resized
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

    }
}
