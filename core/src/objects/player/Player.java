package objects.player;

import Helper.TextureHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static Helper.Constants.PPM;


public class Player extends GameEntity {

    private Sprite sprite;
    private float jumpRotation;
    private boolean isJumping;
    private boolean onGround;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 12f;
        sprite = new Sprite(TextureHelper.changeTexture());
        jumpRotation = 0;
        isJumping = false;
        onGround = false;
        body.setUserData(this);
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        sprite.setTexture(TextureHelper.changeTexture());
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
        checkUserInput();
        sprite.setRotation(jumpRotation);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
        if (onGround) {
            isJumping = false;
        }
    }

    private void checkUserInput() {
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) && onGround) {
            float force = body.getMass() * 18;
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
}
