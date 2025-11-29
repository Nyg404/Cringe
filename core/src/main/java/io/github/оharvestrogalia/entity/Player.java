package io.github.оharvestrogalia.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;
import io.github.оharvestrogalia.utils.CollisionResult;
import io.github.оharvestrogalia.utils.PhysicsEntity;

import java.util.Vector;

public class Player implements PhysicsEntity {
   private final Vector2 position = new Vector2();
   private final Vector2 velocity = new Vector2();
   private Rectangle rectangle;
   private Texture texture;
   private float healt = 20F;
   private PlayerInput input;
   private boolean onGround;


    public Player(){
       texture = new Texture("test.png");
       rectangle = new Rectangle(position.x, position.y, 16, 16);
       input = new PlayerInput(this);
   }

    public void update(float delta){
        input.update();
        velocity.y -= 500 * delta;

        rectangle.setPosition(position.x, position.y);


    }

    public void move(boolean right, boolean left) {

        velocity.x = 0;

        if (right && !left) {
            velocity.x = 150;
        } else if (!right && left) {
            velocity.x = -150;
        }
    }

    public void jump() {
        velocity.y = 200;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, rectangle.width, rectangle.height);
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Rectangle getBounds() {
        return rectangle;
    }

    public void setOnGround(boolean onGround){
       this.onGround = onGround;
       if(onGround){
           velocity.y = 0;
       }
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
        this.rectangle.setPosition(position);
    }

    @Override
    public void onCollision(CollisionResult result) {
        this.onGround = result.onGround;
        if (result.onGround && this.velocity.y < 0) {
            this.velocity.y = 0;
        }
        if (result.hitCeiling) {
            this.velocity.y = Math.min(0, this.velocity.y);
        }
        if (result.hitWall) {
            this.velocity.x = 0;
        }
    }




    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getHealt() {
        return healt;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isOnGround() {
        return onGround;
    }

}
