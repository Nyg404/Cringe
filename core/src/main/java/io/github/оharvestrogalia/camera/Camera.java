package io.github.оharvestrogalia.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.оharvestrogalia.entity.Player;


public class Camera {
    public enum Mode {
        FOLLOW, MANUAL, LOCKED
    }

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Mode currentMode = Mode.FOLLOW;
    private Player target;
    private Vector2 smoothPosition = new Vector2();

    // Границы камеры (можно настраивать)
    private float minX = -Float.MAX_VALUE;
    private float maxX = Float.MAX_VALUE;
    private float minY = -Float.MAX_VALUE;
    private float maxY = Float.MAX_VALUE;

    public Camera(int width, int height) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);
        camera.setToOrtho(false, width, height);
    }

    public void update(float delta) {
        switch (currentMode) {
            case FOLLOW:
                if (target != null) {
                    smoothPosition.lerp(target.getPosition(), 15f * delta);

                    // Ограничиваем камеру границами
                    smoothPosition.x = MathUtils.clamp(smoothPosition.x, minX, maxX);
                    smoothPosition.y = MathUtils.clamp(smoothPosition.y, minY, maxY);

                    camera.position.set(smoothPosition.x, smoothPosition.y, 0);
                }
                break;

            case MANUAL:
                float speed = 200 * delta;
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += speed;
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= speed;
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed;
                break;

            case LOCKED:
                // Камера не двигается
                break;
        }

        camera.update();
    }

    public void setTarget(Player player) {
        this.target = player;
        if (player != null) {
            smoothPosition.set(player.getPosition());
            camera.position.set(smoothPosition.x, smoothPosition.y, 0);
        }
    }

    public void setWorldBounds(float minX, float maxX, float minY, float maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void applyViewport() {
        viewport.apply();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
    }

    public OrthographicCamera getCamera() { return camera; }
    public Mode getMode() { return currentMode; }
    public FitViewport getViewport() { return viewport; }
}
