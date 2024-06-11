package objects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Abstract class representing a game entity.
 */
public abstract class GameEntity {

    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;

    /**
     * Constructs a game entity with the given width, height, and Box2D body.
     *
     * @param width The width of the game entity.
     * @param height The height of the game entity.
     * @param body The Box2D body representing the game entity.
     */
    public GameEntity(float width, float height, Body body){
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    /**
     * Updates the game entity.
     */
    public abstract void update();

    /**
     * Renders the game entity.
     *
     * @param batch The sprite batch used for rendering.
     */
    public abstract void render(SpriteBatch batch);

    /**
     * Gets the Box2D body of the game entity.
     *
     * @return The Box2D body of the game entity.
     */
    public Body getBody() {
        return body;
    }
}
