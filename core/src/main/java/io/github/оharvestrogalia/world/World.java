package io.github.оharvestrogalia.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.оharvestrogalia.world.CollisionProvider;

import java.util.ArrayList;
import java.util.List;

public class World implements CollisionProvider {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private List<Rectangle> collisionObjects = new ArrayList<>();

    public World(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void loadMap(String mapPath) {
        if (map != null) {
            map.dispose();
            collisionObjects.clear();
        }

        map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map);
        loadCollisionObjects(); // Загружаем коллизии при загрузке карты
    }

    public void renderWorld() {
        if (renderer != null) {
            renderer.setView(camera);
            renderer.render(); // рендерим все тайлы
        }
    }

    private void loadCollisionObjects() {
        collisionObjects.clear();
        MapLayer collisionLayer = map.getLayers().get("collision");

        if (collisionLayer != null) {
            for (MapObject object : collisionLayer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    collisionObjects.add(new Rectangle(rect));
                }
            }
        }
    }

    public void dispose() {
        if (map != null) {
            map.dispose();
        }
        collisionObjects.clear();
    }

    // CollisionProvider implementation
    @Override
    public List<Rectangle> getCollisionObjects() {
        return collisionObjects;
    }

    @Override
    public boolean isColliding(Rectangle bounds) {
        for (Rectangle collider : collisionObjects) {
            if (collider.overlaps(bounds)) {
                return true;
            }
        }
        return false;
    }

    public TiledMap getMap() {
        return map;
    }
}
