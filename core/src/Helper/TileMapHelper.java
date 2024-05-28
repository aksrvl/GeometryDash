package Helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHelper {

    private TiledMap tiledMap;

    public TileMapHelper() {

    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("maps/map.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
}