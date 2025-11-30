package io.github.оharvestrogalia.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.оharvestrogalia.input.PlayerInput;
import io.github.оharvestrogalia.utils.CollisionResult;


public class Player extends LivingEntity {
    private final Vector2 velocity = new Vector2();
    private PlayerInput input;
    private boolean onGround;
    private float maxSpeed = 150f;

    public Player(float startX, float startY) {
        super(startX, startY, 16, 16, 20f);
        this.texture = new TextureRegion(new Texture("test.png")); // ИНИЦИАЛИЗИРУЕМ текстуру
        input = new PlayerInput(this);
    }

    @Override
    public void update(float delta) {
        input.update();
        applyGravity(delta);
        applyMovement(delta);
        bounds.setPosition(position.x, position.y); // обновляем bounds
    }

    private void applyGravity(float delta) {
        if (!onGround) {
            velocity.y -= 500 * delta;
        }
    }

    private void applyMovement(float delta) {
        velocity.x = Math.max(Math.min(velocity.x, maxSpeed), -maxSpeed);
    }

    public void move(boolean right, boolean left) {
        velocity.x = 0;

        if (right && !left) {
            velocity.x = maxSpeed;
        } else if (!right && left) {
            velocity.x = -maxSpeed;
        }
    }

    public void jump() {
        velocity.y = 200;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, position.x, position.y, bounds.width, bounds.height);
        }
    }

    // PhysicsEntity implementation
    @Override
    public Vector2 getVelocity() {
        return velocity;
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


    public boolean isOnGround() { return onGround; }
    public void setMaxSpeed(float maxSpeed) { this.maxSpeed = maxSpeed; }

}
