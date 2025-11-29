package io.github.оharvestrogalia.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.оharvestrogalia.camera.Camera;
import io.github.оharvestrogalia.entity.Player;
import io.github.оharvestrogalia.utils.CollisionResult;
import io.github.оharvestrogalia.utils.PhysicSystem;
import io.github.оharvestrogalia.world.World;

public class GameScreen implements Screen {
    private Camera camera;
    private FitViewport fitViewport;
    private World world;
    private SpriteBatch batch;
    private Player player;
    private PhysicSystem physicSystem;
    private ShapeRenderer shapeRenderer;
    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new Camera(426, 240);

        world = new World(camera.getCamera(), batch);
        world.loadMap();

        physicSystem = new PhysicSystem(world);

        player = new Player();
        player.getPosition().set(100,2000);
        camera.setTarget(player);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);
        camera.update(delta);

        // 1. Player рассчитывает velocity
        player.update(delta);

        // 2. PhysicsSystem разрешает движение
        Vector2 desiredMovement = player.getVelocity().cpy().scl(delta);
        CollisionResult result = physicSystem.resolveMovement(player, desiredMovement);

        // ОТЛАДКА:
        Gdx.app.log("PHYSICS", "Desired: " + desiredMovement + ", Allowed: " + result.allowedMovement);
        Gdx.app.log("PHYSICS", "Collisions: " + physicSystem.rectangleList.size());
        Gdx.app.log("PLAYER", "Pos: " + player.getPosition() + ", Vel: " + player.getVelocity());

        // 3. Применяем разрешенное движение
        player.onCollision(result);
        player.setPosition(player.getPosition().cpy().add(result.allowedMovement));


        // 4. Рендер
        world.renderWorld();
        batch.begin();
        player.render(batch);
        batch.end();

        Gdx.app.log("FRAME", "Player Y: " + player.getPosition().y);

    }



    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
