package com.geometrydash.game;

import Helper.TileMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import objects.player.Player;

import static Helper.Constants.PPM;

public class GameScreen extends ScreenAdapter implements ContactListener {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private final GeometryDashGame game;

    //game objects
    private Player player;


    public GameScreen(OrthographicCamera camera, GeometryDashGame game){
        this.camera = camera;
        this.game = game;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -45f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
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

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();

        player.update();
        player.render(batch);
        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public void show() {
        // Ініціалізація вашого світу та інших об'єктів
        world.setContactListener(this);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("groundSensor")) {
            handleGroundContact(fixtureA, fixtureB, true);
        } else if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("groundSensor")) {
            handleGroundContact(fixtureB, fixtureA, true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("groundSensor")) {
            handleGroundContact(fixtureA, fixtureB, false);
        } else if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("groundSensor")) {
            handleGroundContact(fixtureB, fixtureA, false);
        }
    }

    private void handleGroundContact(Fixture sensorFixture, Fixture otherFixture, boolean isBegin) {
        Body playerBody = sensorFixture.getBody();
        Player player = (Player) playerBody.getUserData();
        if (player != null) {
            player.setOnGround(isBegin);
        }
    }

    // Інші методи ContactListener
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

}

