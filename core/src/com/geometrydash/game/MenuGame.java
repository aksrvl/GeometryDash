package com.geometrydash.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MenuGame implements Screen {
    //final GeometryDash game;
    final GeometryDashScreen game;
    public OrthographicCamera camera;
    Texture backgroundTexture;
    Texture playButton;
    Texture settingsButton;
    Texture skinsButton;
    private static final int PLAY_BUTTON_WIDTH = 150;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int SETTINGS_BUTTON_WIDTH = 100;
    private static final int SETTINGS_BUTTON_HEIGHT = 100;
    private static final int SKINS_BUTTON_WIDTH = 100;
    private static final int SKINS_BUTTON_HEIGHT = 100;


    public MenuGame(final GeometryDashScreen game){
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, screenWidth, screenHeight);

        playButton = new Texture("playbutton.png");
        settingsButton = new Texture("settingsbutton.png");
        skinsButton = new Texture("skinbutton.png");


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 480);
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        // Update the camera
        camera.update();
        // Render the background
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 1200, 480);
        //game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Adjust the position and size as needed

        int play_x = Gdx.graphics.getWidth()/2-PLAY_BUTTON_WIDTH/2;
        int play_y = Gdx.graphics.getHeight()/2-PLAY_BUTTON_HEIGHT/2;

        if(Gdx.input.getX()<play_x+PLAY_BUTTON_WIDTH && Gdx.input.getX()>play_x && Gdx.input.getY()<play_y+PLAY_BUTTON_HEIGHT && Gdx.input.getY()>play_y){
            game.batch.draw(playButton, play_x-10, play_y-20, PLAY_BUTTON_WIDTH+20, PLAY_BUTTON_HEIGHT+20);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(camera, game));
            }
        }else{
            game.batch.draw(playButton, play_x, play_y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        game.batch.draw(settingsButton, play_x - PLAY_BUTTON_WIDTH, play_y+20, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        game.batch.draw(skinsButton, play_x+PLAY_BUTTON_WIDTH+50, play_y+20, SKINS_BUTTON_WIDTH, SKINS_BUTTON_HEIGHT);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //camera.setToOrtho(false, width, height);
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
    }



}
