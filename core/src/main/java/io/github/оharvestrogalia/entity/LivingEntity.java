package io.github.оharvestrogalia.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.оharvestrogalia.entity.Entity;

public abstract class LivingEntity extends Entity {
    protected float health;
    protected TextureRegion texture;

    public LivingEntity(float x, float y, float width, float height, float health) {
        super(x, y, width, height);
        this.health = health;
    }

    public abstract void render(SpriteBatch batch);

    public float getHealth() { return health; }
    public void setHealth(float health) { this.health = health; }
    public TextureRegion getTexture() { return texture; }
    public void setTexture(TextureRegion texture) { this.texture = texture; }

    @Override
    public void dispose() {
        texture.getTexture().dispose();
    }
}
