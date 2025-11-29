package io.github.оharvestrogalia.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.оharvestrogalia.entity.Player;
import io.github.оharvestrogalia.world.World;

import java.util.ArrayList;
import java.util.List;

public class PhysicSystem {
    public List<Rectangle> rectangleList = new ArrayList<>();

    public PhysicSystem(World world){
        this.rectangleList.addAll(world.getCollision());
    }

    public CollisionResult resolveMovement(PhysicsEntity entity, Vector2 desiredMovement) {
        Rectangle originalBounds = entity.getBounds();
        Vector2 allowed = new Vector2(desiredMovement);

        // Разрешаем движение по осям
        allowed = resolveAxis(allowed, originalBounds, true); // X
        allowed = resolveAxis(allowed, originalBounds, false); // Y

        // Определяем тип коллизии
        boolean onGround = isOnGround(new Rectangle(
            originalBounds.x + allowed.x,
            originalBounds.y + allowed.y,
            originalBounds.width,
            originalBounds.height
        ));

        boolean hitCeiling = (allowed.y < desiredMovement.y && desiredMovement.y > 0);
        boolean hitWall = (allowed.x < desiredMovement.x);

        // УБЕРИТЕ этот блок - сброс скорости должен быть в классе Player
        // if (onGround && desiredMovement.y < 0) {
        //     // Можно также сбросить velocity в entity здесь
        // }

        return new CollisionResult(allowed, onGround, hitCeiling, hitWall);
    }

    private Vector2 resolveAxis(Vector2 movement, Rectangle originalBounds, boolean isXAxis) {
        if (isXAxis && movement.x == 0) return movement;
        if (!isXAxis && movement.y == 0) return movement;

        Rectangle testBounds = new Rectangle(originalBounds);
        float step = isXAxis ? movement.x : movement.y;
        float direction = Math.signum(step);
        float remaining = Math.abs(step);
        float moved = 0;

        // Пошаговое движение с проверкой коллизий
        while (remaining > 0) {
            testBounds.set(originalBounds);

            if (isXAxis) {
                testBounds.x += (moved + 1) * direction;
            } else {
                testBounds.y += (moved + 1) * direction;
            }

            if (checkCollision(testBounds)) {
                break;
            }

            moved += 1;
            remaining -= 1;
        }

        Vector2 result = new Vector2(movement);
        if (isXAxis) {
            result.x = moved * direction;
        } else {
            result.y = moved * direction;
        }

        return result;
    }

    private boolean isOnGround(Rectangle bounds) {
        // Проверяем коллизию на 2 пикселя ниже для надежности
        Rectangle groundCheck = new Rectangle(bounds);
        groundCheck.y -= 2;
        return checkCollision(groundCheck);
    }

    private boolean checkCollision(Rectangle bounds) {
        for (Rectangle collider : rectangleList) {
            if (collider.overlaps(bounds)) {
                return true;
            }
        }
        return false;
    }
}
