package io.github.оharvestrogalia.world;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public interface CollisionProvider {
    List<Rectangle> getCollisionObjects();
    boolean isColliding(Rectangle bounds);
}
