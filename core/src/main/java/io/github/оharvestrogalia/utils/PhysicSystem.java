package io.github.оharvestrogalia.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.оharvestrogalia.world.World;

public class PhysicSystem {
    private final World world;

    public PhysicSystem(World world) {
        this.world = world;
    }

    public CollisionResult resolveMovement(PhysicsEntity entity, Vector2 desiredMovement) {
        Rectangle bounds = entity.getBounds();
        Vector2 allowed = new Vector2(desiredMovement);


        allowed.y = resolveVertical(bounds, desiredMovement.y);

        Rectangle movedY = new Rectangle(
            bounds.x,
            bounds.y + allowed.y,
            bounds.width,
            bounds.height
        );

        // Потом горизонталь
        allowed.x = resolveHorizontal(movedY, desiredMovement.x);

        Rectangle finalBounds = new Rectangle(
            bounds.x + allowed.x,
            bounds.y + allowed.y,
            bounds.width,
            bounds.height
        );

        // Определяем тип коллизии
        boolean onGround = isOnGround(finalBounds);
        boolean hitCeiling = (allowed.y < desiredMovement.y && desiredMovement.y > 0);
        boolean hitWall = (Math.abs(allowed.x) < Math.abs(desiredMovement.x));

        return new CollisionResult(allowed, onGround, hitCeiling, hitWall);
    }

    private float resolveVertical(Rectangle bounds, float dy) {
        if (dy == 0) return 0;

        int direction = dy > 0 ? 1 : -1;
        float remaining = Math.abs(dy);
        float moved = 0;

        // Пошаговое движение с проверкой коллизий
        while (remaining > 0) {
            float step = Math.min(remaining, 1.0f); // Шаг в 1 пиксель

            Rectangle test = new Rectangle(
                bounds.x,
                bounds.y + moved + (step * direction),
                bounds.width,
                bounds.height
            );

            if (world.isColliding(test)) {
                // При падении ищем точную позицию земли
                if (direction < 0) {
                    return findExactGroundPosition(bounds, moved);
                }
                break; // При прыжке просто останавливаемся
            }

            moved += step * direction;
            remaining -= step;
        }

        return moved;
    }

    private float resolveHorizontal(Rectangle bounds, float dx) {
        if (dx == 0) return 0;

        int direction = dx > 0 ? 1 : -1;
        float remaining = Math.abs(dx);
        float moved = 0;

        // Пошаговое движение по горизонтали
        while (remaining > 0) {
            float step = Math.min(remaining, 1.0f);

            Rectangle test = new Rectangle(
                bounds.x + moved + (step * direction),
                bounds.y,
                bounds.width,
                bounds.height
            );

            if (world.isColliding(test)) {
                break;
            }

            moved += step * direction;
            remaining -= step;
        }

        return moved;
    }

    private float findExactGroundPosition(Rectangle bounds, float currentMoved) {
        float groundY = bounds.y + currentMoved;

        // Проверяем точку коллизии
        for (Rectangle collider : world.getCollisionObjects()) {
            if (collider.overlaps(new Rectangle(bounds.x, groundY, bounds.width, bounds.height))) {
                // ставим игрока ровно на верх коллайдера
                return collider.y + collider.height - bounds.y;
            }
        }

        return currentMoved;
    }


    private boolean isOnGround(Rectangle bounds) {
        // Проверяем, есть ли земля под ногами (1 пиксель ниже)
        Rectangle groundCheck = new Rectangle(
            bounds.x,
            bounds.y - 1,
            bounds.width,
            1
        );
        return world.isColliding(groundCheck);
    }
}
