package io.github.оharvestrogalia.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.оharvestrogalia.camera.Camera;
import io.github.оharvestrogalia.entity.Player;
import io.github.оharvestrogalia.manager.EntityManager;
import io.github.оharvestrogalia.utils.PhysicSystem;
import io.github.оharvestrogalia.world.World;


public class GameScreen implements Screen {
    private Camera camera;
    private World world;
    private SpriteBatch batch;
    private Player player;
    private PhysicSystem physicSystem;
    private EntityManager entityManager;

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new Camera(426, 240);

        world = new World(camera.getCamera());
        world.loadMap("test.tmx");

        physicSystem = new PhysicSystem(world);
        entityManager = new EntityManager();

        player = new Player(100, 2000);
        entityManager.addEntity(player);

        camera.setTarget(player);
    }

    @Override
    public void render(float delta) {
        update(delta);
        render();
    }

    private void update(float delta) {
        player.update(delta);
        entityManager.updateEntities(delta, physicSystem);
        camera.update(delta);
    }

    private void render() {
        ScreenUtils.clear(Color.CLEAR);
        world.renderWorld();

        batch.setProjectionMatrix(camera.getCamera().combined);
        batch.begin();
        player.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        player.dispose();
        entityManager.dispose();
    }
}
