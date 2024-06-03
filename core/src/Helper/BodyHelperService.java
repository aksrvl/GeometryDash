package Helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static Helper.Constants.PPM;

public class BodyHelperService {

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

        // Додати сенсор для виявлення землі
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(width / 2 / PPM, 5 / PPM, new Vector2(0, -height / 2 / PPM), 0);
        FixtureDef sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorFixtureDef.isSensor = true;
        body.createFixture(sensorFixtureDef).setUserData("groundSensor");
        sensorShape.dispose();

        return body;
    }

}