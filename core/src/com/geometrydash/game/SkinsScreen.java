package com.geometrydash.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * The screen for selecting player skins.
 */
public class SkinsScreen implements Screen {
    final GeometryDashGame game;
    public OrthographicCamera camera;
    Texture backgroundTexture;
    Texture returnButton;
    Texture skin1;
    Texture skin2;
    Texture skin3;
    Texture skin4;
    Texture skin5;
    Texture skin6;
    Texture skin7;
    Texture skin8;
    Texture chosenButton;
    private static final int RETURN_BUTTON_WIDTH = 140;
    private static final int RETURN_BUTTON_HEIGHT = 70;
    private static final int SKIN_BUTTON_WIDTH = 220;
    private static final int SKIN_BUTTON_HEIGHT = 220;
    private static final int CHOSEN_BUTTON_WIDTH = 66;
    private static final int CHOSEN_BUTTON_HEIGHT = 56;
    public static int selectedSkin;

    /**
     * Constructs a new SkinsScreen.
     *
     * @param game The GeometryDashGame instance.
     */
    public SkinsScreen(GeometryDashGame game) {
        this.game = game;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        returnButton = new Texture("returnbutton.png");
        skin1 = new Texture("choose1.png");
        skin2 = new Texture("choose2.png");
        skin3 = new Texture("choose3.png");
        skin4 = new Texture("choose4.png");
        skin5 = new Texture("choose5.png");
        skin6 = new Texture("choose6.png");
        skin7 = new Texture("choose7.png");
        skin8 = new Texture("choose8.png");
        chosenButton = new Texture("tick.png");

        backgroundTexture = new Texture(Gdx.files.internal("SkinsBackground.png"));
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

        int row1_y = 500;
        int row2_y = 200;

        renderSkinButton(300, row1_y, skin1, 0);
        renderSkinButton(650, row1_y, skin2, 1);
        renderSkinButton(300, row2_y, skin5, 4);
        renderSkinButton(650, row2_y, skin6, 5);
        renderSkinButton(1000, row1_y, skin3, 2);
        renderSkinButton(1000, row2_y, skin7, 6);
        renderSkinButton(1350, row1_y, skin4, 3);
        renderSkinButton(1350, row2_y, skin8, 7);

        game.batch.end();
    }

    private void renderSkinButton(int x, int y, Texture skinTexture, int skinIndex) {
        int inputX = Gdx.input.getX();
        int inputY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (inputX < x + SKIN_BUTTON_WIDTH && inputX > x && inputY < y + SKIN_BUTTON_HEIGHT && inputY > y) {
            game.batch.draw(skinTexture, x - 10, y - 20, SKIN_BUTTON_WIDTH + 20, SKIN_BUTTON_HEIGHT + 20);
            if (Gdx.input.isTouched()) {
                selectedSkin = skinIndex; // Update the selected skin index
            }
        } else {
            game.batch.draw(skinTexture, x, y, SKIN_BUTTON_WIDTH, SKIN_BUTTON_HEIGHT);
        }
        // Draw the chosen button on the selected skin
        if (selectedSkin == skinIndex) {
            game.batch.draw(chosenButton, x, y, CHOSEN_BUTTON_WIDTH, CHOSEN_BUTTON_HEIGHT);
        }
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
        returnButton.dispose();
    }
}
