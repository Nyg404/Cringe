package io.github.оharvestrogalia.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import io.github.оharvestrogalia.utils.PhysicsEntity;


public abstract class Entity implements PhysicsEntity, Disposable {
    protected final Vector2 position = new Vector2();
    protected final Rectangle bounds;

    public Entity(float x, float y, float width, float height) {
        position.set(x, y);
        bounds = new Rectangle(x, y, width, height);
    }

    public abstract void update(float delta);

    // PhysicsEntity implementation
    @Override
    public Vector2 getPosition() { return position; }
    @Override
    public Rectangle getBounds() { return bounds; }
    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
        this.bounds.setPosition(position);
    }

}
