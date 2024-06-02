package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static Helper.Constants.PPM;

public class Player extends GameEntity{

    private int jumpCounter;

    public Player(float width, float height, Body body){
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;
    }

    @Override
    public void update() {
        x = body.getPosition().x*PPM;
        y = body.getPosition().y*PPM;

        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkUserInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)||Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            float force = body.getMass()*18;
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCounter++;
        }
        //reset jumpcounter
        if(body.getLinearVelocity().y==0){
            jumpCounter = 0;
        }
        body.setLinearVelocity(speed, body.getLinearVelocity().y<18? body.getLinearVelocity().y :18);
    }
}