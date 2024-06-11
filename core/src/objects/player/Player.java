package objects.player;

import Helper.TextureHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.geometrydash.game.LevelsScreen;

import static Helper.Constants.PPM;

/**
 * Represents the player in the game.
 */
public class Player extends GameEntity {

    private Sprite sprite;
    private float jumpRotation;
    private boolean isJumping;
    private boolean onGround;
    private boolean alternateControl;

    /**
     * Constructs a player with the given width, height, and Box2D body.
     *
     * @param width The width of the player.
     * @param height The height of the player.
     * @param body The Box2D body representing the player.
     */
    public Player(float width, float height, Body body) {
        super(width, height, body);
        if (LevelsScreen.selectedLevel == 0) {
            this.speed = 12f;
        } else if (LevelsScreen.selectedLevel == 1) {
            this.speed = 15f;
        } else if (LevelsScreen.selectedLevel == 2) {
            this.speed = 15f;
        } else {
            this.speed=18f;
        }
        jumpRotation = 0;
        isJumping = false;
        onGround = false;
        if (LevelsScreen.selectedLevel == 0 || LevelsScreen.selectedLevel == 2 || LevelsScreen.selectedLevel == 3) {
            sprite = new Sprite(TextureHelper.changeTexture());
            alternateControl = false;
        }
        if (LevelsScreen.selectedLevel == 1) {
            sprite = new Sprite(TextureHelper.shipTexture());
            alternateControl = true;
        }
        body.setUserData(this);
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        if (alternateControl) {
            jumpRotation = 0;
            sprite.setTexture(TextureHelper.shipTexture());
        } else {
            sprite.setTexture(TextureHelper.changeTexture());
        }
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
        checkUserInput();
        sprite.setRotation(jumpRotation);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Sets whether the player is on the ground.
     *
     * @param onGround true if the player is on the ground, false otherwise.
     */
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
        if (onGround) {
            isJumping = false;
        }
    }

    private void checkUserInput() {
        if (alternateControl) {
            checkAlternateControl();
        } else {
            checkDefaultControl();
        }
    }

    private void checkDefaultControl() {
        int gravityForce = 0;
        if (LevelsScreen.selectedLevel == 0) {
            gravityForce = 18;
        } else if (LevelsScreen.selectedLevel == 1) {
            // Handle level 1 gravity force
        } else if (LevelsScreen.selectedLevel == 2) {
            gravityForce = 25;
        } else {
            gravityForce=25;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) && onGround) {
            float force = body.getMass() * gravityForce;
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            isJumping = true;
            onGround = false;
        }
        if (body.getLinearVelocity().y > 0) {
            jumpRotation += 2.5;
            if (jumpRotation > 360) {
                jumpRotation = 0;
            }
        } else if (isJumping) {
            if (jumpRotation < 45 || jumpRotation > 315) {
                jumpRotation = 0;
            } else if (jumpRotation < 135 && jumpRotation > 45) {
                jumpRotation = 90;
            } else if (jumpRotation < 225 && jumpRotation > 135) {
                jumpRotation = 180;
            } else {
                jumpRotation = 270;
            }
            isJumping = false;
        }
        body.setLinearVelocity(speed, body.getLinearVelocity().y < 18 ? body.getLinearVelocity().y : 18);
    }

    private void checkAlternateControl() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            body.setLinearVelocity(speed, 12);
        } else {
            body.setLinearVelocity(speed, -12);
        }
    }

    /**
     * Toggles between alternate and default controls.
     */
    public void changeControl() {
        alternateControl = !alternateControl;
    }
}
