package io.github.оharvestrogalia.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.org.apache.xpath.internal.operations.Mod;
import io.github.оharvestrogalia.entity.Player;

public class Camera {
    public enum Mode{
        FOLLOW,
        MANUAL,
        LOCKED
    }
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Mode currentMode = Mode.FOLLOW;
    private Player target; // цель слежения
    private Vector2 smoothPosition = new Vector2();

    public Camera(int width, int height) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);
    }
    public void update(float delta) {
        switch (currentMode) {
            case FOLLOW:
                if (target != null) {

                    smoothPosition.lerp(target.getPosition(), 5f * delta);
                    camera.position.set(smoothPosition.x, smoothPosition.y, 0);
                }
                break;

            case MANUAL:
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += 50;
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= 50;
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += 50;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= 50;
                break;

            case LOCKED:
                break;
        }

        camera.update();
    }

    public void setTarget(Player player){
        this.target = player;
        if(player != null){
            smoothPosition.set(player.getPosition());
            camera.position.set(smoothPosition.x, smoothPosition.y, 0);
        }
    }
    public void applyViewport(){
        viewport.apply();;
    }
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    public void setMode(Mode mode){
        this.currentMode = mode;
    }

    public OrthographicCamera getCamera() { return camera; }
    public Mode getMode() { return currentMode; }
}
