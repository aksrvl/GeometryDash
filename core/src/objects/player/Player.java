package objects.player;

import Helper.TextureHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.Texture;

import static Helper.Constants.PPM;

public class Player extends GameEntity{

    private Sprite sprite;


    public Player(float width, float height, Body body){
        super(width, height, body);
        this.speed = 10f;
        sprite = new Sprite(TextureHelper.changeTexture());
    }

    @Override
    public void update() {
        x = body.getPosition().x*PPM;
        y = body.getPosition().y*PPM;
        sprite.setTexture(TextureHelper.changeTexture());
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    private void checkUserInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)||Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            float force = body.getMass()*18;
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
        }
        body.setLinearVelocity(speed, body.getLinearVelocity().y<18? body.getLinearVelocity().y :18);
    }
}
