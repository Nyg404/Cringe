package io.github.оharvestrogalia.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.github.оharvestrogalia.utils.CollisionResult;

import io.github.оharvestrogalia.utils.PhysicSystem;
import io.github.оharvestrogalia.utils.PhysicsEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager implements Disposable {
    private List<PhysicsEntity> entities = new ArrayList<>();
    private List<Disposable> disposableEntities = new ArrayList<>();
    public void addEntity(PhysicsEntity entity) {
        entities.add(entity);
        if(entity instanceof Disposable){
            disposableEntities.add((Disposable) entity);
        }
    }

    public void removeEntity(PhysicsEntity entity) {
        entities.remove(entity);
        if(entity instanceof Disposable){
            disposableEntities.remove((Disposable) entity);
        }
    }

    public void updateEntities(float delta, PhysicSystem physics) {
        for (PhysicsEntity entity : entities) {
            // Обновляем velocity (если нужно)
            Vector2 desiredMovement = entity.getVelocity().cpy().scl(delta);

            // Применяем физику
            CollisionResult result = physics.resolveMovement(entity, desiredMovement);
            entity.onCollision(result);

            // Обновляем позицию
            entity.getPosition().add(result.allowedMovement);
        }
    }

    public List<PhysicsEntity> getEntities() {
        return new ArrayList<>(entities);
    }

    public void clear() {
        entities.clear();
    }

    @Override
    public void dispose() {
        for (Disposable disposable : disposableEntities){
            disposable.dispose();
        }
        entities.clear();
    }
}
