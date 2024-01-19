package islandGen;

import adt.Polygon;

public interface TerrainAssigner {
    void assignPolygonTerrain(Polygon polygon, double mesh_width, double mesh_height);
}
