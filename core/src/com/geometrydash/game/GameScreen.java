package com.geometrydash.game;

import Helper.TileMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import objects.player.Player;

import static Helper.Constants.PPM;

public class GameScreen extends ScreenAdapter implements ContactListener {
    public static int attempts=1;
    private OrthographicCamera camera;
    private boolean debug = false;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private final GeometryDashGame game;
    private Player player;
    Texture exitButton;
    private static final int EXIT_BUTTON_WIDTH = 160;
    private static final int EXIT_BUTTON_HEIGHT = 90;

    public GameScreen(OrthographicCamera camera, GeometryDashGame game){
        this.camera = camera;
        this.game = game;
        this.batch = new SpriteBatch();
        if(LevelsScreen.selectedLevel==0){
            this.world = new World(new Vector2(0, -57f), false);
        } else if (LevelsScreen.selectedLevel==1) {
            //this.world = new World(new Vector2(0, -61f), false);
        }
        else if (LevelsScreen.selectedLevel==2) {
            this.world = new World(new Vector2(0, -61f), false);
        }
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        exitButton = new Texture("ExitButtonCr.png");
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
    }

    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x*PPM*10)/10f;
        camera.position.set(position);
        camera.update();
    }


    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthogonalTiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined); // Set projection matrix for world rendering
        batch.begin();
        player.update();
        player.render(batch);
        batch.end();

        // Draw the exit button in screen coordinates
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.begin();

        int inputX = Gdx.input.getX();
        int inputY = Gdx.graphics.getHeight() - Gdx.input.getY();
        // Define the position of the exit button in screen coordinates
        int exit_X = 1770;
        int exit_Y = 1000;

        // Draw the exit button at a fixed position on the screen
        batch.draw(exitButton, exit_X, exit_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        // Check if the mouse/touch is over the exit button
        if (inputX < exit_X + EXIT_BUTTON_WIDTH && inputX > exit_X && inputY < exit_Y + EXIT_BUTTON_HEIGHT && inputY > exit_Y) {
            // If mouse/touch is over the button, and it's clicked, change the screen
            if (Gdx.input.justTouched()) {
                LevelsScreen.selectedLevel = -1;
                game.playNewMusic("music/BackgroundMusic.mp3");
                this.dispose();
                game.setScreen(new LevelsScreen(game));
            }
        }

        batch.end();

        if (debug) {
            box2DDebugRenderer.render(world, camera.combined.scl(PPM));
        }
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public void show() {
        world.setContactListener(this);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        handleContact(fixtureA, fixtureB, true);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        handleContact(fixtureA, fixtureB, false);
    }

    private void handleContact(Fixture fixtureA, Fixture fixtureB, boolean isBegin) {
        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("groundSensor")) {
            handleGroundContact(fixtureA, fixtureB, isBegin);
        } else if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("groundSensor")) {
            handleGroundContact(fixtureB, fixtureA, isBegin);
        } else if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("Portal")) {
            handlePortalContact(fixtureA, fixtureB, isBegin);
        } else if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("Portal")) {
            handlePortalContact(fixtureB, fixtureA, isBegin);
        } else if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("spike")) {
            handleSpikeContact(fixtureA, fixtureB, isBegin);
        } else if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("spike")) {
            handleSpikeContact(fixtureB, fixtureA, isBegin);
        } else if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("end")) {
            endLevel(fixtureA, fixtureB, isBegin);
        } else if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("end")) {
            endLevel(fixtureB, fixtureA, isBegin);
        }
    }

    private void handleSpikeContact(Fixture spikeFixture, Fixture otherFixture, boolean isBegin) {
        if (isBegin) { //тільки кщо починається контакт
            Body playerBody = otherFixture.getBody();
            Player player = (Player) playerBody.getUserData();
            if (player != null) {
                if(LevelsScreen.selectedLevel==0) {
                    game.playNewMusic("music/trainingLevel.mp3");
                } else if (LevelsScreen.selectedLevel==1) {

                } else if (LevelsScreen.selectedLevel==2) {
                    game.playNewMusic("music/level1.mp3");
                } else {
                    //game.playNewMusic("music/trainingLevel.mp3");
                }
                attempts+=1;
                game.setScreen(new GameScreen(camera, game));
            }
        }
    }

    private void handleGroundContact(Fixture sensorFixture, Fixture otherFixture, boolean isBegin) {
        Body playerBody = sensorFixture.getBody();
        Player player = (Player) playerBody.getUserData();
        if (player != null) {
            player.setOnGround(isBegin);
        }
    }

    private void handlePortalContact(Fixture sensorFixture, Fixture otherFixture, boolean isBegin) {
        if (isBegin) {
            Body playerBody = otherFixture.getBody();
            Player player = (Player) playerBody.getUserData();
            if (player != null) {
                player.changeControl();
            }
        }
    }

    private void endLevel(Fixture sensorFixture, Fixture otherFixture, boolean isBegin){
        if (isBegin) {
            Body playerBody = otherFixture.getBody();
            Player player = (Player) playerBody.getUserData();
            if (player != null) {
                this.dispose();
                LevelsScreen.selectedLevel = -1;
                game.playNewMusic("music/BackgroundMusic.mp3");
                game.setScreen(new WinScreen(game));
            }
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}