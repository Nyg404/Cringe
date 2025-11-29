package io.github.оharvestrogalia.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface PhysicsEntity {
    Vector2 getPosition();
    Rectangle getBounds();
    Vector2 getVelocity();
    void setPosition(Vector2 position);
    void onCollision(CollisionResult result);

}
