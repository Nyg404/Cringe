package io.github.оharvestrogalia.utils;

import com.badlogic.gdx.math.Vector2;

public class CollisionResult {
    public final Vector2 allowedMovement;
    public final boolean onGround;
    public final boolean hitCeiling;
    public final boolean hitWall;

    public CollisionResult(Vector2 movement, boolean ground, boolean ceiling, boolean wall) {
        this.allowedMovement = movement;
        this.onGround = ground;
        this.hitCeiling = ceiling;
        this.hitWall = wall;
    }
}
