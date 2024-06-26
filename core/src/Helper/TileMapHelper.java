package Helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.geometrydash.game.LevelsScreen;
import objects.player.Player;
import com.geometrydash.game.GameScreen;

import static Helper.Constants.PPM;

/**
 * Helper class to set up tiled maps and parse map objects.
 */
public class TileMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    /**
     * Constructor to initialize the TileMapHelper with the given GameScreen.
     *
     * @param gameScreen The GameScreen instance.
     */
    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Setup the tiled map based on the selected level.
     *
     * @return The OrthogonalTiledMapRenderer for rendering the map.
     */
    public OrthogonalTiledMapRenderer setupMap() {
        if (LevelsScreen.selectedLevel == 0) {
            tiledMap = new TmxMapLoader().load("maps/trainingLevel.tmx");
        }
        if(LevelsScreen.selectedLevel==1){
            tiledMap = new TmxMapLoader().load("maps/level2.tmx");
        }
        if(LevelsScreen.selectedLevel==2){
            tiledMap = new TmxMapLoader().load("maps/level1.tmx");
        }
        if(LevelsScreen.selectedLevel==3){
            tiledMap = new TmxMapLoader().load("maps/level3.tmx");
        }
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
        parseMapObjects(tiledMap.getLayers().get("spikes").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    /**
     * Parse map objects and create corresponding bodies in the Box2D world.
     *
     * @param mapObjects The map objects to parse.
     */
    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) mapObject).getPolygon();
                String polygonName = mapObject.getName();

                if (polygonName == null) {
                    createStaticBody((PolygonMapObject) mapObject);
                } else if (polygonName.equals("spike")) {
                    createSpike((PolygonMapObject) mapObject);
                } else if (polygonName.equals("end")) {
                    endLevel((PolygonMapObject) mapObject);
                }
            }

            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName.equals("player")) {
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld()
                    );
                    gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));
                } else if (rectangleName.equals("Portal")) {
                    createPortal(rectangle);
                }
            }
        }
    }

    /**
     * Create a static body from a PolygonMapObject.
     *
     * @param polygonMapObject The PolygonMapObject.
     */
    private void createStaticBody(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }

        BodyHelperService.createStaticBody(gameScreen.getWorld(), worldVertices);
    }

    /**
     * Create a portal body from a Rectangle.
     *
     * @param rectangle The Rectangle representing the portal.
     */
    private void createPortal(Rectangle rectangle) {
        Body body = BodyHelperService.createBody(
                rectangle.getX() + rectangle.getWidth() / 2,
                rectangle.getY() + rectangle.getHeight() / 2,
                rectangle.getWidth(),
                rectangle.getHeight(),
                true,
                gameScreen.getWorld()
        );

        for (Fixture fixture : body.getFixtureList()) {
            fixture.setSensor(true);
            fixture.setUserData("Portal");
        }
    }

    /**
     * Create spike bodies from a PolygonMapObject.
     *
     * @param polygonMapObject The PolygonMapObject representing spikes.
     */
    private void createSpike(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }

        for (Fixture fixture : BodyHelperService.createStaticBody(gameScreen.getWorld(), worldVertices).getFixtureList()) {
            fixture.setUserData("spike");
        }
    }

    /**
     * Create an end level body from a PolygonMapObject.
     *
     * @param polygonMapObject The PolygonMapObject representing the end level.
     */
    private void endLevel(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }

        for (Fixture fixture : BodyHelperService.createStaticBody(gameScreen.getWorld(), worldVertices).getFixtureList()) {
            fixture.setUserData("end");
        }
    }
}
