package io.github.оharvestrogalia.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmjMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.оharvestrogalia.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class World implements CollisionProvider{
    private List<Rectangle> collision = new ArrayList<>();
    private TiledMap map;
    private TiledMapRenderer  render;

    public List<Rectangle> getCollision() {
        return collision;
    }

    private OrthographicCamera camera;
    private SpriteBatch batch;


    public World(OrthographicCamera camera, SpriteBatch batch){
        this.camera = camera;
        this.batch = batch;
    }
   public void loadMap(){
       map = new TmxMapLoader().load("test.tmx");
       render = new OrthogonalTiledMapRenderer(map);
       addCollisionMaps();
   }

    public void renderWorld(){
        render.setView(camera);
        render.render();
        renderObjectsAuto();

    }

    private void renderObjectsAuto() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        MapLayer objectLayer = map.getLayers().get("coll");
        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tileObj = (TiledMapTileMapObject) object;

                TextureRegion texture = tileObj.getTile().getTextureRegion();
                float x = tileObj.getX();
                float y = tileObj.getY();
                float width = tileObj.getProperties().get("width", Float.class);
                float height = tileObj.getProperties().get("height", Float.class);

                batch.draw(texture, x, y, width, height);
            }
        }

        batch.end();
    }

    private void addCollisionMaps(){
        MapLayer mapLayer = map.getLayers().get("collision");
        if (mapLayer == null) return;

        for (MapObject object : mapLayer.getObjects()){
            if(object instanceof RectangleMapObject){
                RectangleMapObject rectObj = (RectangleMapObject) object;
                Rectangle rect = rectObj.getRectangle();

                // ПРОСТО КОПИРУЕМ ПРЯМО ИЗ TILED!
                collision.add(new Rectangle(rect));
            }
        }
    }

    @Override
    public List<Rectangle> getCollisionObjects() {
        return getCollision();
    }

    @Override
    public boolean isColliding(Rectangle bounds) {
        for (Rectangle collider : getCollision()) {
            if (collider.overlaps(bounds)) {
                return true;
            }
        }
        return false;
    }
}
