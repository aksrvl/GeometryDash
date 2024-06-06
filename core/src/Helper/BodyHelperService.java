package Helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static Helper.Constants.PPM;

public class BodyHelperService {

    /**
     * Creates a body in the Box2D world.
     *
     * @param x        the x position of the body.
     * @param y        the y position of the body.
     * @param width    the width of the body.
     * @param height   the height of the body.
     * @param isStatic if true, the body will be static; otherwise, it will be dynamic.
     * @param world    the Box2D world where the body will be created.
     * @return the created Body.
     */
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();

        if (!isStatic) {
            addGroundSensor(body, width, height);
        }

        return body;
    }

    /**
     * Adds a ground sensor to the body.
     *
     * @param body   the Body to which the sensor will be added.
     * @param width  the width of the body.
     * @param height the height of the body.
     */
    private static void addGroundSensor(Body body, float width, float height) {
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(width / 2 / PPM, 5 / PPM, new Vector2(0, -height / 2 / PPM), 0);

        FixtureDef sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorFixtureDef.isSensor = true;

        body.createFixture(sensorFixtureDef).setUserData("groundSensor");
        sensorShape.dispose();
    }

    /**
     * Creates a static body in the Box2D world.
     *
     * @param world    the Box2D world where the body will be created.
     * @param vertices the vertices defining the shape of the static body.
     * @return the created static Body.
     */
    public static Body createStaticBody(World world, Vector2[] vertices) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        body.createFixture(shape, 1000);
        shape.dispose();

        return body;
    }
}
